package malangcute.bellytime.bellytimeCustomer.global.auth.dto;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter //ModelAttribute
@Builder
@ToString
public class RegisterWithIdPassRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    @NotNull
    private String phoneNumber;

    private MultipartFile profileImg;
}
