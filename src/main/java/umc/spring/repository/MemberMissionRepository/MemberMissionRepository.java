package umc.spring.repository.MemberMissionRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.emums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;

import java.util.Optional;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
    boolean existsByMemberIdAndMissionId(Long memberId, Long missionId);
    Page<MemberMission> findAllByMemberAndStatus(Member member, MissionStatus status, PageRequest pageRequest);
    Optional<MemberMission> findByMemberIdAndMissionId(Long memberId, Long missionId);
}