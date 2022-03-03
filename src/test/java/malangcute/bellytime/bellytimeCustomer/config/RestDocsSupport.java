package malangcute.bellytime.bellytimeCustomer.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenAuthentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class}) //rest docs, spring test 시 적용
@SpringBootTest
//@MockBean(JpaMetamodelMappingContext.class)
public abstract class RestDocsSupport  { //extends ControllerTest

    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper;


    //obj를 string으
    protected String jsonToString(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    @BeforeEach
    public void setting(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
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
}
