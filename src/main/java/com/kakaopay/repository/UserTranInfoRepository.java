package com.kakaopay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kakaopay.domain.UserTranInfo;
import com.kakaopay.repository.mapping.UserInvestMapping;

public interface UserTranInfoRepository extends JpaRepository<UserTranInfo, Long>{
	List<UserTranInfo> findAllByUserId(Long userid);
	@Query(value = "SELECT p.product_id AS productId, p.title, p.TOTAL_INVESTING_AMOUNT AS totalInvestingAmount ,\r\n" + 
			"u.INVESTED_AT AS investedAt, u.INVEST_AMOUNT AS investAmount FROM PRODUCT p join USER_TRAN_INFO u\r\n" + 
			"on p.product_id = u.product_id where u.user_id = ?1 \r\n" + 
			"order by u.INVESTED_AT asc", nativeQuery = true)
	List<UserInvestMapping> findmyinvestlist(long userId);
}
