package vn.duynv.secutityone.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @Email(message = "Email is not valid")
    private String email;
    private String phone;
    private String gender;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
