package com.toDoApp.toDo_back.dto.request;

import com.toDoApp.toDo_back.utils.StatusEnum;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskRequestDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String description;
    
    @NotBlank
    private StatusEnum status;
}
 