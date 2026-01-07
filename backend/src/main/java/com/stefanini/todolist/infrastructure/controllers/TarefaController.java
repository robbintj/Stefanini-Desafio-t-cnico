package com.stefanini.todolist.infrastructure.controllers;

import com.stefanini.todolist.application.dtos.TarefaCreateDTO;
import com.stefanini.todolist.application.dtos.TarefaResponseDTO;
import com.stefanini.todolist.application.dtos.TarefaUpdateDTO;
import com.stefanini.todolist.application.services.TarefaService;
import com.stefanini.todolist.domain.enums.StatusTarefa;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de Tarefas.
 *
 * <p>Este controller expõe os endpoints da API REST para operações CRUD de tarefas,
 * seguindo os padrões RESTful e as melhores práticas de desenvolvimento.</p>
 *
 * <p>Endpoints disponíveis:
 * <ul>
 *   <li>POST /api/tarefas - Criar nova tarefa</li>
 *   <li>GET /api/tarefas - Listar todas as tarefas</li>
 *   <li>GET /api/tarefas/{id} - Buscar tarefa por ID</li>
 *   <li>GET /api/tarefas/status/{status} - Listar tarefas por status</li>
 *   <li>PUT /api/tarefas/{id} - Atualizar tarefa existente</li>
 *   <li>DELETE /api/tarefas/{id} - Remover tarefa</li>
 * </ul>
 * </p>
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-06
 */
@RestController
@RequestMapping("/api/tarefas")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Tarefas", description = "Endpoints para gerenciamento de tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    /**
     * Cria uma nova tarefa.
     *
     * @param createDTO Dados da tarefa a ser criada
     * @return Dados da tarefa criada com status 201 (Created)
     */
    @PostMapping
    @Operation(summary = "Criar nova tarefa", description = "Cria uma nova tarefa no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<TarefaResponseDTO> criar(
            @Valid @RequestBody TarefaCreateDTO createDTO) {
        log.info("Requisição recebida para criar tarefa");
        TarefaResponseDTO response = tarefaService.criar(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Lista todas as tarefas cadastradas.
     *
     * @return Lista de todas as tarefas
     */
    @GetMapping
    @Operation(summary = "Listar todas as tarefas", description = "Retorna lista com todas as tarefas cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<TarefaResponseDTO>> listarTodas() {
        log.info("Requisição recebida para listar todas as tarefas");
        List<TarefaResponseDTO> response = tarefaService.listarTodas();
        return ResponseEntity.ok(response);
    }

    /**
     * Busca uma tarefa específica por ID.
     *
     * @param id Identificador único da tarefa
     * @return Dados da tarefa encontrada
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar tarefa por ID", description = "Retorna os dados de uma tarefa específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<TarefaResponseDTO> buscarPorId(
            @Parameter(description = "ID da tarefa", required = true)
            @PathVariable Long id) {
        log.info("Requisição recebida para buscar tarefa por ID: {}", id);
        TarefaResponseDTO response = tarefaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Lista tarefas filtradas por status.
     *
     * @param status Status das tarefas a serem listadas
     * @return Lista de tarefas com o status especificado
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Listar tarefas por status", description = "Retorna lista de tarefas filtradas por status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<TarefaResponseDTO>> listarPorStatus(
            @Parameter(description = "Status da tarefa (PENDENTE, EM_ANDAMENTO, CONCLUIDA)", required = true)
            @PathVariable StatusTarefa status) {
        log.info("Requisição recebida para listar tarefas por status: {}", status);
        List<TarefaResponseDTO> response = tarefaService.listarPorStatus(status);
        return ResponseEntity.ok(response);
    }

    /**
     * Atualiza uma tarefa existente.
     *
     * @param id Identificador único da tarefa
     * @param updateDTO Novos dados da tarefa
     * @return Dados da tarefa atualizada
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tarefa", description = "Atualiza os dados de uma tarefa existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<TarefaResponseDTO> atualizar(
            @Parameter(description = "ID da tarefa", required = true)
            @PathVariable Long id,
            @Valid @RequestBody TarefaUpdateDTO updateDTO) {
        log.info("Requisição recebida para atualizar tarefa ID: {}", id);
        TarefaResponseDTO response = tarefaService.atualizar(id, updateDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Remove uma tarefa do sistema.
     *
     * @param id Identificador único da tarefa
     * @return Resposta vazia com status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar tarefa", description = "Remove uma tarefa do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da tarefa", required = true)
            @PathVariable Long id) {
        log.info("Requisição recebida para deletar tarefa ID: {}", id);
        tarefaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

