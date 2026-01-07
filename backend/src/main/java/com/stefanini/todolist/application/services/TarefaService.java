package com.stefanini.todolist.application.services;

import com.stefanini.todolist.application.dtos.TarefaCreateDTO;
import com.stefanini.todolist.application.dtos.TarefaResponseDTO;
import com.stefanini.todolist.application.dtos.TarefaUpdateDTO;
import com.stefanini.todolist.domain.enums.StatusTarefa;

import java.util.List;

/**
 * Interface de serviço para operações de negócio relacionadas a Tarefas.
 *
 * <p>Define o contrato para as operações CRUD e funcionalidades adicionais
 * do sistema de gerenciamento de tarefas.</p>
 *
 * <p>Esta interface segue o princípio de Inversão de Dependência (SOLID),
 * permitindo que implementações sejam facilmente substituídas.</p>
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-06
 */
public interface TarefaService {

    /**
     * Cria uma nova tarefa no sistema.
     *
     * @param createDTO Dados da tarefa a ser criada
     * @return DTO com os dados da tarefa criada
     */
    TarefaResponseDTO criar(TarefaCreateDTO createDTO);

    /**
     * Busca uma tarefa por seu ID.
     *
     * @param id Identificador único da tarefa
     * @return DTO com os dados da tarefa encontrada
     * @throws com.stefanini.todolist.infrastructure.exceptions.ResourceNotFoundException
     *         se a tarefa não for encontrada
     */
    TarefaResponseDTO buscarPorId(Long id);

    /**
     * Lista todas as tarefas cadastradas.
     *
     * @return Lista com todas as tarefas
     */
    List<TarefaResponseDTO> listarTodas();

    /**
     * Lista tarefas filtradas por status.
     *
     * @param status Status das tarefas a serem listadas
     * @return Lista de tarefas com o status especificado
     */
    List<TarefaResponseDTO> listarPorStatus(StatusTarefa status);

    /**
     * Atualiza os dados de uma tarefa existente.
     *
     * @param id Identificador único da tarefa
     * @param updateDTO Novos dados da tarefa
     * @return DTO com os dados da tarefa atualizada
     * @throws com.stefanini.todolist.infrastructure.exceptions.ResourceNotFoundException
     *         se a tarefa não for encontrada
     */
    TarefaResponseDTO atualizar(Long id, TarefaUpdateDTO updateDTO);

    /**
     * Remove uma tarefa do sistema.
     *
     * @param id Identificador único da tarefa
     * @throws com.stefanini.todolist.infrastructure.exceptions.ResourceNotFoundException
     *         se a tarefa não for encontrada
     */
    void deletar(Long id);
}

