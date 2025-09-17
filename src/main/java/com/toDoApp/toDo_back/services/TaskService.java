package com.toDoApp.toDo_back.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toDoApp.toDo_back.dto.response.TaskResponseDTO;
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
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .build()
            )
            .toList();

    }

}
