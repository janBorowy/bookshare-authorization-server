package com.jb.bookshareauthorizationserver.controller;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String login(@RequestParam(name = "error", defaultValue = "false") String isLoginError,
                        Model model) {
        model.addAttribute("isLoginError", BooleanUtils.toBoolean(isLoginError));
        return "login";
    }

}
