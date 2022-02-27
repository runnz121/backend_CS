package malangcute.bellytime.bellytimeCustomer.global.auth.domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public abstract class OAuth2UserInfo {

    protected Map<String, Object> attribute;

    public OAuth2UserInfo(Map<String , Object> attribute){
        this.attribute = attribute;
    }


    public abstract String getId() throws JSONException;

    public abstract String getNickName() throws JSONException;

    public abstract String getEmail() throws JSONException;

    public abstract String getPhone() throws JSONException;
}
