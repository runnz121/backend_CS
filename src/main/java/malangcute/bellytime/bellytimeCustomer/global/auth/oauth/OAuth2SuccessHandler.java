package malangcute.bellytime.bellytimeCustomer.global.auth.oauth;

import lombok.AllArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.config.SecurityProperties;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenProvider;
import org.springframework.security.core.Authentication;
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

//    @Value("${sec.redirect.url}")
//    private final String REDIRECT_URL;

    private final SecurityProperties securityProperties;


    // 로그인 성공시 리다이렉트 할 uri 지정
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String targetUrl = determineTargetUrl(request, response, authentication);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    // 리프레시 토큰을 만들고 지정 uri로 보내주는것
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String token = tokenProvider.createRefreshToken(authentication);

        String redirect_url = securityProperties.RedirectUrl().getRedirectUrl();

        return UriComponentsBuilder.fromUriString(redirect_url).queryParam("token", token).build().toUriString();

    }
}
