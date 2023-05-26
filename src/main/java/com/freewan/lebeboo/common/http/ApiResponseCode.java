package com.freewan.lebeboo.common.http;

import lombok.Getter;

@Getter
public enum ApiResponseCode {
    NOT_FOUND("not-found"), ACCESS_DENIED("access-denied"), UNAUTHORIZED("unauthorized"),
    INTERNAL_SERVER_ERROR("internal-server-error"),
    REQUEST_VALIDATION_ERROR("request-validation-error"),
    SUCCESS("success"), BAD_REQUEST("bad-request"), TOKEN_INVALID("token-invalid"), TOKEN_EXPIRED("token-expired"),
    CHALLENGE_SECURITY_EXPIRED("challenge-security-expired"), CHALLENGE_SECURITY_INVALID("challenge-security-invalid"), OTP_INVALID("otp-invalid"), OTP_EXPIRED("otp-expired");

    private final String label;

    ApiResponseCode(String label) {
        this.label = label;
    }
}
