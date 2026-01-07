package com.stefanini.todolist;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot.
 *
 * <p>Esta classe inicializa a aplicação de gerenciamento de tarefas,
 * configurando automaticamente todos os componentes necessários através
 * das anotações do Spring Boot.</p>
 *
 * <p>A aplicação está estruturada seguindo os princípios de Clean Architecture:
 * <ul>
 *   <li><b>Domain Layer:</b> Entidades, Enums e Repositories (interfaces)</li>
 *   <li><b>Application Layer:</b> DTOs, Services e Use Cases</li>
 *   <li><b>Infrastructure Layer:</b> Controllers, Configurations e Exception Handlers</li>
 * </ul>
 * </p>
 *
 * <p><b>Tecnologias utilizadas:</b></p>
 * <ul>
 *   <li>Spring Boot 3.2.1</li>
 *   <li>Spring Data JPA</li>
 *   <li>SQL Server</li>
 *   <li>Flyway para migrations</li>
 *   <li>Swagger/OpenAPI para documentação</li>
 *   <li>Lombok para redução de código boilerplate</li>
 *   <li>ModelMapper para mapeamento de objetos</li>
 * </ul>
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-06
 */
@SpringBootApplication
@OpenAPIDefinition
public class TodoListApplication {

    /**
     * Método principal que inicializa a aplicação Spring Boot.
     *
     * @param args Argumentos de linha de comando
     */
    public static void main(String[] args) {
        SpringApplication.run(TodoListApplication.class, args);
    }
}

