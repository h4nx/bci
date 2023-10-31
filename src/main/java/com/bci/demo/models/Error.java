package com.bci.demo.models;

import com.bci.demo.utils.HttpStatus;

public class Error {
    private HttpStatus httpStatus;
    private String mensaje;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
