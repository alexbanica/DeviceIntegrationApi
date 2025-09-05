package com.alexlab.pi.deviceintegrationapi.common.controllers;

import com.alexlab.pi.deviceintegrationapi.common.controllers.responses.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class AbstractApiController {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        return new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleGeneralException(Exception e) {
        return new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error");
    }
}
