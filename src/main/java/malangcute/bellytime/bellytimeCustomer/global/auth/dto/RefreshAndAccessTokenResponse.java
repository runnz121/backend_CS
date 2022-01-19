package malangcute.bellytime.bellytimeCustomer.global.auth.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RefreshAndAccessTokenResponse {

    private String accessToken;

    private String refreshToken;

    public static RefreshAndAccessTokenResponse of(String accessToken, String refreshToken) {
        return new RefreshAndAccessTokenResponse(accessToken, refreshToken);
    }

}
