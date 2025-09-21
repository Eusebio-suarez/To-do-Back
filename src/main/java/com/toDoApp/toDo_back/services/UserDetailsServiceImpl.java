package com.toDoApp.toDo_back.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.toDoApp.toDo_back.entity.UserEntity;
import com.toDoApp.toDo_back.repository.UserRepository;
import com.toDoApp.toDo_back.security.SecurityUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;//repositorio para hacer las consultas a la base de datos  

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //buscar el usuario en la base de datos
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);

        //verificar si el optional tiene un valor
        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("no se encontro el usuario con correo: "+email);
        }

        //obtener el userEntity
        UserEntity user = userOptional.get();

        //retornar un objeto de tipo userdetails con la informacion del usuario
        return new SecurityUser(user);
    }
    
}
