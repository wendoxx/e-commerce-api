package org.example.ecommerce.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher) throws Exception {
        return http.authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("api/login").permitAll()
                        .requestMatchers("api/register").permitAll()
                        .requestMatchers("api/v1/order/public/**").permitAll()
                        .requestMatchers("api/v1/product/private/").authenticated()
                        .requestMatchers("api/v1/product/private/save-product").hasRole("ROLE_ADMIN")
                        .requestMatchers("api/v1/product/private/update-product").hasRole("ROLE_ADMIN")
                        .requestMatchers("api/v1/order/private/save-order").hasRole("ROLE_ADMIN")
                        .requestMatchers("api/v1/order/private/update-order").hasRole("ROLE_ADMIN")
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}