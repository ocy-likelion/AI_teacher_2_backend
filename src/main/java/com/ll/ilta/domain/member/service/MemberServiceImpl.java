package com.ll.ilta.domain.member.service;

import com.ll.ilta.domain.member.dto.MemberRequestDTO.ChildRequestDTO;
import com.ll.ilta.domain.member.dto.MemberRequestDTO.UpdateMemberDTO;
import com.ll.ilta.domain.member.entity.Member;
import com.ll.ilta.domain.member.repository.MemberRepository;
import com.ll.ilta.global.payload.code.status.ErrorStatus;
import com.ll.ilta.global.payload.exception.handler.MemberHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private Member findMemberOrThrow(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new MemberHandler(ErrorStatus.NOT_FOUND_USER));
    }

    @Transactional(readOnly = true)
    @Override
    public Member readMember(Long memberId) {
        return findMemberOrThrow(memberId);
    }

    @Override
    public List<Member> readAllMembers() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsChild(Long memberId) {
        Member member = findMemberOrThrow(memberId);
        return member.getChildGrade() != null && member.getChildName() != null;
    }

    @Override
    public void deleteMyInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberHandler(ErrorStatus.NOT_FOUND_USER));
        memberRepository.delete(member);
    }

    @Override
    public Member updateMyInfo(UpdateMemberDTO updateMemberDTO, Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberHandler(ErrorStatus.NOT_FOUND_USER));

        member.updateMember(updateMemberDTO.getNickname());
        return member;
    }

    @Override
    public Member updateChild(ChildRequestDTO childRequestDTO, Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberHandler(ErrorStatus.NOT_FOUND_USER));

        member.updateChild(childRequestDTO.getChildName(), childRequestDTO.getChildGrade());
        return member;
    }
}
