package malangcute.bellytime.bellytimeCustomer.global.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.AccessTokenResponseDto;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.LoginWithIdAndPassRequest;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.RefreshAndAccessTokenResponse;
import malangcute.bellytime.bellytimeCustomer.global.auth.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LoginControllerTest {
    private static final String REFRESH_TOKEN = "refreshToken";
    private static final int MAX_AGE = 24 * 60 * 60 * 100;


    @MockBean
    private LoginService loginService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;


    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @DisplayName("유저 로그인 확인 및 토큰 반환 여부")
    ResponseEntity<?> loginWithIdController(@RequestBody LoginWithIdAndPassRequest loginWithIdAndPassRequest
            , HttpServletResponse response) throws Exception {



        RefreshAndAccessTokenResponse token = loginService.validUser(loginWithIdAndPassRequest);
        createCookie(response, token.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body(new AccessTokenResponseDto(token.getAccessToken()));
    }

    @Test
    void check() {
    }

    @Test
    void registerNewUser() {
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