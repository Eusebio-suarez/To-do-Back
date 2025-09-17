package com.toDoApp.toDo_back.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toDoApp.toDo_back.dto.response.TaskResponseDTO;
import com.toDoApp.toDo_back.services.TaskService;
import com.toDoApp.toDo_back.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;
    
    @GetMapping("")
    public ResponseEntity<ApiResponse<?>> getTasks(@RequestParam Long id){
        try{
            List<TaskResponseDTO> tasks = taskService.getTasks(id);

            if(tasks!=null){
                return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.builder()
                        .success(true)
                        .message("se obtuvieron las tareas correctamente")
                        .data(tasks)
                        .build()
                    );
            }
            else{
                throw new Exception("no se obtuvieron las tareas");
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
}
