package com.youtube.jwttutorial.Security.Services;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.youtube.jwttutorial.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UserDetailsImpl implements UserDetails {
    private User user;
    private Collection<? extends GrantedAuthority> authorities;
    public UserDetailsImpl(User user,Collection<? extends GrantedAuthority> authorities) {
        this.user=user;
        this.authorities = authorities;
    }
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRole().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getRoleName()))
                .collect(Collectors.toList());
        System.out.println(authorities);
        return new UserDetailsImpl(
                user,
                authorities);
    }

    public String getUserFirstName() {
        return user.getUserFirstName();
    }

    public String getUserLastName(){
        return user.getUserLastName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassWord();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
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
}
