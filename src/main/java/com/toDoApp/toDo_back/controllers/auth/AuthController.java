package com.toDoApp.toDo_back.controllers.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toDoApp.toDo_back.dto.request.LoginRequestDTO;
import com.toDoApp.toDo_back.dto.request.UserRequestDTO;
import com.toDoApp.toDo_back.dto.response.UserResponseDTO;
import com.toDoApp.toDo_back.entity.UserEntity;
import com.toDoApp.toDo_back.security.JwtUtils;
import com.toDoApp.toDo_back.services.UserService;
import com.toDoApp.toDo_back.utils.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    //endpoint para registrar un usuario
    @PostMapping("/register")
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
    
    //endpoint para iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>>logIn(@Valid @RequestBody LoginRequestDTO loginRequest){
        try {
            //obtener el usuario con el servicio
            UserEntity user = userService.authenticate(loginRequest)
                .orElseThrow(()-> new Exception("correo o contraseña incorrrecta"));

            if(user !=null){
                //crear el token
                String token =jwtUtils.generateToken(user.getEmail());

                //devolver la respuesta con el token
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(ApiResponse.builder()
                        .success(true)
                        .message("login exitoso")
                        .data(token)
                        .build()
                    );
            }
            else{
                throw new Exception("no sé encontro el usuario");
            }    
        } 
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                    .success(false)
                    .message("error de sesión :"+e.getMessage())
                    .data(null)
                    .build()
                );
        }
    }

}