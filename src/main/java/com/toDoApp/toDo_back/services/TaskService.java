package com.toDoApp.toDo_back.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toDoApp.toDo_back.dto.request.TaskRequestDTO;
import com.toDoApp.toDo_back.dto.response.TaskResponseDTO;
import com.toDoApp.toDo_back.entity.TaskEntity;
import com.toDoApp.toDo_back.entity.UserEntity;
import com.toDoApp.toDo_back.repository.TaskRepository;
import com.toDoApp.toDo_back.repository.UserRepository;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepositoty;

    //obtener las tareas
    public List<TaskResponseDTO> getTasks(Long id) throws Exception{
        
        //obtener el usuario de la db
        UserEntity user = userRepositoty.findById(id)
            .orElseThrow(() -> new Exception("no se encontro el usuario"));

        return taskRepository.findByUser(user).stream()
            .map(task -> TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .status(task.getStatus())
                .build()
            )
            .toList();
    }

    //crear una tarea
    public TaskResponseDTO createTask(Long id,TaskRequestDTO taskRequestDTO) throws Exception{
        //obtener el usuario
        UserEntity user = userRepositoty.findById(id)
            .orElseThrow(()-> new Exception("No se encontro el usuario"));

        //mapear el dto a una tarea con usuario
        TaskEntity task = TaskEntity.builder()
            .title(taskRequestDTO.getTitle())
            .description(taskRequestDTO.getDescription())
            .status(taskRequestDTO.getStatus())
            .user(user)
            .build();

        //hacer el registro en la base de datos    
        TaskEntity taskCreated = taskRepository.save(task);

        //responder con el dto
        return TaskResponseDTO.builder()
            .id(taskCreated.getId())
            .title(taskCreated.getTitle())
            .status(taskCreated.getStatus())
            .build();
    }

}
