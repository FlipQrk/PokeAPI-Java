package com.esteban.pokemonapi.Controller;

import org.springframework.web.bind.annotation.*;

import com.esteban.pokemonapi.DTO.PokemonDTO;
import com.esteban.pokemonapi.DTO.PokemonImageDTO;
import com.esteban.pokemonapi.DTO.PokemonTypeDTO;
import com.esteban.pokemonapi.DTO.PokemonGenDTO;
import com.esteban.pokemonapi.Service.PokemonService;

@RestController
@RequestMapping("/pokemon") //Implementa de forma general el "/pokemon" en todas las rutas
public class HelloController {

    private final PokemonService pokemonService;

    public HelloController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/{name}") // pokemon/pikachu
    public PokemonDTO getPokemon(@PathVariable String name) {
        return pokemonService.getPokemon(name);
    }

    @GetMapping("/{name}/images") // pokemon/pikachu/images
    public PokemonImageDTO getPokemonImages(@PathVariable String name) {
        return pokemonService.getPokemonImages(name);
    }

    @GetMapping("/gen/{gen}") // pokemon/gen/1
    public PokemonGenDTO gPokemonGenDTO(@PathVariable int gen) {
        return pokemonService.getPokemonGenDTO(gen);
    }

    @GetMapping // pokemon?tipo=fire
    public PokemonTypeDTO getPokemonByType(@RequestParam String tipo) {
        return pokemonService.getPokemonByType(tipo);
    }
}
