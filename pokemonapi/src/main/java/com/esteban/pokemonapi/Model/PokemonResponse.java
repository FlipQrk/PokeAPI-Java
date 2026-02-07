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

    // Getters y Setters

    public String getName() { return name; }
    public int getHeight() { return height; }
    public int getWeight() { return weight; }
    public List<AbilityEntry> getAbilities() { return abilities; }

    public void setName(String name) { this.name = name; }
    public void setHeight(int height) { this.height = height; }
    public void setWeight(int weight) { this.weight = weight; }
    public void setAbilities(List<AbilityEntry> abilities) { this.abilities = abilities; }
}
