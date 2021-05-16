package com.kakaopay.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.domain.Product;
import com.kakaopay.domain.UserTranInfo;
import com.kakaopay.dto.ResponseDto;
import com.kakaopay.exception.CommonException;
import com.kakaopay.repository.ProductRepository;
import com.kakaopay.repository.UserTranInfoRepository;
import com.kakaopay.service.InvestService;
@Transactional
@Service("InvestService")
public class InvestServiceImpl implements InvestService {
	@Autowired
	private ProductRepository productRepository;	
	@Autowired
	private UserTranInfoRepository userTranInfoRepository;	
	/*
	메서드명 : 고객 상품 투자 저장
	*/
	public ResponseDto insertInvest(long userId, long productId, long investAmount ) {
		UserTranInfo result = null;
		LocalDateTime today = LocalDateTime.now();
		Optional<Product> prd = productRepository.findByStartedAtLessThanAndFinishedAtGreaterThanAndProductId(today, today, productId);
		if(prd.isPresent()) {
			Product tempPrd = prd.get();
			if(tempPrd.getTotalInvestingAmount()<tempPrd.getCusInvestingAmount()+investAmount) {
				return new ResponseDto("SOLD OUT");
			}
			if("모집완료".equals(tempPrd.getInvestStatus())){
				return new ResponseDto("모집완료된 상품입니다.");
			}
			tempPrd.setCusInvestingAmount(tempPrd.getCusInvestingAmount()+investAmount);
			tempPrd.setInvestCnt(tempPrd.getInvestCnt()+1);
			productRepository.save(tempPrd);
			result = userTranInfoRepository.save(UserTranInfo.builder()
					.userid(userId)
					.productId(prd.get())
					.investAmount(investAmount)
					.build());
		}else {
			return new ResponseDto("상품 정보가 없습니다.");
		}
		return new ResponseDto(result);
	}
	
	/*
	메서드명 : 상품 리스트 조회
	*/
	public ResponseDto productlist(){
		LocalDateTime today = LocalDateTime.now();
		List<Product> prdlist = productRepository.findByStartedAtLessThanAndFinishedAtGreaterThan(today,today);
		ResponseDto Response = new ResponseDto(prdlist);
		return Response;
	}
	
	/*
	메서드명 : 고객이 투자한 상품 리스트 조회
	*/
	public ResponseDto myinvestlist(long userId){
		ResponseDto Response = new ResponseDto(userTranInfoRepository.findmyinvestlist(userId));
		return Response;
	}
}
