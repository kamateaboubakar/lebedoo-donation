package com.freewan.lebeboo.common.http;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulate exceptions thrown by service layer.
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiError {

    @Setter(AccessLevel.NONE)
    private String errorCode;

    private String message;

    private List<? extends ApiSubError> details = new ArrayList<>();

    private ApiError() {
    }

    public ApiError(ApiResponseCode errorCode, String message) {
        this();
        this.errorCode = errorCode.getLabel();
        this.message = message;
    }

    public ApiError(ApiResponseCode errorCode, String message, List<? extends ApiSubError> details) {
        this(errorCode, message);
        this.details = details;
    }
}
