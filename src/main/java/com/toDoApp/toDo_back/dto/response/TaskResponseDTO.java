package com.toDoApp.toDo_back.dto.response;

import com.toDoApp.toDo_back.utils.StatusEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskResponseDTO {
    private Long id;
    private String title;
    private StatusEnum status;
}
