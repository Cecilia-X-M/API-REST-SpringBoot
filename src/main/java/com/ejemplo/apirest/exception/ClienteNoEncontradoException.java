package com.ejemplo.apirest.exception;

public class ClienteNoEncontradoException extends RuntimeException {

    public ClienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}