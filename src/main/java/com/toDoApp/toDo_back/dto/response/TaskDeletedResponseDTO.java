package com.toDoApp.toDo_back.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskDeletedResponseDTO {
    private Long id;
    private String title;
}
