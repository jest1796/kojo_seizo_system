package com.seizou.kojo.domain.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.seizou.kojo.domain.dto.CommonDto;
import com.seizou.kojo.domain.dto.DepartmentDto;
import com.seizou.kojo.domain.form.DepartmentForm;
import com.seizou.kojo.domain.service.Bfmk07Service;

/**
 * 部署情報 コントローラークラス
 * @author N.Nishi
 */
@Controller
@RequestMapping("/b-forme_Kojo/pc/207")
public class Bfmk07Controller {

	private CommonDto commonDto;

	 @Autowired
	 Bfmk07Service service;
	 
	 /** メッセージ取得用の変数を宣言 */
	 private static final String MESSAGE_NAME = ("messages");
	 private static ResourceBundle mes = ResourceBundle.getBundle(MESSAGE_NAME);
	
	 /** 最大表示数 */
	private final int MAX_LIST = 8;
	
	/** 現在のページ */
	private int currentPage = 1;
	
	/** 総ページ数 */
	private int allPages = 0;
	
	
	/**
	 * 初期画面
	 * @param 部署情報フォーム
	 * @param モデル
	 * @return 画面名
	 */
	@GetMapping
	public String init(@ModelAttribute DepartmentForm departmentForm, Model model) {
		
		// 共通DTO（仮設定）
		CommonDto commonDto = new CommonDto("bfmk07", null, "bfkt02", null, "bfm1", "us1", "総務部", "uskr000", null);
		
		// ボタン・入力フォーム非活性確認用データ
//		CommonDto commonDto = new CommonDto("bfmk07", null, "bfkt02", null, "bfm1", "ei1", "営業", "uskr000", null);
		
		this.commonDto = commonDto;

		// 操作権限チェック
		 if (!service.getAuthDiv(commonDto)) {

			 // 入力項目・ボタンを非活性化
			 model.addAttribute("isDisabled", true);
		 }
		
		departmentForm.setAffilicateId("");
		departmentForm.setAffilicateName("");
		departmentForm.setAffilicateNameR("");
		// 適用日（From）にシステム日付を設定する
		LocalDateTime nowDate = LocalDateTime.now();
		DateTimeFormatter dtf1 =
	            DateTimeFormatter.ofPattern("yyyy-MM-dd");
		departmentForm.setApplyStrtDateStr(dtf1.format(nowDate));
		departmentForm.setApplyFinDateStr("");
		
		model.addAttribute("departmentForm", departmentForm);
		
		// 部署情報取得
        List<DepartmentForm> departmentList = service.selectAll();
        
        // データ総件数
        int kensu = departmentList.size();
        
        // 総ページ数
        allPages = kensu / MAX_LIST;
        if (kensu % MAX_LIST != 0) {
        	allPages++;
        }
        
        // 1ページに表示する部署データ（最大8件）のリスト
        List<DepartmentForm> currentList = new ArrayList<DepartmentForm>();
        
        // 表示するページのリストをcurrentListに入れる
        for (int i = MAX_LIST * (currentPage - 1); i < MAX_LIST * currentPage; i++) {
        	try {
        		currentList.add(departmentList.get(i));
        	} catch (Exception e) {
        		break;
        	}
        }
        		
        // 現在ページに表示する部署データ
        model.addAttribute("departmentList", currentList);
        
        // 総データ数
        model.addAttribute("kensu", kensu);
        
        // 総ページ数
        model.addAttribute("allPages", allPages);
        
        // 現在のページ
        model.addAttribute("ima", currentPage);
        
        // 画面に返却
		return "bfmk07View";
	}
	
	
	/* 登録処理
	 * @form
	 * @param モデル
	 * @return 画面名
	 */
	
	@PostMapping(params = "register")
	public String regist(@ModelAttribute DepartmentForm departmentForm, Model model) {
		
		// 登録処理を実行
		DepartmentDto departmentDto = service.regist(departmentForm);	
		
		// 登録チェックのメッセージ表示処理
		if (departmentDto.getMessage() != null) {
			departmentForm.setMessage(mes.getString(departmentDto.getMessage()));
			model.addAttribute("successFlg",false);
		}else {
			departmentForm.setMessage(mes.getString("msafmd001"));
			model.addAttribute("successFlg",true);
		}
		
		// 初期画面処理
		init(departmentForm, model);
		
		return "bfmk07View";
	}
	

	/* 削除処理
	 * @form
	 * @param モデル
	 * @return 画面名
	 */
	@PostMapping(params = "delete")
	public String delete(@ModelAttribute DepartmentForm departmentForm, Model model) {

		// 削除実行
		DepartmentDto returnDto = service.delete(departmentForm, commonDto);
		
		// 削除実行時のメッセージの有無で分岐
		if (returnDto.getMessage() != null) {
			String message = mes.getString(returnDto.getMessage());
			departmentForm.setMessage(message);
			if (returnDto.getMessage().equals("msdvsu002")) {
				model.addAttribute("successFlg",true);
			} else {
				model.addAttribute("successFlg",false);
			}
		}
		
		// 初期画面処理
		init(departmentForm, model);
	
		return "bfmk07View";	
	}
	
	
	/**
	 * ページネーション　一つ戻る
	 * @param 部署情報フォーム
	 * @param モデル
	 * @return　画面名
	 */
	@PostMapping(params = "hidari")
	public String hidari(@ModelAttribute DepartmentForm departmentForm,Model model) {
		
		if (currentPage != 1) {
			currentPage--;
		}
		
		//departmentFormを初期化
		departmentForm = new DepartmentForm();
		
		// 初期画面処理
		init(departmentForm, model);
		return "bfmk07View";
	}
	
	/**
	 * ページネーション　一つ進む
	 * @param 部署情報フォーム
	 * @param モデル
	 * @return 画面名
	 */
	@PostMapping(params = "migi")
	public String migi(@ModelAttribute DepartmentForm departmentForm,Model model) {
		
		if (currentPage != allPages) {
			currentPage++;
		}
		
		// departmentFormを初期化
		departmentForm = new DepartmentForm();
		
		// 初期画面処理
		init(departmentForm, model);
		return "bfmk07View";	
	}
	
	/**
	 * ページネーション　最初のページへ
	 * @param 部署情報フォーム
	 * @param モデル
	 * @return 画面名
	 */
	@PostMapping(params = "saisho")
	public String saisho(@ModelAttribute DepartmentForm departmentForm,Model model) {
		
		currentPage = 1;
		
		// departmentFormを初期化
		departmentForm = new DepartmentForm();
				
		// 初期画面処理
		init(departmentForm, model);
		return "bfmk07View";	
	}
	
	/**
	 * ページネーション　最後のページへ
	 * @param 部署情報フォーム
	 * @param モデル
	 * @return 画面名
	 */
	
	@PostMapping(params = "saigo")
	public String saigo(@ModelAttribute DepartmentForm departmentForm,Model model) {
		
		currentPage = allPages;
		
		// departmentFormを初期化
		departmentForm = new DepartmentForm();
				
		// 初期画面処理
		init(departmentForm, model);
		return "bfmk07View";	
	}
	
	
	/* クリア処理
	 * @form
	 * @param モデル
	 * @return 画面名
	 */
	@PostMapping(params = "clear")
	public String clear(@ModelAttribute DepartmentForm departmentForm, Model model) {
		
		// departmentFormを初期化
		departmentForm = new DepartmentForm();
		
		// 初期画面処理
		init(departmentForm, model);
		
		return "bfmk07View";
	}
	
	
	/**
	 * 戻る処理
	 * @return 画面名
	 */

	@PostMapping(params = "back")
	public String back() {
		return "bfkt02View";
	}
	
}
