package malangcute.bellytime.bellytimeCustomer.global.auth.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginWithIdAndPassRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;


}
