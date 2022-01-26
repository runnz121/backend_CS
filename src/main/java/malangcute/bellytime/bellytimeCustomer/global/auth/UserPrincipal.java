package malangcute.bellytime.bellytimeCustomer.global.auth;

import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserPrincipal implements UserDetails, OAuth2User {

    private final Long id;

    private final String email;

    private final String password;

    private final List<String> roles;

    private Map<String, Object> attributes;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String email, String password,
                         List<String> roles,
                         Collection<? extends GrantedAuthority> authorities){
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.authorities = authorities;
    }

    public static UserPrincipal createUser(User user){
        return new UserPrincipal(
                user.getId(),
                user.getEmail().getEmail(),
                user.getPassword(),
                user.getRoles(),
                user.getAuthorities()
        );
    }

    public static UserPrincipal createUser(User user, Map<String, Object> attributes){
        UserPrincipal userPrincipal = UserPrincipal.createUser(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public Long getId(){
        return id;
    }

    public String getEmail(){
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public String getUsername() {
        return getName();
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

    public void setAttributes(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    public List<String> getRoles() {
        return Collections.unmodifiableList(this.roles);
    }


}
