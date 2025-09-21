package com.toDoApp.toDo_back.controllers.tasksControllers;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toDoApp.toDo_back.dto.request.TaskRequestDTO;
import com.toDoApp.toDo_back.dto.response.TaskCretedResponseDTO;
import com.toDoApp.toDo_back.dto.response.TaskDeletedResponseDTO;
import com.toDoApp.toDo_back.dto.response.TaskUpdatedResponseDTO;
import com.toDoApp.toDo_back.entity.UserEntity;
import com.toDoApp.toDo_back.services.TaskService;
import com.toDoApp.toDo_back.services.UserService;
import com.toDoApp.toDo_back.utils.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;
    
    @Autowired
    UserService userService;

    //endpoint para obtener tareas
    @GetMapping("")
    public ResponseEntity<ApiResponse<?>> getTasks(HttpServletRequest request){

        try{

            //obtener el usuario con la request que tiene los  headers 
            UserEntity user = userService.getUserByRequest(request)
            .orElseThrow();


            List<TaskCretedResponseDTO> tasks = taskService.getTasks(user.getId());

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

    //endpoint para crear una tarea
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createTask(HttpServletRequest request,@RequestBody TaskRequestDTO taskRequest){

        try{

            //obtener el usuario con la request que tiene los  headers 
            UserEntity user = userService.getUserByRequest(request)
            .orElseThrow();


            //crear la tarea con el servicio
            TaskCretedResponseDTO taskCreated = taskService.createTask(user.getId(), taskRequest);

            if(taskCreated != null){
                return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.builder()
                        .success(true)
                        .message("tarea creada correctamente")
                        .data(taskCreated)
                        .build()
                    );
            }
            else{
                throw new Exception("no se pudo crear la tarea");
            }

        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                    .success(false)
                    .message("error: "+e.getMessage())
                    .data(null)
                    .build()
                );
        }
    }

    //endpoind para actualizar una tarea
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<?>> updateTask(@RequestParam Long id,@RequestBody TaskRequestDTO taskRequestDTO){

        try{
            //actualizar la tarea con el servicio
            TaskUpdatedResponseDTO task = taskService.updateTask(id, taskRequestDTO);

            if(task!=null){
                return(ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(ApiResponse.builder()
                        .success(true)
                        .message("se actualizo correctamente")
                        .data(task)
                        .build()
                    ));
            }
            else{
                throw new Exception("no se pudo actualizar la tarea");
            }

        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                    .success(false)
                    .message("Error :"+e.getMessage())
                    .data(null)
                    .build()
                );
        }
    }

    //endpoint para eliminar una tarea
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<?>> deleteTask(@RequestParam Long id){

        try{
            TaskDeletedResponseDTO task = taskService.deleteTask(id);

            if(task !=null){
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(ApiResponse.builder()
                        .success(true)
                        .message("se elimino correctamente")
                        .data(task)
                        .build()
                    );
            }
            else{
                throw new Exception("no se pudo eliminar la tarea");
            }
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                    .success(false)
                    .message("error: "+e.getMessage())
                    .data(null)
                    .build()
                );
        }
    }

}
