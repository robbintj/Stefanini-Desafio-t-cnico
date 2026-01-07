package com.stefanini.todolist.domain.enums;

/**
 * Enumeração que representa os possíveis status de uma tarefa.
 *
 * <p>Esta enum define os três estados principais no ciclo de vida de uma tarefa:
 * <ul>
 *   <li>{@link #PENDENTE} - Tarefa criada mas ainda não iniciada</li>
 *   <li>{@link #EM_ANDAMENTO} - Tarefa em execução</li>
 *   <li>{@link #CONCLUIDA} - Tarefa finalizada</li>
 * </ul>
 * </p>
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-06
 */
public enum StatusTarefa {

    /**
     * Tarefa criada mas ainda não iniciada.
     */
    PENDENTE("Pendente"),

    /**
     * Tarefa em processo de execução.
     */
    EM_ANDAMENTO("Em Andamento"),

    /**
     * Tarefa finalizada com sucesso.
     */
    CONCLUIDA("Concluída");

    private final String descricao;

    /**
     * Construtor do enum.
     *
     * @param descricao Descrição legível do status
     */
    StatusTarefa(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém a descrição legível do status.
     *
     * @return Descrição do status
     */
    public String getDescricao() {
        return descricao;
    }
}

