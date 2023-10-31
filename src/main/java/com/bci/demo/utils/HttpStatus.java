package com.bci.demo.utils;

public enum HttpStatus {
    OK(200, "OK"),
    CREATED(201, "Created"),
    BAD_REQUEST(400, "Bad Request"),
    INVALID_CREDENTIALS(401, "Credenciales Inválidas"),
    CONFLICT(409, "Conflict"),
    DUPLICATE_EMAIL(410, "Correo Duplicado");

    private final int code;
    private final String message;

    HttpStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
