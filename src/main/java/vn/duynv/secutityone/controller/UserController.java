package vn.duynv.secutityone.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.duynv.secutityone.payload.request.UserDto;
import vn.duynv.secutityone.payload.response.ApiResponse;
import vn.duynv.secutityone.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse<UserDto>> getUserByEmail(@PathVariable String email) {
        UserDto userDto = userService.getUserByEmail(email);
        ApiResponse<UserDto> response = ApiResponse.<UserDto>builder()
                .code(200)
                .status(HttpStatus.OK)
                .message("User retrieved successfully")
                .data(userDto)
                .build();

        return ResponseEntity.ok(response);
    }
}
