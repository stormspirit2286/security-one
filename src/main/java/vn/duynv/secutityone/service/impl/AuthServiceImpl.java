package vn.duynv.secutityone.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.duynv.secutityone.configuration.JwtProvider;
import vn.duynv.secutityone.exception.ApiException;
import vn.duynv.secutityone.exception.ErrorCode;
import vn.duynv.secutityone.modal.User;
import vn.duynv.secutityone.modal.UserRole;
import vn.duynv.secutityone.payload.request.UserDto;
import vn.duynv.secutityone.payload.request.UserLoginDto;
import vn.duynv.secutityone.payload.response.AuthResponse;
import vn.duynv.secutityone.repository.UserRepository;
import vn.duynv.secutityone.service.AuthService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AuthResponse signUp(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user != null) {
            throw new ApiException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        User userEntity = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .role(UserRole.ROLE_USER)
                .dob(userDto.getDob())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .gender(userDto.getGender())
                .build();
        User userSaved = userRepository.save(userEntity);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userSaved.getRole().name()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDto.getEmail(),
                userDto.getPassword(),
                authorities
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return AuthResponse.builder()
                .accessToken(jwtProvider.generateToken(authentication))
                .refreshToken(jwtProvider.generateToken(authentication))
                .message("Registration successful")
                .user(maptoUserDto(userSaved))
                .build();
    }

    @Override
    public AuthResponse login(UserLoginDto userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.getEmail());
        if (user == null) {
            throw new ApiException(ErrorCode.INVALID_CREDENTIALS);
        }
        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new ApiException(ErrorCode.INVALID_CREDENTIALS);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userLoginDto.getEmail(),
                userLoginDto.getPassword(),
                authorities
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return AuthResponse.builder()
                .accessToken(jwtProvider.generateToken(authentication))
                .refreshToken(jwtProvider.generateToken(authentication))
                .message("Login successful")
                .user(maptoUserDto(user))
                .build();

    }

    private UserDto maptoUserDto(User user) {
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
