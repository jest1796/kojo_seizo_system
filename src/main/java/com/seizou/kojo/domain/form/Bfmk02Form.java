package com.seizou.kojo.domain.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * ユーザー情報一覧のFormクラス
 * ただし、テストの【仮置き】です
 * @author K.Sakuma
 */

@Data
public class Bfmk02Form {

	private String affilicateId;		// 所属ID
	private String userId;				// ユーザーID
	private String userName;			// ユーザー名
	private String pass;				// パスワード
	private String rePass;				// パスワード再確認
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date expireDateFrom;		// 有効日（FROM）
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date expireDateTo;			// 有効日（TO）
	private String authDiv;				// 権限区分(ゲスト：1、一般：2、管理者：3)
	private Boolean watchAuthFlg;		// 参照権限フラグ（権限あり：true、権限なし：false）
	private Boolean oprAuthFlg;			// 操作権限フラグ（権限あり：true、権限なし：false）
}
