package com.esteban.pokemonapi.Service;
// Paquetes Spring Boot
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// Paquetes del mismo proyecto
import com.esteban.pokemonapi.DTO.PokemonDTO;
import com.esteban.pokemonapi.DTO.PokemonImageDTO;
import com.esteban.pokemonapi.Exception.PokemonNotFoundException;
import com.esteban.pokemonapi.Model.PokemonResponse;
import com.esteban.pokemonapi.Model.PokemonImageResponse;

import java.util.ArrayList;
// Elementos del mapeo
import java.util.List;

@Service
public class PokemonService {

    private static final Logger logger =
        LoggerFactory.getLogger(PokemonService.class);

    private final RestTemplate restTemplate;

    public PokemonService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    // Metodo que hace el llamado a la API
    public PokemonDTO getPokemon(String name) {
        try {
            String url = "https://pokeapi.co/api/v2/pokemon/" + name;

            logger.info("Consultando Pokemon: {}", name); // Uso del Logger
            // Metodo que recibe los datos y hace el mapeo
            PokemonResponse response =
                restTemplate.getForObject(url, PokemonResponse.class);

            List<String> abilities = response.getAbilities()
                .stream()
                .map(a -> a.getAbility().getName())
                .toList();

            return new PokemonDTO(    // Tomamos los valores y los enviamos acorde al DTO
                response.getName(), 
                response.getHeight(),
                response.getWeight(),
                abilities
            );
            // Nota: los valores deben coincidir con los del DTO (PokemonDTO.java)
        } catch (HttpClientErrorException.NotFound e) {
            logger.error("Error consultando pokemon {}", name, e);
            throw new PokemonNotFoundException("No se pudo obtener el pokemon:");
            // Cuando hay una exepción, Java busca una clase con ControllerAdvice.
            // En este caso, solo está en GlobalExceptionHandler.
        }
    }

    // ----------------------------------------------------------- //

    // Metodo que hace el llamado a la Imagen del Pokemon

    public PokemonImageDTO getPokemonImages(String name) {
        String url = "https://pokeapi.co/api/v2/pokemon/" + name;

        PokemonImageResponse response =
                restTemplate.getForObject(url, PokemonImageResponse.class);

        var sprites = response.getSprites();

        List<String> images = new ArrayList<>();

        if (sprites.getFront_default() != null)
            images.add(sprites.getFront_default());

        if (sprites.getBack_default() != null)
            images.add(sprites.getBack_default());

        if (sprites.getFront_shiny() != null)
            images.add(sprites.getFront_shiny());

        if (sprites.getBack_shiny() != null)
            images.add(sprites.getBack_shiny());

        return new PokemonImageDTO(response.getName(), images);
    }


}
