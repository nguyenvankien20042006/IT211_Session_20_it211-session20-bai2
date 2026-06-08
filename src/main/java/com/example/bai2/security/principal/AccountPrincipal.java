package com.example.bai2.security.principal;

import com.example.bai2.model.entity.Account;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountPrincipal implements UserDetails {
    private Account account;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return account.getRole().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole())).toList();
    }

    @Override
    public @Nullable String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
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
        return account.getIsActive();
    }
}
