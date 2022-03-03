package malangcute.bellytime.bellytimeCustomer.global.auth.controller;


import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.*;
import malangcute.bellytime.bellytimeCustomer.global.auth.service.LoginService;
import malangcute.bellytime.bellytimeCustomer.global.auth.util.CookieUtils;
import malangcute.bellytime.bellytimeCustomer.global.exception.FailedToConvertImgFileException;
import malangcute.bellytime.bellytimeCustomer.global.exception.NotValidTokenException;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private static final String REFRESH_TOKEN = "refreshToken";
    private static final int MAX_AGE = 24 * 60 * 60 * 100;
    private static final String DOMAIN = "bellytime.kr";

    private final LoginService loginService;

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> healthCheck(){
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    //권한 기반 로그인 확인용 -> 토큰으로부터 user 객체 갖고오기 완료
    @PostMapping("/check")
    public String check(@RequireLogin User userId){
        return "check";
    }

    // id 로 로그인 했을 때 -> httpresponesdp 쿠키 담아서 보냄
    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponseDto> loginWithIdController(
            @RequestBody LoginWithIdAndPassRequest loginWithIdAndPassRequest
            , HttpServletResponse response) {

        RefreshAndAccessTokenResponse token = loginService.validUser(loginWithIdAndPassRequest);
        createCookie(response, token.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new AccessTokenResponseDto(token.getAccessToken()));
    }

    //새로운 유저 가입
    @PostMapping("/join")
    public ResponseEntity<RegisterCompleteResponse> registerNewUser(
            @ModelAttribute RegisterWithIdPassRequest registerWithIdPassRequest)
            throws FailedToConvertImgFileException {
        loginService.registerNewUser(registerWithIdPassRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RegisterCompleteResponse.of(true,"등록되었습니다"));
    }

    //로그아웃
    @GetMapping("/logout")
    public ResponseEntity logOutUSer(@RequireLogin User user) {
        userService.userLogOut(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private void createCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                //.sameSite(none) -> 도메인 설정후 해당 도메인으로 변경
                .domain(DOMAIN) //.domain
                .sameSite("None")
                //.sameSite("Lax")
                .maxAge(MAX_AGE)
                .path("/")
                .secure(true) // https 접속일때만 감으로 나중에 설정
                .httpOnly(true)
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }
}
