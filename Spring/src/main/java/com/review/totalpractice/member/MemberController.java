package com.review.totalpractice.member;

import com.review.totalpractice.exception.BusinessLogicException;
import com.review.totalpractice.exception.ErrorResponse;
import com.review.totalpractice.exception.ExceptionCode;
import com.review.totalpractice.member.dto.MemberPatchDto;
import com.review.totalpractice.member.dto.MemberPostDto;
import com.review.totalpractice.member.entity.Member;
import com.review.totalpractice.stamp.Stamp;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController // REST API의 리소스를 처리하기 위한 API 엔드포인트로 동작
@RequestMapping("/v1/members") // 클라이언트의 요청과 요청을 처리하는 핸들러 메서드를 매핑, 클래스 전체 공통 URL 설정
@RequiredArgsConstructor
@Validated
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper mapper;


    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto memberPostDto) {

        Member member = mapper.MemberPostDtoToMember(memberPostDto);
        member.setStamp(new Stamp());
        Member response = memberService.createMember(member);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity patchMember(@PathVariable long memberId,
                                      @Valid @RequestBody MemberPatchDto memberPatchDto) {

        return new ResponseEntity<>(memberPatchDto, HttpStatus.OK);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity getMember(@PathVariable long memberId) {
        Member member = memberService.findMember(memberId);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

}
