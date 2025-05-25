package umc.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MemberConverter;
import umc.spring.domain.Review;
import umc.spring.service.MemberService.MemberQueryService;
import umc.spring.web.dto.MemberResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/reviews")
public class MemberReviewController {
    private final MemberQueryService memberQueryService;

    @GetMapping("/members/{memberId}")
    @Operation(summary = "내 리뷰 목록 조회 API",description = "내가 쓴 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "유저 아이디, path variable 입니다!")
    })
    public ApiResponse<MemberResponseDTO.MemberReviewListDTO> getMemberReviewList(@PathVariable(name = "memberId") Long memberId, @RequestParam(name = "page") Integer page) {
        Page<Review> memberReviewList = memberQueryService.getMemberReviewList(memberId, page);
        return ApiResponse.onSuccess(MemberConverter.memberReviewListDTO(memberReviewList));
    }
}
