package com.example.clientePersona.exception;

/**
 * Excepción personalizada que se lanza cuando se detecta una identificación duplicada.
 * Extiende RuntimeException para representar errores específicos relacionados con datos duplicados.
 */
public class IdentificacionDuplicadaException extends RuntimeException {

    /**
     * Constructor que crea una nueva instancia de IdentificacionDuplicadaException con un mensaje específico.
     *
     * @param message El mensaje de error que describe la causa de la excepción.
     */
    public IdentificacionDuplicadaException(String message) {
        super(message);
    }
}
