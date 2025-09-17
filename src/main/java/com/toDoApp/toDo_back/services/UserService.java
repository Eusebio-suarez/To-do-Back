package com.toDoApp.toDo_back.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toDoApp.toDo_back.dto.request.UserRequestDTO;
import com.toDoApp.toDo_back.dto.response.UserResponseDTO;
import com.toDoApp.toDo_back.entity.UserEntity;
import com.toDoApp.toDo_back.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    //obtener todos los usuarios
    public List<UserResponseDTO> getUsers(){
        
        return userRepository.findAll()
            .stream()
            .map(user -> UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build()
            )
            .toList();
            
    }

    //registar un usuario
    public UserResponseDTO registerUser(UserRequestDTO userRequest){

        UserEntity userEntity = UserEntity.builder()
            .email(userRequest.getEmail())
            .name(userRequest.getName())
            .password(userRequest.getPassword())
            .createdAt(LocalDateTime.now())
            .build();

        UserEntity userRegister = userRepository.save(userEntity);

        return UserResponseDTO.builder()
            .id(userRegister.getId())
            .name(userRegister.getName())
            .email(userRegister.getEmail())
            .createdAt(userRegister.getCreatedAt())
            .build();
    }

}
