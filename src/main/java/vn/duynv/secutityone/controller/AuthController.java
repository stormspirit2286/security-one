package vn.duynv.secutityone.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.duynv.secutityone.payload.request.UserDto;
import vn.duynv.secutityone.payload.request.UserLoginDto;
import vn.duynv.secutityone.payload.response.ApiResponse;
import vn.duynv.secutityone.payload.response.AuthResponse;
import vn.duynv.secutityone.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<AuthResponse>> signUp(@Valid @RequestBody UserDto userDto) {
        AuthResponse authResponse = authService.signUp(userDto);
        ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder()
                .status(HttpStatus.CREATED)
                .message("User registered successfully")
                .data(authResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> signUp(@Valid @RequestBody UserLoginDto userLoginDto) {
        AuthResponse authResponse = authService.login(userLoginDto);
        ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder()
                .code(200)
                .status(HttpStatus.CREATED)
                .message("User login successfully")
                .data(authResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
