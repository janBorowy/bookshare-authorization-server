package com.jb.bookshareauthorizationserver.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class UserRegisterRequest {

    @NotBlank
    @Length(min = 3, max = 100)
    String username;

    @NotBlank
    @Length(min = 8, max = 64)
    String plainPassword;

    @NotBlank
    @Email
    String email;
}
