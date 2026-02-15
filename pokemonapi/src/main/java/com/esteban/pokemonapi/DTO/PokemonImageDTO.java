package com.esteban.pokemonapi.DTO;

import java.util.List;

public record PokemonImageDTO(
    String name,    // Nombre del Pokemon
    List<String> images    // Lista con los 4 Sprites del Pokemon
) {}

    /**
        * Los records en Java son clases inmutables
        * Solo se encargan de transportar la informacion
        * Son Data Carriers
        * De tal forma que estos record solo modelan la información para su salida.
        * En este caso solo estamos llevando la informacion de la respuesta de la API
    */