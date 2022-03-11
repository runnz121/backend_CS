package malangcute.bellytime.bellytimeCustomer.global.auth.domain;

import java.util.Map;

public abstract class OAuth2UserInfo {

    protected Map<String, Object> attribute;

    public OAuth2UserInfo(Map<String, Object> attribute) {
        this.attribute = attribute;
    }


    public abstract String getId();

    public abstract String getNickName();

    public abstract String getEmail();

}
