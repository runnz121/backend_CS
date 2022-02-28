package malangcute.bellytime.bellytimeCustomer.global.auth;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoCookieException;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoTokenException;
import malangcute.bellytime.bellytimeCustomer.global.exception.NotValidTokenException;
import malangcute.bellytime.bellytimeCustomer.global.exception.UserIdNotFoundException;
import malangcute.bellytime.bellytimeCustomer.user.domain.Email;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.net.HttpCookie;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenAuthentication tokenAuthentication;
    private final TokenProvider tokenProvider;
    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequireLogin.class);
    }

    // NativeWebRequest 는 컨트롤러보다 먼저 요청담긴 파라미터를 받는다
    @Override
    public User resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory) throws Exception {
        String accessToken = findAccessToken(webRequest);
        String refreshToken = findRefreshToken(webRequest);
        return findAccessAndRefreshToken(accessToken, refreshToken);
    }


    // 엑세스 토큰(리프레시토큰이 존재 하는 경우 발급) -> 헤더에 있음
    private String findAccessToken(NativeWebRequest webRequest) {

        String accessToken = tokenAuthentication
                .getJwtFromRequest(Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class)));

        if (accessToken == null) {
            throw new NoTokenException("엑세스 토큰이 없습니다");
        }
        return accessToken;
    }

    // 리프레시토큰(DB에 없을 경우 발급) -> 쿠키에 있음
    private String findRefreshToken(NativeWebRequest webRequest) {
        String cookie = webRequest.getHeader("Cookie");
        if (cookie == null) {
            throw new NoCookieException("리프레시토큰 발급을 위한 쿠키가 없습니다");
        }
        List<HttpCookie> cookies = HttpCookie.parse(cookie);
        HttpCookie httpCookie = cookies.stream()
                .filter(co -> co.getName().equals("refreshToken") && !co.getValue().isEmpty())
                .findAny()
                .orElseThrow(() -> new NoCookieException("리프레시 토큰이 존재하지 않습니다"));
        return httpCookie.getValue();
    }

    // 리프레시와 엑세스 토큰을 컨트롤러에서 확인하고 유저로 반환
    private User findAccessAndRefreshToken(String accessToken, String refreshToken) {
        if (tokenProvider.validateAccessToken(accessToken, refreshToken)
                && tokenProvider.validateRefreshToken(refreshToken)) {
            return userService.findUserByEmail(tokenProvider.getUserIdFromToken(refreshToken));
        }
        throw new NotValidTokenException("유효한 토큰이 없어 로그인할 수 없습니다");
    }
}
