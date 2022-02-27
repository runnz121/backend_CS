package malangcute.bellytime.bellytimeCustomer.global.auth.oauth;

import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.global.auth.domain.GoogleOAuth2UserInfo;
import malangcute.bellytime.bellytimeCustomer.global.auth.domain.KakaoOAuth2UserInfo;
import malangcute.bellytime.bellytimeCustomer.global.auth.domain.NaverOAuth2UserInfo;
import malangcute.bellytime.bellytimeCustomer.global.auth.domain.OAuth2UserInfo;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoOAuthProviderException;
import malangcute.bellytime.bellytimeCustomer.user.domain.AuthProvider;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

@Slf4j
public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) throws NoOAuthProviderException, JSONException {
        if (AuthProvider.GOOGLE.findAuth(registrationId)) {
            return new GoogleOAuth2UserInfo(attributes);
        }
        else if (AuthProvider.KAKAO.findAuth(registrationId)) {
            return new KakaoOAuth2UserInfo(attributes);
        }
        else if (AuthProvider.NAVER.findAuth(registrationId)) {
            return new NaverOAuth2UserInfo(attributes);
        }
        else {
            throw new NoOAuthProviderException(registrationId + " 으로의 로그인은 아직 지원하지 않습니다!");
        }
    }
}
