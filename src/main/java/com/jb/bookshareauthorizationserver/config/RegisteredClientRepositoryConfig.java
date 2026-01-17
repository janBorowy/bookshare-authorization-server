package com.jb.bookshareauthorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.List;
import java.util.UUID;

@Configuration
public class RegisteredClientRepositoryConfig {

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        var clients = List.of(
                RegisteredClient.withId(UUID.randomUUID().toString())
                        .clientId("bookshare-web-client")
                        .clientSecret("{bcrypt}$2a$08$kZVl1.cIwzYU6ldfdVuLIelDkeMxDFC0yiS1ae7KuyYt1KXESmOW.")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .redirectUri("http://127.0.0.1:3000/authorizationCallback")
                        .scope("bookshare-web")
                        .clientSettings(ClientSettings.builder()
                                .requireProofKey(true)
                                .build())
                        .build()
        );
        return new InMemoryRegisteredClientRepository(clients);
    }
}
