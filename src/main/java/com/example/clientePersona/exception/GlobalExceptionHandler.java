package com.example.clientePersona.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para la aplicación.
 * Captura y maneja las excepciones comunes a nivel de aplicación y proporciona respuestas consistentes en caso de errores.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones de validación de argumentos de método.
     * Captura errores de validación generados por el uso de anotaciones de validación en las solicitudes.
     *
     * @param ex La excepción de validación de argumentos de método.
     * @return Un mapa de errores que contiene los nombres de los campos y los mensajes de error correspondientes.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    /**
     * Maneja las excepciones específicas de identificación duplicada.
     * Captura excepciones de identificación duplicada y devuelve un mensaje de error específico.
     *
     * @param ex La excepción de identificación duplicada.
     * @return Un mapa que contiene el mensaje de error de identificación duplicada.
     */
    @ExceptionHandler(IdentificacionDuplicadaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleIdentificacionDuplicadaException(IdentificacionDuplicadaException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }
}
