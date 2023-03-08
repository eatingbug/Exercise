package com.review.totalpractice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice // Controller 클래스에서 발생하는 예외를 모두 처리 (@ControllerAdvice + @ResponseBody)
public class GlobalExceptionAdvice {

    // RequestBody 의 유효성 검증 에러인 MethodArgumentNotValidException 처리하는 메서드
    // 유효성 검증과정에서 내부적으로 던져진 예외를 해당 메서드가 전달 받는다.
    // 유효성 검증이 실패하면, MethodArgumentNotValidException 이 발생
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST) // HttpStatus 를 HTTP Response 에 포함시킨다.
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // MethodArgumentNotValidException 객체의 메서드를 통해 발생한 에러 정보를 확인할 수 있다.
        final ErrorResponse response = ErrorResponse.of(e.getBindingResult());

        // @RestControllerAdvice 애너테이션을 사용하면 JSON 형식의 데이터를 Response Body로 전송하기 위해 ResponseEntity 로 래핑할 필요가 없다.
        return response;
    }

    // URI 변수로 넘어오는 값의 유효성 검증 에러인 ConstraintViolationException 처리하는 메서드
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        final ErrorResponse response = ErrorResponse.of(e.getConstraintViolations());

        return response;
    }

    // 서비스 계층에서 던진 BusinessLogicException 을 Exception Advice 에서 처리
    @ExceptionHandler
    // BusinessLogicException 처럼 다양한 유형의 Custom Exception 을 처리하는 경우 ResponseEntity 를 사용하여 HttpStatus 를 응답으로 보낸다.
    public ResponseEntity handleBusinessLogicException(BusinessLogicException e) {
        System.out.println(e.getExceptionCode().getStatus());
        System.out.println(e.getMessage());

        final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode().getStatus()));
    }
    // HttpRequestMethodNotSupportedException 을 처리하기 위한 메서드
    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        final ErrorResponse response = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED);

        return response;
    }
    // 구현 상의 실수로 발생하는 Exception 을 처리하기 위한 메서드
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        log.error("# handle Exception", e);

        final ErrorResponse response = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);

        return response;
    }
}
