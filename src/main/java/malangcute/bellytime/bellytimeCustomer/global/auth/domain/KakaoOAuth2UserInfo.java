package malangcute.bellytime.bellytimeCustomer.global.auth.domain;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attribute) throws JSONException {
        super(attribute);
        System.out.println("in kakao auth!!!!!!!!!!!!:" + attribute);
        parsing(attribute);
    }


    public JSONObject parsing(Map<String, Object> at) throws JSONException {
        JSONObject jsonObject = new JSONObject((Map) at.get("kakao_account"));
        System.out.println(jsonObject);
        JSONObject parsed = jsonObject;
        System.out.println(parsed);

        return parsed;
    }



    @Override
    public String getId() throws JSONException {
        return (String) parsing(attribute).get("id");
    }

    @Override
    public String getNickName() throws JSONException {
        return (String) parsing(attribute).get("profile.nickname");
    }

    @Override
    public String getEmail() throws JSONException {
        return (String) parsing(attribute).get("email");
    }

    @Override
    public String getPhone() throws JSONException {
        return (String) parsing(attribute).get("phone");
    }
}
