package com.review.totalpractice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
// Error Response 정보를 만드는 역할
public class ErrorResponse {
    // 에러가 여러개 일 가능성이 있기때문에 List 로 에러 정보를 담는다.

    private int status;
    private String message;
    private ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // MethodArgumentNotValidException 으로 발생하는 에러 정보를 담는 멤버 변수
    private List<ConstraintViolationError> violationErrors;

    // ConstraintViolationException 으로부터 발생하는 에러 정보를 담는 멤버 변수
    private List<FieldError> fieldErrors;


    // private 생성자 지정 => new 방식으로 객체 생성 불가.
    // 대신 of() 메서드를 이용해서 객체 생성 가능 => 객체 생성과 동시에 역할을 명확하게 해준다.
    private ErrorResponse(final List<FieldError> fieldErrors,
                          final List<ConstraintViolationError> violationErrors) {
        this.fieldErrors = fieldErrors;
        this.violationErrors = violationErrors;
    }
    // BindingResult 정보를 담은 ErrorResponse 객체 생성
    // MethodArgumentNotValidException 에러 정보를 얻기 위해 필요한 것이 BindingResult 객체이다.
    public static ErrorResponse of(BindingResult bindingResult) {
        return new ErrorResponse(FieldError.of(bindingResult), null);
    }

    // Set<ConstraintViolation<?>> 객체에 대한 ErrorResponse 객체 생성
    // ConstraintViolationException 에서 에러 정보를 얻기 위해 필요한 것이 Set<ConstraintViolation<?>> 객체
    public static ErrorResponse of(Set<ConstraintViolation<?>> violations) {
        return new ErrorResponse(null, ConstraintViolationError.of(violations));
    }
    // ExceptionCode 객체에 대한 ErrorResponse 객체 생성
    // BusinessLogicException 에서 에러 정보를 얻기 위해 필요한 것이 ExceptionCode 이다.
    public static ErrorResponse of(ExceptionCode exceptionCode) {
        return new ErrorResponse(exceptionCode.getStatus(), exceptionCode.getMessage());
    }
    // HttpRequestMethodNotSupportedException 의 HttpStatus 전달하기 위한 객체 생성
    public static ErrorResponse of(HttpStatus httpStatus) {
        return new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase());
    }


    // Error 메시지로 전달하고 싶은 정보
    @Getter
    @AllArgsConstructor
    // (6) FieldError 가공
    public static class FieldError {
        private String field;
        private Object rejectedValue;
        private String reason;

        public static List<FieldError> of(BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();

            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }

    // (7) ConstraintViolation Error 가공
    @Getter
    @AllArgsConstructor
    public static class ConstraintViolationError {
        private String propertyPath;
        private String rejectedValue;
        private String reason;

        public static List<ConstraintViolationError> of(Set<ConstraintViolation<?>> constraintViolations) {

            return constraintViolations.stream()
                    .map(constraintViolation -> new ConstraintViolationError(
                            constraintViolation.getPropertyPath().toString(),
                            constraintViolation.getInvalidValue().toString(),
                            constraintViolation.getMessage()))
                    .collect(Collectors.toList());
        }
    }
}
