package com.ejemplo.apirest.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {

    private int status;
    private String error;
    private String mensaje;
    private LocalDateTime fecha;
    private Map<String, String> campos;


    // ==========================================
    // CONSTRUCTOR NORMAL
    // ==========================================

    public ErrorResponse(
            int status,
            String error,
            String mensaje,
            LocalDateTime fecha) {

        this.status = status;
        this.error = error;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }


    // ==========================================
    // CONSTRUCTOR CON ERRORES DE CAMPOS
    // ==========================================

    public ErrorResponse(
            int status,
            String error,
            String mensaje,
            LocalDateTime fecha,
            Map<String, String> campos) {

        this.status = status;
        this.error = error;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.campos = campos;
    }


    // ==========================================
    // GETTERS
    // ==========================================

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Map<String, String> getCampos() {
        return campos;
    }
}