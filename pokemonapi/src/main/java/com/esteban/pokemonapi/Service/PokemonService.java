// Importaciones

package com.esteban.pokemonapi.Service;
// Paquetes Spring Boot
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;

// Paquetes del mismo proyecto
import com.esteban.pokemonapi.DTO.PokemonDTO;
import com.esteban.pokemonapi.DTO.PokemonImageDTO;
import com.esteban.pokemonapi.DTO.PokemonTypeDTO;
import com.esteban.pokemonapi.DTO.PokemonGenDTO;
import com.esteban.pokemonapi.Model.PokemonResponse;
import com.esteban.pokemonapi.Model.PokemonTypeResponse;

import reactor.core.publisher.Mono;

import com.esteban.pokemonapi.Model.PokemonImageResponse;
import com.esteban.pokemonapi.Model.PokemonGenResponse;
import com.esteban.pokemonapi.Exception.PokemonNotFoundException;

// Elementos del mapeo
import java.util.ArrayList;
import java.util.List;

// ---------------------------------------------------------------------//

// Constructor
@Service
public class PokemonService {
    // Inyección del Logger para el manejo de errores
    private static final Logger logger =
        LoggerFactory.getLogger(PokemonService.class);
    // Inicialización de las configuraciones
    private final RestTemplate restTemplate;
    private final WebClient webClient;
    // Inyección de las configuraciones (RestTemplate, WebClient)
    public PokemonService(RestTemplate restTemplate, WebClient webClient) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
    }

// ---------------------------------------------------------------------//


    // Metodo que hace el llamado a la API
    public PokemonDTO getPokemon(String name) {
        logger.info("Consultando Pokemon: {}", name); // Logger 

        return webClient.get()
            .uri("/pokemon/" + name)
            .retrieve()
            // onStatus intercepta el estado HTTP de la respuesta
            // Si es 4xx (ej: 404), lanza tu excepción personalizada
            // Mono.error() es la forma reactiva de lanzar una excepción
            .onStatus(status -> status.is4xxClientError(), response -> {
                logger.error("Pokemon no encontrado: {}", name); // Logger del error
                return Mono.error(new PokemonNotFoundException(name));
                // Spring detecta que es RuntimeException y la manda al GlobalExceptionHandler
            })
            .bodyToMono(PokemonResponse.class)
            .map(response -> {
                List<String> abilities = response.getAbilities()
                    .stream()
                    .map(a -> a.getAbility().getName())
                    .toList();
                return new PokemonDTO(
                    response.getName(),      // String Name
                    response.getHeight(),    // int Height
                    response.getWeight(),    // int Weight
                    abilities                // List<String>
                );
            })
            .block(); // Bloqueamos temporalmente para mantener la arquitectura actual
    }

// ----------------------------------------------------------- //

    // Metodo que hace el llamado a la Imagen del Pokemon

    public PokemonImageDTO getPokemonImages(String name) {
        // Iniciamos el método
        logger.info("Buscando las imagénes del Pokemón {}", name);

        // Llamado a la API
        return webClient.get()
            .uri("/pokemon/" + name) // URL
            .retrieve()
            // Manejo de Errores en el llamado
            .onStatus(status -> status.is4xxClientError(), response -> {
                logger.error("Imagenes no encontradas para: {}", name);
                return Mono.error(new PokemonNotFoundException(name));
            })
            // Estructura de la respuesta para el usuario
            .bodyToMono(PokemonImageResponse.class) // Llamamos el modelo de la respuesta
            .map(response -> {   // Enviamos la respuesta al modelo ya mapeada

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

                return new PokemonImageDTO(
                    response.getName(),  // String name
                    images               // List<String>
                );
            })
            .block();   // Bloqueamos temporalmente para mantener la arquitectura actual
    }

// ----------------------------------------------------------- //

    // Metodo que trae todos los pokemons de una generación

    public PokemonGenDTO getPokemonGenDTO(int gen) {
        //Iniciamos el método
        logger.info("Consultando Pokemons de la generación: {}", gen);

        // Hacemos el llamado a la api
        return webClient.get()
            .uri("/generation/" + gen) // URL 
            .retrieve()
        // Manejo de Errores en el llamado
        .onStatus(status -> status.is4xxClientError(), response -> {
            logger.error("No se encontraron los pokemons de la generación: {}", gen);
            return Mono.error(new PokemonNotFoundException(String.valueOf(gen)));
        })
        // Estructura de la respuesta de la API
        .bodyToMono(PokemonGenResponse.class)  // Usamos el modelo realizado para la respuesta
        .map(response -> {  // Enviamos la respuesta ya mapeada
            List<String> pokemon = response.getPokemon_species()
                .stream()
                .map(p -> p.getName())
                .toList();

            return new PokemonGenDTO(
                response.getId(), // int Gen
                pokemon           // List<String> Pokemons
            );
        })
        .block();   // Bloqueamos temporalmente para mantener la arquitectura actual
    }


// ----------------------------------------------------------- //

    // Metodo que trae todos lo pokemons de un mismo tipo (Request Params)

    public PokemonTypeDTO getPokemonByType(String tipo) {
        // Iniciamos el método
    logger.info("Consultando Pokémons de tipo: {}", tipo);

    return webClient.get() // Hacemos la petición
        .uri("/type/" + tipo) // Complementamos el enlace base (WebClientConfig)
        .retrieve()
        // Manejo de Errores y excepciones en la llamada con .onStatus
        .onStatus(status -> status.is4xxClientError(), response -> {
            logger.error("Tipo no encontrado: {}", tipo);       // Expulsada por Logger
            return Mono.error(new PokemonNotFoundException(tipo));  // Expulsada por Exception
        })
        // Armamos la respuesta a enviar
        .bodyToMono(PokemonTypeResponse.class) // Usamos el Model de la respuesta
        .map(response -> {  // Preparamos la respuesta con .map()
            List<String> pokemon = response.getPokemon()
                .stream()
                .map(entry -> entry.getPokemon().getName()) // Doble .get() por el anidamiento
                .toList();
            return new PokemonTypeDTO(response.getName(), pokemon); // Expulsamos la respuesta
        })
        .block();   // Bloqueamos temporalmente para mantener la arquitectura actual
    }
}
