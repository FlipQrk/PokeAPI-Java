package com.esteban.pokemonapi.DTO;

import java.util.List;

public record PokemonDTO(
    String name,
    int height,
    int weight,
    List<String> abilities
) {}
