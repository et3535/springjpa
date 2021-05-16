package com.kakaopay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kakaopay.dto.ErrorResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CommonExceptionController {
	/*
		공통 오류
	*/
	@ExceptionHandler(CommonException.class)
	protected ResponseEntity<ErrorResponseDto> ErrorHandler(CommonException e){
		String errMsg = "오류코드 정의 필요";
		if(!(e.getMessage()==null||"".equals(e.getMessage()))) {
			errMsg = e.getMessage();
		}
		ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), errMsg);
		return new ResponseEntity<>(errorResponse, errorResponse.getErrorStatus());
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	protected ResponseEntity<ErrorResponseDto> ErrorHandler(MissingServletRequestParameterException e){
		String errMsg = "파라메터가 없습니다.";
		ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), errMsg);
		return new ResponseEntity<>(errorResponse, errorResponse.getErrorStatus());
	}
	
	
	
	
}
