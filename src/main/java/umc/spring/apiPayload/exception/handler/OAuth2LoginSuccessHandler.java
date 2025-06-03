package umc.spring.apiPayload.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import umc.spring.config.security.jwt.JwtTokenProvider;
import umc.spring.domain.Member;
import umc.spring.repository.MemberRepository.MemberRepository;

import java.io.IOException;
import java.util.Collections;

public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    public OAuth2LoginSuccessHandler(JwtTokenProvider jwtTokenProvider, MemberRepository memberRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberRepository = memberRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email"); // Google은 "email" 있음

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("소셜 로그인 유저 정보 없음"));

        // Spring Security의 UserDetails 형태로 구성
        User userDetails = new User(member.getEmail(), "", Collections.singleton(() -> "ROLE_USER"));
        Authentication customAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // JWT 생성
        String jwtToken = jwtTokenProvider.generateToken(customAuth);
        System.out.println("JWT Token: " + jwtToken);

        // JSON 응답으로 토큰 전송
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"accessToken\": \"" + jwtToken + "\"}");
    }
}

