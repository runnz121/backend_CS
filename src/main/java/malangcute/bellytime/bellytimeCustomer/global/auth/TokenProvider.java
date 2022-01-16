package malangcute.bellytime.bellytimeCustomer.global.auth;

import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Component
public class TokenProvider {

    private String secretKey;

    private Long validTime;

    public TokenProvider(@Value("{sec.auth.secretkey}") String secretkey,
                         @Value("{sec.auth.validtime}") Long validTime){
        this.secretKey = secretkey;
        this.validTime = validTime;
    }

    public String createRefreshToken(Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String userEmail = userPrincipal.getName();
        Date now = new Date();


    }

    public String createAccessToken(){

    }


}
