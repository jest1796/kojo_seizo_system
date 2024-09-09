package com.seizou.kojo.domain.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 部署情報Form
 * @author N.Nishi
 */
@Data
public class DepartmentForm {
	
		private String 	facCd;				// 工場CD
		private String 	affilicateId;		// 所属ID
		private String 	affilicateName;		// 部署名
		private String 	affilicateNameR;	// 部署名略称
		private Long	affilicateCount;	// 所属人数
		private Date 	applyStrtDate;		// 適用日(FROM)
		private String	applyStrtDateStr;	// String型 適用日(FROM)
		private Date 	applyFinDate;		// 適用日(TO)
		private String	applyFinDateStr;	// String型 適用日(TO)
		private boolean delFlg;				// 削除フラグ
		private String 	createDiv;			// 登録区分
		private Date 	createDate;			// 登録年月日
		private String 	createId;			// 登録者ID
		private Date 	updateDate;			// 更新年月日
		private String 	updateId;			// 更新者ID
		private String  message;        // メッセージ
		private String 	note;				// 備考
		private List<String> checkbox = new ArrayList<String>();	// 削除する所属IDのリスト
}
