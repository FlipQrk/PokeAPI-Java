package com.esteban.pokemonapi.Exception;

public class PokemonNotFoundException extends RuntimeException {

    public PokemonNotFoundException(String name) {
        super("Pokemon no encontrado: " + name);
    }
}
