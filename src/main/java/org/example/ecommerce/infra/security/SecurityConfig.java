package org.example.ecommerce.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher) throws Exception {
        return http.authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("api/v1/order/public/**").permitAll()
                        .requestMatchers("api/v1/product/private/").authenticated()
                        .requestMatchers("api/v1/product/private/save-product").hasRole("ROLE_ADMIN")
                        .requestMatchers("api/v1/product/private/update-product").hasRole("ROLE_ADMIN")
                        .requestMatchers("api/v1/order/private/save-order").hasRole("ROLE_ADMIN")
                        .requestMatchers("api/v1/order/private/update-order").hasRole("ROLE_ADMIN")

                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("username")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(userDetails); //TODO: modificar, pois o usuário vai ser salvo no DB
    }
}
