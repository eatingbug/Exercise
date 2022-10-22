package com.review.totalpractice.member;

import com.review.totalpractice.exception.BusinessLogicException;
import com.review.totalpractice.exception.ExceptionCode;
import com.review.totalpractice.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private MemberRepository memberRepository;

    public Member createMember(Member member) {

        Member createMember = member;
        return createMember;
    }

    public Member updateMember(Member member) {

        Member updatedMember = member;
        return updatedMember;
    }

    public Member findMember(long memberId) {

        throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
    }

    public List<Member> findMembers() {
        return null;
    }

    public void deleteMember(long memberId){

        String logResult = null;
        System.out.println(logResult.toUpperCase());
    }

    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }

    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findMember;
    }

}
