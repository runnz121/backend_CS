package malangcute.bellytime.bellytimeCustomer.global.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import malangcute.bellytime.bellytimeCustomer.global.auth.CookieAuthRepositories;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenAuthentication;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenProvider;
import malangcute.bellytime.bellytimeCustomer.global.auth.oauth.CustomOAuth2UserService;
import malangcute.bellytime.bellytimeCustomer.global.auth.oauth.OAuth2FailureHandler;
import malangcute.bellytime.bellytimeCustomer.global.auth.oauth.OAuth2SuccessHandler;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.security.authentication.AuthenticationManager;
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


@EnableWebSecurity
@AllArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final SecurityProperties securityProperties;

    private final CustomOAuth2UserService customOAuth2UserService;

    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private final OAuth2FailureHandler oAuth2FailureHandler;

    private final CookieAuthRepositories cookieAuthRepositories;

    private final TokenProvider tokenProvider;

    private final UserService userDetailsService;

    private final long MAX_AGES = 3600;


    // 인증 메니저
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    // 인증 정보 저장
    @Autowired
    public CookieAuthRepositories cookieAuthRepositories() {
        return new CookieAuthRepositories();
    }


    //cors 에러 처리
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        String frontEndDomain = securityProperties.getCors().getFrontEndDomain();

        configuration.addAllowedOrigin(frontEndDomain);
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(MAX_AGES);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
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
                        "/login"
                ).permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().hasAnyRole("USER","ADMIN")
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
                .failureHandler(oAuth2FailureHandler)
                .and()
                .addFilterBefore(new TokenAuthentication(tokenProvider, userDetailsService), UsernamePasswordAuthenticationFilter.class);
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
