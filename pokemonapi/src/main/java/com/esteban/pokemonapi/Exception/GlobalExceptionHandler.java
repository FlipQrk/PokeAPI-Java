package com.esteban.pokemonapi.Exception;

import java.time.LocalDateTime;

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
    public ResponseEntity<ErrorResponse> handleRuntimeException(PokemonNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),   // Capturamos el mensaje enviado desde el Service
                404,  // Lo complementamos con un Estado 404: No encontrado
                LocalDateTime.now()  // Añadimos la hora en que se produció el error
        );
        // Con RuntimeException ex Spring inyecta la excepcion de forma automatica
        // NOTA: No es necesario usar el ResponseEntity en el controller ya que aqui ya está
        return ResponseEntity // Define la respuesta HTTP
                .status(404)
                .body(error); // Expulsamos el cuerpo del ErrorResponse 
    }
}

/**
    * En este caso el Script funciona como:
    * Cuando ocurra RuntimeException en cualquier controller, usa este método.
*/