package com.example.clientePersona.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción personalizada que se lanza cuando un recurso solicitado no se encuentra.
 * Extiende RuntimeException y se asocia con el estado HTTP 404 Not Found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor que crea una nueva instancia de ResourceNotFoundException con un mensaje específico.
     *
     * @param message El mensaje de error que describe la causa de la excepción.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
