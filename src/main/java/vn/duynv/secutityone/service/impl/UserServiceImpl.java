package vn.duynv.secutityone.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.duynv.secutityone.exception.ApiException;
import vn.duynv.secutityone.exception.ErrorCode;
import vn.duynv.secutityone.mapper.UserMapper;
import vn.duynv.secutityone.modal.User;
import vn.duynv.secutityone.modal.UserRole;
import vn.duynv.secutityone.payload.request.UserDto;
import vn.duynv.secutityone.payload.response.UserResponse;
import vn.duynv.secutityone.repository.UserRepository;
import vn.duynv.secutityone.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponse getDetailsUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse updateUser(String email, UserDto userDto) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setDob(userDto.getDob());
        user.setGender(userDto.getGender());
        user.setUsername(userDto.getUsername());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse changePassword(String newPassword) {
        return null;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public UserResponse createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.ROLE_USER);
        User userSaved = userRepository.save(user);
        return userMapper.toResponse(userSaved);
    }

    @Override
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        userRepository.delete(user);
    }
}
