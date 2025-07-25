package com.ll.ilta.domain.member.v2.controller;

import com.ll.ilta.domain.member.v2.converter.MemberConverter;
import com.ll.ilta.domain.member.v2.dto.Member;
import com.ll.ilta.domain.member.v2.dto.request.MemberRequestDTO;
import com.ll.ilta.domain.member.v2.dto.response.MemberResponseDTO;
import com.ll.ilta.domain.member.v2.service.MemberService;
import com.ll.ilta.global.payload.BaseResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members") //자녀 정보 생성
    public BaseResponse<MemberResponseDTO.JoinResultDTO> createMember(@RequestBody MemberRequestDTO.JoinDTO joinDTO) {
        Member member = memberService.createMember(joinDTO);
        return BaseResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }

    @GetMapping("/members/{memberId}") //자녀 정보 수정
    public BaseResponse<MemberResponseDTO.MemberPreviewDTO> readMember(@PathVariable Long memberId) {
        Member member = memberService.readMember(memberId);
        return BaseResponse.onSuccess(MemberConverter.toMemberPreviewDTO(member));
    }

    @GetMapping("/members")
    public BaseResponse<MemberResponseDTO.MemberPreviewListDTO> readUsers() {
        List<Member> memberList = memberService.readMembers();
        return BaseResponse.onSuccess(MemberConverter.toMemberPreviewListDTO(memberList));
    }

    @DeleteMapping("/members/{memberId}")
    public BaseResponse<String> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return BaseResponse.onSuccess("삭제에 성공하였습니다.");
    }

    @PatchMapping("/members/{memberId}")
    public BaseResponse<MemberResponseDTO.MemberPreviewDTO> updateMember(
        @RequestBody MemberRequestDTO.UpdateMemberDTO updateMemberDTO, @PathVariable Long memberId) {
        Member member = memberService.updateMember(updateMemberDTO, memberId);
        return BaseResponse.onSuccess(MemberConverter.toMemberPreviewDTO(member));
    }

    @GetMapping("/members/{memberId}/child-info")
    public BaseResponse<Boolean> checkChildInfo(@PathVariable Long memberId) {
        boolean hasChild = memberService.hasChildInfo(memberId);
        return BaseResponse.onSuccess(hasChild);
    }
}
