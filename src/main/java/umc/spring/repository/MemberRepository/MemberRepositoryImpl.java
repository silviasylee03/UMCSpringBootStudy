package umc.spring.repository.MemberRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import umc.spring.domain.QMember;
import umc.spring.web.dto.MyPageDto;

import static com.querydsl.core.types.Projections.constructor;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QMember member = QMember.member;

    @Override
    public MyPageDto findByMemberId(Long member_id) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (member_id != null) {
            predicate.and(member.id.eq(member_id));
        }

        return jpaQueryFactory
                .select(constructor(
                        MyPageDto.class,
                        member.name,
                        member.email,
                        member.phone,
                        member.phoneVerified,
                        member.point
                ))
                .from(member)
                .where(predicate)
                .fetchOne();
    }
}
