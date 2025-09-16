package com.toDoApp.toDo_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toDoApp.toDo_back.entity.TaskEntity;

public interface TaskRepository extends JpaRepository<Long, TaskEntity> {
    
}
