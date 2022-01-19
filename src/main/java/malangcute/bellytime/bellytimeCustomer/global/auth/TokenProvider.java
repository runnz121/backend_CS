package malangcute.bellytime.bellytimeCustomer.global.auth;

import io.jsonwebtoken.*;
import malangcute.bellytime.bellytimeCustomer.global.exception.NotValidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class TokenProvider {

    private String secretKey;

    private String validTimeforRefresh;

    private String validTimeforAccess;

    public TokenProvider(@Value("{sec.auth.secretkey}") String secretkey,
                         @Value("{sec.auth.validtimeforRefresh}") String validTimeforRefresh,
                         @Value("{sec.auth.validtimeforAccess}") String validTimeforAccess){
        this.secretKey = secretkey;
        this.validTimeforRefresh = validTimeforRefresh;
        this.validTimeforAccess = validTimeforAccess;
    }

    //리프레시 토큰 생성(100일)
    public String createRefreshToken(Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String userEmail = userPrincipal.getName();
        Date now = new Date();

        Claims claims = Jwts.claims().setSubject(userEmail);
        Date validate = new Date(now.getTime() + Long.parseLong(validTimeforRefresh));

        return generateJwt(claims, now, validate, secretKey);
    }

    //엑세스 토큰 생성(1일) 역할도 같이 넣음
    public String createAccessToken(Authentication authentication, List<String> roles){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String userEmail = userPrincipal.getName();
        Date now = new Date();

        Claims claims = Jwts.claims().setSubject(userEmail);
        claims.put("roles", roles);
        Date validate = new Date(now.getTime() + Long.parseLong(validTimeforAccess));

        return generateJwt(claims, now, validate, secretKey);
    }

    //jwt 토큰 생성
    public String generateJwt(Claims claims,Date now, Date validate, String secretKey){
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    //유효 토큰인지 확인
    public boolean validateToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            // 비교시간.before(기준시간) -> 기준시간을 지남 -> true를 반환
            // expirdate 기준 시간보다 커야됨
            if (claims.getBody().getExpiration().before(new Date())) {
                throw new NotValidTokenException("유효기간 만료된 코튼입니다");
            }

            return true;
        } catch (JwtException ex){
            throw new NotValidTokenException("유효하지 않은 토큰입니다");
        }
    }

    //토큰 갖고와서 유저 아이디 찾기(이메일 반환)
    public String getUserIdFromToken(String token){
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
    }
}
