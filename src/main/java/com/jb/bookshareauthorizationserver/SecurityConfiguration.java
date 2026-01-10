package com.jb.bookshareauthorizationserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final String ENCODE_ID = "bcrypt";
    private static final int BCRYPT_ENCRYPTION_STRENGTH = 8;

    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        var authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();
        return http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(HttpMethod.GET, "/login").permitAll();
                    authorize.requestMatchers(HttpMethod.GET, "/register").permitAll();
                    authorize.requestMatchers(HttpMethod.GET, "/public/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/register").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .formLogin(formLogin -> {
                    formLogin.loginPage("/login")
                            .loginProcessingUrl("/login")
                            .failureUrl("/login?error=true")
                            .defaultSuccessUrl("/", true);
                })
                .logout(Customizer.withDefaults())
//                .oauth2AuthorizationServer(Customizer.withDefaults())
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new DelegatingPasswordEncoder(ENCODE_ID, Map.of(
                ENCODE_ID, new BCryptPasswordEncoder(BCRYPT_ENCRYPTION_STRENGTH)
        ));
    }

    // TODO: this stuff is for client
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var source = new UrlBasedCorsConfigurationSource();
        var config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("http://127.0.0.1:4200");
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
