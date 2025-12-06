package vn.duynv.secutityone.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {
    @NotBlank
    @Email(message = "Email must be valid")
    private String email;
    @NotBlank
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;
}
