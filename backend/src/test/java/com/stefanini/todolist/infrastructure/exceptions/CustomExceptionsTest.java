package com.stefanini.todolist.infrastructure.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para as exceções customizadas.
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-07
 */
class CustomExceptionsTest {

    @Test
    @DisplayName("Deve criar ResourceNotFoundException com mensagem")
    void testResourceNotFoundException() {
        String message = "Tarefa não encontrada";
        ResourceNotFoundException exception = new ResourceNotFoundException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Deve criar ResourceNotFoundException com mensagem e causa")
    void testResourceNotFoundExceptionWithCause() {
        String message = "Tarefa não encontrada";
        Throwable cause = new RuntimeException("Causa raiz");
        ResourceNotFoundException exception = new ResourceNotFoundException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("Deve criar BusinessRuleException com mensagem")
    void testBusinessRuleException() {
        String message = "Violação de regra de negócio";
        BusinessRuleException exception = new BusinessRuleException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Deve criar BusinessRuleException com mensagem e causa")
    void testBusinessRuleExceptionWithCause() {
        String message = "Violação de regra de negócio";
        Throwable cause = new RuntimeException("Causa raiz");
        BusinessRuleException exception = new BusinessRuleException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("Deve criar InvalidDataException com mensagem")
    void testInvalidDataException() {
        String message = "Dados inválidos";
        InvalidDataException exception = new InvalidDataException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Deve criar InvalidDataException com mensagem e causa")
    void testInvalidDataExceptionWithCause() {
        String message = "Dados inválidos";
        Throwable cause = new RuntimeException("Causa raiz");
        InvalidDataException exception = new InvalidDataException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}

