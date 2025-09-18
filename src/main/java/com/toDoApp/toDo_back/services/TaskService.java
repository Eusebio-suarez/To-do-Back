package com.toDoApp.toDo_back.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toDoApp.toDo_back.dto.request.TaskRequestDTO;
import com.toDoApp.toDo_back.dto.response.TaskCretedResponseDTO;
import com.toDoApp.toDo_back.dto.response.TaskDeletedResponseDTO;
import com.toDoApp.toDo_back.dto.response.TaskUpdatedResponseDTO;
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
    public List<TaskCretedResponseDTO> getTasks(Long id) throws Exception{
        
        //obtener el usuario de la db
        UserEntity user = userRepositoty.findById(id)
            .orElseThrow(() -> new Exception("no se encontro el usuario"));

        return taskRepository.findByUser(user).stream()
            .map(task -> TaskCretedResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .build()
            )
            .toList();
    }

    //crear una tarea
    public TaskCretedResponseDTO createTask(Long id,TaskRequestDTO taskRequestDTO) throws Exception{
        //obtener el usuario
        UserEntity user = userRepositoty.findById(id)
            .orElseThrow(()-> new Exception("No se encontro el usuario"));

        //mapear el dto a una tarea con usuario
        TaskEntity task = TaskEntity.builder()
            .title(taskRequestDTO.getTitle())
            .description(taskRequestDTO.getDescription())
            .status(taskRequestDTO.getStatus())
            .user(user)
            .createdAt(LocalDateTime.now())
            .build();

        //hacer el registro en la base de datos    
        TaskEntity taskCreated = taskRepository.save(task);

        //responder con el dto
        return TaskCretedResponseDTO.builder()
            .id(taskCreated.getId())
            .title(taskCreated.getTitle())
            .description(taskCreated.getDescription())
            .status(taskCreated.getStatus())
            .createdAt(taskCreated.getCreatedAt())
            .build();
    }

    //actualizar una tarea
    public TaskUpdatedResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) throws Exception{

        //bucar la tarea
        TaskEntity task = taskRepository.findById(id)
            .orElseThrow(() -> new Exception("no se encontro la tarea"));
        
        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setStatus(taskRequestDTO.getStatus());

        //actualizar el registro en la db    
        TaskEntity taskUpdated = taskRepository.save(task);

        return TaskUpdatedResponseDTO.builder()
            .id(taskUpdated.getId())
            .title(taskUpdated.getTitle())
            .description(taskUpdated.getDescription())
            .status(taskUpdated.getStatus())
            .updatedAt(taskUpdated.getUpdadeAt())
            .build();
    }

    //eliminar una tarea
    public TaskDeletedResponseDTO deleteTask(Long id) throws Exception{

        //buscar la tarea a eliminar
        TaskEntity task = taskRepository.findById(id)
            .orElseThrow(()-> new Exception("no se encontro la tarea"));

        taskRepository.deleteById(task.getId());

        return TaskDeletedResponseDTO.builder()
            .id(task.getId())
            .title(task.getTitle())
            .build();
    }

}
