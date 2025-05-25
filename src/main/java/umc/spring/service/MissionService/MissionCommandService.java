package umc.spring.service.MissionService;

import umc.spring.domain.Mission;

public interface MissionCommandService {
    public boolean isMissionAlreadyChallenged(Long memberId, Long missionId);
    public void challengeMission(Long memberId, Long missionId);
}
