package com.review.totalpractice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
// BusinessLogicException 으로 전달할 에러메시지를 생성
// 개발자가 의도적으로 예외를 던져야 하는 상황에서 ExceptionCode 정보만 바꿔서 원하는대로 처리가능
public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member Not Found"),
    MEMBER_EXISTS(404, "Member already existed");

    @Getter
    private int status;

    @Getter
    private String message;
}
