package malangcute.bellytime.bellytimeCustomer.config;

import malangcute.bellytime.bellytimeCustomer.global.auth.TokenAuthentication;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenProvider;
import malangcute.bellytime.bellytimeCustomer.global.auth.service.LoginService;
import malangcute.bellytime.bellytimeCustomer.global.config.SecurityConfigsList;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

public abstract class ControllerTest {

    @MockBean
    protected TokenAuthentication tokenAuthentication;

    @MockBean
    protected LoginService loginService;

    @MockBean
    protected UserService userService;

    @MockBean
    protected WebSecurityConfiguration webSecurityConfiguration;

    @MockBean
    protected JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @MockBean
    protected SecurityConfigsList securityConfigsList;

    @MockBean
    protected TokenProvider tokenProvider;

    @MockBean
    protected UserRepository userRepository;
}
