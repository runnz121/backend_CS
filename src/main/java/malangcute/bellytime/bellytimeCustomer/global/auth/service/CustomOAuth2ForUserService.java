package malangcute.bellytime.bellytimeCustomer.global.auth.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.global.auth.UserPrincipal;
import malangcute.bellytime.bellytimeCustomer.global.auth.domain.OAuth2UserInfo;
import malangcute.bellytime.bellytimeCustomer.global.auth.oauth.OAuth2UserInfoFactory;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoOAuthProviderException;
import malangcute.bellytime.bellytimeCustomer.user.domain.AuthProvider;
import malangcute.bellytime.bellytimeCustomer.user.domain.Email;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class CustomOAuth2ForUserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

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
    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest,
                                         OAuth2User oAuth2User) throws NoOAuthProviderException, JSONException {

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
        return UserPrincipal.createUser(user, attributes);
    }


    //새로운 유저 등록
    private User registerUser(OAuth2UserRequest oAuth2UserRequest,
                              OAuth2UserInfo oAuth2UserInfo) throws NoOAuthProviderException, JSONException {
        AuthProvider authProvider = AuthProvider.of(oAuth2UserRequest.getClientRegistration().getRegistrationId());

        User user = User.builder()
                .email(oAuth2UserInfo.getEmail())
                .passWord(oAuth2UserInfo.getId())
                .phoneNumber("")
                .nickName(oAuth2UserInfo.getNickName())
                .profileImg(null)
                .build();

        user.setAuthProvider(authProvider);

        return userRepository.save(user);
    }
}
