package malangcute.bellytime.bellytimeCustomer.user.domain;


import lombok.*;
import malangcute.bellytime.bellytimeCustomer.user.domain.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@ToString
@Table(name= "users")
public class User extends BaseTimeEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private NickName nickname;

    @Embedded
    private Email email;

    @Embedded
    private PassWord password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = true)
    private String profileImg;

    @Builder
    public User (Long id, String nickName, String email, String passWord, String phoneNumber,String profileImg){
        this.id = id;
        this.nickname = new NickName(nickName);
        this.email = new Email(email);
        this.password = new PassWord(passWord);
        this.phoneNumber = phoneNumber;
        this.profileImg = profileImg;
    }



}
