package malangcute.bellytime.bellytimeCustomer.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import malangcute.bellytime.bellytimeCustomer.global.auth.CookieAuthRepositories;
import malangcute.bellytime.bellytimeCustomer.global.auth.JWTExceptionFilter;
import malangcute.bellytime.bellytimeCustomer.global.auth.service.CustomUserService;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenAuthentication;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenProvider;
import malangcute.bellytime.bellytimeCustomer.global.auth.service.CustomOAuth2ForUserService;
import malangcute.bellytime.bellytimeCustomer.global.auth.oauth.OAuth2FailureHandler;
import malangcute.bellytime.bellytimeCustomer.global.auth.oauth.OAuth2SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.ForwardedHeaderFilter;



@EnableWebSecurity
@Configuration
@Slf4j
@AllArgsConstructor
public class SecurityConfigsList extends WebSecurityConfigurerAdapter {



    private final SecurityProperties securityProperties;

    private final CustomOAuth2ForUserService customOAuth2UserService;

    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private final OAuth2FailureHandler oAuth2FailureHandler;

    private final TokenProvider tokenProvider;

    private final CustomUserService userDetailsService;

    private final ObjectMapper objectMapper;

    private static final long MAX_AGE = 24 * 60 * 60 * 100;

    // 인증 메니저
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    // 인증 정보 저장
    @Autowired
    public CookieAuthRepositories cookieAuthRepositories() {
        return new CookieAuthRepositories();
    }

    @Bean
    FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
        final FilterRegistrationBean<ForwardedHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<ForwardedHeaderFilter>();
        filterRegistrationBean.setFilter(new ForwardedHeaderFilter());
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegistrationBean;
    }


    //cors 에러 처리
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        String frontEndDomain = securityProperties.getCors().getFrontEndDomain();

        //배포시 아래 2개로 바꿀것
        configuration.addAllowedOriginPattern("*"); // 이거 다시 비활성화
        //configuration.addAllowedOrigin(frontEndDomain);
        // configuration.addAllowedOrigin("http://localhost:3000/"); //-> 추후 다시 바꾸기
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        //configuration.addExposedHeader("Set-Cookie");
        configuration.setMaxAge(MAX_AGE);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.requiresChannel().anyRequest().requiresSecure().and()
                .cors()
                .and()
                .httpBasic().disable()
                .exceptionHandling().authenticationEntryPoint(new AuthEntryPoint())
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .authorizeRequests()
                .antMatchers(
                        "/h2-console/**",
                        "/oauth2/**",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/login",
                        "/join",
                        "/cookie",
                        "/**", // 배포시 바꿈
                        "/cooltime/check",
                        "/cooltime/**",
                        "/searchby/**"
                ).permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(cookieAuthRepositories())
                .and()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/**")
                .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2SuccessHandler)
                .failureHandler(oAuth2FailureHandler);
                // .and()
                // .addFilterBefore(new TokenAuthentication(tokenProvider, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                // .addFilterBefore(new JWTExceptionFilter(tokenProvider, objectMapper), TokenAuthentication.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
