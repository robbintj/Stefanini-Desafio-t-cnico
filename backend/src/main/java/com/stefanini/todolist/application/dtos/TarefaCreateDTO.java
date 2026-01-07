package com.stefanini.todolist.application.dtos;

import com.stefanini.todolist.domain.enums.StatusTarefa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO para criação de uma nova tarefa.
 *
 * <p>Este objeto é utilizado para transferir dados na requisição de criação de tarefa.
 * Contém validações para garantir a integridade dos dados recebidos.</p>
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
@Schema(description = "Dados para criação de uma nova tarefa")
public class TarefaCreateDTO {

    /**
     * Título da tarefa.
     */
    @NotBlank(message = "O título é obrigatório")
    @Size(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres")
    @Schema(description = "Título da tarefa", example = "Implementar API REST", required = true)
    private String titulo;

    /**
     * Descrição detalhada da tarefa.
     */
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    @Schema(description = "Descrição detalhada da tarefa", example = "Desenvolver endpoints CRUD seguindo padrões RESTful")
    private String descricao;

    /**
     * Status inicial da tarefa.
     * Se não informado, será definido como PENDENTE.
     */
    @Schema(description = "Status da tarefa", example = "PENDENTE", defaultValue = "PENDENTE")
    private StatusTarefa status;
}

