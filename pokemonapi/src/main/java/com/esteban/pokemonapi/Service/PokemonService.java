package com.esteban.pokemonapi.Service;
// Paquetes Spring Boot
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.esteban.pokemonapi.DTO.PokemonDTO;

// Elementos del mapeo
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PokemonService {

    private final RestTemplate restTemplate = new RestTemplate();

    // Metodo que hace el llamado a la API
    public PokemonDTO getPokemon(String name) {
        try {
            String url = "https://pokeapi.co/api/v2/pokemon/" + name;
            // Metodo que recibe los datos y hace el mapeo
            Map<String, Object> response =
                    restTemplate.getForObject(url, Map.class);
            // Variables a usar para el mapeo
            String pokemonName = (String) response.get("name"); // Nombre
            int height = (int) response.get("height"); // Altura
            int weight = (int) response.get("weight"); // Peso

            List<Map<String, Object>> abilitiesRaw =
                    (List<Map<String, Object>>) response.get("abilities"); // Abilidades

            List<String> abilities = new ArrayList<>(); // Array con los datos de las habilidades
            /**
                * Bucle For-Each en Java para extraer las habilidades
                * Por cada una de las habilidades realiza un recorrido
                * Extrae el nombre y lo añade a la lista (Raw)
                * Raw previamente definido
            */
            for (Map<String, Object> abilityEntry : abilitiesRaw) {
                Map<String, Object> ability =
                        (Map<String, Object>) abilityEntry.get("ability");

                abilities.add((String) ability.get("name"));
            }

            return new PokemonDTO(pokemonName, height, weight, abilities); // End Point
            // Nota: los valores deben coincidir con los del DTO (PokemonDTO.java)
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Pokemon no encontrado: " + name);
        }
    }
}
