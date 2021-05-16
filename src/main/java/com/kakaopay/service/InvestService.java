package com.kakaopay.service;

import com.kakaopay.domain.UserTranInfo;
import com.kakaopay.dto.ResponseDto;

public interface InvestService {
	/*
	메서드명 : 고객 상품 투자 저장
	*/
	ResponseDto insertInvest(long userId, long productId, long investAmount );
	/*
	메서드명 : 상품 리스트 조회
	*/
	ResponseDto productlist();
	/*
	메서드명 : 고객이 투자한 상품 리스트 조회
	*/
	ResponseDto myinvestlist(long userId);
	
	
}
