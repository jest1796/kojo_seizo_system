package com.seizou.kojo.domain.dto;

import java.util.Date;
import java.util.List;

import com.seizou.kojo.domain.form.Bfmk02Form;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザー情報 DTO
 * @author K.Sakuma
 */
@Data
@NoArgsConstructor
public class UserInfoDto {

	private String   displayId;	      // 画面ID
	private String   exDisplayId;     // 遷移元画面ID
	private String   message;	      // メッセージID
	private String   facCd;		      // 工場CD
	private String   affilicateId;    // 所属ID
	private String   affilicateName;  // 所属名
	private String   userId;		  // ユーザーID
	private String   userName;	      // ユーザー名
	private String   pass;			  // パスワード
	private String   rePass;	      // パスワード再確認
	private List<String> authDivList; // 権限区分
	private String   expireDateFrom;  // 適用日（FROM）
	private String   expireDateTo;	  // 適用日（TO）
	private Date     systemDate;	  // システム日時
	private String   authDiv;		  // 権限区分(ゲスト：1、一般：2、管理者：3)
	private String   authDivLoginUser;// 権限区分(ゲスト：1、一般：2、管理者：3、admin：4)：ログインユーザー
	private Boolean  watchAuthFlg;	  // 参照権限フラグ（権限あり：true、権限なし：false）
	private Boolean  oprAuthFlg;      // 操作権限フラグ（権限あり：true、権限なし：false）
	private Bfmk02Form bfmk02Form;	  // ユーザー情報一覧フォーム	
}