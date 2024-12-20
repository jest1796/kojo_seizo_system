package com.seizou.kojo.domain.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.seizou.kojo.domain.dao.UserInfoDao;

/**
* ユーザー情報一覧 Repository
* @author K.Tomonari
*/
@Repository
public class Bfmk02Repository {

    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    NamedParameterJdbcTemplate namedJdbc;

    /**
     *  権限区分検索
     * @param affilicateId
     * @param userId
     * @return 権限区分
     * @throws DataAccessException
     */
    public String authDivSearch(String affilicateId, String userId) throws DataAccessException {

        // ユーザー権限区分の取得
        Map<String, Object> map = jdbc.queryForMap(
        		   "SELECT auth_div "          // 権限区分
        		+ " FROM user_info"
                + " WHERE affilicate_id = ?"   // 所属ID
        		+ " AND user_id = ?",          // ユーザーID
        		affilicateId,                  // 所属ID
        		userId);                       // ユーザーID

        // 権限区分
        return (String) map.get("auth_div");
    }

    /**
     *  ユーザー情報検索
     * @param userInfoDao
     * @return List<UserInfoDao>
     * @throws DataAccessException
     */
    public List<UserInfoDao> searchUserInfo(UserInfoDao userInfoDao) throws DataAccessException {
    	// SQLの定義
    	String sql = "SELECT user_info.affilicate_id" // 所属ID
        		+ " ,belonging.affilicate_name"       // 所属名
        		+ " ,user_info.user_id"               // ユーザーID
        		+ " ,user_info.user_name"             // ユーザー名
        		+ " ,user_info.auth_div"              // 権限区分
        		+ " ,user_info.watch_auth_flg"        // 参照権限フラグ
        		+ " ,user_info.opr_auth_flg"          // 操作権限フラグ
        		+ " ,user_info.expire_date_from"      // 有効期限日（FROM）
        		+ " ,user_info.expire_date_to"        // 有効期限日（TO）
        		+ " ,user_info.pass"                  // パスワード
        		+ " FROM user_info" 
        		+ " INNER JOIN belonging"
        		+ " ON user_info.affilicate_id = belonging.affilicate_id"
        		+ " WHERE user_info.del_flg = '0'";
    	
        // Mapにパラメータを設定
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        // 所属IDの条件
        if(!(userInfoDao.getAffilicateId().isEmpty())) {
        	sql += " AND user_info.affilicate_id = :affilicateId";
        	parameters.put("affilicateId", userInfoDao.getAffilicateId());
        }
        // ユーザーIDの条件
        if(!(userInfoDao.getUserId().isEmpty())) {
        	sql += " AND user_info.user_id = :userId";
        	parameters.put("userId", userInfoDao.getUserId());
        }
        // ユーザー名の条件
        if(!(userInfoDao.getUserName().isEmpty())) {
        	sql += " AND user_info.user_name LIKE :userName";
        	String userName = "%" + userInfoDao.getUserName() + "%";
        	parameters.put("userName", userName);
        }
        // 権限区分の条件
        if(!(userInfoDao.getAuthDiv().isEmpty())) {
        	sql += " AND user_info.auth_div IN(:authDiv)";
        	parameters.put("authDiv", userInfoDao.getAuthDiv());
        }
        // 有効期限日（FROM）の条件
        if(!(userInfoDao.getExpireDateFrom().isEmpty())) {
        	sql += " AND user_info.expire_date_from = :expireDateFrom";
        	parameters.put("expireDateFrom", userInfoDao.getExpireDateFrom());
        }
        // 有効期限日（TO）の条件
        if(!(userInfoDao.getExpireDateTo().isEmpty())) {
        	sql += " AND user_info.expire_date_to = :expireDateTo";
        	parameters.put("expireDateTo", userInfoDao.getExpireDateTo());
        }
        
        // SQLの定義
        sql += " ORDER BY user_info.affilicate_id , user_info.user_id";
        
        // SQL実行
        List<Map<String, Object>> getList = namedJdbc.queryForList(sql, parameters);
        
        // 結果返却用の変数
        List<UserInfoDao> userInfoDaoList = new ArrayList<>();
        
        //DateからStringに変換に利用
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");

        // 取得したデータを結果返却用のListに格納
        for (Map<String, Object> map : getList) {
        	
            //UserInfoDaoインスタンスの生成
        	userInfoDao = new UserInfoDao();
        	
        	List<String> authDiv = new ArrayList<String>();
        	// 権限区分を名称に変換して追加
        	authDiv.add(divNameSearch("bfm1", "USRK", "00",
        			(String) map.get("auth_div")));

            // UserInfoDaoインスタンスに取得したデータをセット
        	userInfoDao.setAffilicateId((String) map.get("affilicate_id"));       // 所属ID
        	userInfoDao.setAffilicateName((String) map.get("affilicate_name"));   // 所属名
        	userInfoDao.setUserId((String) map.get("user_id"));                   // ユーザーID
        	userInfoDao.setUserName((String) map.get("user_name"));               // ユーザー名
        	userInfoDao.setAuthDiv(authDiv);                                      // 権限区分
        	userInfoDao.setWatchAuthFlg((Boolean) map.get("watch_auth_flg"));     // 参照権限フラグ
        	userInfoDao.setOprAuthFlg((Boolean) map.get("opr_auth_flg"));         // 操作権限フラグ
        	userInfoDao.setExpireDateFrom((String) 
        			dateFormat.format(map.get("expire_date_from")));              // 有効期限日（FROM）
        	if(map.get("expire_date_to") != null) {
        		userInfoDao.setExpireDateTo((String) 
        			dateFormat.format(map.get("expire_date_to")));                // 有効期限日（TO）
        	}
        	userInfoDao.setPass((String) map.get("pass"));                        // パスワード
        	
            //結果返却用のListに追加
            userInfoDaoList.add(userInfoDao);
        }

        return userInfoDaoList;
    }

    /**
     *  ユーザー情報削除
     * @param userInfoDao
     * @return 処理件数
     * @throws DataAccessException
     */
    public int deleteUserInfo(UserInfoDao userInfoDao) throws DataAccessException {

    	// 削除フラグを'1'にして、登録区分を'D'に更新
        int rowNumber = jdbc.update("UPDATE user_info"
                + " SET"
                + " del_flg = '1',"             // 削除フラグ
                + " update_id = ?,"             // 更新者ID
                + " update_date = DATE(NOW())," // 更新年月日
                + " create_div = 'D'"           // 登録区分
                + " WHERE affilicate_id = ?"    // 所属ID
                + " AND user_id = ?"            // ユーザーID
                + " AND fac_cd = ?",            // 工場CD
                userInfoDao.getUserId(),        // ユーザーID
                userInfoDao.getAffilicateId(),  // 所属ID
                userInfoDao.getUserId(),        // ユーザーID
                userInfoDao.getFacCd());        // 工場CD

        return rowNumber;
    }
    
    /**
     *  区分名称検索
     * @param facCd
     * @param bigFuncCd
     * @param middleFuncCd
     * @param divCd
     * @return 区分名称
     * @throws DataAccessException
     */
    public String divNameSearch(String facCd, 
    		String bigFuncCd, String middleFuncCd, String divCd) throws DataAccessException {

        // 区分名称検索の取得
        Map<String, Object> map = jdbc.queryForMap(
        		   "SELECT div_name "       // 区分名称
        		+ " FROM division"             
                + " WHERE fac_cd = ?"       // 工場CD
        		+ " AND big_func_cd = ?"    // 大項目
        		+ " AND middle_func_cd = ?" // 中項目
        		+ " AND div_cd = ?",        // 区分値
        		facCd,                      // 工場CD
        		bigFuncCd,                  // 大項目
        		middleFuncCd,               // 中項目
        		divCd);                     // 区分値

        // 区分名称
        return (String) map.get("div_name");
    }
}