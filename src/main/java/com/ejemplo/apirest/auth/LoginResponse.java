package com.ejemplo.apirest.auth;

public class LoginResponse {

    private String mensaje;
    private String correo;
    private String token;

    public LoginResponse(
            String mensaje,
            String correo,
            String token) {

        this.mensaje = mensaje;
        this.correo = correo;
        this.token = token;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getCorreo() {
        return correo;
    }

    public String getToken() {
        return token;
    }
}