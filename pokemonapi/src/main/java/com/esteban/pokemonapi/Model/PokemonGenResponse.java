package com.esteban.pokemonapi.Model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PokemonGenResponse {

    private int id;

    @JsonProperty("pokemon_species")  

    /**
        * JsonProperty ayuda a que Jackson de Java reciba ciertas cosas como atributos
        * En este caso, habría problemas ya que los atributos usan camelCase, no snake_case
        * Entonces usamos el JsonProperty para que Java busque y use el atributo así.
    */

    private List<PokemonSpecies> pokemon_species;

    // Getters

    public int getId() {
        return id;
    }

    public List<PokemonSpecies> getPokemon_species() {
        return pokemon_species;
    }

    public static class PokemonSpecies {

        private String name;

        public String getName() {
            return name;
        }
    }
}