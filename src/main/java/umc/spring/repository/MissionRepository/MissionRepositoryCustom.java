package umc.spring.repository.MissionRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.spring.domain.Mission;
import umc.spring.domain.emums.MissionStatus;

public interface MissionRepositoryCustom {
    Page<Mission> findMissionByStatus(Long member_id, MissionStatus status, Pageable pageable);
    Page<Mission> findMissionByRegion(Long member_id, Long region_id, Pageable pageable);
}
