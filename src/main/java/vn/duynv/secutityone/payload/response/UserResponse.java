package vn.duynv.secutityone.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
public class UserResponse {
    private long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String gender;
    private String phone;
    private LocalDateTime createdAt;

}
