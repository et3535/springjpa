package com.kakaopay.domain;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product implements Serializable {
	/**
	 *  부동산 투자 상품 정보 테이블
	 */
	private static final long serialVersionUID = 2097179054238328622L;

	@Id
	@GeneratedValue
	@Column(name = "productId")
	private long productId;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "totalInvestingAmount" , nullable = false)
	private long totalInvestingAmount;
	
	@Column(name = "cusInvestingAmount" , nullable = false)
	private long cusInvestingAmount;
	
	@Column(name = "investCnt" , nullable = false)
	private int investCnt;
	
	@Column(name = "investStatus" , nullable = false)
	private String investStatus;
	
	@Column(name = "startedAt" , nullable = false)
	private LocalDateTime startedAt;

	@Column(name = "finishedAt" , nullable = false)
	private LocalDateTime finishedAt;
	
	@Column(name = "rangeAt" , nullable = false)
	private long rangeAt;
	
	@Builder
	public Product(String title, long totalAmount, LocalDateTime startat, LocalDateTime endat) {
		this.title = title;
		this.totalInvestingAmount = totalAmount;
		this.startedAt = startat;
		this.finishedAt = endat;
		this.cusInvestingAmount = 0;
		this.investCnt = 0;
		this.investStatus = "모집중";
		this.rangeAt = Duration.between(startat, endat).toDays();
	}
	public Product() {
		
	}
}
