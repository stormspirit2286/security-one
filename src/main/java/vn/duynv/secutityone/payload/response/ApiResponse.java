package vn.duynv.secutityone.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private int code;
    private HttpStatus status;

    private String message;

    private T data;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private String error;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .message("Success")
                .build();
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message, int code) {
        return ApiResponse.<T>builder()
                .code(code)
                .status(status)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message, String error, int code) {
        return ApiResponse.<T>builder()
                .code(code)
                .status(status)
                .message(message)
                .error(error)
                .build();
    }
}
