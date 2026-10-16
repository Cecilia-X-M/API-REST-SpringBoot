package com.ejemplo.apirest.exception;

import com.ejemplo.apirest.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ==========================================
    // CLIENTE NO ENCONTRADO
    // ==========================================

    @ExceptionHandler(ClienteNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> clienteNoEncontrado(
            ClienteNoEncontradoException ex) {

        ErrorResponse error = new ErrorResponse(
                404,
                "CLIENTE_NO_ENCONTRADO",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }


    // ==========================================
    // CORREO DUPLICADO
    // ==========================================

    @ExceptionHandler(CorreoDuplicadoException.class)
    public ResponseEntity<ErrorResponse> correoDuplicado(
            CorreoDuplicadoException ex) {

        ErrorResponse error = new ErrorResponse(
                409,
                "CORREO_DUPLICADO",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }


    // ==========================================
    // DATOS INVÁLIDOS
    // ==========================================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> datosInvalidos(
            MethodArgumentNotValidException ex) {

        Map<String, String> campos = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        campos.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        ErrorResponse error = new ErrorResponse(
                400,
                "DATOS_INVALIDOS",
                "Los datos enviados no son válidos",
                LocalDateTime.now(),
                campos
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    // ==========================================
    // CREDENCIALES INVÁLIDAS
    // ==========================================

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> credencialesInvalidas() {

        ErrorResponse error = new ErrorResponse(
                401,
                "CREDENCIALES_INVALIDAS",
                "Correo o contraseña incorrectos",
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(error);
    }

    // ==========================================
    // ERROR INTERNO
    // ==========================================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> errorInterno(
            Exception ex) {


        ErrorResponse error = new ErrorResponse(
                500,
                "ERROR_INTERNO",
                "Ocurrio un error interno en el servidor",
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}