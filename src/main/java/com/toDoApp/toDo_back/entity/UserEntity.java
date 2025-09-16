package com.toDoApp.toDo_back.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@Entity
@Table(name="users")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min=3)
    private String name;

    @Email
    @NotBlank
    @Column(unique=true)
    private String email;

    @Size(min=8)
    private String password;

    @Column(name="created_at",updatable=false)
    private LocalDateTime createdAt;

    @Column(name="update_at",updatable=true)
    private LocalDateTime updadeAt;

    @OneToMany(mappedBy="user")
    private List<TaskEntity> tasks;

}
