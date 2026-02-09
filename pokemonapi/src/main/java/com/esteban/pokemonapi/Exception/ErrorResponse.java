package com.esteban.pokemonapi.Exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message, // Informacion del Error
        int status, // Respuesta HTTP; Estado de la respuesta HTTP
        LocalDateTime timestamp // Hora en que se produció la exepción
) {}

    /**
        * Los records en Java son clases inmutables
        * Solo se encargan de transportar la informacion
        * Son Data Carriers
        * De tal forma que estos record solo modelan la información para su salida.
        * En este caso solo estamos llevando la informacion del GlobalExceptionHanlder.
    */
