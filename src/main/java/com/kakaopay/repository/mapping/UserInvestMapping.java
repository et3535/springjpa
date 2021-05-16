package com.kakaopay.repository.mapping;

import java.time.LocalDateTime;

public interface UserInvestMapping {
	long getProductId();
	String getTitle();
	long getTotalInvestingAmount();
	LocalDateTime getInvestedAt();
	long getInvestAmount();
}
