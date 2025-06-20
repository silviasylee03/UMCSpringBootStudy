package umc.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MissionConverter;
import umc.spring.converter.ReviewConverter;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.service.ReviewService.ReviewCommandService;
import umc.spring.service.StoreService.StoreCommandService;
import umc.spring.service.StoreService.StoreQueryService;
import umc.spring.validation.annotation.ExistStore;
import umc.spring.validation.annotation.ValidPage;
import umc.spring.web.dto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreRestController {
    private final ReviewCommandService reviewCommandService;
    private final StoreCommandService storeCommandService;
    private final StoreQueryService storeQueryService;

    @PostMapping("/{regionId}/stores")
    public ApiResponse<StoreCreateResponseDTO.StoreCreateResultDTO> addStore(@RequestBody @Valid StoreCreateRequestDTO.StoreCreateDTO request, @PathVariable Long regionId) {
        Store store = storeCommandService.addStore(request, regionId);
        return ApiResponse.onSuccess(StoreConverter.toAddResultDTO(store));
    }

    @PostMapping(
            value = "/{storeId}/reviews",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE      // JSON + 파일 업로드
    )
    public ApiResponse<ReviewResponseDTO.ReviewCreateResultDTO> addReview(
            @Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            @RequestPart("request") @Valid ReviewRequestDTO.ReviewCreateDTO request,   // 리뷰 본문(JSON)
            @ExistStore @PathVariable Long storeId,                              // 대상 매장
            @RequestHeader("X-AUTH-ID") Long memberId,                           // 작성 회원
            @RequestPart(value = "image", required = false) MultipartFile reviewImage  // 첨부 이미지
    ) {
        Review review = reviewCommandService.createReview(memberId, storeId, request, reviewImage);
        return ApiResponse.onSuccess(ReviewConverter.toCreateReviewDTO(review));
    }


    @GetMapping("/{storeId}/reviews")
    @Operation(summary = "특정 가게의 리뷰 목록 조회 API",description = "특정 가게의 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "storeId", description = "가게의 아이디, path variable 입니다!"),
            @Parameter(name = "page", description = "페이지 번호 (1부터 시작)", schema = @Schema(defaultValue = "1", minimum = "1"))
    })
    public ApiResponse<StoreCreateResponseDTO.ReviewPreviewListDTO> getReviewList(
            @ExistStore @PathVariable(name = "storeId") Long storeId,
            @ValidPage @RequestParam(name = "page", defaultValue = "1") Integer page){
        Page<Review> reviewList = storeQueryService.getReviewList(storeId, page - 1);
        return ApiResponse.onSuccess(StoreConverter.reviewPreviewListDTO(reviewList));
    }

    @GetMapping("/{storeId}/missions")
    @Operation(summary = "특정 가게의 미션 목록 조회 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "storeId", description = "가게의 아이디, path variable 입니다!"),
            @Parameter(name = "page", description = "페이지 번호 (1부터 시작)", schema = @Schema(defaultValue = "1", minimum = "1"))
    })
    public ApiResponse<MissionChallengeResponseDTO.StoreMissionListDTO> getMissionList(
            @ExistStore @PathVariable(name = "storeId") Long storeId,
            @ValidPage @RequestParam(name = "page", defaultValue = "1") Integer page){
        Page<Mission> missionList = storeQueryService.getMissionList(storeId, page - 1);
        return ApiResponse.onSuccess(MissionConverter.storeMissionListDTO(missionList));
    }
}
