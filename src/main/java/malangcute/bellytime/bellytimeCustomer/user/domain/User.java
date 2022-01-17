package malangcute.bellytime.bellytimeCustomer.user.domain;


import lombok.*;
import malangcute.bellytime.bellytimeCustomer.user.domain.common.BaseTimeEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@ToString
@Table(name= "users")
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

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = true)
    private String profileImg;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>(Collections.singletonList(Role.USER.getRoleName()));

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider authProvider;

    private String providerId;


    @Builder
    public User (Long id, String nickName, String email, String passWord, String phoneNumber,String profileImg){
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
}
