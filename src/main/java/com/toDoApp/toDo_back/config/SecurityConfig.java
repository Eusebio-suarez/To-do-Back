package com.toDoApp.toDo_back.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.toDoApp.toDo_back.security.filters.JwtAuthorizationFilter;


@Configuration
@EnableWebSecurity// se le indica a spring que esta clase contiene las reglas y la configuracion de seguridad
@EnableMethodSecurity(prePostEnabled=true)//habilita la seguridad a nivel de metodos (post y pre autenticación/filtro)
public class SecurityConfig {

    @Bean
    //metodo para encriptar las
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //filtro de jwt
    @Autowired
    public JwtAuthorizationFilter jwtAuthorizationFilter;
    
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
            //definir que no sevan a manejar sessiones
            .sessionManagement(sesion ->sesion
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
            //.httpBasic(Customizer.withDefaults())
            .build();
    }
}
