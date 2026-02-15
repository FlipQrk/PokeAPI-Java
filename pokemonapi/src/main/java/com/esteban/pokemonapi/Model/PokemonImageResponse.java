package com.esteban.pokemonapi.Model;

public class PokemonImageResponse {
    // Definimos los datos que traeremos para la Imagen
    private String name;
    private Sprites sprites;

    // Getters

    public String getName() {
        return name;
    }

    public Sprites getSprites() {
        return sprites;
    }

    // Clase con todos los Sprites del Pokemon

    public static class Sprites {
        private String front_default;
        private String back_default;
        private String front_shiny;
        private String back_shiny;

        public String getFront_default() {
            return front_default;
        }

        public String getBack_default() {
            return back_default;
        }

        public String getFront_shiny() {
            return front_shiny;
        }

        public String getBack_shiny() {
            return back_shiny;
        }
    }

}
