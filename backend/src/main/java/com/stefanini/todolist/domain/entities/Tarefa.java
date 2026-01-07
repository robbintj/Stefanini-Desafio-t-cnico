package com.stefanini.todolist.domain.entities;

import com.stefanini.todolist.domain.enums.StatusTarefa;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Entidade que representa uma Tarefa no sistema de gerenciamento de tarefas.
 *
 * <p>Esta classe é mapeada para a tabela "tarefas" no banco de dados e contém
 * todas as informações relacionadas a uma tarefa, incluindo título, descrição,
 * datas de criação/atualização e status atual.</p>
 *
 * <p>Utiliza anotações do Lombok para geração automática de getters, setters,
 * construtores e outros métodos utilitários.</p>
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-06
 */
@Entity
@Table(name = "tarefas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class Tarefa {

    /**
     * Identificador único da tarefa.
     * Gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Título da tarefa.
     * Campo obrigatório com no máximo 100 caracteres.
     */
    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    /**
     * Descrição detalhada da tarefa.
     * Campo opcional com no máximo 500 caracteres.
     */
    @Column(name = "descricao", length = 500)
    private String descricao;

    /**
     * Data e hora de criação da tarefa.
     * Gerado automaticamente no momento da criação.
     */
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    /**
     * Data e hora da última atualização da tarefa.
     * Atualizado automaticamente a cada modificação.
     */
    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    /**
     * Status atual da tarefa.
     * Valores possíveis: PENDENTE, EM_ANDAMENTO, CONCLUIDA.
     * Valor padrão: PENDENTE.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private StatusTarefa status = StatusTarefa.PENDENTE;

    /**
     * Método executado antes de persistir a entidade.
     * Garante que o status padrão seja PENDENTE se não foi definido.
     */
    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = StatusTarefa.PENDENTE;
        }
    }
}

