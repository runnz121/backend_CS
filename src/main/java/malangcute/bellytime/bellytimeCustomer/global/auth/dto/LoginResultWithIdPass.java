package malangcute.bellytime.bellytimeCustomer.global.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginResultWithIdPass {

	private Long userId;

	private String userNickName;

	private RefreshAndAccessTokenResponse tokens;

	public static LoginResultWithIdPass of (Long userId, String userNickName, RefreshAndAccessTokenResponse tokens) {
		return new LoginResultWithIdPass(userId, userNickName, tokens);
	}
}
