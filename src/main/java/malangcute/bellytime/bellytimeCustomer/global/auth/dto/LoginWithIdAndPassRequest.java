package malangcute.bellytime.bellytimeCustomer.global.auth.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginWithIdAndPassRequest {


    private String email;

    private String password;


}
