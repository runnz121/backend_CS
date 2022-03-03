package malangcute.bellytime.bellytimeCustomer.global.auth;

import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.global.auth.util.CookieUtils;
import malangcute.bellytime.bellytimeCustomer.global.config.SecurityProperties;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoCookieException;
import malangcute.bellytime.bellytimeCustomer.global.exception.NotValidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class TokenProvider {

    private final SecurityProperties securityProperties;


    //리프레시 토큰 생성(100일)
    public String createRefreshToken(String userPk){
        Date now = new Date();
        Claims claims = Jwts.claims().setSubject(userPk);
        Date validate = new Date(now.getTime() + securityProperties
                .getAuth()
                .getValidtimeforRefresh());
        return generateJwt(claims, now, validate, securityProperties.
                getAuth()
                .getSecretkey());
    }

    //엑세스 토큰 생성(1일) 역할도 같이 넣음
    public String createAccessToken(String userPk){
        Date now = new Date();
        Claims claims = Jwts.claims().setSubject(userPk);
        Date validate = new Date(now.getTime() + securityProperties
                .getAuth()
                .getValidtimeforAccess());
        return generateJwt(claims, now, validate, securityProperties
                .getAuth()
                .getSecretkey());
    }

    //jwt 토큰 생성
    public String generateJwt(Claims claims, Date now, Date validate, String secretKey){
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    //리프레시 토큰검증 확인
    public boolean validateRefreshToken(String token){
        try{
            Jws<Claims> claims
                    = Jwts.parser().setSigningKey(securityProperties
                    .getAuth()
                    .getSecretkey())
                    .parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                throw new NotValidTokenException("유효기간 만료된 리프레시토큰입니다");
            }
            return true;
        } catch (JwtException ex){
            log.trace("validate RefreshToken ex : ", ex);
            throw new NotValidTokenException("유효하지 않은 리프레시토큰입니다");
        }
    }

    // 엑세스 토큰 검증
    public boolean validateAccessToken(String accessToken, String refreshToken){
        try {

            Jws<Claims> claimsAccess
                    = Jwts.parser().setSigningKey(securityProperties
                    .getAuth()
                    .getSecretkey())
                    .parseClaimsJws(accessToken);

            Date expirationRefresh
                    = Jwts.parser().setSigningKey(securityProperties
                    .getAuth()
                    .getSecretkey())
                    .parseClaimsJws(refreshToken)
                    .getBody()
                    .getExpiration();

            log.trace("claimAccess : ", claimsAccess);
            log.trace("expirationRefresh : ", expirationRefresh);
            if (expirationRefresh.before(claimsAccess.getBody().getExpiration())) {
                return false;
            }
            return true;
        } catch (JwtException ex){
            log.trace("validate AccessToken ex : ", ex);
            return false;
        }
    }



    //토큰 갖고와서 유저 아이디 찾기(이메일 반환)
    public String getUserIdFromToken(String token){
            Claims claims = Jwts.parser()
                    .setSigningKey(securityProperties.getAuth().getSecretkey())
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
    }

}
