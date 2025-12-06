package vn.duynv.secutityone.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.duynv.secutityone.payload.request.UserDto;
import vn.duynv.secutityone.payload.response.ApiResponse;
import vn.duynv.secutityone.payload.response.UserResponse;
import vn.duynv.secutityone.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse<UserResponse>> getDetailUser(@PathVariable String email) {
        UserResponse userResponse = userService.getDetailsUser(email);
        ApiResponse<UserResponse> apiResponse = ApiResponse.success("Get user details successfully", userResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        ApiResponse<String> apiResponse = ApiResponse.success("Delete user successfully");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("/{email}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable String email, @RequestBody UserDto userDto) {
        UserResponse userResponse = userService.updateUser(email, userDto);
        ApiResponse<UserResponse> apiResponse = ApiResponse.success("Update user successfully", userResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> userResponseList = userService.getAllUsers();
        ApiResponse apiResponse = ApiResponse.success("Get all users successfully", userResponseList);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody UserDto userDto) {
        UserResponse userResponse = userService.createUser(userDto);
        ApiResponse<UserResponse> apiResponse = ApiResponse.success("Create user successfully", userResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Current user: {}", authentication.getName());
        UserResponse userResponse = userService.getDetailsUser(authentication.getName());
        ApiResponse<UserResponse> apiResponse = ApiResponse.success("Get user successfully", userResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }



}
