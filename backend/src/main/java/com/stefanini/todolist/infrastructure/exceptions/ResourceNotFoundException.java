package com.stefanini.todolist.infrastructure.exceptions;

/**
 * Exceção lançada quando um recurso solicitado não é encontrado.
 *
 * <p>Esta exceção é utilizada quando uma operação tenta acessar um recurso
 * que não existe no sistema, como uma tarefa com ID inexistente.</p>
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-06
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Construtor com mensagem de erro.
     *
     * @param message Mensagem descritiva do erro
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Construtor com mensagem e causa raiz.
     *
     * @param message Mensagem descritiva do erro
     * @param cause Causa raiz da exceção
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

