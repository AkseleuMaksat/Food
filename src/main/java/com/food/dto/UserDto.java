package com.food.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotBlank(message = "Full name cannot be empty")
    private String fullName;
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
