package malangcute.bellytime.bellytimeCustomer.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.persistence.EntityManager;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class}) //rest docs, spring test 시 적용
@SpringBootTest
@Transactional
//@MockBean(JpaMetamodelMappingContext.class)
public abstract class TestSupport { //extends ControllerTest

    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected EntityManager em;


    //obj를 string으
    protected String jsonToString(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    // jwt를 필터 에서 거름으로 이를 우회하기 위한 설정  https://kimyhcj.tistory.com/386
    //DelegatingFilterProxy delegateProxyFilter = new DelegatingFilterProxy();
    //delegateProxyFilter.init(new MockFilterConfig(context.getServletContext(), BeanIds.SPRING_SECURITY_FILTER_CHAIN));


    @BeforeEach
    public void setting(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                //.addFilter(delegateProxyFilter)
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
