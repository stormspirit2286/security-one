package vn.duynv.secutityone.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.duynv.secutityone.configuration.JwtProvider;
import vn.duynv.secutityone.exception.ApiException;
import vn.duynv.secutityone.exception.ErrorCode;
import vn.duynv.secutityone.mapper.UserMapper;
import vn.duynv.secutityone.modal.CustomUserDetails;
import vn.duynv.secutityone.modal.User;
import vn.duynv.secutityone.modal.UserRole;
import vn.duynv.secutityone.payload.request.UserDto;
import vn.duynv.secutityone.payload.request.UserLoginDto;
import vn.duynv.secutityone.payload.response.AuthResponse;
import vn.duynv.secutityone.repository.UserRepository;
import vn.duynv.secutityone.service.AuthService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse signUp(UserDto userDto) {
        log.info("Sign up user with email: {}", userDto.getEmail());

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ApiException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User userEntity = userMapper.toEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setRole(UserRole.ROLE_USER);

        User userSaved = userRepository.save(userEntity);
        log.info("User registered successfully: {}", userSaved.getEmail());

        CustomUserDetails userDetails = new CustomUserDetails(userSaved);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return AuthResponse.builder()
                .accessToken(jwtProvider.generateToken(authentication))
                .refreshToken(jwtProvider.generateRefreshToken(authentication))
                .message("Registration successful")
                .user(userMapper.toResponse(userSaved))
                .build();
    }

    @Override
    public AuthResponse login(UserLoginDto userLoginDto) {
        log.info("Login attempt for email: {}", userLoginDto.getEmail());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDto.getEmail(),
                            userLoginDto.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            User user = userRepository.findById(userDetails.getUserId())
                    .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

            log.info("User logged in successfully: {}", userDetails.getEmail());

            return AuthResponse.builder()
                    .accessToken(jwtProvider.generateToken(authentication))
                    .refreshToken(jwtProvider.generateRefreshToken(authentication))
                    .message("Login successful")
                    .user(userMapper.toResponse(user))
                    .build();

        } catch (BadCredentialsException e) {
            log.error("Invalid credentials for email: {}", userLoginDto.getEmail());
            throw new ApiException(ErrorCode.INVALID_CREDENTIALS);
        }
    }
}