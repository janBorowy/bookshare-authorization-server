package com.jb.bookshareauthorizationserver.model;

import lombok.Data;

@Data
public class LoginRequest {

    String username;
    String password;
}
