package com.toDoApp.toDo_back.controllers.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    
    @GetMapping("/login")
    public String logIn(){
        return "login success";
    }

}
