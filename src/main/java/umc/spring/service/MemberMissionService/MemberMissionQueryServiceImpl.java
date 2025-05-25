package umc.spring.service.MemberMissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.domain.emums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.repository.MemberMissionRepository.MemberMissionRepository;

@Service
@RequiredArgsConstructor
public class MemberMissionQueryServiceImpl implements MemberMissionQueryService {
    private final MemberMissionRepository memberMissionRepository;

    @Transactional
    public void completeMission(Long memberId, Long missionId) {
        MemberMission memberMission = memberMissionRepository.findByMemberIdAndMissionId(memberId, missionId)
                .orElseThrow(() -> new IllegalArgumentException("진행 중인 미션을 찾을 수 없습니다."));

        if (memberMission.getStatus() == MissionStatus.COMPLETE) {
            throw new IllegalStateException("이미 완료되었거나 만료된 미션입니다.");
        } else if (memberMission.getStatus() == MissionStatus.ACTIVE) {
            throw new IllegalStateException("아직 진행 전 미션입니다.");
        }

        memberMission.setStatus(MissionStatus.COMPLETE);
    }
}
