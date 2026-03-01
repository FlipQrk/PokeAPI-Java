package com.esteban.pokemonapi.Model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PokemonTypeResponse {
    private String name;

    @JsonProperty("pokemon")  

    /**
        * JsonProperty ayuda a que Jackson de Java reciba ciertas cosas como atributos
        * En este caso, habría problemas ya que los atributos usan camelCase, no snake_case
        * Entonces usamos el JsonProperty para que Java busque y use el atributo así.
    */

    private List<PokemonEntry> pokemon; // Usamos el atributo pokemon sin preocupaciones

    // Getters

    public String getName() { return name; }

    public List<PokemonEntry> getPokemon() { return pokemon; }

    // Clase que contiene cada uno de los Pokemons del RequestParam
    public static class PokemonEntry {
        // El objeto anidado "pokemon" dentro de cada entrada
        @JsonProperty("pokemon")
        private PokemonDetail pokemon;

        // Getters

        public PokemonDetail getPokemon() { return pokemon; }

        public static class PokemonDetail {
            private String name;                        // String Name ej: Charmander
            public String getName() { return name; }
        }
    }
}
