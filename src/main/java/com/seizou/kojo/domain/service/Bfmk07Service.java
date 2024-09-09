package com.seizou.kojo.domain.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.seizou.kojo.domain.dao.DepartmentDao;
import com.seizou.kojo.domain.dto.CommonDto;
import com.seizou.kojo.domain.dto.DepartmentDto;
import com.seizou.kojo.domain.form.DepartmentForm;
import com.seizou.kojo.domain.repository.Bfmk07Repository;

/**
 * 部署情報 Service
 * @author N.Nishi
 */
@Transactional
@Service
public class Bfmk07Service {
	
	
	@Autowired
    Bfmk07Repository repository;

	
	/**
	 * 部署一覧表示
	 * @return 部署一覧データ
	 */
	public List<DepartmentForm> selectAll() {

		return repository.selectAll();	
	}


	/**
	 * 部署登録
	 * @param departmentForm
	 * @return DepartmentDto
	 */
	public DepartmentDto regist(DepartmentForm departmentForm) {

		// 入力内容チェック
		DepartmentDto returnDto = checkValidate(departmentForm);

		// エラーメッセージがない場合登録実行へ
		if (returnDto.getMessage() == null) {
			DepartmentDao returnDao = new DepartmentDao();

			// Daoに値を詰め替え
			returnDao.setFacCd("bfm1");
			returnDao.setAffilicateId(departmentForm.getAffilicateId());
			returnDao.setAffilicateName(departmentForm.getAffilicateName());
			returnDao.setAffilicateNameR(departmentForm.getAffilicateNameR());
			
			// StringからDateに変換に利用
    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			try {
				returnDao.setApplyStrtDate(dateFormat.parse(departmentForm.getApplyStrtDateStr()));
			} catch (ParseException e) {
				// 何もしない
			}

			try {
				returnDao.setApplyFinDate(dateFormat.parse(departmentForm.getApplyFinDateStr()));
			} catch (ParseException e) {
				returnDao.setApplyFinDate(null);
			}

			// 登録実行
			returnDao = repository.regist(returnDao);

			// データベース登録エラーチェック
			if (returnDao.getMessage() != null) {
				returnDto.setMessage(returnDao.getMessage());
			}
		}
		return returnDto;
	}

	/**
	 * 削除
	 * @param departmentForm
	 * @return DepartmentDto
	 */
	public DepartmentDto delete(DepartmentForm departmentForm, CommonDto commonDto) {

		// 戻り値を宣言
		DepartmentDto returnDto = new DepartmentDto();

		// チェックボックスの値を確認
		if (departmentForm.getCheckbox().isEmpty() || departmentForm.getCheckbox().size() == 0) {
			returnDto.setMessage("msdver008");
			return returnDto;
		}

		// 権限区分の取得
		String authDiv = repository.getAuthDiv(commonDto.getUserId());
		
		// admin権限以外の場合
		if (!("4".equals(authDiv))) {

			// チェックボックスのチェック分、ループ
			for (String check : departmentForm.getCheckbox()) {
				
				// 所属人数が0人でない場合、エラーメッセージを出す
				if (repository.getAffilicateCount(check) != 0) {
					returnDto.setMessage("msdver009");
					return returnDto;
				}
			}
		}

		// 削除実行
		for (String affilicateId : departmentForm.getCheckbox()) {
			repository.delete(affilicateId);
		}
		// 削除完了メッセージ
		returnDto.setMessage("msdvsu002");

		return returnDto;
	}
	
	
	 /**
	 * 入力フォームのチェック
	 * @param departmentForm
	 * @return DepartmentDto
	 */
	public DepartmentDto checkValidate(DepartmentForm departmentForm) {
		 DepartmentDto departmentDto = new DepartmentDto();
		 
		// 所属IDが空白の場合
	    if(StringUtils.isEmpty(departmentForm.getAffilicateId())) {
	    		departmentDto.setMessage("msdver001");
	    		return departmentDto;
	    }
	    	
    	// 所属部署が空白の場合
    	if(StringUtils.isEmpty(departmentForm.getAffilicateName())) {
    		departmentDto.setMessage("msdver002");
    		return departmentDto;
    	}	

    	// 部署名略称が入力されていない場合
    	if(StringUtils.isEmpty(departmentForm.getAffilicateNameR())) {
    		// 部署名が20文字以上の場合
    		if(departmentForm.getAffilicateName().length() >= 20) {
    			departmentDto.setMessage("msdver004");
    			return departmentDto;
    		} else {
    			departmentForm.setAffilicateNameR(departmentForm.getAffilicateName());
    		}
    	}	
    	
    	// 適用日(FROM)が空白の場合
    	if(StringUtils.isEmpty(departmentForm.getApplyStrtDateStr())) {
    		departmentDto.setMessage("msdver005");
    		return departmentDto;
    	}
    	
    	// 適用日(To)と適用日(From)が入力されている場合
    	if(!(StringUtils.isEmpty(departmentForm.getApplyFinDateStr())
    		|| StringUtils.isEmpty(departmentForm.getApplyStrtDateStr()))){

    		// StringからDateに変換に利用
    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		try {
    			Date ApplyStrtDate = dateFormat.parse(
    					departmentForm.getApplyStrtDateStr());   // 有効期限日（FROM)
    			Date ApplyFinDate = dateFormat.parse(
    					departmentForm.getApplyFinDateStr());    // 有効期限日（TO)
    			
    			// 有効期限日（FROM)が有効期限日（TO)より未来の場合
    			if(ApplyStrtDate.after(ApplyFinDate)) {
    				departmentDto.setMessage("msdver006");
    			}						
    		}catch (ParseException e) {
    			e.printStackTrace();
    		}
    	}
		return departmentDto;
	 }
	
	 
	/**
	 * 権限区分の取得
	 * @param commonDto
	 * @return boolean
	 */
	public boolean getAuthDiv(CommonDto commonDto) {

		// ユーザー権限の取得実行
		String authDiv = repository.getAuthDiv(commonDto.getUserId());

		
		// 操作権限の有無を判定
		// adminの場合
		if ("4".equals(authDiv)) {

			return true;

		// 所属IDが「総務部」かつ、権限区分が「一般」以上の場合
		} else if (!"1".equals(authDiv) && "us1".equals(commonDto.getAffId())) {

			return true;

		} else {

			return false;
		}
	}
}	