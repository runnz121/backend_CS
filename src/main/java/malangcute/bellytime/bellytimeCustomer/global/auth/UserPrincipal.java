package malangcute.bellytime.bellytimeCustomer.global.auth;

import malangcute.bellytime.bellytimeCustomer.user.domain.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class UserPrincipal implements UserDetails, OAuth2User {

    private final Long id;

    private final String email;

    private final String password;

    private final String nickName;

    private final List<String> roles;

    private Map<String, Object> attributes;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String email, String password,
                         String nickName,
                         List<String> roles,
                         Collection<? extends GrantedAuthority> authorities){
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.roles = roles;
        this.authorities = authorities;
    }



    public static UserPrincipal createUser(User user){
        return new UserPrincipal(
                user.getId(),
                user.getEmail().getEmail(),
                user.getPassword(),
                user.getNickname().getNickName(),
                user.getRoles(),
                user.getAuthorities()
        );
    }


    @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nickName;
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

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }


}
