package vn.duynv.secutityone.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.duynv.secutityone.exception.ApiException;
import vn.duynv.secutityone.exception.ErrorCode;
import vn.duynv.secutityone.modal.User;
import vn.duynv.secutityone.payload.request.UserDto;
import vn.duynv.secutityone.repository.UserRepository;
import vn.duynv.secutityone.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ApiException(ErrorCode.USER_NOT_FOUND);
        }
        return mapToUserDto(user);
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setGender(user.getGender());
        userDto.setDob(user.getDob());
        return userDto;
    }
}
