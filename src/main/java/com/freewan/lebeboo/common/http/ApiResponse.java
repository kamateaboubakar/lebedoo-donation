package com.freewan.lebeboo.common.http;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class ApiResponse {

    @Setter(AccessLevel.NONE)
    private String code;
    private String message;

    public ApiResponse(ApiResponseCode apiResponseCode, String message) {
        this.code = apiResponseCode.getLabel();
        this.message = message;
    }

    public void setCode(ApiResponseCode apiResponseCode) {
        this.code = apiResponseCode.getLabel();
    }
}
