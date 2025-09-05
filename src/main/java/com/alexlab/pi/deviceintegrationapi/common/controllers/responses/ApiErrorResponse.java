package com.alexlab.pi.deviceintegrationapi.common.controllers.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiErrorResponse {
    private final int status;
    private final String message;
}
