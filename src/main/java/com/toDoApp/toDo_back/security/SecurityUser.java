package com.toDoApp.toDo_back.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.toDoApp.toDo_back.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;


@Builder
@AllArgsConstructor//adapatar el user entity a un user detaills implementando la interfas
public class SecurityUser implements UserDetails{

    //entidad usuario que se debe adaptar a un userdetails
    private UserEntity user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //rol del usuario
        return List.of(new SimpleGrantedAuthority("ROLE_"+user.getRol()));
    }

    @Override
    public String getPassword() {
        //contraseña del usuario
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        //email del usuario que va a ser utilizado como username
        return user.getEmail();        
    }
    
}
