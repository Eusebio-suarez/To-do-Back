package com.toDoApp.toDo_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toDoApp.toDo_back.entity.TaskEntity;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Long> {
    
}
