package vn.duynv.secutityone.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiException extends RuntimeException {
    private ErrorCode errorCode;
    private HttpStatus status;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.status = switch (errorCode) {
            case EMAIL_ALREADY_EXISTS -> HttpStatus.CONFLICT;
            case USER_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case INVALID_CREDENTIALS, TOKEN_EXPIRED, INVALID_REFRESH_TOKEN -> HttpStatus.UNAUTHORIZED;
            default -> HttpStatus.BAD_REQUEST;
        };
    }
}
