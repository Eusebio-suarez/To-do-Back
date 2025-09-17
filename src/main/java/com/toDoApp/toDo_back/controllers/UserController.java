package com.toDoApp.toDo_back.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toDoApp.toDo_back.dto.request.UserRequestDTO;
import com.toDoApp.toDo_back.dto.response.UserResponseDTO;
import com.toDoApp.toDo_back.services.UserService;
import com.toDoApp.toDo_back.utils.ApiResponse;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    //endpoint para obtener los usuarios
    public ResponseEntity<ApiResponse<?>>getUsers(){
        try{
            List<UserResponseDTO> users = userService.getUsers();

            if(users!=null){
                return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.builder()
                        .success(true)
                        .message("se obtuvieron los usuarios correctamente")
                        .data(users)
                        .build()
                    );
            }
            else{
                throw new Exception("no se obtuvieron los usuarios");
            }

        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                    .success(false)
                    .message("error :"+e.getMessage())
                    .data(null)
                    .build()
                );
        }
    }

    @PostMapping("")
    //endpoint para registrar un usuario
    public ResponseEntity<ApiResponse<?>>registerUser(@Valid @RequestBody UserRequestDTO userRequest){
        try{
            UserResponseDTO user = userService.registerUser(userRequest);
        
            if(user != null){
                return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.builder()
                        .success(true)
                        .message("usuario creado correctamente")
                        .data(user)
                        .build()
                    );
            }
            else{
                throw new Exception("no se pudo crear el usuario");
            }
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                    .success(false)
                    .message("error al crear el usuario :" +e.getMessage())
                    .data(null)
                    .build()
                );
        }
    }
}
