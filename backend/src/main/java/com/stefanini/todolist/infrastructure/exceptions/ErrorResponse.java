package com.stefanini.todolist.infrastructure.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe que representa a resposta de erro padronizada da API.
 *
 * <p>Esta classe é utilizada para retornar informações detalhadas sobre erros
 * que ocorrem durante o processamento das requisições.</p>
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Resposta de erro padronizada")
public class ErrorResponse {

    /**
     * Timestamp de quando o erro ocorreu.
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Data e hora do erro", example = "2026-01-07T10:30:00")
    private LocalDateTime timestamp;

    /**
     * Código de status HTTP.
     */
    @Schema(description = "Código de status HTTP", example = "404")
    private Integer status;

    /**
     * Nome do status HTTP.
     */
    @Schema(description = "Nome do status HTTP", example = "Not Found")
    private String error;

    /**
     * Mensagem descritiva do erro.
     */
    @Schema(description = "Mensagem descritiva do erro", example = "Tarefa não encontrada com ID: 123")
    private String message;

    /**
     * Path da requisição que gerou o erro.
     */
    @Schema(description = "Caminho da requisição", example = "/tarefas/123")
    private String path;

    /**
     * Lista de erros de validação (quando aplicável).
     */
    @Schema(description = "Lista de erros de validação")
    private List<ValidationError> validationErrors;

    /**
     * Classe interna que representa um erro de validação.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Erro de validação de campo")
    public static class ValidationError {

        /**
         * Nome do campo que apresentou erro.
         */
        @Schema(description = "Nome do campo", example = "titulo")
        private String field;

        /**
         * Mensagem de erro do campo.
         */
        @Schema(description = "Mensagem de erro", example = "O título é obrigatório")
        private String message;

        /**
         * Valor rejeitado (opcional).
         */
        @Schema(description = "Valor rejeitado", example = "")
        private Object rejectedValue;
    }
}

