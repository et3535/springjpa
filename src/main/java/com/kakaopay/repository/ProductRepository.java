package com.kakaopay.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kakaopay.domain.Product;
import com.kakaopay.domain.UserTranInfo;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByStartedAtLessThanAndFinishedAtGreaterThan(LocalDateTime stime, LocalDateTime etime);
	Optional<Product> findByStartedAtLessThanAndFinishedAtGreaterThanAndProductId(LocalDateTime stime, LocalDateTime etime, long productId);
}
