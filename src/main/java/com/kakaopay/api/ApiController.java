package com.kakaopay.api;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.domain.Product;
import com.kakaopay.domain.UserTranInfo;
import com.kakaopay.dto.ResponseDto;
import com.kakaopay.exception.CommonException;
import com.kakaopay.service.InvestService;
import com.sun.istack.NotNull;

@RestController
@RequestMapping(value = "/api")
@Validated
public class ApiController {
	@Resource(name="InvestService")
	private InvestService investservice;

	/*
	 * 1. 전체 투자 상품 조회 API
	 * 요청 값 : 없음
	 * 응답 : 상품ID, 상품제목, 총 모집금액, 현재 모집금액, 투자자 수, 투자모집상태(모집중, 모집완료), 상품 모집기간
	*/
	@PostMapping(value="/productlist")
	public ResponseDto productlist()
	{
		try{
			return investservice.productlist();
		}catch(Exception ex) {
			throw new CommonException(ex.toString());
		}
	}
	/*
	 * 2. 투자하기
	 * 요청 값 : 사용자 식별값, 상품ID, 투자금액
	 * 응답 : 투자한 내용 응답 받으며, 총 투자모집 금액을 넘어서면 sold-out 상태 응답
	*/
	@PostMapping(value="/insertInvest")
	public ResponseDto insertInvest(@RequestHeader(value = "X-USER-ID") @NotNull Long userId,
			@RequestParam(value="productId") @NotNull Long productId,
			@RequestParam(value="investAmount") @NotNull Long investAmount) 
	{
		try {
			return investservice.insertInvest(userId, productId, investAmount);

		}catch(Exception ex) {
			throw new CommonException(ex.toString());
		}
	}
	/*
	 * 3. 나의투자상품 조회 API
	 * 요청 값 : 사용자 식별값
	 * 응답 : 상품ID, 상품 제목, 총 모집금액, 나의투자금액, 투자일시
 	*/
	@PostMapping(value="/myinvestlist")
	public ResponseDto myinvestlist(@RequestHeader(value = "X-USER-ID") @NotNull Long userId) throws JsonProcessingException
	{
		try {
			return investservice.myinvestlist(userId);
		}catch(Exception ex) {
			throw new CommonException(ex.toString());
		}
	}
}
