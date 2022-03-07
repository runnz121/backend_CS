package malangcute.bellytime.bellytimeCustomer.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoOAuthProviderException;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum AuthProvider {
    IDPASS("idpass"),
    GOOGLE("google"),
    KAKAO("kakao"),
    NAVER("naver");

    private final String authprovider;

//    AuthProvider(String authprovider) {
//        this.authprovider = authprovider;
//    }


    public static AuthProvider of(String authprovider) throws NoOAuthProviderException {
        return Arrays.stream(AuthProvider.values())
                .filter(authProvider -> authProvider.authprovider.equalsIgnoreCase(authprovider))
                .findFirst()
                .orElseThrow(() -> new NoOAuthProviderException("해당하는 제공자가 없습니다"));
    }

    public boolean findAuth(String authprovider){
        return this.authprovider.equalsIgnoreCase(authprovider);
    }
}
