package com.ll.ilta.domain.member.v2.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.ilta.domain.member.v2.converter.MemberConverter;
import com.ll.ilta.domain.member.v2.dto.request.MemberRequestDTO;
import com.ll.ilta.domain.member.v2.dto.response.MemberResponseDTO;
import com.ll.ilta.domain.member.v2.entity.Member;
import com.ll.ilta.domain.member.v2.service.MemberService;
import com.ll.ilta.global.payload.response.BaseResponse;
import com.ll.ilta.global.security.v2.member.PrincipalDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Members", description = "회원 관련 API")
@RestController
@RequestMapping(value = "/api/v2/members", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "내 정보 조회", description = "JWT로 인증된 사용자 정보 조회")
    @GetMapping("/me/profile")
    public BaseResponse<MemberResponseDTO.MemberPreviewDTO> readMember(
        @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Long memberId = principalDetails.getMemberId();
        System.out.println(">>>> memberId: " + memberId);

        Member member = memberService.readMember(memberId);
        System.out.println(">>>> memberService result: " + member);

        MemberResponseDTO.MemberPreviewDTO dto = MemberConverter.toMemberPreviewDTO(member);
        System.out.println(">>>> 컨트롤러 result 객체 확인: " + dto);

        try {
            String json = new ObjectMapper().writeValueAsString(dto);
            System.out.println(">>>> 직렬화된 JSON: " + json);
        } catch (JsonProcessingException e) {
            System.out.println(">>>> JSON 직렬화 실패: " + e.getMessage());
        }

        return BaseResponse.onSuccess(dto);
    }

    @Operation(summary = "[관리자 전용] 전체 회원 조회", description = "관리자 기능이라 추후 구현 예정")
    @GetMapping("/admin/all")
    public BaseResponse<MemberResponseDTO.MemberPreviewListDTO> readAllMember() {
        List<Member> memberList = memberService.readAllMembers();
        return BaseResponse.onSuccess(MemberConverter.toMemberPreviewListDTO(memberList));
    }

    @Operation(summary = "회원 정보 수정", description = "이름, 프로필 사진 수정")
    @PatchMapping("/me/profile")
    public BaseResponse<MemberResponseDTO.MemberPreviewDTO> updateMyInfo(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody MemberRequestDTO.UpdateMemberDTO updateMemberDTO) {
        Long memberId = principalDetails.getMemberId();
        Member member = memberService.updateMyInfo(updateMemberDTO, memberId);
        return BaseResponse.onSuccess(MemberConverter.toMemberPreviewDTO(member));
    }

//    @Operation(summary = "카카오 로그아웃", description = "카카오 토큰 연동 해제")
//    @PostMapping("/logout")
//    public BaseResponse<String> kakaoLogout(@AuthenticationPrincipal PrincipalDetails principalDetails) {
//        KakaoUtil.unlinkKakao(principalDetails.getEmail()); // Kakao API 호출
//        return BaseResponse.onSuccess("로그아웃 완료");
//    }

    @Operation(summary = "회원 탈퇴", description = "카카오 연동 해제 및 사용자 정보 삭제")
    @DeleteMapping("/me/profile")
    public BaseResponse<String> deleteMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMemberId();
        memberService.deleteMyInfo(memberId);
        return BaseResponse.onSuccess("삭제에 성공하였습니다.");
    }


    @Operation(
        summary = "자녀 정보 유무 확인",
        description = "카카오 로그인 후 자녀 이름과 학년 유무를 체크하여 홈 화면 이동 판단"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "자녀 정보 존재 여부 반환"),
        @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @GetMapping("/me/child-info/exist")
    public BaseResponse<Boolean> existsChildInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMemberId();
        boolean hasChild = memberService.existsChildInfo(memberId);
        return BaseResponse.onSuccess(hasChild);
    }
}
