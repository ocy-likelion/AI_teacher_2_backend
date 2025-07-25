package com.ll.ilta.domain.member.v2.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.ll.ilta.domain.member.v2.converter.MemberConverter;
import com.ll.ilta.domain.member.v2.dto.request.MemberRequestDTO;
import com.ll.ilta.domain.member.v2.dto.response.MemberResponseDTO;
import com.ll.ilta.domain.member.v2.entity.Member;
import com.ll.ilta.domain.member.v2.service.MemberService;
import com.ll.ilta.global.payload.response.BaseResponse;
import com.ll.ilta.global.security.v2.member.PrincipalDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member API", description = "회원 관련 API(MemberV2Controller)")
@RestController
@RequestMapping(value = "/api/v2", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입")
    @PostMapping("/members")
    public BaseResponse<MemberResponseDTO.JoinResultDTO> createMember(@RequestBody MemberRequestDTO.JoinDTO joinDTO) {
        Member member = memberService.createMember(joinDTO);
        return BaseResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }

    @Operation(summary = "내 정보 조회")
    @GetMapping("/members/me")
    public BaseResponse<MemberResponseDTO.MemberPreviewDTO> readMember(
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMemberId();
        Member member = memberService.readMember(memberId);
        return BaseResponse.onSuccess(MemberConverter.toMemberPreviewDTO(member));
    }

    @Operation(summary = "전체 회원 조회")
    @GetMapping("/members")
    public BaseResponse<MemberResponseDTO.MemberPreviewListDTO> readAllMember() {
        List<Member> memberList = memberService.readAllMember();
        return BaseResponse.onSuccess(MemberConverter.toMemberPreviewListDTO(memberList));
    }

    @Operation(summary = "내 정보 삭제")
    @DeleteMapping("/members/me")
    public BaseResponse<String> deleteMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMemberId();
        memberService.deleteMyInfo(memberId);
        return BaseResponse.onSuccess("삭제에 성공하였습니다.");
    }

    @Operation(summary = "내 정보 수정")
    @PatchMapping("/members/me")
    public BaseResponse<MemberResponseDTO.MemberPreviewDTO> updateMyInfo(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody MemberRequestDTO.UpdateMemberDTO updateMemberDTO) {
        Long memberId = principalDetails.getMemberId();
        Member member = memberService.updateMyInfo(updateMemberDTO, memberId);
        return BaseResponse.onSuccess(MemberConverter.toMemberPreviewDTO(member));
    }

    @Operation(summary = "내 자녀 정보 등록 여부 확인")
    @GetMapping("/members/me/child-info")
    public BaseResponse<Boolean> checkChildInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMemberId();
        boolean hasChild = memberService.checkChildInfo(memberId);
        return BaseResponse.onSuccess(hasChild);
    }
}
