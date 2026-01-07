package com.stefanini.todolist.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuração do OpenAPI/Swagger para documentação da API.
 *
 * <p>Esta classe configura o Swagger UI e a geração automática de documentação
 * da API REST, facilitando o teste e integração com a aplicação.</p>
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-06
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configura as informações gerais da documentação OpenAPI.
     *
     * @return Objeto OpenAPI configurado
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desenvolvimento")
                ))
                .info(new Info()
                        .title("API de Gerenciamento de Tarefas - To-Do List")
                        .description("API RESTful para gerenciamento de tarefas desenvolvida como parte do desafio técnico Stefanini. " +
                                "Esta API permite criar, listar, editar e excluir tarefas, seguindo os princípios de Clean Code e Clean Architecture.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Stefanini Challenge")
                                .email("challenge@stefanini.com")
                                .url("https://stefanini.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                );
    }
}

