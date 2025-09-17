package com.toDoApp.toDo_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toDoApp.toDo_back.entity.TaskEntity;
import com.toDoApp.toDo_back.entity.UserEntity;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Long> {
    public List<TaskEntity> findByUser(UserEntity user);
}
