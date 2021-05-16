package com.kakaopay.config;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.domain.Product;
import com.kakaopay.domain.UserTranInfo;
import com.kakaopay.repository.ProductRepository;
import com.kakaopay.repository.UserTranInfoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitDB {
	
	private final InitService initService;
	
	@PostConstruct
	public void init() throws JsonProcessingException {
		initService.createProductAndBuyUser();
	}
	
	@Component
	@Transactional
	@RequiredArgsConstructor
	static class InitService{
		private final ProductRepository productRepository;	
		private final UserTranInfoRepository userTranInfoRepository;	
		public void createProductAndBuyUser() throws JsonProcessingException {
			LocalDateTime today = LocalDateTime.now();
			/*
			 * 상품 유효기간이  지난 상품
			 * */
			productRepository.save(Product.builder()
					.title("제목1")
					.totalAmount(100000)
					.startat(today.minusDays(20))
					.endat(today.minusDays(1)
					).build());

			productRepository.save(Product.builder()
					.title("제목2")
					.totalAmount(200000)
					.startat(today.minusDays(2))
					.endat(today.minusDays(5)
					).build());
			
			/*
			 * 유효기간 정상인 사품 추가
			*/
			productRepository.save(Product.builder()
					.title("제목3")
					.totalAmount(300000)
					.startat(LocalDateTime.now().minusDays(3))
					.endat(LocalDateTime.now().plusDays(10)
					).build());
			
			/*
			 * 1번 상품에 고객이 투자한 금액 추가( 필드 : UPDATE )
			 * 고객 상품 투자 1건 (1번상품, 금액 30000원 INSERT)
			*/
			Optional<Product> updatePrd = productRepository.findById((long) 1);
			if(updatePrd.isPresent()) {
				Product temPrd = updatePrd.get();
				temPrd.setCusInvestingAmount(temPrd.getCusInvestingAmount()+30000);
				productRepository.save(temPrd);
			}
			userTranInfoRepository.save(UserTranInfo.builder()
					.userid(1)
					.productId(updatePrd.get())
					.investAmount(30000)
					.build());
		}
	}

}
