package com.motorbit.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

@Builder 
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T> (

    LocalDateTime timestamp,
    int status,
    String message,
    T data
) {
    public static <T> ApiResponse<T> created(String message, T data) {
        return ApiResponse.<T>builder()
                            .timestamp(LocalDateTime.now())
                            .status(201)
                            .message(message)
                            .data(data)
                            .build();
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return ApiResponse.<T>builder()
                            .timestamp(LocalDateTime.now())
                            .status(200)
                            .message(message)
                            .data(data)
                            .build();
    }

    public static <T> ApiResponse<T> ok(String message) {
        return ApiResponse.<T>builder()
                            .timestamp(LocalDateTime.now())
                            .status(200)
                            .message(message)
                            .build();
    }
}
