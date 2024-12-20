package com.seizou.kojo.domain.dao;

import java.util.List;

import com.seizou.kojo.domain.entity.BelongingEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部署情報一覧 Dao
 * 
 */

@Data
@NoArgsConstructor

public class BelongingShowDao {
	private List<BelongingEntity> belongingList;	// 部署情報リスト
	private String nowPage;							// 現在のページ
	private String totalPage;						// ページ総数
	private String totalBelonging;					// 部署総数
}
