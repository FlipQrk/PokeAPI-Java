package com.esteban.pokemonapi.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // Esto registra la clase consiguiente de forma global.

/**
    * GlobalExceptionHandler es un interceptor de excepciones que tiene Sprign.
    * Este intercepta y captura excepciones en: Controllers, Services, Validaciones y Request HTTP
    * Todo esto antes de que Spring arroje error 500 como error base
    * Funciona como un Try/Catch global para el proyecto
*/
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class) 
    // Cuando ocurre una excepcion, Spring busca un metodo con ExceptionHandler
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        // Con RuntimeException ex Spring inyecta la excepcion de forma automatica
        return ResponseEntity // Define la respuesta HTTP
                .status(404)
                .body(ex.getMessage());
    }
}

/**
    * En este caso el Script funciona como:
    * Cuando ocurra RuntimeException en cualquier controller, usa este método.
*/