package com.jb.bookshareauthorizationserver.config;

import com.jb.bookshareauthorizationserver.properties.KeyPathProperties;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class JWKConfig {

    private final KeyPathProperties keyPathProperties;

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        var keyPair = new KeyPair(getPublicKey(), getPrivateKey());
        var publicKey = (RSAPublicKey) keyPair.getPublic();
        var privateKey = (RSAPrivateKey) keyPair.getPrivate();
        var rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        var jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @SneakyThrows
    private PublicKey getPublicKey() {
        var publicKeyPath = ResourceUtils.getFile("classpath:%s"
                .formatted(keyPathProperties.getAuthServerPublicKeyFilePath())).toPath();
        var keyBytes = Files.readAllBytes(publicKeyPath);
        var spec = new X509EncodedKeySpec(keyBytes);
        var kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    @SneakyThrows
    private PrivateKey getPrivateKey() {
        var privateKeyPath = ResourceUtils.getFile("classpath:%s"
                .formatted(keyPathProperties.getAuthServerPrivateKeyFilePath())).toPath();
        var keyBytes = Files.readAllBytes(privateKeyPath);
        var spec = new PKCS8EncodedKeySpec(keyBytes);
        var kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

}
