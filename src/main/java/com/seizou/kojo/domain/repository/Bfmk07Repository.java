package com.seizou.kojo.domain.repository;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.seizou.kojo.domain.dao.DepartmentDao;
import com.seizou.kojo.domain.form.DepartmentForm;

/**
 * 部署情報 Repository
 * @author N.Nishi
 */
@Repository
public class Bfmk07Repository {

	@Autowired
    JdbcTemplate jdbc;
    
    
    /**
     * 部署情報取得
     * @param
     * @return departmentFormList
     */
    public List<DepartmentForm> selectAll() {
    	
    	// 結果返却用の変数
    	List<DepartmentForm> departmentFormList = new ArrayList<>();
    	
    	// SQLの定義 
    	String sql =  "SELECT "
	    			+ "     b.affilicate_id	"	
	    			+ "    ,b.affilicate_Name "
	    			+ "    ,b.affilicate_Name_r "
	    			+ "    ,COALESCE(COUNT(u.affilicate_id), 0) as affilicate_count"
	    			+ "    ,b.apply_strt_date "
	    			+ "    ,b.apply_fin_date "
	    			+ "FROM belonging AS b "
	    			+ "LEFT JOIN user_info AS u "
	    			+ "ON b.affilicate_id = u.affilicate_id "
	    			+ "WHERE b.del_flg = 0 "
	    			+ "GROUP BY b.affilicate_id,b.affilicate_Name,b.affilicate_Name_r "
	    			+ "		,b.apply_strt_date,b.apply_fin_date	"
	    			+ "ORDER BY b.apply_strt_date,b.affilicate_id";
    	
    	// SQLの実行
    	List<Map<String, Object>> getList = jdbc.queryForList(sql);		
    	
    	// DateからStringへ変換に利用
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
    	

        // 取得したデータを結果返却用のListに格納
        for (Map<String, Object> map : getList) {
        	
        	// DepartmentFormインスタンスの作成
        	DepartmentForm departmentForm = new DepartmentForm();
        
	    	departmentForm.setAffilicateId((String) map.get("affilicate_id"));			 //	所属ID			
	    	departmentForm.setAffilicateName((String) map.get("affilicate_name"));       // 所属名
	    	departmentForm.setAffilicateNameR((String) map.get("affilicate_name_r"));    // 所属名略称
	    	departmentForm.setAffilicateCount((Long) map.get("affilicate_count"));       // 所属人数
        	departmentForm.setApplyStrtDateStr((String)									 // 適用開始日
        			dateFormat.format(map.get("apply_strt_date")));						 
        	if(map.get("apply_fin_date") != null) {										 // 適用終了日
        		departmentForm.setApplyFinDateStr((String) 
        			dateFormat.format(map.get("apply_fin_date")));               
        	}

	    	
	    	// 結果返却用のListに追加
	    	departmentFormList.add(departmentForm);
        }
    	return departmentFormList;  	
    }

	/**
	 * 登録
	 * @param departmentDao
	 * @return departmentDao
	 */
	public DepartmentDao regist(DepartmentDao departmentDao) {
		try {
			
			String sql = "INSERT INTO belonging("
					+ "	 	fac_cd"
					+ "	   ,affilicate_id"
					+ "    ,affilicate_name"
					+ "    ,affilicate_name_r"
					+ "    ,apply_strt_date"
					+ "    ,apply_fin_date"
					+ "    ,del_flg"
					+ "    ,create_div"
					+ "    ,create_date"
					+ "    ,create_id"
					+ "    ,update_date"
					+ "    ,update_id"
					+ "    ) "
					+ "   VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			
			jdbc.update(sql
						,"bfm1"
						,departmentDao.getAffilicateId()
						,departmentDao.getAffilicateName()
						,departmentDao.getAffilicateNameR()
						,departmentDao.getApplyStrtDate()
						,departmentDao.getApplyFinDate()
						,"0"
						,"C"
						,LocalDateTime.now()
						,"admin"
						,LocalDateTime.now()
						,"admin");
		} catch (DuplicateKeyException e) {
			departmentDao.setMessage("msdver003");
		
		} catch (Exception e) {
			departmentDao.setMessage("msdver007");
		}					
		return departmentDao;
	}

	/**
	 * 削除
	 * @param affilicateId
	 * @return returnDao
	 */
	public DepartmentDao delete(String affilicateId) {

		// 戻り値を宣言
		DepartmentDao returnDao = new DepartmentDao();

		String sql = "UPDATE 	  " 
				+"      belonging "
				+"    SET         "
				+"	    del_flg = 1 " 
				+"    WHERE "
				+"	    affilicate_id = ?";
		
		jdbc.update(sql,affilicateId);
		
		return returnDao;
	}

	/**
	 * 権限区分を取得する
	 * @param userId
	 * @return 権限区分
	 */
	public String getAuthDiv(String userId) {

		// 戻り値を宣言
		String authDiv = "";

		// 権限区分の検索
		try {
			String sql =  "SELECT "
	    			+ "    	　 ui.auth_div "	
	    			+ "    FROM user_info AS ui "
	    			+ "    WHERE user_id = ?";

			// SQLの実行
			Map<String, Object> authDivMap = jdbc.queryForMap(sql, userId);
			authDiv = (String)authDivMap.get("auth_div");
		} catch (Exception e) {
		}
		return authDiv;
	}
	
	
	/**
	 * 所属人数を取得する
	 * @param check
	 * @return 所属人数
	 */
	public long getAffilicateCount(String check) {

		// 戻り値を宣言
		long count = 0;

		// 所属人数の検索
		try {
			String sql = "SELECT "
	    			+ 	  "COUNT(ui.affilicate_id) AS count "	
	    			+ 	  "FROM user_info AS ui "
	    			+ 	  "WHERE ui.affilicate_id = ?";
			
			// SQLの実行
			Map<String, Object> authDivMap = jdbc.queryForMap(sql, check);
			count = (long)authDivMap.get("count");
		} catch (Exception e) {
		}
		return count;
	}
}
