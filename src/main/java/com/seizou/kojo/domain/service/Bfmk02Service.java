package com.seizou.kojo.domain.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.seizou.kojo.domain.dao.UserInfoDao;
import com.seizou.kojo.domain.dto.CommonDto;
import com.seizou.kojo.domain.dto.UserInfoDto;
import com.seizou.kojo.domain.form.SearchInfoForm;
import com.seizou.kojo.domain.repository.Bfmk02Repository;

/**
* ユーザー情報一覧 Service
* @author K.Tomonari
*/
@Transactional
@Service
public class Bfmk02Service {

    @Autowired
    Bfmk02Repository repo;

    /**
     * 初期画面
     * @param commonDto
     * @return UserInfoDto
     */
    public UserInfoDto init(CommonDto commonDto) {
    	
    	// 結果返却用の変数
    	UserInfoDto userInfoDto = new UserInfoDto();
    	// 権限区分を検索
    	String authDiv = repo.authDivSearch(commonDto.getAffId(), commonDto.getUserId());
    	
    	// ユーザーがゲストまたは一般の場合
        if(authDiv.equals("1") || authDiv.equals("2")) {
        	userInfoDto.setMessage("mszumd001");   // 参照・更新権限エラーメッセージ
        }
        
        // 権限区分を格納
        userInfoDto.setAuthDiv(authDiv);
        
        return userInfoDto;
    }
    
    /**
     *  検索
     * @param searchInfoForm
     * @return List<UserInfoDto>
     */
    public List<UserInfoDto> search(SearchInfoForm searchInfoForm) {
    	
    	// 入力チェック
    	UserInfoDto userInfoDto = checkValidate(searchInfoForm);
    	
		// 結果返却用の変数
		List<UserInfoDto> userInfoDtoList = new ArrayList<>();
    	
    	// メッセージが空の場合
    	if(userInfoDto.getMessage() == null) {
    		UserInfoDao inputDao = new UserInfoDao();
    	
    		inputDao.setAffilicateId(searchInfoForm.getAffilicateId());     // 所属ID
    		inputDao.setUserId(searchInfoForm.getUserId());                 // ユーザーID
    		inputDao.setUserName(searchInfoForm.getUserName());             // ユーザー名
    		inputDao.setAuthDiv(searchInfoForm.getAuthDiv());               // 権限区分
    		inputDao.setExpireDateFrom(searchInfoForm.getExpireDateFrom()); // 適用日（FROM）
    		inputDao.setExpireDateTo(searchInfoForm.getExpireDateTo());     // 適用日（TO）
        
    		// 全件取得
    		List<UserInfoDao> userInfoDaoList = repo.searchUserInfo(inputDao);
    	
    		// 取得したデータを結果返却用のListに格納していく
    		for (UserInfoDao dao : userInfoDaoList) {

    			//Userインスタンスの生成
    			UserInfoDto  dto = new UserInfoDto();
        	        	
    			// UserInfoDtoインスタンスに取得したデータをセットする
    			dto.setAffilicateId(dao.getAffilicateId());             // 所属ID
    			dto.setAffilicateName(dao.getAffilicateName());         // 所属名
    			dto.setUserId(dao.getUserId());                         // ユーザーID
    			dto.setUserName(dao.getUserName());                     // ユーザー名
    			dto.setAuthDivList(dao.getAuthDiv());                   // 権限区分
    			dto.setWatchAuthFlg(dao.getWatchAuthFlg());             // 参照権限フラグ
    			dto.setOprAuthFlg(dao.getOprAuthFlg());                 // 操作権限フラグ
    			dto.setExpireDateFrom(dao.getExpireDateFrom());         // 有効期限日（FROM）
    			dto.setExpireDateTo(dao.getExpireDateTo());             // 有効期限日（TO）
    			dto.setPass(dao.getPass());                             // パスワード
        	
    			//結果返却用のListに追加
    			userInfoDtoList.add(dto);
    		}
    	} else {
			//結果返却用のListに追加
			userInfoDtoList.add(userInfoDto);
    	}
    	
        return userInfoDtoList;
    }
    
    /**
     *  削除
     * @param deleteUser
     * @param affilicateId
     * @param userId
     * @param facCd
     * @return エラーメッセージ
     */
    public String delete(List<String> deleteUser, 
    		List<String> affilicateId,
    		List<String> userId,
    		List<String> facCd) {
        
    	// メッセージ
    	String message = null;
    	if(deleteUser == null) {
    		message = "mszumd005";   // 
    	} else {
    		// チェックされたユーザーごとに削除処理を実行
    		for(String str : deleteUser) {
    			UserInfoDao dao = new UserInfoDao();
    			int num = Integer.parseInt(str);
    		
    			dao.setAffilicateId(affilicateId.get(num)); // 所属ID
    			dao.setUserId(userId.get(num));             // ユーザーID
    			dao.setFacCd(facCd.get(num));               // 工場CD

    			// 削除処理の実行
    			int rowNumber = repo.deleteUserInfo(dao);

    			// 削除できなかった場合
    			if (rowNumber <= 0) {
    				// update成功
    				message = "mszumd005";   // 
    			}
    		}
    	}

        return message;
    }
    
    /**
     *  入力チェック
     * @param searchInfoForm
     * @return UserInfoDto
     */
    public UserInfoDto checkValidate(SearchInfoForm searchInfoForm) {
    	UserInfoDto userInfoDto = new UserInfoDto();
        
    	// 年月日のパターン
	    Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    	// 有効期限日（FROM）が入力されている場合
    	if(!StringUtils.isEmpty(searchInfoForm.getExpireDateFrom())) {
    	    //判定するパターンを生成
    	    Matcher matcher = pattern.matcher(searchInfoForm.getExpireDateFrom());

    	    // パターンに一致しない場合
    	    if(!matcher.find()) {
        		userInfoDto.setMessage("mszumd002");
        		return userInfoDto;
    	    }
    	}
    	
    	// 有効期限日（TO）が入力されている場合
    	if(!StringUtils.isEmpty(searchInfoForm.getExpireDateTo())) {
    	    //判定するパターンを生成
    	    Matcher matcher = pattern.matcher(searchInfoForm.getExpireDateTo());

    	    // パターンに一致しない場合
    	    if(!matcher.find()) {
        		userInfoDto.setMessage("mszumd003");
        		return userInfoDto;
    	    }
    	}
    	
    	// 有効期限日（FROM）と有効期限日（TO）が入力されている場合
    	if(!(StringUtils.isEmpty(searchInfoForm.getExpireDateFrom())
    	   || StringUtils.isEmpty(searchInfoForm.getExpireDateTo()))) {
        	//StringからDateに変換に利用
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            try {
				Date expireDateFromDate = dateFormat.parse(
						searchInfoForm.getExpireDateFrom()); // 有効期限日（FROM）
				Date expireDateToDate = dateFormat.parse(
						searchInfoForm.getExpireDateTo());   // 有効期限日（TO）
				
				// 有効期限日（FROM）が有効期限日（TO）より未来日の場合
				if(expireDateFromDate.after(expireDateToDate)) {
					userInfoDto.setMessage("mszumd004");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
    	
    	return userInfoDto;
    }
}