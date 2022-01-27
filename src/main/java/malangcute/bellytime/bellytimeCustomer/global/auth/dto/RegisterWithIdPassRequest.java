package malangcute.bellytime.bellytimeCustomer.global.auth.dto;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter //ModelAttribute
@Builder
@ToString
public class RegisterWithIdPassRequest {

    private String email;

    private String password;

    private String name;

    private String nickname;

    private String phoneNumber;

    private MultipartFile profileImg;
}
