package malangcute.bellytime.bellytimeCustomer.global.auth.controller;

import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.config.TestSupport;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenProvider;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.LoginWithIdAndPassRequest;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.RefreshAndAccessTokenResponse;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.RegisterWithIdPassRequest;
import malangcute.bellytime.bellytimeCustomer.global.auth.service.LoginService;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("유저 로그인 테스트 ")
@Slf4j
class LoginControllerTest extends TestSupport {

    private User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @MockBean
    private LoginService loginService;

    @MockBean
    private UserService userService;


    private static final RefreshAndAccessTokenResponse refreshAndAccessTokenResponse =
            new RefreshAndAccessTokenResponse("accessToken", "refreshToken");

    private PasswordEncoder PASSWORD_ENCODER =
            new BCryptPasswordEncoder();

    private static final MockMultipartFile USER_IMG =
            new MockMultipartFile("profileImg", "cats.JPEG", MediaType.IMAGE_JPEG_VALUE, "cats".getBytes());


    public static final LoginWithIdAndPassRequest LOGIN_REQUEST =
           new LoginWithIdAndPassRequest("runnz@gmail.com", "test");

    public static final RegisterWithIdPassRequest REGISTER =
            RegisterWithIdPassRequest.builder()
                .email("runnz121@gmail.com")
                .password("test")
                .name("종빈")
                .nickname("이상해씨")
                .phoneNumber("010-222-2222")
                .profileImg(USER_IMG)
                .build();

    @BeforeEach
    public void setUser() {
        user = User
                .builder()
                .nickName("종빈")
                .email("runnz@gmail.com")
                .passWord(PASSWORD_ENCODER.encode("test"))
                .phoneNumber("010-1234-1234")
                .profileImg("cats.jpeg")
                .build();
        userRepository.save(user);
    }


    @DisplayName("로그인 컨트롤러 헬스 체크")
    @Test
    void healthCheck() throws Exception {
        mockMvc.perform(
                get("/"))
                .andExpect(status().isOk());
    }

    @DisplayName("아이디 비밀번호 기반으로 엑세스 토큰과 리프레시 토큰을 반환")
    @Test
    void loginWithIdController() throws Exception {

        // given
        given(loginService.validUser(any())).willReturn(refreshAndAccessTokenResponse);

        // when, then
        mockMvc.perform(
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonToString(LOGIN_REQUEST))
                        )
                .andExpect(status().isOk())
                .andDo(
                        document("login-with-id-pass",
                                requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("유저의 이메일"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("유저의 비밀번호")
                                ),
                                responseHeaders(
                                        headerWithName("Set-Cookie").description("리프레시토큰 반환")
                                ),
                                responseFields(
                                        fieldWithPath("accessToken").type(JsonFieldType.STRING).description("엑세스 토큰 반환")
                                )
                        ));
    }

    @DisplayName("새로운 유저 등록 체크(새로운 유저 가입)")
    @Test
    void registerNewUser() throws Exception {

        // given
        loginService.registerNewUser(REGISTER);

        // when -> void는 willdonothing  https://yhmane.tistory.com/198
        willDoNothing().given(loginService).registerNewUser(REGISTER);

        // then
        mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/join")
                                .file(USER_IMG)
                                .param("email", REGISTER.getEmail())
                                .param("password",REGISTER.getPassword())
                                .param("name", REGISTER.getName())
                                .param("nickname",REGISTER.getNickname())
                                .param("phoneNumber", REGISTER.getPhoneNumber())
                        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(
                        document("register-with-form",
                                requestParameters(
                                        parameterWithName("email").description("유저가 요청하는 이메일 (유일값)"),
                                        parameterWithName("password").description("유저가 가입시 요청하는 비밀번호"),
                                        parameterWithName("name").description("유저의 이름"),
                                        parameterWithName("nickname").description("유저의 닉네임 (유일값)"),
                                        parameterWithName("phoneNumber").description("유저의 핸드폰 번호")
                                ),
                                requestParts(
                                        partWithName("profileImg").description("유저의 프로필 이미지")
                                ),
                                responseFields(
                                        fieldWithPath("complete").type(JsonFieldType.BOOLEAN).description("정상등록시 true 반환"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("정상등록시 '등록되었습니다' 메세지반환")
                                )
                        )
                );
    }


    @DisplayName("유저 로그아웃 체크(리프레시 토큰 삭제 됨)")
    @Test
    void logOutUSer() throws Exception {

        // given
        user.setRefreshToken("refreshToken");
        String token = tokenProvider.createAccessToken(user.getEmail().getEmail());

        // when
        userService.userLogOut(user);

        // then
        mockMvc.perform(
                        get("/logout")
                                .header("Authorization","Bearer " + token)
                                .header("Cookie", "refreshToken="+token)

                )
                .andExpect(status().isOk())
                .andDo(
                        document("logout-with-token",
                                requestHeaders(
                                        headerWithName("Cookie").description("유저의 리프레시토큰"),
                                        headerWithName("Authorization").description("유저의 엑세스 토큰")
                                )
                        )
                );
    }
}