package vn.duynv.secutityone.service;

import vn.duynv.secutityone.payload.request.UserDto;

public interface UserService {
    UserDto getUserByEmail(String email);
}
