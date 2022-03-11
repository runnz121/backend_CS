package malangcute.bellytime.bellytimeCustomer.global.auth.domain;

import java.util.Map;

import org.json.JSONObject;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    private static final String KAKAO_KEY = "kakao_account";

    public KakaoOAuth2UserInfo(Map<String, Object> attribute) {
        super(attribute);
        parsing(attribute);
    }

    public JSONObject parsing(Map<String, Object> at) {
        return new JSONObject((Map) at.get(KAKAO_KEY));
    }



    @Override
    public String getId() {
        return (String) parsing(attribute).get("id");
    }

    @Override
    public String getNickName() {
        return (String) parsing(attribute).get("profile.nickname");
    }

    @Override
    public String getEmail() {
        return (String) parsing(attribute).get("email");
    }


}
