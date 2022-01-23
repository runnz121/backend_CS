package malangcute.bellytime.bellytimeCustomer.global.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.auth.controller.LoginController;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.AccessTokenResponseDto;
import malangcute.bellytime.bellytimeCustomer.global.auth.service.CustomUserService;
import malangcute.bellytime.bellytimeCustomer.global.auth.service.LoginService;
import malangcute.bellytime.bellytimeCustomer.global.auth.util.CookieUtils;
import malangcute.bellytime.bellytimeCustomer.global.exception.NotValidTokenException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@AllArgsConstructor
public class JWTExceptionFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static String BEARER = "Bearer ";
    public static final String REFRESH = "refreshToken";


    private final TokenProvider tokenprovider ;
    private final CustomUserService userDetailsService;
    private final ObjectMapper objectMapper;


    // 헤더에서 유효토큰 있는지 확인
    public String getJwtFromRequest(HttpServletRequest request){
        String token = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(token) && token.startsWith(BEARER)){
            return token.substring(BEARER.length(), token.length());
        }
        return null;
    }

    // 쿠키에서 리프레시 토큰값을 반환
    public String getRefreshFromRequest(HttpServletRequest request) {
        Optional<Cookie> cookie = CookieUtils.getCookie(request, REFRESH);
        if (cookie.isPresent()){
            Cookie refreshCookie = cookie.orElseThrow(() -> new NotValidTokenException("리프레시토큰이 없습니다"));
            return refreshCookie.getValue();
        }
        return null;
    }

    // 토큰으로 부터 아이디 찾아서(이메일) 권한 부여
    public Authentication getAuthentication(String token){
        String userEmail = tokenprovider.getUserIdFromToken(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String accessToken = getJwtFromRequest(request);
        String refreshToken = getRefreshFromRequest(request);
        String userPk = null;

        if (refreshToken != null){
            userPk = tokenprovider.getUserIdFromToken(refreshToken);
        }

        try {
            //TokenAuthentication 필터로 진행
            filterChain.doFilter(request, response);

            //유효토큰 만료 예외처리 발생시 -> 컨트롤러로 토큰 보내서 엑세스 토큰 반환
        } catch(JwtException ex) {
            String newAccess = tokenprovider.createAccessToken(userPk,refreshToken);
           // String newAccess = tokenprovider.createRefreshToken(refreshToken);
            System.out.println("token dofilter " + newAccess);
            sendNewToken(response, newAccess);
        }
    }

    // 엑세스 토큰 생성 및 응답
    public void sendNewToken(HttpServletResponse res, String token) throws IOException {
        res.setContentType("application/json; charset=UTF-8");
        AccessTokenResponseDto acc = new AccessTokenResponseDto(token);
        System.out.println(acc);
        String result = objectMapper.writeValueAsString(acc);
        res.getWriter().write(result);
        System.out.println("result " + result);
    }
}
