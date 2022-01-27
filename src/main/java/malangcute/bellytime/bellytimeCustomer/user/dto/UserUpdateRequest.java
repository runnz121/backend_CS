package malangcute.bellytime.bellytimeCustomer.user.dto;

import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
public class UserUpdateRequest {

    private String email;

    private String nickname;

    private MultipartFile profileImg;

}
