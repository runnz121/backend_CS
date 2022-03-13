package malangcute.bellytime.bellytimeCustomer.global.auth.controller;


import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.*;
import malangcute.bellytime.bellytimeCustomer.global.auth.service.LoginService;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.FailedToConvertImgFileException;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.bind.v2.model.core.Ref;

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

    // id 로 로그인 했을 때 -> httpresponesdp 쿠키 담아서 보냄
    @PostMapping("/login")
    public ResponseEntity<FirstLoginDto> loginWithIdController(
            @RequestBody LoginWithIdAndPassRequest loginWithIdAndPassRequest
            , HttpServletResponse response) {

        LoginResultWithIdPass res = loginService.validUser(loginWithIdAndPassRequest);
        createCookie(response, res.getTokens().getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK)
                .body(FirstLoginDto.of(res.getUserId(), res.getUserNickName(), res.getTokens().getAccessToken()));
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
    @DeleteMapping("/log-out")
    public ResponseEntity<AccessTokenResponseDto> logOutUSer(@RequireLogin User user, HttpServletResponse response) {
        userService.userLogOut(user);
        deleteCookie(response);
        return ResponseEntity.noContent().build();
    }

    private void createCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .sameSite("None") //-> 도메인 설정후 해당 도메인으로 변경
                .domain(DOMAIN) //.domain
                //.sameSite("None")
                //.sameSite("Lax")
                .maxAge(MAX_AGE)
                .path("/")
                .secure(true) // https 접속일때만 감으로 나중에 설정
                .httpOnly(true)
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

    private void deleteCookie(HttpServletResponse response) {
        ResponseCookie deleteCookie = ResponseCookie.from(REFRESH_TOKEN, "")
            .sameSite("None")
            .domain(DOMAIN)
            .maxAge(0)
            .path("/")
            .secure(true)
            .httpOnly(true)
            .build();
        response.addHeader("Set-Cookie", deleteCookie.toString());

        // Cookie deleteCookie = new Cookie(REFRESH_TOKEN, null);
        // deleteCookie.setPath("/");
        // deleteCookie.setMaxAge(0);
        // response.addCookie(deleteCookie);
    }
}
