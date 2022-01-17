package malangcute.bellytime.bellytimeCustomer.global.auth;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CookieAuthRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {


    // provider가 초기에 쿠키에 담아서 보내주는 문자 형태
    public static final String OAUTH_COOKIE = "oauth2_auth_request";
    public static final String REDIRECT_COOKIE = "redirect_uri";
    public static final int COOKIEEXP = 180;


    //쿠키를 역직렬화 하여 인증내용이 담겨있는 쿠키 반환 그리고 OAuth2AuthorizationRequest 여기에 저장됨
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return CookieUtils.getCookie(request, OAUTH_COOKIE)
                .map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    // 쿠키에 권한에 대한 응답내용을 저장하여 리다이렉트 uri로 보내줌
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null){
            CookieUtils.deleteCookie(request, response, OAUTH_COOKIE);
            CookieUtils.deleteCookie(request, response, REDIRECT_COOKIE);
            return;
        }

        CookieUtils.addCookie(response, OAUTH_COOKIE, CookieUtils.serialize(authorizationRequest), COOKIEEXP);
        String redirectUriLogin = request.getParameter(REDIRECT_COOKIE);
        if(StringUtils.isNotBlank(redirectUriLogin)){
            CookieUtils.addCookie(response, REDIRECT_COOKIE, redirectUriLogin, COOKIEEXP);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return this.loadAuthorizationRequest(request);
    }


    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, OAUTH_COOKIE);
        CookieUtils.deleteCookie(request,response,REDIRECT_COOKIE);
    }
}
