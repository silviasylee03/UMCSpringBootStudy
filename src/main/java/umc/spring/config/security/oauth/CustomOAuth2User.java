package umc.spring.config.security.oauth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import umc.spring.domain.Member;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
public class CustomOAuth2User implements OAuth2User {

    private final Member member;
    private final Map<String, Object> attributes;

    public CustomOAuth2User(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 여기선 단순하게 ROLE_USER만 반환
        return List.of(() -> member.getRole().getSecurityRole());
    }

    @Override
    public String getName() {
        return String.valueOf(member.getId()); // 보통 식별자로 memberId 사용
    }

    public String getEmail() {
        return member.getEmail();
    }
}
