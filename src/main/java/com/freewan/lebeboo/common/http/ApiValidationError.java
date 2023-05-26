package com.freewan.lebeboo.common.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiValidationError extends ApiSubError {

    private String field;
    private String msg;
}
