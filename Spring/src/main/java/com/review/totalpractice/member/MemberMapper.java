package com.review.totalpractice.member;

import com.review.totalpractice.member.dto.MemberPatchDto;
import com.review.totalpractice.member.dto.MemberPostDto;
import com.review.totalpractice.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mapping(target = "memberId", constant = "0L")
    Member MemberPostDtoToMember(MemberPostDto memberPostDto);

    @Mapping(target = "memberId", constant = "0L")
    Member MemberPatchDtoToMember(MemberPatchDto memberPatchDto);
}
