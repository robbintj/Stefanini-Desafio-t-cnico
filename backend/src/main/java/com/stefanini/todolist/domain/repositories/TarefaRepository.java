package com.stefanini.todolist.domain.repositories;

import com.stefanini.todolist.domain.entities.Tarefa;
import com.stefanini.todolist.domain.enums.StatusTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para operações de persistência da entidade {@link Tarefa}.
 *
 * <p>Esta interface estende {@link JpaRepository} fornecendo métodos CRUD básicos
 * e adiciona queries personalizadas para buscar tarefas por diferentes critérios.</p>
 *
 * <p>O Spring Data JPA implementa automaticamente esta interface em tempo de execução,
 * fornecendo todas as operações de banco de dados necessárias.</p>
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-06
 */
@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    /**
     * Busca todas as tarefas com um determinado status.
     *
     * @param status Status das tarefas a serem buscadas
     * @return Lista de tarefas com o status especificado
     */
    List<Tarefa> findByStatus(StatusTarefa status);

    /**
     * Busca tarefas cujo título contenha o texto especificado (case-insensitive).
     *
     * @param titulo Texto a ser buscado no título
     * @return Lista de tarefas que contém o texto no título
     */
    List<Tarefa> findByTituloContainingIgnoreCase(String titulo);

    /**
     * Busca tarefas criadas após uma data específica.
     *
     * @param data Data de referência
     * @return Lista de tarefas criadas após a data especificada
     */
    List<Tarefa> findByDataCriacaoAfter(LocalDateTime data);

    /**
     * Busca tarefas criadas entre duas datas.
     *
     * @param dataInicio Data inicial do período
     * @param dataFim Data final do período
     * @return Lista de tarefas criadas no período especificado
     */
    List<Tarefa> findByDataCriacaoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    /**
     * Conta o número de tarefas por status.
     *
     * @param status Status das tarefas a serem contadas
     * @return Número de tarefas com o status especificado
     */
    Long countByStatus(StatusTarefa status);

    /**
     * Busca todas as tarefas ordenadas por data de criação descendente.
     *
     * @return Lista de tarefas ordenadas da mais recente para a mais antiga
     */
    @Query("SELECT t FROM Tarefa t ORDER BY t.dataCriacao DESC")
    List<Tarefa> findAllOrderByDataCriacaoDesc();

    /**
     * Busca tarefas por status e título (query customizada).
     *
     * @param status Status da tarefa
     * @param titulo Texto a ser buscado no título
     * @return Lista de tarefas que atendem aos critérios
     */
    @Query("SELECT t FROM Tarefa t WHERE t.status = :status AND LOWER(t.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))")
    List<Tarefa> findByStatusAndTitulo(@Param("status") StatusTarefa status, @Param("titulo") String titulo);
}

