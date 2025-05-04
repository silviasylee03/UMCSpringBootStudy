package umc.spring.repository.MemberRepository;

import umc.spring.web.dto.MyPageDto;

public interface MemberRepositoryCustom {
    MyPageDto findByMemberId(Long member_id);
}
