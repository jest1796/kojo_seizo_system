package com.seizou.kojo.domain.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seizou.kojo.domain.dao.InsertUserInfoDao;
import com.seizou.kojo.domain.dto.CommonDto;
import com.seizou.kojo.domain.dto.UserInfoDto;
import com.seizou.kojo.domain.repository.Bfmk01Repository;

/**
* ユーザー情報 Service
* @author K.Sakuma
*/
@Transactional
@Service
public class Bfmk01Service {
	
	@Autowired
	Bfmk01Repository bfmk01Repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * 初期画面
	 * @param commonDto
	 * @param userInfoDto
	 * @return userInfoDtoInitResult
	 */
	public UserInfoDto init(CommonDto commonDto, UserInfoDto userInfoDto) {
		
		System.out.println("サービス：initメソッド開始");
		
		// 戻り値用ユーザー情報DTOを宣言し、初期化する。
		UserInfoDto userInfoDtoInitResult = new UserInfoDto();
		
		// 権限チェック
		// 権限チェック用にリポジトリーへデータを渡すためのDaoを宣言し、初期化する。
		InsertUserInfoDao insertUserInfoDaoAuthDiv = new InsertUserInfoDao();
		insertUserInfoDaoAuthDiv.setFacCd(commonDto.getFacCd());			// 工場CD
		insertUserInfoDaoAuthDiv.setAffilicateId(commonDto.getAffId());		// 所属ID
		insertUserInfoDaoAuthDiv.setUserId(commonDto.getUserId());			// ユーザーID
		
		// リポジトリーのメソッド「権限区分検索」を呼び出す。
		String authDiv = bfmk01Repository.authDivSeach(insertUserInfoDaoAuthDiv);
		
		// ログインユーザーの権限区分により場合分け
		if(authDiv.equals("1") || authDiv.equals("2")) {
			// ログインユーザーの権限区分が 「2：一般」 以下の場合
			System.out.println("サービス：initメソッド：if：ログインユーザーの権限区分が 「2：一般」 以下");
			
			// 取得した権限区分をDTOに設定
			userInfoDtoInitResult.setAuthDivLoginUser(authDiv);
			// メッセージID：msuzer001　を戻り値．メッセージ に設定する。
			userInfoDtoInitResult.setMessage("msuzer001");
			
			// ロールバックする。
			System.out.println("サービス：initメソッド：if：ログインユーザーの権限区分が 「2：一般」 以下の場合　ロールバック");
			// OUTパラメータに戻り値を設定し、返却する。
			return userInfoDtoInitResult;
			
		} else if(authDiv.equals("3") || authDiv.equals("4")) {
			// ログインユーザーの権限区分が 「3：管理者」 以上
			System.out.println("サービス：initメソッド：if：ログインユーザーの権限区分が 「3：管理者」 以上");
			
			// 取得した権限区分をDTOに設定
			userInfoDtoInitResult.setAuthDivLoginUser(authDiv);
			
		} else {
			// 処理なし
		}
		
		// 値の設定
		if(commonDto.getTmpDispId().equals("bfkt02")) {
			// 登録の場合
			System.out.println("サービス：initメソッド：if：ユーザー情報登録時");
			
			// システム日時を取得し、年月日をフォーマットする。（yyyy.MM.dd形式）
			Date nowDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
			String nowDateString = sdf.format(nowDate);
			// フォーマットした値を戻り値．ユーザー情報DTO．有効日（FROM）に設定する。
			userInfoDtoInitResult.setExpireDateFrom(nowDateString);	
			// システム日時を設定
			userInfoDtoInitResult.setSystemDate(nowDate);
			
		} else if(commonDto.getTmpDispId().equals("bfmk02")) {
			// 更新の場合
			System.out.println("サービス：initメソッド：if：ユーザー情報更新時");
			
			// 値の設定用（ユーザー情報更新時のみ）にリポジトリーへデータを渡すためのDaoを宣言し、初期化する。
			InsertUserInfoDao insertUserInfoDaoUpdate = new InsertUserInfoDao();
			insertUserInfoDaoUpdate.setFacCd(userInfoDto.getFacCd());						// 工場CD
			insertUserInfoDaoUpdate.setAffilicateId(userInfoDto.getAffilicateId());			// 所属ID
			insertUserInfoDaoUpdate.setUserId(userInfoDto.getUserId());						// ユーザーID
			
			// ユーザー情報マスタ検索
			// リポジトリーのメソッド「ユーザー情報取得」を呼び出す。
			InsertUserInfoDao insertUserInfoDaoUpdateResult = bfmk01Repository.getUserInfo(insertUserInfoDaoUpdate);
			
			// 取得したDaoの適用日（FROM）と適用日（TO）をDate→String変換
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
			String expireDateFromString = sdf.format(insertUserInfoDaoUpdateResult.getExpireDateFrom());
			// 適用日（TO）がnullでない場合のみ
			// 適用日（TO）を、取得したDaoを戻り値．ユーザー情報DTOに設定する。
			if(insertUserInfoDaoUpdateResult.getExpireDateTo() != null) {
				String expireDateToString = sdf.format(insertUserInfoDaoUpdateResult.getExpireDateTo());
				userInfoDtoInitResult.setExpireDateTo(expireDateToString);									// 有効日（TO）
			} else {
				// 処理なし
			}
			
			// 取得したDaoを戻り値．ユーザー情報DTOに設定する。
			userInfoDtoInitResult.setAffilicateId(insertUserInfoDaoUpdateResult.getAffilicateId());			// 所属ID
			userInfoDtoInitResult.setUserId(insertUserInfoDaoUpdateResult.getUserId());						// ユーザーID
			userInfoDtoInitResult.setUserName(insertUserInfoDaoUpdateResult.getUserName());					// ユーザー名
			userInfoDtoInitResult.setPass(insertUserInfoDaoUpdateResult.getPass());							// パスワード
			userInfoDtoInitResult.setRePass(insertUserInfoDaoUpdateResult.getRePass());						// パスワード再確認
			userInfoDtoInitResult.setExpireDateFrom(expireDateFromString);									// 有効日（FROM）
			userInfoDtoInitResult.setAuthDiv(insertUserInfoDaoUpdateResult.getAuthDiv());					// 権限区分(ゲスト：1、一般：2、管理者：3)
			userInfoDtoInitResult.setWatchAuthFlg(insertUserInfoDaoUpdateResult.isWatchAuthFlg());			// 参照権限フラグ（権限あり：true、権限なし：false）
			userInfoDtoInitResult.setOprAuthFlg(insertUserInfoDaoUpdateResult.isOprAuthFlg());				// 操作権限フラグ（権限あり：true、権限なし：false）
			
		} else {
			// 処理なし
		}
		
		System.out.println("サービス：initメソッド終了");
		
		return userInfoDtoInitResult;
	}
	
	/**
	 * 登録
	 * @param userInfoDto
	 * @param commonDto
	 * @return userInfoDtoInsertResult
	 */
	public UserInfoDto insert(UserInfoDto userInfoDto, CommonDto commonDto) {
		
		System.out.println("サービス：insertメソッド開始");
		
		// 戻り値用ユーザー情報DTOを宣言し、初期化する。
		UserInfoDto userInfoDtoInsertResult = new UserInfoDto();
		
		// 登録前のチェック
		// プライベートメソッド「入力チェック」を呼び出す。
		UserInfoDto userInfoDtoCheckValidateResult = checkValidate(userInfoDto, commonDto);
		// 戻り値．メッセージに値が設定されている場合
		if(!(userInfoDtoCheckValidateResult.getMessage() == null)) {
			System.out.println("サービス：insert：if：戻り値．メッセージに値が設定されている場合　ロールバック");
			// ロールバックする。
			// OUTパラメータに戻り値を設定し、返却する。
			return userInfoDtoCheckValidateResult;
			
		} else {
			// 処理なし
		}
		
		// パスワードのハッシュ化
		String hashPass = passwordEncoder.encode(userInfoDto.getPass());
		
		// 登録日の日付をString→Date型へ変換 有効日(FROM)
		Date expireDateFromDate = null;
		try {
			expireDateFromDate = convertDate(userInfoDto.getExpireDateFrom());
		} catch (ParseException e1) {
			System.out.println("サービス：insert：入力チェック後：登録日の日付をString→Date型へ変換 有効日(FROM)：ParseException発生");	
		}
		
		// 登録日の日付をString→Date型へ変換 有効日(TO)
		Date expireDateToDate = null;
		try {
			expireDateToDate = convertDate(userInfoDto.getExpireDateTo());
		} catch (ParseException e1) {
			System.out.println("サービス：insert：入力チェック後：登録日の日付をString→Date型へ変換 有効日(TO)：ParseException発生");	
		}
		
		// システム日時を取得する
		Date nowDate = new Date();
		
		// ユーザーID作成
		// リポジトリーへデータを渡すためのDaoを宣言し、初期化する。
		InsertUserInfoDao insertUserInfoDaoInsert = new InsertUserInfoDao();
		insertUserInfoDaoInsert.setFacCd(commonDto.getFacCd());						// 工場CD
		insertUserInfoDaoInsert.setAffilicateId(userInfoDto.getAffilicateId());		// 所属ID
		insertUserInfoDaoInsert.setUserId(userInfoDto.getUserId());					// ユーザーID
		insertUserInfoDaoInsert.setUserName(userInfoDto.getUserName());				// ユーザー名
		insertUserInfoDaoInsert.setPass(userInfoDto.getPass());						// パスワード
		insertUserInfoDaoInsert.setHashPass(hashPass);								// パスワード（暗号化）
		insertUserInfoDaoInsert.setExpireDateFrom(expireDateFromDate);				// 適用日（FROM）
		insertUserInfoDaoInsert.setExpireDateTo(expireDateToDate);					// 適用日（TO）
		insertUserInfoDaoInsert.setAuthDiv(userInfoDto.getAuthDiv());				// 権限区分(ゲスト：1、一般：2、管理者：3)
		insertUserInfoDaoInsert.setWatchAuthFlg(userInfoDto.getWatchAuthFlg());		// 参照権限フラグ（権限あり：true、権限なし：false）
		insertUserInfoDaoInsert.setOprAuthFlg(userInfoDto.getOprAuthFlg());			// 操作権限フラグ（権限あり：true、権限なし：false）
		insertUserInfoDaoInsert.setSystemDate(nowDate);								// システム日時
		insertUserInfoDaoInsert.setLoginUserId(commonDto.getUserId());				// ユーザーID （ログインユーザー）
		
		// 区分マスタ検索
		// リポジトリーのメソッド「区分値取得」を呼び出す。
		System.out.println("サービス：insert：リポジトリーのメソッド「区分値取得」を呼び出し");
		String divCdResult = bfmk01Repository.getDivision(insertUserInfoDaoInsert);
		System.out.println("取得した区分値：" + divCdResult);
		
		// ユーザー情報マスタ検索
		// Daoへ区分マスタ検索メソッドで取得した「区分値」を格納
		insertUserInfoDaoInsert.setDivCd(divCdResult);
		// リポジトリーのメソッド「ユーザー情報連番取得」を呼び出す。
		System.out.println("サービス：insert：リポジトリーのメソッド「ユーザー情報連番取得」を呼び出し");
		String serialNumResult = bfmk01Repository.getUserInfoSerialNum(insertUserInfoDaoInsert);
		System.out.println("取得したユーザー情報連番：" + serialNumResult);
		// 取得した値で場合分け
		if(serialNumResult == null) {
			// nullの場合
			System.out.println("サービス：insert：if：取得した連番がnull");
			serialNumResult = "001";
		} else {
			// 取得した値に+1加算
			System.out.println("サービス：insert：if：取得した連番に+1加算");
			int serialNumResultInt = Integer.parseInt(serialNumResult) + 1;
			serialNumResult = Integer.toString(serialNumResultInt);
		}
		// 連番の桁数で場合分け
		if(serialNumResult.length() == 1) {
			// 連番が1桁の時
			System.out.println("サービス：insert：if：連番の桁数が1桁");
			// 連番の左2桁を"0"で埋める
			serialNumResult = 0 + (0 + serialNumResult);
			
		} else if(serialNumResult.length() == 2) {
			// 連番が2桁の時
			System.out.println("サービス：insert：if：連番の桁数が2桁");
			// 連番の左1桁を"0"で埋める
			serialNumResult = 0 + serialNumResult;
			
		} else {
			// 処理なし
		}
		System.out.println("取得したユーザー情報連番に+1加算：" + serialNumResult);
		
		// ユーザーIDの生成
		// 『所属ID（前２桁）』 + 『(1)で取得した区分値』 + 『(2)で取得した連番 + 1』
		String newUserId = userInfoDto.getAffilicateId().substring(0, 2)+ divCdResult + serialNumResult;
		System.out.println("生成したユーザーID：" + newUserId);
		// 生成したユーザーIDをDaoへ格納
		insertUserInfoDaoInsert.setUserId(newUserId);
		
		// 登録処理
		System.out.println("サービス：insert：登録処理");
		try {
			// リポジトリーのメソッド「登録」を呼び出す。
			bfmk01Repository.userInfoRegister(insertUserInfoDaoInsert);
			
		} catch(DuplicateKeyException e) {
			// 一意制約違反の例外が発生した場合
			// リポジトリーから例外をthrow句を使ってスローしてくる
			System.out.println("サービス：insert：if：戻り値．一意制約違反の例外が発生した場合　ロールバック");
			// メッセージID：msuzer017　を戻り値．メッセージIDに設定する。
			userInfoDtoInsertResult.setMessage("msuzer017");
			// ロールバックする。
			// OUTパラメータに戻り値を設定し、返却する。
			return userInfoDtoInsertResult;
		}
		
		// 完了処理
		// メッセージID：msuzer015　を戻り値．メッセージに設定する。
		userInfoDtoInsertResult.setMessage("msuzer015");
		// コミットする。
		// OUTパラメータ．ユーザー情報DTOに戻り値（生成したユーザーIDと登録した内容）を設定し、返却する。
		userInfoDtoInsertResult.setFacCd(userInfoDto.getFacCd());					// 工場CD
		userInfoDtoInsertResult.setAffilicateId(userInfoDto.getAffilicateId());		// 所属ID
		userInfoDtoInsertResult.setUserId(newUserId);								// ユーザーID
		userInfoDtoInsertResult.setUserName(userInfoDto.getUserName());				// ユーザー名
		userInfoDtoInsertResult.setPass(userInfoDto.getPass());						// パスワード
		userInfoDtoInsertResult.setRePass(userInfoDto.getRePass());					// パスワード再確認
		userInfoDtoInsertResult.setExpireDateFrom(userInfoDto.getExpireDateFrom());	// 有効日（FROM）
		userInfoDtoInsertResult.setExpireDateTo(userInfoDto.getExpireDateTo());		// 有効日（TO）
		userInfoDtoInsertResult.setAuthDiv(userInfoDto.getAuthDiv());				// 権限区分(ゲスト：1、一般：2、管理者：3)
		userInfoDtoInsertResult.setWatchAuthFlg(userInfoDto.getWatchAuthFlg());		// 参照権限フラグ（権限あり：true、権限なし：false）
		userInfoDtoInsertResult.setOprAuthFlg(userInfoDto.getOprAuthFlg());			// 操作権限フラグ（権限あり：true、権限なし：false）
		
		System.out.println("サービス：insertメソッド終了");
		
		return userInfoDtoInsertResult;
		
	}
	
	/**
	 * 更新
	 * @param userInfoDto
	 * @param commonDto
	 * @return userInfoDtoUpdateResult
	 */
	public UserInfoDto update(UserInfoDto userInfoDto, CommonDto commonDto) {
		
		System.out.println("サービス：updateメソッド開始");
		
		// 戻り値用ユーザー情報DTOを宣言し、初期化する。
		UserInfoDto userInfoDtoUpdateResult = new UserInfoDto();
		
		// 更新前のチェック
		// プライベートメソッド「入力チェック」を呼び出す。
		UserInfoDto userInfoDtoCheckValidateResult = checkValidate(userInfoDto, commonDto);
		// 戻り値．メッセージに値が設定されている場合
		if(!(userInfoDtoCheckValidateResult.getMessage() == null)) {
			System.out.println("サービス：update：if：戻り値．メッセージに値が設定されている場合　ロールバック");
			// ロールバックする。
			// OUTパラメータに戻り値を設定し、返却する。
			return userInfoDtoCheckValidateResult;
			
		} else {
			// 処理なし
		}
		
		// パスワードのハッシュ化
		String hashPass = passwordEncoder.encode(userInfoDto.getPass());
		
		// 更新日の日付をString→Date型へ変換 有効日(FROM)
		Date expireDateFromDate = null;
		try {
			expireDateFromDate = convertDate(userInfoDto.getExpireDateFrom());
		} catch (ParseException e1) {
			System.out.println("サービス：insert：入力チェック後：更新日の日付をString→Date型へ変換 有効日(FROM)：ParseException発生");	
		}
		
		// 更新日の日付をString→Date型へ変換 有効日(TO)
		Date expireDateToDate = null;
		try {
			expireDateToDate = convertDate(userInfoDto.getExpireDateTo());
		} catch (ParseException e1) {
			System.out.println("サービス：insert：入力チェック後：更新日の日付をString→Date型へ変換 有効日(TO)：ParseException発生");	
		}
		
		// システム日時を取得する
		Date nowDate = new Date();
		
		// ユーザーID作成
		// リポジトリーへデータを渡すためのDaoを宣言し、初期化する。
		InsertUserInfoDao insertUserInfoDaoUpdate = new InsertUserInfoDao();
		insertUserInfoDaoUpdate.setFacCd(commonDto.getFacCd());						// 工場CD
		insertUserInfoDaoUpdate.setAffilicateId(userInfoDto.getAffilicateId());		// 所属ID
		insertUserInfoDaoUpdate.setUserId(userInfoDto.getUserId());					// ユーザーID
		insertUserInfoDaoUpdate.setUserName(userInfoDto.getUserName());				// ユーザー名
		insertUserInfoDaoUpdate.setPass(userInfoDto.getPass());						// パスワード
		insertUserInfoDaoUpdate.setHashPass(hashPass);								// パスワード（暗号化）
		insertUserInfoDaoUpdate.setExpireDateFrom(expireDateFromDate);				// 適用日（FROM）
		insertUserInfoDaoUpdate.setExpireDateTo(expireDateToDate);					// 適用日（TO）
		insertUserInfoDaoUpdate.setAuthDiv(userInfoDto.getAuthDiv());				// 権限区分(ゲスト：1、一般：2、管理者：3)
		insertUserInfoDaoUpdate.setWatchAuthFlg(userInfoDto.getWatchAuthFlg());		// 参照権限フラグ（権限あり：true、権限なし：false）
		insertUserInfoDaoUpdate.setOprAuthFlg(userInfoDto.getOprAuthFlg());			// 操作権限フラグ（権限あり：true、権限なし：false）
		insertUserInfoDaoUpdate.setSystemDate(nowDate);								// システム日時
		insertUserInfoDaoUpdate.setLoginUserId(commonDto.getUserId());				// ユーザーID （ログインユーザー）
		
		// 更新結果の戻り値用の変数を設定
		int rowNumber = 0;
		
		// 更新処理
		System.out.println("サービス：update：更新処理");
		try {
			// リポジトリーのメソッド「更新」を呼び出す。
			rowNumber = bfmk01Repository.userInfoUpdate(insertUserInfoDaoUpdate);
			
		} catch(OptimisticLockingFailureException e) {
			// 排他処理の例外が発生した場合
			// リポジトリーから例外をthrow句を使ってスローしてくる
			System.out.println("サービス：update：if：戻り値．排他処理の例外が発生した場合　ロールバック");
			// メッセージID：msuzer018　を戻り値．メッセージIDに設定する。
			userInfoDtoUpdateResult.setMessage("msuzer018");
			// ロールバックする。
			// OUTパラメータに戻り値を設定し、返却する。
			return userInfoDtoUpdateResult;
			
		}
		
		// データが存在しない例外が発生した場合
		if(rowNumber == 0) {
			System.out.println("サービス：update：if：戻り値．データが存在しない例外が発生した場合　ロールバック");
			// メッセージID：msuzer019　を戻り値．メッセージIDに設定する。
			userInfoDtoUpdateResult.setMessage("msuzer019");
			// ロールバックする。
			// OUTパラメータに戻り値を設定し、返却する。
			return userInfoDtoUpdateResult;
			
		} else {
			// 処理なし
		}
		
		// 完了処理
		// メッセージID：msuzer016　を戻り値．メッセージに設定する。
		userInfoDtoUpdateResult.setMessage("msuzer016");
		// コミットする。
		// OUTパラメータ．ユーザー情報DTOに戻り値（更新した内容）を設定し、返却する。
		userInfoDtoUpdateResult.setFacCd(userInfoDto.getFacCd());					// 工場CD
		userInfoDtoUpdateResult.setAffilicateId(userInfoDto.getAffilicateId());		// 所属ID
		userInfoDtoUpdateResult.setUserId(userInfoDto.getUserId());					// ユーザーID
		userInfoDtoUpdateResult.setUserName(userInfoDto.getUserName());				// ユーザー名
		userInfoDtoUpdateResult.setPass(userInfoDto.getPass());						// パスワード
		userInfoDtoUpdateResult.setRePass(userInfoDto.getRePass());					// パスワード再確認
		userInfoDtoUpdateResult.setExpireDateFrom(userInfoDto.getExpireDateFrom());	// 有効日（FROM）
		userInfoDtoUpdateResult.setExpireDateTo(userInfoDto.getExpireDateTo());		// 有効日（TO）
		userInfoDtoUpdateResult.setAuthDiv(userInfoDto.getAuthDiv());				// 権限区分(ゲスト：1、一般：2、管理者：3)
		userInfoDtoUpdateResult.setWatchAuthFlg(userInfoDto.getWatchAuthFlg());		// 参照権限フラグ（権限あり：true、権限なし：false）
		userInfoDtoUpdateResult.setOprAuthFlg(userInfoDto.getOprAuthFlg());			// 操作権限フラグ（権限あり：true、権限なし：false）
		
		System.out.println("サービス：updateメソッド終了");
		
		return userInfoDtoUpdateResult;
		
	}
	
	/**
	 * 入力チェックのメソッド
	 * @param userInfoDto
	 * @return uIDtoInsertCheckValidateResult
	 */
	public UserInfoDto checkValidate(UserInfoDto userInfoDto, CommonDto commonDto) {
		
		System.out.println("サービス：checkValidateメソッド開始");
		
		// 戻り値用ユーザー情報DTOを宣言し、初期化する。
		UserInfoDto uIDtoInsertCheckValidateResult = new UserInfoDto();
		
		// String→Date型へ変換 有効日（FROM）
		Date expireDateFromDate = null;
		try {
			expireDateFromDate = convertDate(userInfoDto.getExpireDateFrom());
		} catch (ParseException e) {
			uIDtoInsertCheckValidateResult.setMessage("msuzer020");
			return uIDtoInsertCheckValidateResult;
		}
		
		// String→Date型へ変換 有効日（TO）
		// 有効日（TO）が入力されていて、かつ
		// 「yyyy.MM.dd」の形式でない（＝String→Date変換に失敗）の場合
		Date expireDateToDate = null;
		if(!(userInfoDto.getExpireDateTo().isEmpty())) {
			try {
				expireDateToDate = convertDate(userInfoDto.getExpireDateTo());
			} catch (ParseException e) {
				uIDtoInsertCheckValidateResult.setMessage("msuzer020");
				return uIDtoInsertCheckValidateResult;
			}
		}
		
		// 入力チェック
		// INパラメータ．ユーザー情報DTOに設定された各値のチェックを行う。
		// 以下の条件の場合、エラーメッセージを出力し、処理を中断する。（ロールバック）
		// 所属IDが空白の場合
		if(userInfoDto.getAffilicateId().isEmpty()) {
			uIDtoInsertCheckValidateResult.setMessage("msuzer002");
			return uIDtoInsertCheckValidateResult;
		}
		// ユーザー名が空白の場合
		if(userInfoDto.getUserName().isEmpty()) {
			uIDtoInsertCheckValidateResult.setMessage("msuzer004");
			return uIDtoInsertCheckValidateResult;
		}
		// パスワードが空白の場合
		if(userInfoDto.getPass().isEmpty()) {
			uIDtoInsertCheckValidateResult.setMessage("msuzer005");
			return uIDtoInsertCheckValidateResult;
		}
		// パスワード再確認が空白の場合
		if(userInfoDto.getRePass().isEmpty()) {
			uIDtoInsertCheckValidateResult.setMessage("msuzer007");
			return uIDtoInsertCheckValidateResult;
		}
		// 有効日（FROM）が空白の場合
		if(userInfoDto.getExpireDateFrom().isEmpty()) {
			uIDtoInsertCheckValidateResult.setMessage("msuzer011");
			return uIDtoInsertCheckValidateResult;
		}
		// 所属IDがマスターに登録なしの場合（ログインユーザー：admin権限のみ）
		if(commonDto.getUserId().equals("al00000")) {
			// 戻り値用のDaoを宣言し、初期化する。
			InsertUserInfoDao insertUserInfoDao = new InsertUserInfoDao();
			insertUserInfoDao.setAffilicateId(userInfoDto.getAffilicateId());		// 所属ID
			// 所属マスターにて所属IDの有無を検索
			String affilicateIdAdminResult = bfmk01Repository.affilicateIdSeach(insertUserInfoDao);
			// 所属マスターに登録がない場合
			if(affilicateIdAdminResult == null) {
				uIDtoInsertCheckValidateResult.setMessage("msuzer003");
				return uIDtoInsertCheckValidateResult;
			}
		}
		// パスワードが半角英数字でそれぞれ1文字以上使い、かつ8文字以上16文字以下以外の場合
		String pass = userInfoDto.getPass();
		String regex = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])[a-zA-Z0-9]{8,16}$";
		if(!(Pattern.matches(regex ,pass))) {
			uIDtoInsertCheckValidateResult.setMessage("msuzer006");
			return uIDtoInsertCheckValidateResult;
		}
		// パスワード再確認がパスワードと異なる場合
		if(!(userInfoDto.getPass().equals(userInfoDto.getRePass()))) {
			uIDtoInsertCheckValidateResult.setMessage("msuzer008");
			return uIDtoInsertCheckValidateResult;
		}
		// 権限区分にチェックなしの場合
		if(userInfoDto.getAuthDiv() == null) {
			uIDtoInsertCheckValidateResult.setMessage("msuzer009");
			return uIDtoInsertCheckValidateResult;
		}
		// 権限区分に複数にチェックがある場合
		if(userInfoDto.getAuthDiv().length() > 1) {
			uIDtoInsertCheckValidateResult.setMessage("msuzer010");
			return uIDtoInsertCheckValidateResult;
		}
		// 有効日（FROM）が日付以外の場合
		if(!(checkDate(userInfoDto.getExpireDateFrom()))) {
			uIDtoInsertCheckValidateResult.setMessage("msuzer012");
			return uIDtoInsertCheckValidateResult;
		}
		// 有効日（TO）が入力されていて、かつ
		// 有効日（FROM）が有効日（TO）より未来日の場合
		if(!(expireDateToDate == null) && expireDateFromDate.after(expireDateToDate)) {
			uIDtoInsertCheckValidateResult.setMessage("msuzer013");
			return uIDtoInsertCheckValidateResult;
		}
		// 有効日（TO）が日付以外の場合
		if(expireDateToDate == null) {
			// チェックOK。処理なし
		} else if(!(checkDate(userInfoDto.getExpireDateTo()))) {
			uIDtoInsertCheckValidateResult.setMessage("msuzer014");
			return uIDtoInsertCheckValidateResult;
		}
		
		System.out.println("サービス：checkValidateメソッド終了");
		
		// OUTパラメータ．ユーザー情報DTOに戻り値を設定し、返却する。
		return uIDtoInsertCheckValidateResult;
		
	}
	
	/**
	 * 日付のString→Date型へ変換
	 * @param strDate
	 * @return convertDate
	 */
	private static Date convertDate(String strDate) throws ParseException {
		
		System.out.println("サービス：convertDateメソッド開始");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		Date convertDate = null;
		try {
			convertDate = sdf.parse(strDate);
		} catch(ParseException e) {
			System.out.println("サービス：convertDateメソッド ParseException発生");
			throw e;
		}
		
		System.out.println("サービス：convertDateメソッド終了");
		
		return convertDate;
	}
	
	/**
	 * 日付の妥当性チェック
	 * @param strDate
	 * @return true / false
	 */
	private static boolean checkDate(String strDate) {
		
		System.out.println("サービス：checkDateメソッド開始");
		
		// 10文字（yyyy.MM.dd）でない場合はロールバック
        if(strDate.length() != 10) {
            return false;
        }
        
        // チェック用に「yyyy.MM.dd」→「yyyy/MM/dd」へ変換
        strDate = strDate.replace('.', '/');
        
        // チェック
        DateFormat format = DateFormat.getDateInstance();
        format.setLenient(false);
        try {
            format.parse(strDate);
            System.out.println("サービス：日付の妥当性チェック【OK】：checkDateメソッド終了");
            return true;
        } catch(Exception e) {
        	System.out.println("サービス：日付の妥当性チェック【NG】：checkDateメソッド終了");
            return false;
        }
    }
}
