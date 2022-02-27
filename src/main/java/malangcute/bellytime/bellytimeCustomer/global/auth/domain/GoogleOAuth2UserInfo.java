package malangcute.bellytime.bellytimeCustomer.global.auth.domain;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(Map<String, Object> attribute) {

        super(attribute);
        System.out.println("in gogole auth!!!!!!!!!!!!:" + attribute);
    }

    @Override
    public String getId() {
        return (String) attribute.get("sub");
    }

    @Override
    public String getNickName() {
        return (String) attribute.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attribute.get("email");
    }

    @Override
    public String getPhone() {
        return (String) attribute.get("phone");
    }
}
