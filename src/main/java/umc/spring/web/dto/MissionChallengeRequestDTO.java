package umc.spring.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import umc.spring.validation.annotation.ValidMissionChallenge;

public class MissionChallengeRequestDTO {

    @Getter
    @ValidMissionChallenge
    public static class MissionActiveDTO {
        @NotNull
        Long memberId;

        @NotNull
        Long missionId;
    }
}
