package com.workoutly.application.user.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class ApplicationUser implements UserDetails {

    private final Set<? extends GrantedAuthority> grantedAuthorities;
    private final String username;
    private final String password;
    private final boolean isAccountNonExpired;
    private final boolean isAccountNonLocked;
    private final boolean isCredentialsNonExpired;
    private final boolean isEnabled;

    private ApplicationUser(Builder builder) {
        grantedAuthorities = builder.grantedAuthorities;
        username = builder.username;
        password = builder.password;
        isAccountNonExpired = builder.isAccountNonExpired;
        isAccountNonLocked = builder.isAccountNonLocked;
        isCredentialsNonExpired = builder.isCredentialsNonExpired;
        isEnabled = builder.isEnabled;
    }


    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public static final class Builder {
        private Set<? extends GrantedAuthority> grantedAuthorities;
        private String username;
        private String password;
        private boolean isAccountNonExpired;
        private boolean isAccountNonLocked;
        private boolean isCredentialsNonExpired;
        private boolean isEnabled;

        private Builder() {
        }

        public Builder grantedAuthorities(Set<? extends GrantedAuthority> val) {
            grantedAuthorities = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder isAccountNonExpired(boolean val) {
            isAccountNonExpired = val;
            return this;
        }

        public Builder isAccountNonLocked(boolean val) {
            isAccountNonLocked = val;
            return this;
        }

        public Builder isCredentialsNonExpired(boolean val) {
            isCredentialsNonExpired = val;
            return this;
        }

        public Builder isEnabled(boolean val) {
            isEnabled = val;
            return this;
        }

        public ApplicationUser build() {
            return new ApplicationUser(this);
        }
    }
}
