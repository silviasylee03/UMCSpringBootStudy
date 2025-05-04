package umc.spring.repository.MissionRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import umc.spring.domain.*;
import umc.spring.domain.emums.MissionStatus;
import umc.spring.domain.mapping.QMemberMission;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MissionRepositoryImpl implements MissionRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QMission mission = QMission.mission;
    private final QStore store = QStore.store;
    private final QMemberMission memberMission = QMemberMission.memberMission;
    private final QRegion region = QRegion.region;
    private final QMember member = QMember.member;

    @Override
    public Page<Mission> findMissionByStatus(Long member_id, MissionStatus status, Pageable pageable) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (member_id != null) {
            predicate.and(memberMission.member.id.eq(member_id));
        }

        if (status != null) {
            predicate.and(mission.status.eq(status));
        }

        List<Mission> missionList = jpaQueryFactory
                .select(mission)
                .from(memberMission)
                .join(memberMission.mission, mission)
                .join(mission.store, store)
                .where(predicate)
                .fetch();

        long total = jpaQueryFactory
                .select(memberMission.count())
                .from(memberMission)
                .where(predicate)
                .fetchOne();

        return new PageImpl<>(missionList, pageable, total);
    }

    @Override
    public Page<Mission> findMissionByRegion(Long member_id, Long region_id, Pageable pageable) {
        BooleanBuilder predicate = new BooleanBuilder()
                .and(store.region.id.eq(region_id))
                .and(mission.deadline.gt(LocalDate.now())) // 마감 안 지난 미션
                .and(mission.id.notIn( // 아직 도전하지 않은 미션
                JPAExpressions
                        .select(memberMission.mission.id)
                        .from(memberMission)
                        .where(memberMission.member.id.eq(member_id))
        ));

        List<Mission> missionList = jpaQueryFactory
                .selectFrom(mission)
                .join(mission.store, store).fetchJoin()
                .where(predicate)
                .fetch();

        long total = jpaQueryFactory
                .select(mission.count())
                .from(mission)
                .join(mission.store, store)
                .where(predicate)
                .fetchOne();

        return new PageImpl<>(missionList, pageable, total);
    }

}
