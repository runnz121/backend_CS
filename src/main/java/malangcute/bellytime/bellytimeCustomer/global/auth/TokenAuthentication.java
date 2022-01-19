package malangcute.bellytime.bellytimeCustomer.global.auth;


import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoTokenException;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@AllArgsConstructor
public class TokenAuthentication extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static String BEARER = "Bearer ";

    private final TokenProvider tokenprovider ;
    private final UserDetailsService userDetailsService;


    // 헤더에서 유효토큰 있는지 확인
    public String getJwtFromRequest(HttpServletRequest request){
        String token = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(token) && token.startsWith(BEARER)){
            return token.substring(BEARER.length(), token.length());
        }
        else
            throw new NoTokenException("토큰에 존재하지않습니다");
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
        String jwtToken = getJwtFromRequest(request);

        if (tokenprovider.validateToken(jwtToken)){
            Authentication authentication = getAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
