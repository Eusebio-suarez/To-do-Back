package com.toDoApp.toDo_back.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.toDoApp.toDo_back.dto.request.LoginRequestDTO;
import com.toDoApp.toDo_back.dto.request.UserRequestDTO;
import com.toDoApp.toDo_back.dto.response.UserResponseDTO;
import com.toDoApp.toDo_back.entity.UserEntity;
import com.toDoApp.toDo_back.repository.UserRepository;
import com.toDoApp.toDo_back.security.JwtUtils;
import com.toDoApp.toDo_back.utils.RoleEnum;

import jakarta.servlet.http.HttpServletRequest;


@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

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
            .password(encoder.encode(userRequest.getPassword()))
            .rol(RoleEnum.USER)
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

    //autenticar un usuario
    public Optional<UserEntity> authenticate(LoginRequestDTO loginRequest){
        //bucar el usuario con el repository
        Optional<UserEntity> userSearched = userRepository.findByEmail(loginRequest.getEmail());

        //validar que se encontro el usuario
        if(userSearched.isPresent()){
            //obtener el valor del opcional(userEntity)
            UserEntity user = userSearched.get();

            //comparar las contraseñas con el password encoder
            if(encoder.matches(loginRequest.getPassword(),user.getPassword())){
                //de devuelve la entidad ya que la contraseña es correcta
                return Optional.of(user);
            }
        }

        return Optional.empty();//la contraseña o el usuario son incorrectos
    }

    //obtener la informacion de un usuario con el token
    public Optional<UserEntity> getUserByRequest(HttpServletRequest request){
        //obtener el token de los header para obtener el usuario
        String authorization = request.getHeader("Authorization");

            //validar que este el token
        if(authorization != null && authorization.startsWith("Bearer ")){

            String token = authorization.substring(7);

            //obtener el subjet del token
            String email = jwtUtils.getUsername(token);

            //bucar el usuario con el subject(email) del token
            UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("no se encontro el usuario"));

            return Optional.of(user);
        }

        return Optional.empty();
    }

}
