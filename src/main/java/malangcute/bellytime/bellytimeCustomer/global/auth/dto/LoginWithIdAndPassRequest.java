package malangcute.bellytime.bellytimeCustomer.global.auth.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Builder
public class LoginWithIdAndPassRequest {


    private String email;


    private String password;
}
