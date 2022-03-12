package malangcute.bellytime.bellytimeCustomer.global.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FirstLoginDto {

	private Long userId;

	private String userNickName;

	private String accessToken;

	public static FirstLoginDto of (Long userId, String userNickName, String accessToken) {
		return new FirstLoginDto(userId, userNickName, accessToken);
	}
}
