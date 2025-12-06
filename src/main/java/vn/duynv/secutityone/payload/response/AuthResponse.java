package vn.duynv.secutityone.payload.response;

import lombok.*;
import vn.duynv.secutityone.payload.request.UserDto;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private UserDto user;
    private String message;
    private LocalDateTime expireTime;
    private final LocalDateTime createdAt =  LocalDateTime.now();
}