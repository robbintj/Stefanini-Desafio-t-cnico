package com.stefanini.todolist.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * Configuração de CORS (Cross-Origin Resource Sharing) para a API.
 *
 * <p>Esta configuração permite que a aplicação frontend Angular acesse a API
 * sem restrições de origem cruzada, facilitando o desenvolvimento e integração.</p>
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-06
 */
@Configuration
public class CorsConfig {

    /**
     * Configura o filtro CORS para a aplicação.
     *
     * @return Filtro CORS configurado
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permite credenciais
        config.setAllowCredentials(true);

        // Origens permitidas (frontend Angular)
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));

        // Headers permitidos
        config.addAllowedHeader("*");

        // Métodos HTTP permitidos
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Headers expostos
        config.addExposedHeader("Authorization");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

