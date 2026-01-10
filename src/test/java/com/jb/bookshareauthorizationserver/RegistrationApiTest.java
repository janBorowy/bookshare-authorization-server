package com.jb.bookshareauthorizationserver;

import com.jb.bookshareauthorizationserver.data.entity.UserEntity;
import com.jb.bookshareauthorizationserver.data.repository.UserRepository;
import com.jb.bookshareauthorizationserver.model.UserRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.jb.bookshareauthorizationserver.JacksonTest.OBJECT_MAPPER;
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
        var request = UserRegisterRequest.builder()
                .username("test username")
                .email("test@mail.com")
                .plainPassword("test password")
                .build();
        mvc.perform(post(getFullUrl("/register"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(request)))
                .andExpect(status().is3xxRedirection());

        assertThat(userRepository.findByEmail("test@mail.com"))
                .isNotEmpty()
                .get()
                .doesNotMatch(UserEntity::isDisabled);
    }

    @Test
    void userRegisterBadRequest() throws Exception {
        var request = UserRegisterRequest.builder()
                .username("test")
                .email("test@mail.com")
                .plainPassword("test password")
                .build();
        mvc.perform(post(getFullUrl("/register"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        assertThat(userRepository.findByEmail("test@mail.com"))
                .isEmpty();
    }

    private String getFullUrl(String path) {
        return "http://localhost:%s%s".formatted(port, path);
    }
}
