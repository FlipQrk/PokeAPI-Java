package com.esteban.pokemonapi.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .baseUrl("https://pokeapi.co/api/v2")
            .build();
    }
}
/**
 * WebCLient es una configuración usada en Java Spring
 * Sirve para hacer metodos reactivos
 * Estos metodos reactivos ayudan a manejar las llamadas asincronas con un paradigma diferente
 * No permite que una actividad se frene por otra que funcione
 * Permite que las tareas puedan iniciarse sin esperar una respuesta del sistema
 * El sistema maneja tareas de forma simulatena
 */