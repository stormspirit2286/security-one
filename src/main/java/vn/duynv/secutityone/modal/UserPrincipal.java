package vn.duynv.secutityone.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPrincipal {
    private final Long userId;
    private final String username;
    private final String email;
}
