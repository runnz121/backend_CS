package malangcute.bellytime.bellytimeCustomer.global.auth.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenProvider;
import malangcute.bellytime.bellytimeCustomer.global.auth.UserPrincipal;
import malangcute.bellytime.bellytimeCustomer.global.auth.domain.OAuth2UserInfo;
import malangcute.bellytime.bellytimeCustomer.global.auth.oauth.OAuth2UserInfoFactory;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoOAuthProviderException;
import malangcute.bellytime.bellytimeCustomer.user.domain.AuthProvider;
import malangcute.bellytime.bellytimeCustomer.user.domain.Email;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
@AllArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    //정상적인 유저 인증이 완료되면(구글을 통해) -> 여기로 오게됨 그 다음에 successhandler로 감

    private final UserRepository userRepository;

    private final TokenProvider tokenProvider;

//    private final AuthenticationManager authenticationManager;



    // OAuth2User에는 구글 개인정보(요청)이 들어있음
    // 아래 메소드를 바탕으로 인증 처리함
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);


        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }


    //로그인 요청한 유저의 등록 아이디와 속성값을 받아옴때 -> Oauth
    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) throws NoOAuthProviderException {

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 인증받은 유저 정보가 저장되어있는 곳
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);

        Optional<User> userOptional = userRepository.findByEmail(new Email(oAuth2UserInfo.getEmail()));

        if (userOptional.isPresent()){
            User user = userOptional.get();
            return UserPrincipal.createUser(user, attributes);
        }

        User user = registerUser(userRequest, oAuth2UserInfo);

        // Oauth로그인시 이메일 소스제공자를 비밀번호로 쿠키 생성 및 리프레시토큰 추가
//        Authentication loginUser = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        user.getEmail(),
//                        user.getProviderId()
//                )
//        );
//
//        String refreshToken = tokenProvider.createRefreshToken(loginUser);
//        createCookie(response, refreshToken);

        return UserPrincipal.createUser(user, attributes);
    }


    //새로운 유저 등록
    private User registerUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) throws NoOAuthProviderException {
        AuthProvider authProvider = AuthProvider.of(oAuth2UserRequest.getClientRegistration().getRegistrationId());

        User user = User.builder()
                .nickName(oAuth2UserInfo.getNickName())
                .email(oAuth2UserInfo.getEmail())
                .build();

        user.setAuthProvider(authProvider);
        user.setProviderId(oAuth2UserInfo.getId());
        return userRepository.save(user);
    }

}
