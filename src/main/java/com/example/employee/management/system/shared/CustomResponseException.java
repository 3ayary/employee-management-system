package com.example.employee.management.system.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomResponseException extends RuntimeException {

    private int code;
    private String message;

    public static CustomResponseException ResourceNotFound(String message) {
        return new CustomResponseException(404, message);
    }

    public static CustomResponseException BadRequest(String message) {
        return new CustomResponseException(400, message);
    }

    public static CustomResponseException BadCredintials() {
        return new CustomResponseException(401, "Bad Credintials!");
    }

}
