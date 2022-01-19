package malangcute.bellytime.bellytimeCustomer.global.auth.dto;


import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Builder
@ToString
public class RegisterRequest {

    private String id;

    private String password;

    private String name;

    private String nickname;

    private String phonenumber;

    private String profileImg;
}
