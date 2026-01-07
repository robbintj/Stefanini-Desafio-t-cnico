package com.stefanini.todolist.infrastructure.exceptions;

/**
 * Exceção lançada quando ocorre uma violação de regra de negócio.
 *
 * <p>Esta exceção é utilizada quando uma operação não pode ser realizada
 * devido a uma violação de regras de negócio da aplicação.</p>
 *
 * <p>Exemplos de uso:
 * <ul>
 *   <li>Tentativa de atualizar uma tarefa concluída</li>
 *   <li>Tentativa de deletar uma tarefa em andamento sem permissão</li>
 *   <li>Violação de restrições de negócio específicas do domínio</li>
 * </ul>
 * </p>
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-07
 */
public class BusinessRuleException extends RuntimeException {

    /**
     * Construtor com mensagem de erro.
     *
     * @param message Mensagem descritiva do erro
     */
    public BusinessRuleException(String message) {
        super(message);
    }

    /**
     * Construtor com mensagem e causa raiz.
     *
     * @param message Mensagem descritiva do erro
     * @param cause Causa raiz da exceção
     */
    public BusinessRuleException(String message, Throwable cause) {
        super(message, cause);
    }
}

