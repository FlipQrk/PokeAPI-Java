package com.esteban.pokemonapi.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.esteban.pokemonapi.DTO.PokemonDTO;
import com.esteban.pokemonapi.Service.PokemonService;

@RestController
public class HelloController {

    private final PokemonService pokemonService;

    public HelloController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hola desde Spring Boot 🚀";
    }

    @GetMapping("/pokemon/{name}")
    public PokemonDTO getPokemon(@PathVariable String name) {
        return pokemonService.getPokemon(name);
    }

    @GetMapping("/{name}")
    public PokemonDTO getPokemon(@PathVariable String name) {
        return pokemonService.getPokemon(name);
    }
}
