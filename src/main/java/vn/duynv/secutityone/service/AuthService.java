package vn.duynv.secutityone.service;

import vn.duynv.secutityone.payload.request.UserDto;
import vn.duynv.secutityone.payload.request.UserLoginDto;
import vn.duynv.secutityone.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse signUp(UserDto userDto);
    AuthResponse login(UserLoginDto userLoginDto);
}
