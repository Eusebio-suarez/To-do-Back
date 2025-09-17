package com.toDoApp.toDo_back.dto.response;

import java.time.LocalDateTime;


import com.toDoApp.toDo_back.utils.StatusEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskCretedResponseDTO {
    private Long id;
    private String title;
    private String description;
    private StatusEnum status;
    private LocalDateTime createdAt;
}
