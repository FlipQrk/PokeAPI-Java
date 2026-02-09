package com.esteban.pokemonapi.Model;

import java.util.List;

public class PokemonResponse {
    /**
        * Definimos los datos a extraer de la API.
        * En el caso de las habilidades:
        * Usamos una lista al ser dos habilidades por pokemon.
        * Habilidad principal y oculta.
    */
    private String name;
    private int height;
    private int weight;
    private List<AbilityEntry> abilities;

    // Getters 

    public String getName() { 
        return name; 
    }
    public int getHeight() { 
        return height; 
    }
    public int getWeight() { 
        return weight; 
    }
    public List<AbilityEntry> getAbilities() { 
        return abilities; 
    }

    // Clases que permiten el uso de las habilidades
    
    public static class AbilityEntry {
        private Ability ability;

        public Ability getAbility() {
            return ability;
        }
    }

    public static class Ability {
        private String name;

        public String getName() {
            return name;
        }
    }
}
