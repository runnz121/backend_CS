package malangcute.bellytime.bellytimeCustomer.global.auth.controller;


import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import lombok.AllArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.*;
import malangcute.bellytime.bellytimeCustomer.global.auth.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class LoginController {

    private static final String REFRESH_TOKEN = "refreshToken";
    private static final int MAX_AGE = 24 * 60 * 60 * 100;


    //기본적으로 리프레시 토큰만 발급해줌으로(Oauth도) 프론트에서 해당 토큰을 받아서 쿠키에 저장 해서 처음 보내줘야된다
    private final LoginService loginService;

    // id 로 로그인 했을 때 -> httpresponesdp 쿠키 담아서 보냄
    @PostMapping("/login")
    public ResponseEntity<?> loginWithIdController(@RequestBody LoginWithIdAndPassRequest loginWithIdAndPassRequest
            , HttpServletResponse response) throws Exception {

        RefreshAndAccessTokenResponse token = loginService.validUser(loginWithIdAndPassRequest);
        createCookie(response, token.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body(new AccessTokenResponseDto(token.getAccessToken()));
    }

    @PostMapping("/check")
    public String check(){
        return "checking";
    }

    @PostMapping("/admin")
    public String admin(){
        return "admin";
    }



    //새로운 유저 가입
    @PostMapping("/join")
    public ResponseEntity<?> registerNewUser(@RequestBody RegisterWithIdPassRequest registerWithIdPassRequest){
        loginService.registerNewUser(registerWithIdPassRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(RegisterCompleteResponse.of(true,"등록되었습니다"));
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
