package com.sinaukoding.martinms.event_booking_system.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {

    private int status;
    private boolean success;
    private String message;

    @JsonIgnoreProperties({"pageable", "sort"})
    private T data;

    public static <T> BaseResponse<T> ok(String message) {
        return BaseResponse.<T>builder()
                .success(true)
                .status(200)
                .message(StringUtils.isNotBlank(message) ? message : "")
                .build();
    }

    public static <T> BaseResponse<T> ok(String message, T data) {
        return BaseResponse.<T>builder()
                .success(true)
                .status(200)
                .message(StringUtils.isNotBlank(message) ? message : "")
                .data(data)
                .build();
    }

    public static <T> BaseResponse<T> error(String message, T data) {
        return BaseResponse.<T>builder()
                .status(500)
                .success(false)
                .message(StringUtils.isNotBlank(message) ? message : "")
                .data(data)
                .build();
    }

    public static <T> BaseResponse<T> badRequest(String message) {
        return BaseResponse.<T>builder()
                .status(400)
                .success(false)
                .message(message)
                .data(null)
                .build();
    }

    public static <T> BaseResponse<T> forbiddenAccess(String message) {
        return BaseResponse.<T>builder()
                .status(403)
                .success(false)
                .message(message)
                .data(null)
                .build();
    }

    public static <T> BaseResponse<T> create(int status, boolean success, String message, T data) {
        return BaseResponse.<T>builder()
                .status(status)
                .success(success)
                .message(StringUtils.isNotBlank(message) ? message : "")
                .data(data)
                .build();
    }

    public static BaseResponse<?> unauthorizedAccess(String message) {
        return BaseResponse.builder()
                .status(401)
                .success(false)
                .message(StringUtils.isNotBlank(message) ? message : "")
                .data(null)
                .build();
    }

    public static BaseResponse<?> notFound(String message) {
        return BaseResponse.builder()
                .status(404)
                .success(false)
                .message(StringUtils.isNotBlank(message) ? message : "")
                .data(null)
                .build();
    }

}

