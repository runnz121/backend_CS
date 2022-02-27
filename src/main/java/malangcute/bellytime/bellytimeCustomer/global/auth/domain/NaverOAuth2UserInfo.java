package malangcute.bellytime.bellytimeCustomer.global.auth.domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    public NaverOAuth2UserInfo(Map<String, Object> attribute) throws JSONException {
        super(attribute);
        System.out.println("in naver auth!!!!!!!!!!!!:" + attribute);
        System.out.println(attribute.get("response"));
        parsing(attribute);
    }

    public JSONObject parsing(Map<String, Object> at) throws JSONException {
        JSONObject jsonObject = new JSONObject((Map) at.get("response"));
        System.out.println(jsonObject);
        JSONObject parsed = jsonObject;
        System.out.println("in json" + parsed.get("id"));
        return parsed;
    }



    @Override
    public String getId() throws JSONException {
        System.out.println(parsing(attribute).get("id"));
        return (String) parsing(attribute).get("id");
    }

    @Override
    public String getNickName() throws JSONException {
        return (String) parsing(attribute).get("name");
    }

    @Override
    public String getEmail() throws JSONException {
        return (String) parsing(attribute).get("email");
    }

    @Override
    public String getPhone() throws JSONException {
        return (String) parsing(attribute).get("mobile");
    }
}
