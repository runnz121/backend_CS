package malangcute.bellytime.bellytimeCustomer.global.auth.oauth;
import lombok.AllArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.auth.UserPrincipal;
import malangcute.bellytime.bellytimeCustomer.global.config.SecurityProperties;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenProvider;
import malangcute.bellytime.bellytimeCustomer.user.domain.Email;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@AllArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    private static final String REFRESH_TOKEN = "refreshToken";
    private static final int MAX_AGE = 24 * 60 * 60 * 100;

    private final SecurityProperties securityProperties;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    // 로그인 성공시 리다이렉트 할 uri 지정
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String targetUrl = determineTargetUrl(request, response, authentication);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    // 리프레시 토큰을 만들고 지정 uri로 토큰 생성해서 보내주는것 -> 프론트는 이 토큰을 받아서 쿠키에 해당이름으로 보내줘야함
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String refreshToken = tokenProvider.createRefreshToken(userPrincipal.getEmail());
        String accessToken = tokenProvider.createAccessToken(userPrincipal.getEmail(), refreshToken);

        User user = userRepository.findByEmail(new Email(userPrincipal.getEmail())).orElseThrow();
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
        createCookie(response, refreshToken);


        String redirect_uri =  securityProperties.getReuris().getGoogle();


        return UriComponentsBuilder.fromUriString(redirect_uri)
                .queryParam("accessToken", accessToken)
                .build().toUriString();
    }

    private void createCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                //.sameSite(none) -> 도메인 설정후 해당 도메인으로 변경
                .sameSite("none")
                .maxAge(MAX_AGE)
                .path("/")
                .secure(true)
                .httpOnly(true)
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }
}
