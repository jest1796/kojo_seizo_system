package com.seizou.kojo.domain.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 部署情報Dao
 * @author N.Nshi
 */
@Data
public class DepartmentDto {
	private String 	facCd;				// 工場CD
	private String 	affilicateId;		// 所属ID
	private String 	affilicateName;		// 部署名
	private String 	affilicateNameR;	// 部署名略称
	private long	affilicateCount;	// 所属人数
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
	private List<String> affilicateIdList;	// 削除する所属IDのリスト
}
