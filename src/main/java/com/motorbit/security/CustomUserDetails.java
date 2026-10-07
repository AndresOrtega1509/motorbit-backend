package com.motorbit.security;

import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.motorbit.model.Usuario;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter  
@Builder 
@RequiredArgsConstructor 
public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public static CustomUserDetails createFromUsuario(Usuario usuario) {
        return CustomUserDetails.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(
                    List.of(
                        new SimpleGrantedAuthority(
                            "ROLE_" + usuario.getRol().name()
                        )
                    )
                )
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
