package com.toDoApp.toDo_back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    @Bean //registar un objeto en el contenedor de spring(gestionado automaticamente)
    public SecurityFilterChain securityChain(HttpSecurity http) throws Exception{

        return http
            //desactivar el csrf
            .csrf(csrf -> csrf.disable())
            //configurar los endpoints 
            .authorizeHttpRequests(auth -> auth
                //rutas permitidas
                .requestMatchers("/api/v1/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults())
            .build();

    }
}
