package umc.spring.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.spring.service.MissionService.MissionCommandService;
import umc.spring.validation.annotation.ValidMissionChallenge;
import umc.spring.web.dto.MissionChallengeRequestDTO;

@Component
@RequiredArgsConstructor
public class MissionChallengeValidator implements ConstraintValidator<ValidMissionChallenge, MissionChallengeRequestDTO.MissionActiveDTO> {

    private final MissionCommandService missionCommandService; // ✅ 반드시 서비스 계층 통해야 함

    @Override
    public boolean isValid(MissionChallengeRequestDTO.MissionActiveDTO dto, ConstraintValidatorContext context) {
        if (dto.getMemberId() == null || dto.getMissionId() == null) return false;
        return !missionCommandService.isMissionAlreadyChallenged(dto.getMemberId(), dto.getMissionId());
    }
}

