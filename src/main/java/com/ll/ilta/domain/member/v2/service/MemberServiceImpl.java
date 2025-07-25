package com.ll.ilta.domain.member.v2.service;


import com.ll.ilta.domain.member.v2.converter.MemberConverter;
import com.ll.ilta.domain.member.v2.dto.request.MemberRequestDTO;
import com.ll.ilta.domain.member.v2.entity.Member;
import com.ll.ilta.domain.member.v2.repository.MemberRepository;
import com.ll.ilta.global.payload.code.status.ErrorStatus;
import com.ll.ilta.global.payload.exception.handler.MemberHandler;
import com.ll.ilta.global.security.v2.member.PrincipalDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member createMember(MemberRequestDTO.JoinDTO joinDTO) {
        Member member = MemberConverter.toMember(joinDTO, passwordEncoder);
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    @Override
    public Member readMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberHandler(ErrorStatus.NOT_FOUND_USER));
    }

    @Override
    public List<Member> readAllMember() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean checkChildInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberHandler(ErrorStatus.NOT_FOUND_USER));
        return member.getGrade() != null && member.getName() != null;
    }


    @Override
    public void deleteMyInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberHandler(ErrorStatus.NOT_FOUND_USER));
        memberRepository.delete(member);
    }

    @Override
    public Member updateMyInfo(MemberRequestDTO.UpdateMemberDTO updateMemberDTO, Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberHandler(ErrorStatus.NOT_FOUND_USER));
        member.update(updateMemberDTO.getName(), updateMemberDTO.getGrade());
        return member;
    }


}
