package com.jb.bookshareauthorizationserver.service;

import com.jb.bookshareauthorizationserver.data.repository.UserRepository;
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var entity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user with email %s".formatted(email)));
        return User.builder()
                .username(entity.getUsername())
                .password(entity.getEncodedPassword())
                .disabled(entity.isDisabled())
                .build();
    }
}
