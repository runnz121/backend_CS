package malangcute.bellytime.bellytimeCustomer.global.auth;


import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.auth.service.CustomUserService;
import malangcute.bellytime.bellytimeCustomer.global.auth.util.CookieUtils;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoTokenException;
import malangcute.bellytime.bellytimeCustomer.global.exception.NotValidTokenException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.Optional;


@AllArgsConstructor
public class TokenAuthentication extends OncePerRequestFilter {
//public class TokenAuthentication {

    public static final String AUTHORIZATION = "Authorization";
    public static String BEARER = "Bearer ";
    public static final String REFRESH = "refreshToken";

    private final TokenProvider tokenprovider ;
    private final CustomUserService userDetailsService;


    // 헤더에서 유효토큰 있는지 확인
    public String getJwtFromRequest(HttpServletRequest request){
        String token = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(token) && token.startsWith(BEARER)){
            return token.substring(BEARER.length(), token.length());
        }
        return null;
    }


    // 쿠키에서 리프레시 토큰값을 반환
    public String getRefreshFromRequest(HttpServletRequest request){
        Optional<Cookie> cookie = CookieUtils.getCookie(request, REFRESH);
        Cookie refreshCookie = cookie.orElseThrow(() -> new NotValidTokenException("리프레시토큰이 없습니다"));
        return refreshCookie.getValue();
    }

    // 토큰으로 부터 아이디 찾아서(이메일) 권한 부여
    public Authentication getAuthentication(String token){
        String userEmail = tokenprovider.getUserIdFromToken(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    //권한 갖고와서 securitycontextholder에 저장(유저 인증 정보 저장) -> accesstoken처리
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

       // String refreshToken = getRefreshFromRequest(request);
        String accessToken = getJwtFromRequest(request);

        // 둘다 유효한 토큰이면 인증객체 저장
        if (StringUtils.hasText(accessToken) && tokenprovider.validateRefreshToken(accessToken)){
            Authentication authentication = getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
