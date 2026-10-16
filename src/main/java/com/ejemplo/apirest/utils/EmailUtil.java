package com.ejemplo.apirest.utils;

public class EmailUtil {

    public static boolean esValido(String correo) {
        return correo.contains("@");
    }
}
