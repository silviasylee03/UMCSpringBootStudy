package umc.spring.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.service.MissionService.MissionCommandService;
import umc.spring.web.dto.MissionChallengeRequestDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class ChallengeMissionController {
    private final MissionCommandService missionCommandService;

    @PostMapping("/challenge")
    public ResponseEntity<ApiResponse<String>> challengeMission(
            @Valid @RequestBody MissionChallengeRequestDTO.MissionActiveDTO request) {

        missionCommandService.challengeMission(request.getMemberId(), request.getMissionId());

        return ResponseEntity.ok(ApiResponse.onSuccess("미션 도전이 성공적으로 등록되었습니다."));
    }
}
