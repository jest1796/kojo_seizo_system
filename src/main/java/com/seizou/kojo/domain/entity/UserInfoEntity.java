package com.seizou.kojo.domain.entity;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザー情報のEntityクラス
 * @author M.Kimura
 * @author K.Sakuma
 */

@Data
@NoArgsConstructor
public class UserInfoEntity {

	private String facCd;				// 工場CD
	private String affilicateId;		// 所属ID
	private String affilicateName;		// 所属名
	private String userId;				// ユーザーID
	private String userName;			// ユーザー名
	private String pass;				// パスワード
	private String rePass;				// パスワード再確認
	private Date expireDateFrom;		// 適用日（FROM）
	private Date expireDateTo;			// 適用日（TO）
	private String authDiv;				// 権限区分
	private boolean watchAuthFlg;		// 参照権限フラグ
	private boolean oprAuthFlg;			// 操作権限フラグ
//	private boolean delFlg;				// 削除フラグ
//	private String createDiv;			// 登録区分
//	private Date createDate;			// 登録年月日
//	private String createId;			// 登録者ID
//	private Date updateDate;			// 更新年月日
//	private String updateId;			// 更新者ID
//	private String note;				// 備考
	private String divCd;				// 区分値
	private Date systemDate;			// システム日時

}
