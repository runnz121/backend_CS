package malangcute.bellytime.bellytimeCustomer.global.auth.controller;

import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.config.RestDocsSupport;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenProvider;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;


@DisplayName("유저 로그인 테스트 ")
@Slf4j
class LoginControllerTest extends RestDocsSupport {

    private static final long REFRESH_TOKEN_EXPIRES = 10000;
    private static final long ACCESS_TOKEN_EXPIRES = 10000;

    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";

    private static final String CHECK = "/check";


    private User user;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void setUser() {
        user = User
                .builder()
                .nickName("종빈")
                .email("runna121@gmail.com")
                .passWord("test")
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


    @DisplayName("유저 토큰 확인 후 로그인")
    @Test
    void check() throws Exception {

        // given
        String accessToken = tokenProvider.createAccessToken(user.getEmail().getEmail());
        String refreshToken = tokenProvider.createAccessToken(user.getEmail().getEmail());
        log.info(">>>accesstoken :" + accessToken);
        log.info(">>>user :" + user.getEmail().getEmail());

        // when
        ResultActions resultActions = 유저_조회(accessToken, refreshToken);

        // then
        유저_조회_완료(resultActions);
    }

    @DisplayName("아이디, 비밀번호 로그인 체크")
    @Test
    void loginWithIdController() {
    }


    @DisplayName("새로운 유저 등록 체크(새로운 유저 가입)")
    @Test
    void registerNewUser() {
    }


    @DisplayName("유저 로그아웃 체크(리프레시 토큰 삭제 됨)")
    @Test
    void logOutUSer() {
    }


    private ResultActions 유저_조회(String accessToken, String refreshToken) throws Exception {
        return this.mockMvc.perform(post(CHECK)
                        .cookie()
    }


    private void 유저_조회_완료(ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk());

    }


    /**
     *  rest docs 작성
     */

//    private void 유저_조회_rest_docs(ResultActions resultActions) {
//        resultActions.andDo(
//                document("/check",
//                        requestHeaders(
//                                headerWithName("Authorization").description("AccessToken - Bearer 토큰")
//                        ),
//                        requestParts
//        )
//    }

}