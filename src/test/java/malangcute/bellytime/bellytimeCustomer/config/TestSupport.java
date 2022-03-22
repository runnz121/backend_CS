package malangcute.bellytime.bellytimeCustomer.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.security.config.BeanIds;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import malangcute.bellytime.bellytimeCustomer.global.auth.LoginArgumentResolver;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenProvider;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class}) //rest docs, spring test 시 적용
@SpringBootTest
@Transactional
@ActiveProfiles("test")
// @AutoConfigureRestDocs
// @MockBean(JpaMetamodelMappingContext.class)
public abstract class TestSupport { //extends ControllerTest

    protected MockMvc mockMvc;

    @Autowired
    protected EntityManager em;

    @Autowired
    protected UserRepository userRepository;

    @MockBean
    protected TokenProvider tokenProvider;

    protected static PasswordEncoder PASSWORD_ENCODER =
        new BCryptPasswordEncoder();

    @Autowired
    private WebApplicationContext context;



    protected static final String ACCESS_TOKEN = "accessToken";

    protected static final String REFRESH_TOKEN = "refreshToken";

    protected static final User 사용자 = 가입된_유저(1L);

    protected static User 가입된_유저 (Long id) {
        return User
            .builder()
            .id(id)
            .nickName("종빈")
            .email("runnz@gmail.com")
            .passWord(PASSWORD_ENCODER.encode("test"))
            .phoneNumber("010-1234-1234")
            .profileImg("cats.jpeg")
            .build();
    }

    // @BeforeEach
    // public void setUser() {
    //     가입된_유저 = User
    //         .builder()
    //         .nickName("종빈")
    //         .email("runnz@gmail.com")
    //         .passWord(PASSWORD_ENCODER.encode("test"))
    //         .phoneNumber("010-1234-1234")
    //         .profileImg("cats.jpeg")
    //         .build();
    //     userRepository.save(가입된_유저);
    // }




    //obj를 string으
    protected static String jsonToString(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    // jwt를 필터 에서 거름으로 이를 우회하기 위한 설정  https://kimyhcj.tistory.com/386
    // DelegatingFilterProxy delegateProxyFilter = new DelegatingFilterProxy();
    // delegateProxyFilter.init(new MockFilterConfig(context.getServletContext(), BeanIds.SPRING_SECURITY_FILTER_CHAIN));
    //
    //



    @BeforeEach
    public void setting(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) throws
        ServletException {



        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
               // .addFilter(delegateProxyFilter)
                .apply(documentationConfiguration(restDocumentationContextProvider)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint()) //요청부분 문서화
                        .withRequestDefaults(modifyUris() //http://docs.api.com 으로 접속
                                .scheme("http")
                                .host("docs.api.com")
                                .removePort())
                       .withResponseDefaults(prettyPrint())//응답부분 문서화
                )
                .build();
    }

    public static final Attributes.Attribute field(
            final String key,
            final String value){
        return new Attributes.Attribute(key,value);
    }

    @AfterEach
    public void init() {
        em.flush();
        em.clear();
    }
}
