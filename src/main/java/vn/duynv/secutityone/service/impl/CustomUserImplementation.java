package vn.duynv.secutityone.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.duynv.secutityone.exception.ApiException;
import vn.duynv.secutityone.exception.ErrorCode;
import vn.duynv.secutityone.modal.CustomUserDetails;
import vn.duynv.secutityone.modal.User;
import vn.duynv.secutityone.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class CustomUserImplementation implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        return new CustomUserDetails(user);
    }
}
