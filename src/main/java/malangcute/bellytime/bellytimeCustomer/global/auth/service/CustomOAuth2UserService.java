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
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

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
        System.out.println("인증유저" + oAuth2User);
        System.out.println("***********useRequest********");
        System.out.println(userRequest);
        System.out.println("clientRegistration : " +userRequest.getClientRegistration().getClientName());
        System.out.println("clientRegistration : " +userRequest.getClientRegistration().getClientId());
        System.out.println("accestoken : " +userRequest.getAccessToken().getTokenValue());
        System.out.println("additionaparameter : " +userRequest.getAdditionalParameters());
        System.out.println("***********END********");
        //userRequest.getAdditionalParameters().put("id_token",userRequest.getAccessToken());

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }


    //로그인 요청한 유저의 등록 아이디와 속성값을 받아옴때 -> Oauth
    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) throws NoOAuthProviderException, JSONException {

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        System.out.println("here" + attributes);

        // 인증받은 유저 정보가 저장되어있는 곳
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);

        Optional<User> userOptional = userRepository.findByEmail(new Email(oAuth2UserInfo.getEmail()));

        if (userOptional.isPresent()){
            User user = userOptional.get();
            return UserPrincipal.createUser(user, attributes);
        }
        User user = registerUser(userRequest, oAuth2UserInfo);

        System.out.println("process :" + user);

        return UserPrincipal.createUser(user, attributes);
    }


    //새로운 유저 등록
    private User registerUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) throws NoOAuthProviderException, JSONException {
        AuthProvider authProvider = AuthProvider.of(oAuth2UserRequest.getClientRegistration().getRegistrationId());

        System.out.println("authprovider: " + authProvider);
        System.out.println(oAuth2UserInfo.getId());
        System.out.println(oAuth2UserInfo.getEmail());
        System.out.println(oAuth2UserInfo.getNickName());

        User user = User.builder()
                .email(oAuth2UserInfo.getEmail())
                .passWord(oAuth2UserInfo.getId())
                .phoneNumber("")
                .nickName("USER")
                .profileImg(null)
                .build();

        user.setAuthProvider(authProvider);

        return userRepository.save(user);
    }
}
