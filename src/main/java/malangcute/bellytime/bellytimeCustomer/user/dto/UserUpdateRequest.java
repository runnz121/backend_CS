package malangcute.bellytime.bellytimeCustomer.user.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
public class UserUpdateRequest {

    private String email;

    private String nickname;

    private MultipartFile profileImg;

}
