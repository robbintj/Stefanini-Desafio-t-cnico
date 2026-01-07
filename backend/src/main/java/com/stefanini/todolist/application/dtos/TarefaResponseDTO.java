package com.stefanini.todolist.application.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stefanini.todolist.domain.enums.StatusTarefa;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO de resposta contendo os dados de uma tarefa.
 *
 * <p>Este objeto é utilizado para transferir dados de tarefa nas respostas da API.
 * Contém todos os campos da tarefa, incluindo informações de auditoria.</p>
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-06
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dados de resposta de uma tarefa")
public class TarefaResponseDTO {

    /**
     * Identificador único da tarefa.
     */
    @Schema(description = "ID único da tarefa", example = "1")
    private Long id;

    /**
     * Título da tarefa.
     */
    @Schema(description = "Título da tarefa", example = "Implementar API REST")
    private String titulo;

    /**
     * Descrição detalhada da tarefa.
     */
    @Schema(description = "Descrição detalhada da tarefa", example = "Desenvolver endpoints CRUD seguindo padrões RESTful")
    private String descricao;

    /**
     * Data e hora de criação da tarefa.
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Data e hora de criação", example = "2026-01-06T10:30:00")
    private LocalDateTime dataCriacao;

    /**
     * Data e hora da última atualização.
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Data e hora da última atualização", example = "2026-01-06T15:45:00")
    private LocalDateTime dataAtualizacao;

    /**
     * Status atual da tarefa.
     */
    @Schema(description = "Status atual da tarefa", example = "EM_ANDAMENTO")
    private StatusTarefa status;
}

