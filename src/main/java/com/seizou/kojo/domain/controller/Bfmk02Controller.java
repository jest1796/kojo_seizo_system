package com.seizou.kojo.domain.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.seizou.kojo.domain.dto.CommonDto;
import com.seizou.kojo.domain.dto.UserInfoDto;
import com.seizou.kojo.domain.form.SearchInfoForm;
import com.seizou.kojo.domain.service.Bfmk02Service;

/**
* ユーザー情報一覧 Controller
* @author K.Tomonari
*/
@Controller
@RequestMapping("/b-forme_Kojo")
public class Bfmk02Controller {
	
    @Autowired
    Bfmk02Service service;
    
	// メッセージ取得用の変数を宣言
	private static final String MESSAGES_NAME = "resources_ja";
	private static ResourceBundle mes = ResourceBundle.getBundle(MESSAGES_NAME);
	
	//最大表示数
	private final int MAX_LIST = 6;
	
	//最大件数
	private final int MAX_RECODE = 500;
	
	/**
	 * 初期画面
	 * @param form
	 * @param model
	 * @return 画面名
	 */
	@GetMapping("/pc/202")
	public String init(@ModelAttribute SearchInfoForm form, Model model) {
		
		// 共通DTO
		CommonDto commonDto = new CommonDto(null, null, "bfmk02", null, "bfm1", "all", null, "al00000", null);
		// 初期表示処理
		UserInfoDto userInfoDto = service.init(commonDto);
		
		// メッセージを設定
		if(userInfoDto.getMessage() != null) {
			String message = mes.getString(userInfoDto.getMessage());
			model.addAttribute("message" , message);
			model.addAttribute("successFlg" , false);
		}
		// ユーザーが管理者の場合
		if(userInfoDto.getAuthDiv().equals("3")) {
			form.setAffilicateId(commonDto.getAffId());
			model.addAttribute("searchInfoForm" , form);
		}
		
		// 権限を格納
		model.addAttribute("authDiv", userInfoDto.getAuthDiv());
		
		return "bfmk02View";
	}
	
	// 
	/**
	 * メニュー画面に移動
	 * @param model
	 * @return 画面名
	 */
	@GetMapping("/pc/202/back")
	public String back(Model model) {
		return "bfkt02View";
	}
	
	/**
	 * 検索処理
	 * @param form
	 * @param model
	 * @return 画面名
	 */
	@PostMapping("/pc/202/search")
	public String search(@ModelAttribute SearchInfoForm form,
	        Model model) {
		
        //ユーザー情報一覧の取得
        List<UserInfoDto> userAllList = service.search(form);
        
        //表示用ユーザー情報
        List<UserInfoDto> userList = new ArrayList<UserInfoDto>();
        
		// ユーザー情報リストが500件より多い場合
		if(userAllList.size() > MAX_RECODE) {
			userAllList = userAllList.subList(0, MAX_RECODE);
			
			// メッセージが格納されていない場合
			if(model.getAttribute("message") == null) {
				String message = mes.getString("mszumd006");
				model.addAttribute("message" , message);
				model.addAttribute("successFlg" , true);
			}
		}
        
        //表示用ユーザーに要素追加
        if(userAllList.size() > MAX_LIST) {
        	for(int i = 0; i < MAX_LIST; i++) {
        		userList.add(userAllList.get(i));
        	}
        }
        else {
        	userList = userAllList;
        }
        
        // ページ数計算
        int page = userAllList.size() / MAX_LIST;
        if(userAllList.size() % MAX_LIST != 0) {
        	page = page + 1;
        }
        
        // 検索結果が1件以上の場合
        if(userAllList.size() >= 1) {
        	// メッセージがある場合
        	if(userAllList.get(0).getMessage() != null) {
        		String message = mes.getString(userAllList.get(0).getMessage());
				model.addAttribute("message" , message);
				model.addAttribute("successFlg" , false);
			} else {
                //Modelにユーザー情報リストを登録
                model.addAttribute("userList", userList);
                model.addAttribute("currentPage", 1);
                model.addAttribute("page",  page);
                model.addAttribute("userListSize", userAllList.size());
        	}
		}
		
		return "bfmk02View";
	}
	
	/**
	 * 1ページ処理
	 * @param form
	 * @param model
	 * @return 画面名
	 */
	@PostMapping("/pc/202/startPage")
	public String startPage(@ModelAttribute SearchInfoForm form,
	        Model model) {
		
        //ユーザー情報一覧の取得
        List<UserInfoDto> userAllList = service.search(form);
        
        //表示用ユーザー情報
        List<UserInfoDto> userList = new ArrayList<UserInfoDto>();
        
		// ユーザー情報リストが500件より多い場合
		if(userAllList.size() > MAX_RECODE) {
			userAllList = userAllList.subList(0, MAX_RECODE);
    		String message = mes.getString("mszumd006");
			model.addAttribute("message" , message);
			model.addAttribute("successFlg" , true);
		}
        
        //表示用ユーザーに要素追加
        if(userAllList.size() > MAX_LIST) {
        	for(int i = 0; i < MAX_LIST; i++) {
        		userList.add(userAllList.get(i));
        	}
        } else {
        	userList = userAllList;
        }

        // ページ数計算
        int page = userAllList.size() / MAX_LIST;
        if(userAllList.size() % MAX_LIST != 0) {
        	page = page + 1;
        }

        //Modelにユーザー情報リストを登録
        model.addAttribute("userList", userList);
        model.addAttribute("currentPage", 1);
        model.addAttribute("page", page);
        model.addAttribute("userListSize", userAllList.size());

        return "bfmk02View";
	}
	
	/**
	 * 前ページ処理
	 * @param form
	 * @param currentPage
	 * @param model
	 * @return 画面名
	 */
	@PostMapping("/pc/202/prevPage")
	public String prevPage(@ModelAttribute SearchInfoForm form,
			@RequestParam(required = false) Integer currentPage,
	        Model model) {
		
        //ユーザー情報一覧の取得
        List<UserInfoDto> userAllList = service.search(form);
        
        //表示用ユーザー情報
        List<UserInfoDto> userList = new ArrayList<UserInfoDto>();
        
		// ユーザー情報リストが500件より多いの場合
		if(userAllList.size() > MAX_RECODE) {
			userAllList = userAllList.subList(0, MAX_RECODE);
    		String message = mes.getString("mszumd006");
			model.addAttribute("message" , message);
			model.addAttribute("successFlg" , true);
		}
        
        if(userAllList.size() > MAX_LIST) {
        	//ページ数を減らす
        	currentPage = currentPage - 1;
        	//前ページの先頭要素番号を取得
        	int indexPage = currentPage * MAX_LIST - MAX_LIST;
        	//前ページの最終要素番号を取得
        	int lastPage = indexPage + MAX_LIST;
        	
        	//表示用ユーザーに要素追加
        	for(int i = indexPage; i < lastPage; i++) {
        		if(i >= 0) {
        			userList.add(userAllList.get(i));
        		}
        	}
        } else {
        	userList = userAllList;
        }
        
        // ページ数計算
        int page = userAllList.size() / MAX_LIST;
        if(userAllList.size() % MAX_LIST != 0) {
        	page = page + 1;
        }

        //Modelにユーザー情報リストを登録
        model.addAttribute("userList", userList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("page", page);
        model.addAttribute("userListSize", userAllList.size());
        
        // 表示用ユーザー情報が空の場合
        if(userList.isEmpty()) {
        	startPage(form, model);
        }
        
		return "bfmk02View";
	}
	
	/**
	 * 次ページ処理
	 * @param form
	 * @param currentPage
	 * @param model
	 * @return 画面名
	 */
	@PostMapping("/pc/202/nextPage")
	public String nextPage(@ModelAttribute SearchInfoForm form,
			@RequestParam(required = false) Integer currentPage,
	        Model model) {
		
        //ユーザー情報一覧の取得
        List<UserInfoDto> userAllList = service.search(form);
        
        //表示用ユーザー情報
        List<UserInfoDto> userList = new ArrayList<UserInfoDto>();
        
		// ユーザー情報リストが500件より多い場合
		if(userAllList.size() > MAX_RECODE) {
			userAllList = userAllList.subList(0, MAX_RECODE);
    		String message = mes.getString("mszumd006");
			model.addAttribute("message" , message);
			model.addAttribute("successFlg" , true);
		}
        
        if(userAllList.size() > MAX_LIST) {
        	//ページ数を増やす
        	currentPage = currentPage + 1;
        	//次ページの先頭要素番号を取得
        	int indexPage = currentPage * MAX_LIST - MAX_LIST;
        	//次ページの最終要素番号を取得
        	int lastPage = indexPage + MAX_LIST;
        	
        	//表示用ユーザーに要素追加
        	for(int i = indexPage; i < lastPage; i++) {
        		if(i < userAllList.size()) {
        			userList.add(userAllList.get(i));
        		}
        	}
        } else {
        	userList = userAllList;
        }
        
        // ページ数計算
        int page = userAllList.size() / MAX_LIST;
        if(userAllList.size() % MAX_LIST != 0) {
        	page = page + 1;
        }
        
        //Modelにユーザー情報リストを登録
        model.addAttribute("userList", userList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("page", page);
        model.addAttribute("userListSize", userAllList.size());
        
        // 表示用ユーザー情報が空の場合
        if(userList.isEmpty()) {
        	endPage(form, model);
        }
        
		return "bfmk02View";
	}
	
	/**
	 * 最終ページ処理
	 * @param form
	 * @param model
	 * @return 画面名
	 */
	@PostMapping("/pc/202/endPage")
	public String endPage(@ModelAttribute SearchInfoForm form,
	        Model model) {
		
        //ユーザー情報一覧の取得
        List<UserInfoDto> userAllList = service.search(form);
        
        //表示用ユーザー情報
        List<UserInfoDto> userList = new ArrayList<UserInfoDto>();
        
		// ユーザー情報リストが500件より多い場合
		if(userAllList.size() > MAX_RECODE) {
			userAllList = userAllList.subList(0, MAX_RECODE);
    		String message = mes.getString("mszumd006");
			model.addAttribute("message" , message);
			model.addAttribute("successFlg" , true);
		}
		
        if(userAllList.size() > MAX_LIST) {
        	//最後ページの先頭要素を取得
        	int indexPage = userAllList.size() - (userAllList.size() % MAX_LIST);
        	if(userAllList.size() % MAX_LIST == 0) {
        		indexPage = indexPage - MAX_LIST;
        	}
        	//表示用ユーザーに要素追加
        	for(int i = indexPage; i < userAllList.size(); i++) {
        		userList.add(userAllList.get(i));
        	}
        } else {
        	userList = userAllList;
        }

        // ページ数計算
        int page = userAllList.size() / MAX_LIST;
        if(userAllList.size() % MAX_LIST != 0) {
        	page = page + 1;
        }
        
        //Modelにユーザー情報リストを登録
        model.addAttribute("userList", userList);
        model.addAttribute("currentPage", page);
        model.addAttribute("page",  page);
        model.addAttribute("userListSize", userAllList.size());
        
		return "bfmk02View";
	   
	}
	
	/**
	 * クリア処理
	 * @param form
	 * @param model
	 * @return 画面名
	 */
	@PostMapping("/pc/202/clear")
	public String clear(@ModelAttribute SearchInfoForm form, Model model) {
		
		//ユーザー情報一覧の削除
		model.addAttribute("userList", null);
		// 初期画面処理実行
		init(form, model);
		
		return "bfmk02View";
	}
	
	/**
	 * 削除処理
	 * @param form
	 * @param deleteUser
	 * @param affilicateIdList
	 * @param userIdList
	 * @param facCdList
	 * @param bindingResult
	 * @param model
	 * @return 画面名
	 */
	@PostMapping("/pc/202/delete")
	public String delete(@ModelAttribute SearchInfoForm form,
			@RequestParam(required = false) List<String> deleteUser,
			@RequestParam(required = false) List<String> affilicateIdList,
			@RequestParam(required = false) List<String> userIdList,
			@RequestParam(required = false) List<String> facCdList,
	        Model model) {
		
		//ユーザー情報の削除
		String message = service.delete(deleteUser, affilicateIdList, userIdList, facCdList);
		// メッセージがある場合
		if(message != null) {
			message = mes.getString(message);
			model.addAttribute("message" , message);
			model.addAttribute("successFlg" , false);
		}

		// 検索処理を実行
		search(form, model);
        
		return "bfmk02View";
	}
	
	/**
	 * リンク処理
	 * @param model
	 * @return リダイレクト
	 */
	@GetMapping("/pc/202/link")
	public String link(
	        Model model) {
        
		return "redirect:/b-forme_Kojo/pc/201";
	}
}
