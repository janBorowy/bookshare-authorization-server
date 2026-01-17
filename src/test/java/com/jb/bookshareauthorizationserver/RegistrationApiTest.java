package com.jb.bookshareauthorizationserver;

import com.jb.bookshareauthorizationserver.data.entity.UserEntity;
import com.jb.bookshareauthorizationserver.data.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@Import(MySQLTestContainerConfig.class)
class RegistrationApiTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void userRegisterSuccess() throws Exception {
        mvc.perform(post(getFullUrl("/register"))
                        .with(csrf())
                        .param("username", "test username")
                        .param("email", "test@mail.com")
                        .param("plainPassword", "testPassword"))
                .andExpect(status().is3xxRedirection());

        assertThat(userRepository.findByEmail("test@mail.com"))
                .get()
                .doesNotMatch(UserEntity::isDisabled);
    }

    @Test
    void userRegisterBadRequest() throws Exception {
        mvc.perform(post(getFullUrl("/register"))
                        .with(csrf())
                        .param("username", "test")
                        .param("email", "test")
                        .param("plainPassword", "test password"))
                .andExpect(status().isBadRequest());

        assertThat(userRepository.findByEmail("test@mail.com"))
                .isEmpty();
    }

    @Test
    void userWithSameEmailAlreadyExists() throws Exception {
        userRepository.save(UserEntity.builder()
                .username("iAlreadyExist")
                .email("alreadyExists@mail.com")
                .encodedPassword("somePassword")
                .build());

        mvc.perform(post(getFullUrl("/register"))
                        .with(csrf())
                        .param("username", "iWannaMakeNewAccount")
                        .param("email", "alreadyExists@mail.com")
                        .param("plainPassword", "myNewPassword"))
                .andExpect(status().isBadRequest());

        assertThat(userRepository.findByEmail("alreadyExists@mail.com"))
                .get()
                .matches(it -> Objects.equals(it.getUsername(), "iAlreadyExist"));
    }

    private String getFullUrl(String path) {
        return "http://localhost:%s%s".formatted(port, path);
    }
}
