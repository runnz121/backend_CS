package malangcute.bellytime.bellytimeCustomer.user.domain;


import lombok.*;
import malangcute.bellytime.bellytimeCustomer.chat.domain.Chat;
import malangcute.bellytime.bellytimeCustomer.comment.domain.Comment;
import malangcute.bellytime.bellytimeCustomer.cooltime.domain.CoolTime;
import malangcute.bellytime.bellytimeCustomer.follow.domain.FollowShop;
import malangcute.bellytime.bellytimeCustomer.follow.domain.FollowUser;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;
import malangcute.bellytime.bellytimeCustomer.reservation.domain.Reservation;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name ="users")
//@Table(name= "users")
public class User extends BaseTimeEntity implements UserDetails {

    @Id
    // 테스트 때문에 주석처리
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private NickName nickname;

    @Embedded
    private Email email;

    @Embedded
    private PassWord password;

    //@Column(name = "phonenumber" , nullable = false)
    private String phoneNumber;

    //유저 프로필 이미지
    //user가 연관관계 주인임으로 mappedby -> users
//    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
//    @JoinColumn(name = "profileimg")

    //연관관계 주인을 userimg로
    //@OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)

    //@Column(name = "profileImg")
    private String profileImg;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>(Collections.singletonList(Role.USER.getRoleName()));

    @Enumerated(EnumType.STRING)
    //@Column(name = "authprovider")
    private AuthProvider authProvider;

    //@Column(name = "refreshtoken")
    private String refreshToken;

    //@Column(name = "providerid")
    private String providerId;

    /*
        연관관계 : N쪽이 외래키 있음 = N쪽이 주인이다 = 주인은 mapped by 사용 안함(따라서 user에 mappedby 적용 주인아니니깐)
     */
    //mappedby = 반대쪽에 매핑되는 필드값
    @OneToMany(mappedBy = "userId", cascade = CascadeType.PERSIST, orphanRemoval = true) //정보만 맵핑
    private List<CoolTime> coolTime = new ArrayList<>();

    /**
     * 채팅 설정
     */
    @OneToMany(mappedBy = "makerId", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Chat> makerId = new ArrayList<>();

    @OneToMany(mappedBy = "inviteId")
    private List<Chat> inviteId = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<FollowShop> followShops = new ArrayList<>();
    //private Set<FollowShop> followShops = new LinkedHashSet<>();
    //private List<FollowShop> followShops = new ArrayList<>();

    /**
     * follower
     */
    @OneToMany(mappedBy = "hostId", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<FollowUser> hostId = new ArrayList<>();

    @OneToMany(mappedBy = "friendId", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<FollowUser> friendId = new ArrayList<>();

    /**
     *  reservaiton
     */
    @OneToMany(mappedBy = "userId", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Reservation> userId = new ArrayList<>();

    /**
     *
     */
    @OneToMany(mappedBy = "userId")
    private List<Comment> commentId = new ArrayList<>();






    @Builder
    public User (Long id, String nickName, String email, String passWord, String phoneNumber, String profileImg){
        this.id = id;
        this.nickname = new NickName(nickName);
        this.email = new Email(email);
        this.password = new PassWord(passWord);
        this.phoneNumber = phoneNumber;
        this.profileImg = profileImg;
    }


    //역할별 권한 부여
    @Override
    public Collection <? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    //바뀌지 않도록 역할 부여
    public List<String> getRoles() {
        return Collections.unmodifiableList(this.roles);
    }

    @Override
    public String getUsername() {
        return getNickname().getNickName();
    }

    @Override
    public String getPassword(){
        return this.password.getPassWord();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public void setProviderId(String providerId){
        this.providerId = providerId;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setProfileImg(String imgUrl) { this.profileImg = imgUrl; }

    public void setNickname(String nickname) {this.nickname = new NickName(nickname);}


//    public void setImg(UserImg profileImg) {
//        this.profileImg = profileImg;
//    }
//
//    private void setUserImg(UserImg userImg) {
//        if (Objects.isNull(userImg)) {
//            return;
//        }
//        if (userImg.hasNotUser()) {
//            profileImg.setUser(this);
//        }
//    }
}
