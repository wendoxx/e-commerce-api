package org.example.oauth2resourceserverproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        //order
                        .requestMatchers("/api/v1/order/public/**").hasAuthority("SCOPE_USER")

                        //product
                        .requestMatchers("/api/v1/product/public/**").permitAll()
                        .requestMatchers("/api/v1/product/private/**").hasAuthority("SCOPE_ADMIN")
                )
                .build();
    }
}
