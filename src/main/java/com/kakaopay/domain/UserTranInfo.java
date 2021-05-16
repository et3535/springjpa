package com.kakaopay.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserTranInfo implements Serializable{
	/**
	 *  고객 투자 정보 테이블
	 */
	private static final long serialVersionUID = -4461686487303856179L;
	
	@Id
	@GeneratedValue
	@Column(name = "num")
	private long num;	
	@Column(name = "userId", nullable = false)
	private Long userId;
	@ManyToOne
	@JoinColumn(name = "productId", nullable = false)
	private Product productId;
	@Column(name = "investAmount", nullable = false)
	private long investAmount;
	@Column(name = "investedAt", nullable = false)
	private LocalDateTime investedAt;
	
	@Builder
	public UserTranInfo(long userid, Product productId, long investAmount) {
		this.userId = userid;
		this.productId = productId;
		this.investAmount = investAmount;
		this.investedAt = LocalDateTime.now();
	}
}
