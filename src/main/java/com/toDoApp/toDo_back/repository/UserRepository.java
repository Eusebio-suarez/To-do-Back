package com.toDoApp.toDo_back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toDoApp.toDo_back.entity.UserEntity;



@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    
    //metodo para buscar el usuario por el email
    public Optional<UserEntity> findByEmail(String email);
}
