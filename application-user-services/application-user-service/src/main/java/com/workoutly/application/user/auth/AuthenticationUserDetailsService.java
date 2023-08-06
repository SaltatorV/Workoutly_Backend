package com.workoutly.application.user.auth;

import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.port.output.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthenticationUserDetailsService implements UserDetailsService {

    private final static String SUFIX = "ROLE_";

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return toUserDetails(userRepository.findByUsername(username));
    }


    private UserDetails toUserDetails(UserSnapshot snapshot) {
        UserDetails details = ApplicationUser
                .builder()
                .username(snapshot.getUsername())
                .password(snapshot.getPassword())
                .isAccountNonLocked(snapshot.isEnabled())
                .isEnabled(snapshot.isEnabled())
                .isCredentialsNonExpired(snapshot.isEnabled())
                .isAccountNonExpired(snapshot.isEnabled())
                .grantedAuthorities(createPermissionsFromRole(snapshot.getRole()))
                .build();

        return details;
    }

    private Set<SimpleGrantedAuthority> createPermissionsFromRole(UserRole role) {
        Set<SimpleGrantedAuthority> authorities = role
                .getPermissions()
                .stream()
                .map(userPermission -> new SimpleGrantedAuthority(userPermission.getPermission()))
                .collect(Collectors.toSet());

        authorities.add(new SimpleGrantedAuthority(SUFIX + role.name()));

        return authorities;
    }
}