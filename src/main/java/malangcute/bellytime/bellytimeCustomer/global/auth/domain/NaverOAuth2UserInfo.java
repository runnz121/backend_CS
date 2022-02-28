package malangcute.bellytime.bellytimeCustomer.global.auth.domain;

import org.json.JSONObject;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    private final static String NAVER_KEY = "response";

    public NaverOAuth2UserInfo(Map<String, Object> attribute) {
        super(attribute);
        parsing(attribute);
    }

    public JSONObject parsing(Map<String, Object> at) {
        return new JSONObject((Map) at.get(NAVER_KEY));
    }


    @Override
    public String getId() {
        return (String) parsing(attribute).get("id");
    }

    @Override
    public String getNickName() {
        return (String) parsing(attribute).get("name");
    }

    @Override
    public String getEmail() {
        return (String) parsing(attribute).get("email");
    }


}
