package com.jb.bookshareauthorizationserver.service;

import com.jb.bookshareauthorizationserver.data.repository.UserRepository;
import com.jb.bookshareauthorizationserver.data.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var entity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user with email %s".formatted(email)));
        var roles = userRoleRepository.findRolesById(entity.getId());
        return User.builder()
                .username(entity.getUsername())
                .password(entity.getEncodedPassword())
                .disabled(entity.isDisabled())
                .roles(roles)
                .build();
    }
}
