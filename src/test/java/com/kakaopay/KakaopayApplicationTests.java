package com.kakaopay;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.domain.Product;
import com.kakaopay.dto.ResponseDto;
import com.kakaopay.exception.CommonException;
import com.kakaopay.repository.ProductRepository;
import com.kakaopay.service.InvestService;

import lombok.extern.slf4j.Slf4j;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class KakaopayApplicationTests {

	@Autowired
	public ProductRepository productRepository;
	
	@Resource(name="InvestService")
	private InvestService investservice;
	
	@Test
	@DisplayName("투자금액초과 테스트")
	public void 투자금액초과테스트() {
		long userId = 1;
		long productId = 3;
		long amt = 2000000; // 초과 금액 만들기
		ResponseDto result = investservice.insertInvest(userId, productId, amt);
		assertEquals(result.getBody().toString(), "SOLD OUT");
		
	}
	
	@Test
	@DisplayName("상품모집기간내의상품조회 테스트")
	public void 상품모집기간내의상품조회테스트() {
		LocalDateTime today = LocalDateTime.now();
		/*
		 * 하루 지난 상품
		 * */
		productRepository.save(Product.builder()
				.title("제목1")
				.totalAmount(100000)
				.startat(today.minusDays(20))
				.endat(today.plusDays(1)
				).build());
		/*
		 * 유효기간 정상인 사품
		*/
		productRepository.save(Product.builder()
				.title("제목2")
				.totalAmount(200000)
				.startat(today.minusDays(2))
				.endat(today.plusDays(5)
				).build());
		
		
		
		productRepository.save(Product.builder()
				.title("제목3")
				.totalAmount(30000)
				.startat(LocalDateTime.now().minusDays(3))
				.endat(LocalDateTime.now().plusDays(10)
				).build());
		
		
		/*
		 * 유효기간 정상인 상품만 조회
		*/
		ResponseDto result = investservice.productlist();
		List<Product> plist = (List<Product>) result.getBody();
		for(Product p : plist) {
			assertEquals(false, p.getStartedAt().isAfter(today) ||p.getFinishedAt().isBefore(today));
		}
	}

}
