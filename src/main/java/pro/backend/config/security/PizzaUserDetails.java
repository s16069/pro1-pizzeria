package pro.backend.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pro.backend.model.User;

import java.util.Collection;
import java.util.Collections;

public class PizzaUserDetails implements UserDetails {

    private static final long serialVersionUID = 1113799434508676095L;
    private Collection<? extends GrantedAuthority> authorities;

    private User user;

    public PizzaUserDetails(User user) {
        this.authorities = getGrantedAuthorities(user);
        this.user = user;
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(User user) {
        if (user.getUserRole() == null) {
            return Collections.emptyList();
        }

        String role = user.getUserRole().name();
        GrantedAuthority authority = new SimpleGrantedAuthority(role);

        return Collections.singletonList(authority);
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPass();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return user != null;
    }

}

