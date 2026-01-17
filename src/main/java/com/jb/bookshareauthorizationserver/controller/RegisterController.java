package com.jb.bookshareauthorizationserver.controller;

import com.jb.bookshareauthorizationserver.model.UserRegisterRequest;
import com.jb.bookshareauthorizationserver.service.UserRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final UserRegistrationService userRegistrationService;

    @GetMapping
    public String register() {
        return "register";
    }

    @PostMapping
    public String register(@Valid UserRegisterRequest request) {
        userRegistrationService.register(request);
        return "redirect:/";
    }
}
