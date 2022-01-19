package malangcute.bellytime.bellytimeCustomer.global.auth.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AccessTokenResponseDto {

    private String accessToken;



}
