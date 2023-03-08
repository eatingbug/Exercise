package com.review.totalpractice.exception;

import lombok.Getter;

// 서비스 계층에서 발생하는 다양한 Exception 을 처리하기 위한 클래스
public class BusinessLogicException extends RuntimeException{
    @Getter
    private ExceptionCode exceptionCode;

    public BusinessLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
