package com.stefanini.technicaltest.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class CustomWebSecurityAdapter {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorizations) ->
                authorizations
                .requestMatchers("/api/**", "/v*/**").permitAll()
                .anyRequest().permitAll()/*authenticated()*/);// .disable() Deshabilita CSRF para pruebas de SOAP
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

}
