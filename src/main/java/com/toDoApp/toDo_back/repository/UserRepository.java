package com.toDoApp.toDo_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toDoApp.toDo_back.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<Long,UserEntity> {
    
}
