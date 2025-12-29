package vn.duynv.secutityone.exception;


import lombok.Getter;

@Getter
public enum ErrorCode {
    // Auth
    INVALID_CREDENTIALS(1001, "Username or password is invalid"),
    EMAIL_ALREADY_EXISTS(1002, "Email already exists"),
    TOKEN_EXPIRED(1003, "Token has expired"),
    INVALID_REFRESH_TOKEN(1004, "Refresh token has expired"),

    // User
    USER_NOT_FOUND(2001, "User not found"),
    PHONE_ALREADY_EXISTS(2002, "Phone already used"),

    // Common
    VALIDATION_ERROR(9001, "Input data is invalid"),
    INTERNAL_SERVER_ERROR(9999, "System error"),

    // Category
    SLUG_ALREADY_EXISTS(3001, "Slug already exists"),
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
