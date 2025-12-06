package vn.duynv.secutityone.service;

import vn.duynv.secutityone.payload.request.UserDto;
import vn.duynv.secutityone.payload.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getDetailsUser(String email);
    UserResponse updateUser(String email, UserDto userDto);
    UserResponse changePassword(String newPassword);
    List<UserResponse> getAllUsers();

    UserResponse createUser(UserDto userDto);

    void deleteUser(String email);
}
