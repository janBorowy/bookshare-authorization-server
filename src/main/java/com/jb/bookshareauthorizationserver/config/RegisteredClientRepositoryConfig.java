package com.jb.bookshareauthorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Configuration
public class RegisteredClientRepositoryConfig {

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        // TODO: register client some other way
        var clients = List.of(
                RegisteredClient.withId(UUID.randomUUID().toString())
                        .clientId("bookshare-web-client")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .redirectUri("http://localhost:3000/authorizationCallback")
                        .scope("bookshare-web")
                        .clientSettings(ClientSettings.builder()
                                .requireProofKey(true)
                                .build())
                        .tokenSettings(TokenSettings.builder()
                                // TODO: dev value
                                .accessTokenTimeToLive(Duration.ofDays(1))
                                .refreshTokenTimeToLive(Duration.ofDays(7))
                                .build())
                        .build()
        );
        return new InMemoryRegisteredClientRepository(clients);
    }
}
