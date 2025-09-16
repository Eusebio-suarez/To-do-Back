package com.toDoApp.toDo_back.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toDoApp.toDo_back.dto.response.UserResponseDTO;
import com.toDoApp.toDo_back.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    //obtener todos los usuarios
    public List<UserResponseDTO> getUsers(){
        
        return (List<UserResponseDTO>) userRepository.findAll()
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

}
