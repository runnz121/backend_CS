package malangcute.bellytime.bellytimeCustomer.user.domain;


import lombok.*;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoUserException;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserImg extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private String url;

    //연관관계 주인 -> private UserImg profileImg
    @OneToOne(mappedBy = "profileImg")

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
    private User user;


    @Builder
    public UserImg(String name, String url){
        this.name = name;
        this.url = url;
    }

    public boolean hasNotUser() {
        return Objects.isNull(this.user);
    }

    public void setUser(User user) {
        if (Objects.isNull(this.user)) {
            this.user = user;
            return;
        }
        throw new NoUserException("사용자 사진을 지정할 수 없습니다.");
    }

}
