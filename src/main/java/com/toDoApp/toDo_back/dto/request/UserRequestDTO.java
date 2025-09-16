package com.toDoApp.toDo_back.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDTO {
    @NotBlank
    @Size(min=3)
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min=8)
    private String password;
}
