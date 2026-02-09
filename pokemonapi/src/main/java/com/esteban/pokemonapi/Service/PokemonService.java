package com.esteban.pokemonapi.Service;
// Paquetes Spring Boot
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.esteban.pokemonapi.DTO.PokemonDTO;
import com.esteban.pokemonapi.Exception.PokemonNotFoundException;
import com.esteban.pokemonapi.Model.PokemonResponse;

// Elementos del mapeo
import java.util.ArrayList;
import java.util.List;

@Service
public class PokemonService {

    private final RestTemplate restTemplate = new RestTemplate();

    // Metodo que hace el llamado a la API
    public PokemonDTO getPokemon(String name) {
        try {
            String url = "https://pokeapi.co/api/v2/pokemon/" + name;
            // Metodo que recibe los datos y hace el mapeo
            PokemonResponse response =
                restTemplate.getForObject(url, PokemonResponse.class);

            List<String> abilities = new ArrayList<>(); // Array con los datos de las habilidades
            /**
                * Bucle For-Each en Java para extraer las habilidades
                * Por cada una de las habilidades realiza un recorrido
                * Extrae el nombre y lo añade a la lista (Raw)
                * Raw previamente definido
            */
            for (PokemonResponse.AbilityEntry entry : response.getAbilities()) {
                abilities.add(entry.getAbility().getName());
            }

            return new PokemonDTO(    // Tomamos los valores y los enviamos acorde al DTO
                response.getName(), 
                response.getHeight(),
                response.getWeight(),
                abilities
            );
            // Nota: los valores deben coincidir con los del DTO (PokemonDTO.java)
        } catch (HttpClientErrorException.NotFound e) {
            throw new PokemonNotFoundException(name);
            // Cuando hay una exepción, Java busca una clase con ControllerAdvice.
            // En este caso, solo está en GlobalExceptionHandler.
        }
    }
}
