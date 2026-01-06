package com.jb.bookshareauthorizationserver.service;

import com.jb.bookshareauthorizationserver.data.repository.UserRepository;
import com.jb.bookshareauthorizationserver.mapper.UserRegisterRequestToUserEntityMapper;
import com.jb.bookshareauthorizationserver.model.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final UserRegisterRequestToUserEntityMapper mapper;

    public void register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user with given email already exists");
        }

        var userToSave = mapper.map(request);
        userRepository.save(userToSave);
    }
}
