package umc.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MissionConverter;
import umc.spring.domain.Mission;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.service.MemberMissionService.MemberMissionQueryService;
import umc.spring.service.MemberService.MemberQueryService;
import umc.spring.service.MissionService.MissionCommandService;
import umc.spring.service.MissionService.MissionQueryService;
import umc.spring.validation.annotation.ValidPage;
import umc.spring.web.dto.MissionChallengeRequestDTO;
import umc.spring.web.dto.MissionChallengeResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class ChallengeMissionController {
    private final MissionCommandService missionCommandService;
    private final MemberQueryService memberQueryService;
    private final MemberMissionQueryService memberMissionQueryService;

    @PostMapping("/challenge")
    public ResponseEntity<ApiResponse<String>> challengeMission(
            @Valid @RequestBody MissionChallengeRequestDTO.MissionActiveDTO request) {

        missionCommandService.challengeMission(request.getMemberId(), request.getMissionId());

        return ResponseEntity.ok(ApiResponse.onSuccess("미션 도전이 성공적으로 등록되었습니다."));
    }

    @GetMapping("/{memberId}/challenge")
    @Operation(summary = "내가 진행 중인 미션 목록 조회 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "유저 아이디, path variable 입니다!"),
            @Parameter(name = "page", description = "페이지 번호 (1부터 시작)", schema = @Schema(defaultValue = "1", minimum = "1"))
    })
    public ApiResponse<MissionChallengeResponseDTO.MemberChallengingMissionListDTO> getMissionList(
            @PathVariable(name = "memberId") Long memberId,
            @ValidPage @RequestParam(name = "page", defaultValue = "1") Integer page){
        Page<MemberMission> missionList = memberQueryService.getMemberMissionList(memberId, page - 1);
        return ApiResponse.onSuccess(MissionConverter.missionToMemberChallengingMissionListDTO(missionList));
    }

    @PostMapping("/{memberId}/complete/{missionId}")
    public ResponseEntity<Void> completeMission(
            @PathVariable Long memberId,
            @PathVariable Long missionId
    ) {
        memberMissionQueryService.completeMission(memberId, missionId);
        return ResponseEntity.ok().build();
    }
}
