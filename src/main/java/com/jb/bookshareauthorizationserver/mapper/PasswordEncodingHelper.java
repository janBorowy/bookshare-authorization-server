package com.jb.bookshareauthorizationserver.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Named("passwordEncodingHelper")
@Service
@RequiredArgsConstructor
public class PasswordEncodingHelper {

    private final PasswordEncoder passwordEncoder;

    @Named("encodePassword")
    public String encodePassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }
}
