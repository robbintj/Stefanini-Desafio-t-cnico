package com.stefanini.todolist.infrastructure.exceptions;

/**
 * Exceção lançada quando os dados fornecidos são inválidos.
 *
 * <p>Esta exceção é utilizada quando os dados recebidos não atendem aos
 * critérios de validação esperados, além das validações de Bean Validation.</p>
 *
 * <p>Exemplos de uso:
 * <ul>
 *   <li>Formato de data inválido</li>
 *   <li>Combinação de campos incompatíveis</li>
 *   <li>Valores fora do intervalo permitido</li>
 * </ul>
 * </p>
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-07
 */
public class InvalidDataException extends RuntimeException {

    /**
     * Construtor com mensagem de erro.
     *
     * @param message Mensagem descritiva do erro
     */
    public InvalidDataException(String message) {
        super(message);
    }

    /**
     * Construtor com mensagem e causa raiz.
     *
     * @param message Mensagem descritiva do erro
     * @param cause Causa raiz da exceção
     */
    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}

