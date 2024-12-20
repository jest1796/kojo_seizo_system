package com.seizou.kojo.domain.controller;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seizou.kojo.domain.dto.CommonDto;
import com.seizou.kojo.domain.dto.UserInfoDto;
import com.seizou.kojo.domain.form.Bfmk01Form;
import com.seizou.kojo.domain.form.Bfmk02Form;
import com.seizou.kojo.domain.service.Bfmk01Service;

/**
* ユーザー情報 Controller
* @author K.Sakuma
*/
@Controller
@RequestMapping("/b-forme_Kojo")
public class Bfmk01Controller {
	
	@Autowired
	Bfmk01Service bfmk01Service;
	
	// メッセージ取得用の変数を宣言
	private static final String MESSAGES_NAME = "resources_ja";
	private static ResourceBundle mes = ResourceBundle.getBundle(MESSAGES_NAME);
	
	/**
	 * 初期画面
	 * @param bfmk01Form
	 * @param model
	 * @return bfmk01View
	 */
	@GetMapping("/pc/201")
	// CommonDtoの渡し方に関して未決定のため、仮置きで対応。
	// 遷移前画面（メニュー/ユーザー情報一覧）の実装がまだのためにUserInfoDtoを渡せないので、引数からUserInfoDtoを削除した仮置きメソッド。
//	public String initUserInfo(@ModelAttribute Bfmk01Form bfmk01Form, UserInfoDto userInfoDto, CommonDto commonDto, Model model) {
	public String initUserInfo(@ModelAttribute Bfmk01Form bfmk01Form, Model model) {
		
		System.out.println("コントローラー：initUserInfoメソッド開始");
		
// --------------------------------------------------------------------------------------
// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓【仮置き】↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
// --------------------------------------------------------------------------------------	
		// 仮置き：共通DTO
		// ログインユーザーを設定
		// 登録と更新画面の切り替え（登録：遷移前画面ID：bfkt02、更新：遷移前画面ID：bfmk02）
		// 遷移前画面ID（bfkt02：「メニュー」画面→ユーザー情報登録画面へ、 bfmk02：「ユーザー情報一覧」画面→ユーザー情報更新画面へ）
		// 								　　　画面ID, 画面名, 遷移前画面ID, メッセージ, 工場CD, 所属ID, 所属名, 		ユーザーID, 	ユーザー名
//		// guest
//		System.out.println("コントローラー：ログインユーザー：guest");
//		CommonDto commonDto = new CommonDto(null,  null,  "bfmk02",   null,   "bfm1",  "hk1",  null,	"hkgt000", 	null);
//		// gene
//		System.out.println("コントローラー：ログインユーザー：gene");
//		CommonDto commonDto = new CommonDto(null,  null,  "bfmk02",   null,   "bfm1",  "it1",  null,	"itns000",  null);
		// boss
		System.out.println("コントローラー：ログインユーザー：boss");
		CommonDto commonDto = new CommonDto(null,  null,  "bfmk02",   null,   "bfm1",  "us1",  null,	"uskr000",  null);
//		// admin
//		System.out.println("コントローラー：ログインユーザー：admin");
//		CommonDto commonDto = new CommonDto(null,  null,  "bfmk02",   null,   "bfm1",  "all",  null,	"al00000", 	null);
		
		// 仮置き：「メニュー」画面から渡されてくるUserInfoDto
		UserInfoDto userInfoDtoRegister = new UserInfoDto();
		
		// 仮置き：「ユーザー情報一覧」画面から渡されてくるUserInfoDto
		UserInfoDto userInfoDtoUpdate = new UserInfoDto();
		// 仮置き：「ユーザー情報一覧」画面から渡されてくるユーザー情報一覧フォーム
		Bfmk02Form bfmk02FormUpdate = new Bfmk02Form();
//		// ユーザー名：guestを更新する仮定
//		System.out.println("コントローラー：更新対象のユーザー名：guest");
//		userInfoDtoUpdate.setFacCd("bfm1");					// 工場CD
//		userInfoDtoUpdate.setAffilicateId("hk1");			// 所属ID
//		userInfoDtoUpdate.setUserId("hkgt000");				// ユーザーID
//		userInfoDtoUpdate.setBfmk02Form(bfmk02FormUpdate);	// ユーザー情報一覧フォーム	
//		// ユーザー名：geneを更新する仮定
//		System.out.println("コントローラー：更新対象のユーザー名：gene");
//		userInfoDtoUpdate.setFacCd("bfm1");					// 工場CD
//		userInfoDtoUpdate.setAffilicateId("it1");			// 所属ID
//		userInfoDtoUpdate.setUserId("itns000");				// ユーザーID
//		userInfoDtoUpdate.setBfmk02Form(bfmk02FormUpdate);	// ユーザー情報一覧フォーム	
//		// ユーザー名：bossを更新する仮定
//		System.out.println("コントローラー：更新対象のユーザー名：boss");
//		userInfoDtoUpdate.setFacCd("bfm1");					// 工場CD
//		userInfoDtoUpdate.setAffilicateId("us1");			// 所属ID
//		userInfoDtoUpdate.setUserId("uskr000");				// ユーザーID
//		userInfoDtoUpdate.setBfmk02Form(bfmk02FormUpdate);	// ユーザー情報一覧フォーム
//		// ユーザー名：adminを更新する仮定
//		System.out.println("コントローラー：更新対象のユーザー名：admin");
//		userInfoDtoUpdate.setFacCd("bfm1");					// 工場CD
//		userInfoDtoUpdate.setAffilicateId("all");			// 所属ID
//		userInfoDtoUpdate.setUserId("al00000");				// ユーザーID
//		userInfoDtoUpdate.setBfmk02Form(bfmk02FormUpdate);	// ユーザー情報一覧フォーム	
		// ユーザー名：ああああを更新する仮定
		System.out.println("コントローラー：更新対象のユーザー名：ああああ");
		userInfoDtoUpdate.setFacCd("bfm1");					// 工場CD
		userInfoDtoUpdate.setAffilicateId("us1");			// 所属ID
		userInfoDtoUpdate.setUserId("usns001");				// ユーザーID
		userInfoDtoUpdate.setBfmk02Form(bfmk02FormUpdate);	// ユーザー情報一覧フォーム	
// --------------------------------------------------------------------------------------
// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑【仮置き】↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
// --------------------------------------------------------------------------------------
		
		// クリアボタン及び登録ボタンの操作可否の設定（true：有効、false：無効）
		boolean clearBtnOpeFlg = true;
		boolean registerBtnOpeFlg = true;
		// ユーザー情報更新時、ユーザーID、ユーザー名、有効日(FROM)のテキストボックス編集可否の設定（true：不可能、false：可能）
		boolean updateTextBoxFlg = true;
		// 登録ボタンが「登録機能」か「更新機能」かの設定（true：登録、false：更新）
		boolean registerOrUpdateBtnFlg = true;
		// 所属ID、ユーザーIDの操作可否（true：操作不可、false：操作可能）
		boolean affilicateIdOrUserIdTextboxFlg = true;
		
		// 「登録画面の表示」か「更新画面の表示」かで場合分け
		if(commonDto.getTmpDispId().equals("bfkt02")) {
			// 登録の場合
			System.out.println("コントローラー：initUserInfoメソッド：if：「ユーザー情報」登録画面");
			
			// サービスクラスのパブリックメソッド「初期画面」を呼び出す。
			UserInfoDto userInfoDtoRegisterResult = bfmk01Service.init(commonDto, userInfoDtoRegister);
			
			// ログインユーザーの権限区分により場合分けして処理
			boolean loginUserAuthProcessResult = loginUserAuthProcess(userInfoDtoRegisterResult, bfmk01Form, clearBtnOpeFlg, registerBtnOpeFlg, affilicateIdOrUserIdTextboxFlg, model);
			// ログインユーザーの権限区分＝1：ゲスト　または　2：一般）　の場合はロールバック
			if(!loginUserAuthProcessResult) {
				System.out.println("コントローラー：initUserInfoメソッド：if：「ユーザー情報」登録画面 ロールバック/n");
				
				// クリアボタン及び登録ボタンを操作不可能に設定する。
				clearBtnOpeFlg = false;
				registerBtnOpeFlg = false;
				model.addAttribute("clearBtnOpeFlg", clearBtnOpeFlg);
				model.addAttribute("registerBtnOpeFlg", registerBtnOpeFlg);
				// ユーザー情報登録時、ユーザーID、ユーザー名、有効日(FROM)のテキストボックスを編集可能（false）に設定する。
				updateTextBoxFlg = false;
				model.addAttribute("updateTextBoxFlg", updateTextBoxFlg);
				// 登録ボタンが「登録機能」であると設定
				registerOrUpdateBtnFlg = true;
				model.addAttribute("registerOrUpdateBtnFlg", registerOrUpdateBtnFlg);
				// ログインユーザーの権限区分がadminの場合のみ
				// 所属ID、ユーザーIDのテキストボックスを編集可能（false）に設定する。
				if(commonDto.getUserId().equals("al00000")) {
					affilicateIdOrUserIdTextboxFlg = false;
					model.addAttribute("affilicateIdOrUserIdTextboxFlg", affilicateIdOrUserIdTextboxFlg);
					
				} else {
					affilicateIdOrUserIdTextboxFlg = true;
					model.addAttribute("affilicateIdOrUserIdTextboxFlg", affilicateIdOrUserIdTextboxFlg);
					
				}
				
				return "bfmk01View";
				
			} else {
				// 処理なし
			}
			
			// CommonDtoクラス（所属ID）をBfmk01Formクラスへ変換
			bfmk01Form.setAffilicateId(commonDto.getAffId());
			// ModelにBfmk01Formを登録
			model.addAttribute("bfmk01Form", bfmk01Form);
			
			// クリアボタン及び登録ボタンを操作可能に設定する。
			clearBtnOpeFlg = true;
			registerBtnOpeFlg = true;
			model.addAttribute("clearBtnOpeFlg", clearBtnOpeFlg);
			model.addAttribute("registerBtnOpeFlg", registerBtnOpeFlg);
			// ユーザー情報登録時、ユーザーID、ユーザー名、有効日(FROM)のテキストボックスを編集可能（false）に設定する。
			updateTextBoxFlg = false;
			model.addAttribute("updateTextBoxFlg", updateTextBoxFlg);
			// 登録ボタンが「登録機能」であると設定
			registerOrUpdateBtnFlg = true;
			model.addAttribute("registerOrUpdateBtnFlg", registerOrUpdateBtnFlg);
			// ログインユーザーの権限区分がadminの場合のみ
			// 所属ID、ユーザーIDのテキストボックスを編集可能（false）に設定する。
			if(commonDto.getUserId().equals("al00000")) {
				affilicateIdOrUserIdTextboxFlg = false;
				model.addAttribute("affilicateIdOrUserIdTextboxFlg", affilicateIdOrUserIdTextboxFlg);
				
			} else {
				affilicateIdOrUserIdTextboxFlg = true;
				model.addAttribute("affilicateIdOrUserIdTextboxFlg", affilicateIdOrUserIdTextboxFlg);
				
			}
			
		} else if(commonDto.getTmpDispId().equals("bfmk02")) {
			// 更新の場合
			System.out.println("コントローラー：initUserInfoメソッド：if：「ユーザー情報」更新画面");
			
			// サービスクラスのパブリックメソッド「初期画面」を呼び出す。
			UserInfoDto userInfoDtoUpdateResult = bfmk01Service.init(commonDto, userInfoDtoUpdate);
			
			// ログインユーザーの権限区分により場合分けして処理
			boolean loginUserAuthProcessResult = loginUserAuthProcess(userInfoDtoUpdateResult, bfmk01Form, clearBtnOpeFlg, registerBtnOpeFlg, affilicateIdOrUserIdTextboxFlg, model);
			// ログインユーザーの権限区分＝1：ゲスト　または　2：一般）　の場合はロールバック
			if(!loginUserAuthProcessResult) {
				System.out.println("コントローラー：initUserInfoメソッド：if：「ユーザー情報」更新画面 ロールバック/n");
				
				// クリアボタン及び登録ボタンを操作不可能に設定する。
				clearBtnOpeFlg = false;
				registerBtnOpeFlg = false;
				model.addAttribute("clearBtnOpeFlg", clearBtnOpeFlg);
				model.addAttribute("registerBtnOpeFlg", registerBtnOpeFlg);
				// ユーザー情報登録時、ユーザーID、ユーザー名、有効日(FROM)のテキストボックスを編集可能（false）に設定する。
				updateTextBoxFlg = false;
				model.addAttribute("updateTextBoxFlg", updateTextBoxFlg);
				// 登録ボタンが「登録機能」であると設定
				registerOrUpdateBtnFlg = true;
				model.addAttribute("registerOrUpdateBtnFlg", registerOrUpdateBtnFlg);
				// ログインユーザーの権限区分がadminの場合のみ
				// 所属ID、ユーザーIDのテキストボックスを編集可能（false）に設定する。
				if(commonDto.getUserId().equals("al00000")) {
					affilicateIdOrUserIdTextboxFlg = false;
					model.addAttribute("affilicateIdOrUserIdTextboxFlg", affilicateIdOrUserIdTextboxFlg);
					
				} else {
					affilicateIdOrUserIdTextboxFlg = true;
					model.addAttribute("affilicateIdOrUserIdTextboxFlg", affilicateIdOrUserIdTextboxFlg);
					
				}
				
				return "bfmk01View";
				
			} else {
				// 処理なし
			}
			
			// ModelにBfmk01Formを登録
			model.addAttribute("bfmk01Form", bfmk01Form);
			
			// クリアボタン及び登録ボタンを操作可能に設定する。
			clearBtnOpeFlg = true;
			registerBtnOpeFlg = true;
			model.addAttribute("clearBtnOpeFlg", clearBtnOpeFlg);
			model.addAttribute("registerBtnOpeFlg", registerBtnOpeFlg);
			// ユーザーID、ユーザー名、有効日(FROM)のテキストボックスを編集不可能（readonly）に設定する。
			updateTextBoxFlg = true;
			model.addAttribute("updateTextBoxFlg", updateTextBoxFlg);
			// 登録ボタンが「更新機能」であると設定
			registerOrUpdateBtnFlg = false;
			model.addAttribute("registerOrUpdateBtnFlg", registerOrUpdateBtnFlg);
			// ログインユーザーの権限区分がadminの場合のみ
			// 所属ID、ユーザーIDのテキストボックスを編集可能（false）に設定する。
			if(commonDto.getUserId().equals("al00000")) {
				affilicateIdOrUserIdTextboxFlg = false;
				model.addAttribute("affilicateIdOrUserIdTextboxFlg", affilicateIdOrUserIdTextboxFlg);
				
			} else {
				affilicateIdOrUserIdTextboxFlg = true;
				model.addAttribute("affilicateIdOrUserIdTextboxFlg", affilicateIdOrUserIdTextboxFlg);
				
			}
			
		} else {
			// 処理なし
		}
		
		System.out.println("コントローラー：initUserInfoメソッド終了\n");
		
		return "bfmk01View";
	}
	
	/**
	 * ログインユーザー権限メソッド
	 * @param userInfoDto
	 * @param bfmk01Form
	 * @param clearBtnFlg
	 * @param registerBtnFlg
	 * @param affilicateIdOrUserIdTextboxFlg
	 * @param model
	 * @return loginUserAuthProcessResult
	 */
	private static boolean loginUserAuthProcess(UserInfoDto userInfoDto, Bfmk01Form bfmk01Form, Boolean clearBtnFlg, Boolean registerBtnFlg, Boolean affilicateIdOrUserIdTextboxFlg, Model model) {
		
		// 返り値の格納用変数
		// true		：ログインユーザーの権限区分＝3：管理者　または　4：admin
		// false	：ログインユーザーの権限区分＝1：ゲスト または 2：一般
		boolean loginUserAuthProcessResult = true;
		
		// ログインユーザーの権限区分により場合分け
		if(userInfoDto.getAuthDivLoginUser().equals("1") || userInfoDto.getAuthDivLoginUser().equals("2")) {
			// （ログインユーザーの権限区分＝1：ゲスト　または　2：一般）　の場合
			System.out.println("コントローラー：ログインユーザー権限メソッド：if：ログインユーザーの権限区分＝1：ゲスト　または　2：一般");
			
			// 返り値の格納用変数を設定（ログインユーザーの権限区分＝1：ゲスト または 2：一般）
			loginUserAuthProcessResult = false;
			
			// メッセージを取得し、Modelに登録
			String authDivErrMsg = mes.getString(userInfoDto.getMessage());
			model.addAttribute("message" ,authDivErrMsg);
			// クリアボタン及び登録ボタンを操作不可に設定する。
			clearBtnFlg = false;
			model.addAttribute("clearBtnFlg", clearBtnFlg);
			registerBtnFlg = false;
			model.addAttribute("registerBtnFlg", registerBtnFlg);
			
		} else if(userInfoDto.getAuthDivLoginUser().equals("3") || userInfoDto.getAuthDivLoginUser().equals("4")) {
			// （ログインユーザーの権限区分＝3：管理者　または　4：admin）　の場合
			System.out.println("コントローラー：ログインユーザー権限メソッド：if：ログインユーザーの権限区分＝3：管理者　または　4：admin");
			
			// UserInfoDtoクラスをBfmk01Formクラスへ変換
			bfmk01Form.setAffilicateId(userInfoDto.getAffilicateId());			// 所属ID
			bfmk01Form.setUserId(userInfoDto.getUserId());						// ユーザーID
			bfmk01Form.setUserName(userInfoDto.getUserName());					// ユーザー名
			bfmk01Form.setPass(userInfoDto.getPass());							// パスワード
			bfmk01Form.setRePass(userInfoDto.getRePass());						// パスワード再確認
			bfmk01Form.setExpireDateFrom(userInfoDto.getExpireDateFrom());		// 有効日（FROM）
			bfmk01Form.setExpireDateTo(userInfoDto.getExpireDateTo());			// 有効日（TO）
			bfmk01Form.setAuthDiv(userInfoDto.getAuthDiv());					// 権限区分(ゲスト：1、一般：2、管理者：3)
			bfmk01Form.setWatchAuthFlg(userInfoDto.getWatchAuthFlg());			// 参照権限フラグ（権限あり：true、権限なし：false）
			bfmk01Form.setOprAuthFlg(userInfoDto.getOprAuthFlg());				// 操作権限フラグ（権限あり：true、権限なし：false）
			
			// （ログインユーザーの権限区分＝3：管理者）　の場合、所属ID/ユーザーIDの操作可否 = true(操作不可)に
			// （ログインユーザーの権限区分＝4：admin）　の場合、所属ID/ユーザーIDの操作可否 = false(操作可能)に設定する
			if(userInfoDto.getAuthDivLoginUser().equals("3")) {
				affilicateIdOrUserIdTextboxFlg = true;
				model.addAttribute("affilicateIdOrUserIdTextboxFlg", affilicateIdOrUserIdTextboxFlg);
				
			} else if(userInfoDto.getAuthDivLoginUser().equals("4")) {
				affilicateIdOrUserIdTextboxFlg = false;
				model.addAttribute("affilicateIdOrUserIdTextboxFlg", affilicateIdOrUserIdTextboxFlg);
				
			} else {
				// 処理なし
			}
			
		} else {
			// 処理なし
		}
		
		return loginUserAuthProcessResult;
		
	}
	
	/**
	 * 戻る
	 * @param bfmk01Form
	 * @param model
	 * @return back
	 */
	@PostMapping(value = "/pc/201", params = "back")
	// CommonDtoの渡し方に関して未決定のため、仮置きで対応。
	// ユーザー情報DTOの渡し方が分からないため、未記述
//	public String back(@ModelAttribute Bfmk01Form bfmk01Form, UserInfoDto userInfoDto, CommonDto commonDto, Model model) {
	public String back(@ModelAttribute Bfmk01Form bfmk01Form, Model model) {
		
		System.out.println("コントローラー：backメソッド開始");
		
// --------------------------------------------------------------------------------------
// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓【仮置き】↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
// --------------------------------------------------------------------------------------	
		// 仮置き：共通DTO
		// ログインユーザーを設定
		// 登録と更新画面の切り替え（登録：遷移前画面ID：bfkt02、更新：遷移前画面ID：bfmk02）
		// 遷移前画面ID（bfkt02：「メニュー」画面→ユーザー情報登録画面へ、 bfmk02：「ユーザー情報一覧」画面→ユーザー情報更新画面へ）
		// 								　　　画面ID, 画面名, 遷移前画面ID, メッセージ, 工場CD, 所属ID, 所属名, 		ユーザーID, 	ユーザー名
//		// guest
//		System.out.println("コントローラー：ログインユーザー：guest");
//		CommonDto commonDto = new CommonDto(null,  null,  "bfmk02",   null,   "bfm1",  "hk1",  null,	"hkgt000", 	null);
//		// gene
//		System.out.println("コントローラー：ログインユーザー：gene");
//		CommonDto commonDto = new CommonDto(null,  null,  "bfmk02",   null,   "bfm1",  "it1",  null,	"itns000",  null);
		// boss
		System.out.println("コントローラー：ログインユーザー：boss");
		CommonDto commonDto = new CommonDto(null,  null,  "bfmk02",   null,   "bfm1",  "us1",  null,	"uskr000",  null);
//		// admin
//		System.out.println("コントローラー：ログインユーザー：admin");
//		CommonDto commonDto = new CommonDto(null,  null,  "bfmk02",   null,   "bfm1",  "all",  null,	"al00000", 	null);
// --------------------------------------------------------------------------------------
// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑【仮置き】↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
// --------------------------------------------------------------------------------------
		
		// 返り値の格納用変数
		String back = null;
		
		if(commonDto.getTmpDispId().equals("bfkt02")) {
			// 画面ID（遷移元）がメニュー画面の場合
			System.out.println("コントローラー：backメソッド：if：画面ID（遷移元）がメニュー画面の場合");
			back = "redirect:/b-forme_Kojo/pc/002";
			
			// 共通DTOを返却する設定をする。
			// CommonDtoの渡し方に関して未決定のため、未記述
			// ～～～CommonDtoを渡すためのコード～～～
			
		} else if(commonDto.getTmpDispId().equals("bfmk02")) {
			// 画面ID（遷移元）がユーザー情報一覧画面の場合
			System.out.println("コントローラー：backメソッド：if：画面ID（遷移元）がユーザー情報一覧画面の場合");
			back = "redirect:/b-forme_Kojo/pc/202";
			
			// 共通DTOを返却する設定をする。
			// CommonDtoの渡し方に関して未決定のため、未記述
			// ～～～CommonDtoを渡すためのコード～～～
			
			// ユーザー情報DTO（の中の「ユーザー情報一覧フォーム」）を返却する設定をする。
			// ユーザー情報DTOの渡し方が分からないため、未記述
			// ～～～ユーザー情報DTOを渡すためのコード～～～
			
		} else {
			// 処理なし
		}
		
		System.out.println("コントローラー：backメソッド終了\n");
		
		return back;
		
	}
	
	/**
	 * クリア
	 * @param bfmk01Form
	 * @param model
	 * @return initUserInfo(bfmk01Form, model)
	 */
	@PostMapping(value = "/pc/201", params = "clear")
	// CommonDtoの渡し方に関して未決定のため、仮置きで対応。
	// ユーザー情報DTOの渡し方が分からないため、未記述
//	public String clearUserInfo(@ModelAttribute Bfmk01Form bfmk01Form, UserInfoDto userInfoDto, CommonDto commonDto, Model model) {
	public String clearUserInfo(@ModelAttribute Bfmk01Form bfmk01Form, Model model) {
		
		System.out.println("コントローラー：clearUserInfoメソッド開始");
		
		// UserInfoDtoクラス（＝Formの変更前データ）をBfmk01Formクラスへ変換
		// ユーザー情報DTOの渡し方が分からないため、未記述
		// なお現在、「initUserInfoメソッド」側でユーザー情報DTOを仮置きしてるので、ここが未記述でも「クリア」動作をしている。
//		bfmk01Form.setAffilicateId(userInfoDto.getAffilicateId());		// 所属ID
//		bfmk01Form.setUserId(userInfoDto.getUserId());					// ユーザーID
//		bfmk01Form.setUserName(userInfoDto.getUserName());				// ユーザー名
//		bfmk01Form.setPass(userInfoDto.getPass());						// パスワード
//		bfmk01Form.setRePass(userInfoDto.getRePass());					// パスワード再確認
//		bfmk01Form.setExpireDateFrom(userInfoDto.getExpireDateFrom());	// 適用日（FROM）
//		bfmk01Form.setExpireDateTo(userInfoDto.getExpireDateTo());		// 適用日（TO）
//		bfmk01Form.setAuthDiv(userInfoDto.getAuthDiv());				// 権限区分(ゲスト：1、一般：2、管理者：3)
//		bfmk01Form.setWatchAuthFlg(userInfoDto.getWatchAuthFlg());		// 参照権限フラグ（権限あり：true、権限なし：false）
//		bfmk01Form.setOprAuthFlg(userInfoDto.getOprAuthFlg());			// 操作権限フラグ（権限あり：true、権限なし：false）
		
		System.out.println("コントローラー：clearUserInfoメソッド終了\n");
		
		return initUserInfo(bfmk01Form, model);
				
	}
	
	/**
	 * 登録
	 * @param bfmk01Form
	 * @param model
	 * @return bfmk01View
	 */
	// CommonDtoの渡し方に関して未決定のため、仮置きで対応。
	@PostMapping(value = "/pc/201", params = "register")
//	public String userInfoRegister(@ModelAttribute Bfmk01Form bfmk01Form, CommonDto commonDto, Model model) {
	public String userInfoRegister(@ModelAttribute Bfmk01Form bfmk01Form, Model model) {
	
		System.out.println("コントローラー：userInfoRegisterメソッド開始");
		
// --------------------------------------------------------------------------------------
// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓【仮置き】↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
// --------------------------------------------------------------------------------------
		// 仮置き：共通DTO
		// ログインユーザーを設定
		// 登録：遷移前画面ID：bfkt02
		// 遷移前画面ID（bfkt02：「メニュー」画面→ユーザー情報登録画面へ）
		// 								　　　画面ID, 画面名, 遷移前画面ID, メッセージ, 工場CD, 所属ID, 所属名, 		ユーザーID, 	ユーザー名
//		// guest
//		System.out.println("コントローラー：ログインユーザー：guest");
//		CommonDto commonDto = new CommonDto(null,  null,  "bfkt02",   null,   "bfm1",  "hk1",  null,	"hkgt000", 	null);
//		// gene
//		System.out.println("コントローラー：ログインユーザー：gene");
//		CommonDto commonDto = new CommonDto(null,  null,  "bfkt02",   null,   "bfm1",  "it1",  null,	"itns000",  null);
		// boss
		System.out.println("コントローラー：ログインユーザー：boss");
		CommonDto commonDto = new CommonDto(null,  null,  "bfkt02",   null,   "bfm1",  "us1",  null,	"uskr000",  null);
//		// admin
//		System.out.println("コントローラー：ログインユーザー：admin");
//		CommonDto commonDto = new CommonDto(null,  null,  "bfkt02",   null,   "bfm1",  "all",  null,	"al00000", 	null);
// --------------------------------------------------------------------------------------
// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑【仮置き】↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
// --------------------------------------------------------------------------------------
		
		// クリアボタン及び登録ボタンの操作可否の設定（true：有効、false：無効）
		boolean clearBtnOpeFlg = true;
		boolean registerBtnOpeFlg = true;
		// ユーザー情報更新時、ユーザーID、ユーザー名、有効日(FROM)のテキストボックス編集可否の設定（true：不可能、false：可能）
		boolean updateTextBoxFlg = true;
		// 登録ボタンが「登録機能」か「更新機能」かの設定（true：登録、false：更新）
		boolean registerOrUpdateBtnFlg = true;
		// 所属ID、ユーザーIDの操作可否（true：操作不可、false：操作可能）
		boolean affilicateIdOrUserIdTextboxFlg = true;
		
		// BFmk01FormクラスからUserInfoDtoクラスへ変換
		UserInfoDto userInfoDto = new UserInfoDto();
		userInfoDto.setAffilicateId(bfmk01Form.getAffilicateId());		// 所属ID
		userInfoDto.setUserId(bfmk01Form.getUserId());					// ユーザーID
		userInfoDto.setUserName(bfmk01Form.getUserName());				// ユーザー名
		userInfoDto.setPass(bfmk01Form.getPass());						// パスワード
		userInfoDto.setRePass(bfmk01Form.getRePass());					// パスワード再確認
		userInfoDto.setExpireDateFrom(bfmk01Form.getExpireDateFrom());	// 適用日（FROM）
		userInfoDto.setExpireDateTo(bfmk01Form.getExpireDateTo());		// 適用日（TO）
		userInfoDto.setAuthDiv(bfmk01Form.getAuthDiv());				// 権限区分(ゲスト：1、一般：2、管理者：3)
		userInfoDto.setWatchAuthFlg(bfmk01Form.getWatchAuthFlg());		// 参照権限フラグ（権限あり：true、権限なし：false）
		userInfoDto.setOprAuthFlg(bfmk01Form.getOprAuthFlg());			// 操作権限フラグ（権限あり：true、権限なし：false）
		
		// サービスクラスのパブリックメソッド「登録」を呼び出す。
		UserInfoDto userInfoDtoInsertResult = bfmk01Service.insert(userInfoDto, commonDto);
		
		// 登録成功の可否で場合分け
		if(!(userInfoDtoInsertResult.getMessage().equals("msuzer015"))) {
			System.out.println("コントローラー：userInfoRegisterメソッド：if：登録が失敗したの場合 ロールバック\n");
			// 登録が失敗したの場合（メッセージID：msuzer015以外）
			// OUTパラメータに戻り値を設定し、ロールバックする。
			// メッセージを取得し、Modelに登録
			String authDivErrMsg = mes.getString(userInfoDtoInsertResult.getMessage());
			model.addAttribute("message" ,authDivErrMsg);
			
			// クリアボタン及び登録ボタンを操作可能に設定する。
			clearBtnOpeFlg = true;
			registerBtnOpeFlg = true;
			model.addAttribute("clearBtnOpeFlg", clearBtnOpeFlg);
			model.addAttribute("registerBtnOpeFlg", registerBtnOpeFlg);
			// ユーザーID、ユーザー名、有効日(FROM)のテキストボックスを編集可能に設定する。
			updateTextBoxFlg = false;
			model.addAttribute("updateTextBoxFlg", updateTextBoxFlg);
			// 登録ボタンが「登録機能」であると設定
			registerOrUpdateBtnFlg = true;
			model.addAttribute("registerOrUpdateBtnFlg", registerOrUpdateBtnFlg);
			// ログインユーザーの権限区分がadminの場合のみ
			// 所属ID、ユーザーIDのテキストボックスを編集可能（false）に設定する。
			if(commonDto.getUserId().equals("al00000")) {
				affilicateIdOrUserIdTextboxFlg = false;
				model.addAttribute("affilicateIdOrUserIdTextboxFlg", affilicateIdOrUserIdTextboxFlg);
				
			} else {
				affilicateIdOrUserIdTextboxFlg = true;
				model.addAttribute("affilicateIdOrUserIdTextboxFlg", affilicateIdOrUserIdTextboxFlg);
				
			}
			
			return "bfmk01View";
			
		} else if(userInfoDtoInsertResult.getMessage().equals("msuzer015")) {
			// 登録が成功したの場合（メッセージID：msuzer015）
			// OUTパラメータに戻り値を設定し、ユーザー情報の登録画面用のViewを返す。
			// メッセージを取得し、Modelに登録
			String authDivErrMsg = mes.getString(userInfoDtoInsertResult.getMessage());
			model.addAttribute("message" ,authDivErrMsg);
			
			// UserInfoDtoクラスをBfmk01Formクラスへ変換
			bfmk01Form.setAffilicateId(userInfoDtoInsertResult.getAffilicateId());			// 所属ID
			bfmk01Form.setUserId(userInfoDtoInsertResult.getUserId());						// ユーザーID
			bfmk01Form.setUserName(userInfoDtoInsertResult.getUserName());					// ユーザー名
			bfmk01Form.setPass(userInfoDtoInsertResult.getPass());							// パスワード
			bfmk01Form.setRePass(userInfoDtoInsertResult.getRePass());						// パスワード再確認
			bfmk01Form.setExpireDateFrom(userInfoDtoInsertResult.getExpireDateFrom());		// 有効日（FROM）
			bfmk01Form.setExpireDateTo(userInfoDtoInsertResult.getExpireDateTo());			// 有効日（TO）
			bfmk01Form.setAuthDiv(userInfoDtoInsertResult.getAuthDiv());					// 権限区分(ゲスト：1、一般：2、管理者：3)
			bfmk01Form.setWatchAuthFlg(userInfoDtoInsertResult.getWatchAuthFlg());			// 参照権限フラグ（権限あり：true、権限なし：false）
			bfmk01Form.setOprAuthFlg(userInfoDtoInsertResult.getOprAuthFlg());				// 操作権限フラグ（権限あり：true、権限なし：false）
			model.addAttribute("bfmk01Form", bfmk01Form);
			
			// クリアボタン及び登録ボタンを操作可能に設定する。
			clearBtnOpeFlg = true;
			registerBtnOpeFlg = true;
			model.addAttribute("clearBtnOpeFlg", clearBtnOpeFlg);
			model.addAttribute("registerBtnOpeFlg", registerBtnOpeFlg);
			// ユーザーID、ユーザー名、有効日(FROM)のテキストボックスを編集可能に設定する。
			updateTextBoxFlg = true;
			model.addAttribute("updateTextBoxFlg", updateTextBoxFlg);
			// 登録ボタンが「登録機能」であると設定
			registerOrUpdateBtnFlg = true;
			model.addAttribute("registerOrUpdateBtnFlg", registerOrUpdateBtnFlg);
			// ログインユーザーの権限区分がadminの場合のみ
			// 所属ID、ユーザーIDのテキストボックスを編集可能（false）に設定する。
			if(commonDto.getUserId().equals("al00000")) {
				affilicateIdOrUserIdTextboxFlg = false;
				model.addAttribute("affilicateIdOrUserIdTextboxFlg", affilicateIdOrUserIdTextboxFlg);
				
			} else {
				affilicateIdOrUserIdTextboxFlg = true;
				model.addAttribute("affilicateIdOrUserIdTextboxFlg", affilicateIdOrUserIdTextboxFlg);
				
			}
			
		} else {
			// 処理なし
		}
		
		System.out.println("コントローラー：userInfoRegisterメソッド終了\n");
		
		return "bfmk01View";
		
	}
	
	/**
	 * 更新
	 * @param bfmk01Form
	 * @param model
	 * @return bfmk01View
	 */
	@PostMapping(value = "/pc/201", params = "update")
	// CommonDtoの渡し方に関して未決定のため、仮置きで対応。
//	public String userInfoUpdate(@ModelAttribute Bfmk01Form bfmk01Form, CommonDto commonDto, Model model) {
	public String userInfoUpdate(@ModelAttribute Bfmk01Form bfmk01Form, Model model) {
		
		System.out.println("コントローラー：userInfoUpdateメソッド開始");
		
// --------------------------------------------------------------------------------------
// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓【仮置き】↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
// --------------------------------------------------------------------------------------
		// 仮置き：共通DTO
		// ログインユーザーを設定
		// 更新：遷移前画面ID：bfmk02
		// 遷移前画面ID（bfmk02：「ユーザー情報一覧」画面→ユーザー情報更新画面へ）
		// 								　　　画面ID, 画面名, 遷移前画面ID, メッセージ, 工場CD, 所属ID, 所属名, 		ユーザーID, 	ユーザー名
//		// guest
//		System.out.println("コントローラー：ログインユーザー：guest");
//		CommonDto commonDto = new CommonDto(null,  null,  "bfmk02",   null,   "bfm1",  "hk1",  null,	"hkgt000", 	null);
//		// gene
//		System.out.println("コントローラー：ログインユーザー：gene");
//		CommonDto commonDto = new CommonDto(null,  null,  "bfmk02",   null,   "bfm1",  "it1",  null,	"itns000",  null);
		// boss
		System.out.println("コントローラー：ログインユーザー：boss");
		CommonDto commonDto = new CommonDto(null,  null,  "bfmk02",   null,   "bfm1",  "us1",  null,	"uskr000",  null);
//		// admin
//		System.out.println("コントローラー：ログインユーザー：admin");
//		CommonDto commonDto = new CommonDto(null,  null,  "bfmk02",   null,   "bfm1",  "all",  null,	"al00000", 	null);
// --------------------------------------------------------------------------------------
// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑【仮置き】↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
// --------------------------------------------------------------------------------------
		
		// クリアボタン及び登録ボタンの操作可否の設定（true：有効、false：無効）
		boolean clearBtnOpeFlg = true;
		boolean registerBtnOpeFlg = true;
		// ユーザー情報更新時、ユーザーID、ユーザー名、有効日(FROM)のテキストボックス編集可否の設定（true：不可能、false：可能）
		boolean updateTextBoxFlg = true;
		// 登録ボタンが「登録機能」か「更新機能」かの設定（true：登録、false：更新）
		boolean registerOrUpdateBtnFlg = false;
		// 所属ID、ユーザーIDの操作可否（true：操作不可、false：操作可能）
		boolean affilicateIdOrUserIdTextboxFlg = true;
		
		// BFmk01FormクラスからUserInfoDtoクラスへ変換
		UserInfoDto userInfoDto = new UserInfoDto();
		userInfoDto.setAffilicateId(bfmk01Form.getAffilicateId());		// 所属ID
		userInfoDto.setUserId(bfmk01Form.getUserId());					// ユーザーID
		userInfoDto.setUserName(bfmk01Form.getUserName());				// ユーザー名
		userInfoDto.setPass(bfmk01Form.getPass());						// パスワード
		userInfoDto.setRePass(bfmk01Form.getRePass());					// パスワード再確認
		userInfoDto.setExpireDateFrom(bfmk01Form.getExpireDateFrom());	// 適用日（FROM）
		userInfoDto.setExpireDateTo(bfmk01Form.getExpireDateTo());		// 適用日（TO）
		userInfoDto.setAuthDiv(bfmk01Form.getAuthDiv());				// 権限区分(ゲスト：1、一般：2、管理者：3)
		userInfoDto.setWatchAuthFlg(bfmk01Form.getWatchAuthFlg());		// 参照権限フラグ（権限あり：true、権限なし：false）
		userInfoDto.setOprAuthFlg(bfmk01Form.getOprAuthFlg());			// 操作権限フラグ（権限あり：true、権限なし：false）
		
		// サービスクラスのパブリックメソッド「更新」を呼び出す。
		UserInfoDto userInfoDtoUpdateResult = bfmk01Service.update(userInfoDto, commonDto);
		
		// 更新成功の可否で場合分け
		if(!(userInfoDtoUpdateResult.getMessage().equals("msuzer016"))) {
			System.out.println("コントローラー：userInfoUpdateメソッド：if：更新が失敗したの場合 ロールバック\n");
			// 更新が失敗したの場合（メッセージID：msuzer016以外）
			// OUTパラメータに戻り値を設定し、ロールバックする。
			// メッセージを取得し、Modelに登録
			String authDivErrMsg = mes.getString(userInfoDtoUpdateResult.getMessage());
			model.addAttribute("message" ,authDivErrMsg);
			
			// クリアボタン及び登録ボタンを操作可能に設定する。
			clearBtnOpeFlg = true;
			registerBtnOpeFlg = true;
			model.addAttribute("clearBtnOpeFlg", clearBtnOpeFlg);
			model.addAttribute("registerBtnOpeFlg", registerBtnOpeFlg);
			// ユーザーID、ユーザー名、有効日(FROM)のテキストボックスを編集不可能に設定する。
			updateTextBoxFlg = true;
			model.addAttribute("updateTextBoxFlg", updateTextBoxFlg);
			// 登録ボタンが「更新機能」であると設定
			registerOrUpdateBtnFlg = false;
			model.addAttribute("registerOrUpdateBtnFlg", registerOrUpdateBtnFlg);
			// 所属ID、ユーザーIDのテキストボックスを編集不可（true）に設定する。
			affilicateIdOrUserIdTextboxFlg = true;
			model.addAttribute("affilicateIdOrUserIdTextboxFlg", affilicateIdOrUserIdTextboxFlg);
			
			return "bfmk01View";
			
		} else if(userInfoDtoUpdateResult.getMessage().equals("msuzer016")) {
			// 更新が成功したの場合（メッセージID：msuzer016）
			// OUTパラメータに戻り値を設定し、ユーザー情報の更新画面用のViewを返す。
			// メッセージを取得し、Modelに登録
			String authDivErrMsg = mes.getString(userInfoDtoUpdateResult.getMessage());
			model.addAttribute("message" ,authDivErrMsg);
			
			// UserInfoDtoクラスをBfmk01Formクラスへ変換
			bfmk01Form.setAffilicateId(userInfoDtoUpdateResult.getAffilicateId());			// 所属ID
			bfmk01Form.setUserId(userInfoDtoUpdateResult.getUserId());						// ユーザーID
			bfmk01Form.setUserName(userInfoDtoUpdateResult.getUserName());					// ユーザー名
			bfmk01Form.setPass(userInfoDtoUpdateResult.getPass());							// パスワード
			bfmk01Form.setRePass(userInfoDtoUpdateResult.getRePass());						// パスワード再確認
			bfmk01Form.setExpireDateFrom(userInfoDtoUpdateResult.getExpireDateFrom());		// 有効日（FROM）
			bfmk01Form.setExpireDateTo(userInfoDtoUpdateResult.getExpireDateTo());			// 有効日（TO）
			bfmk01Form.setAuthDiv(userInfoDtoUpdateResult.getAuthDiv());					// 権限区分(ゲスト：1、一般：2、管理者：3)
			bfmk01Form.setWatchAuthFlg(userInfoDtoUpdateResult.getWatchAuthFlg());			// 参照権限フラグ（権限あり：true、権限なし：false）
			bfmk01Form.setOprAuthFlg(userInfoDtoUpdateResult.getOprAuthFlg());				// 操作権限フラグ（権限あり：true、権限なし：false）
			model.addAttribute("bfmk01Form", bfmk01Form);
			
			// クリアボタン及び登録ボタンを操作可能に設定する。
			clearBtnOpeFlg = true;
			registerBtnOpeFlg = true;
			model.addAttribute("clearBtnOpeFlg", clearBtnOpeFlg);
			model.addAttribute("registerBtnOpeFlg", registerBtnOpeFlg);
			// ユーザーID、ユーザー名、有効日(FROM)のテキストボックスを編集不可能に設定する。
			updateTextBoxFlg = true;
			model.addAttribute("updateTextBoxFlg", updateTextBoxFlg);
			// 登録ボタンが「更新機能」であると設定
			registerOrUpdateBtnFlg = false;
			model.addAttribute("registerOrUpdateBtnFlg", registerOrUpdateBtnFlg);
			// 所属ID、ユーザーIDのテキストボックスを編集不可（true）に設定する。
			affilicateIdOrUserIdTextboxFlg = true;
			model.addAttribute("affilicateIdOrUserIdTextboxFlg", affilicateIdOrUserIdTextboxFlg);
			
		} else {
			// 処理なし
		}
		
		System.out.println("コントローラー：userInfoUpdateメソッド終了\n");
		
		return "bfmk01View";
		
	}
	
}
