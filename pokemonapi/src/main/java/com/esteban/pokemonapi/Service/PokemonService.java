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
import com.esteban.pokemonapi.DTO.PokemonGenDTO;
import com.esteban.pokemonapi.Model.PokemonResponse;

import reactor.core.publisher.Mono;

import com.esteban.pokemonapi.Model.PokemonImageResponse;
import com.esteban.pokemonapi.Model.PokemonGenResponse;
import com.esteban.pokemonapi.Exception.PokemonNotFoundException;

import java.util.ArrayList;
// Elementos del mapeo
import java.util.List;

@Service
public class PokemonService {

    private static final Logger logger =
        LoggerFactory.getLogger(PokemonService.class);

    private final RestTemplate restTemplate;
    private final WebClient webClient;

    public PokemonService(RestTemplate restTemplate, WebClient webClient) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
    }
    // Metodo que hace el llamado a la API
    public PokemonDTO getPokemon(String name) {
        logger.info("Consultando Pokemon: {}", name); // Logger igual que antes

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
                    response.getName(),
                    response.getHeight(),
                    response.getWeight(),
                    abilities
                );
            })
            .block(); // Bloqueamos temporalmente para mantener la arquitectura actual
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

    // ----------------------------------------------------------- //

    // Metodo que trae todos los pokemons de una generación

    public PokemonGenDTO getPokemonGenDTO(int gen) {

    String url = "https://pokeapi.co/api/v2/generation/" + gen;

        PokemonGenResponse response =
            restTemplate.getForObject(url, PokemonGenResponse.class);

        List<String> pokemon = response.getPokemon_species()
                .stream()
                .map(p -> p.getName())
                .toList();

        return new PokemonGenDTO(
            response.getId(),  // int
            pokemon            // List<String>
        );
    }
}
