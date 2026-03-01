package com.esteban.pokemonapi.DTO;

import java.util.List;

public record PokemonTypeDTO(
    String tipo,            // Nombre del tipo (fire, water, etc)
    List<String> pokemon    // Lista de pokémons de ese tipo
) {}

    /**
        * Los records en Java son clases inmutables
        * Solo se encargan de transportar la informacion
        * Son Data Carriers
        * * De tal forma que estos record solo modelan la información para su salida.
        * En este caso solo estamos llevando la informacion de la respuesta de la API
    */
