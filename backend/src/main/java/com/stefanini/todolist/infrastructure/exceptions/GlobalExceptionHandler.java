package com.stefanini.todolist.infrastructure.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manipulador global de exceções da aplicação.
 *
 * <p>Esta classe centraliza o tratamento de exceções lançadas pelos controllers,
 * convertendo-as em respostas HTTP apropriadas com mensagens de erro padronizadas.</p>
 *
 * <p>Exceções tratadas:
 * <ul>
 *   <li>ResourceNotFoundException - Recurso não encontrado (404)</li>
 *   <li>BusinessRuleException - Violação de regra de negócio (422)</li>
 *   <li>InvalidDataException - Dados inválidos (400)</li>
 *   <li>MethodArgumentNotValidException - Erro de validação (400)</li>
 *   <li>HttpMessageNotReadableException - JSON malformado (400)</li>
 *   <li>MethodArgumentTypeMismatchException - Tipo de argumento inválido (400)</li>
 *   <li>NoHandlerFoundException - Endpoint não encontrado (404)</li>
 *   <li>Exception - Erros genéricos e não tratados (500)</li>
 * </ul>
 * </p>
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-07
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Trata exceções de recurso não encontrado.
     *
     * @param ex Exceção lançada
     * @param request Requisição HTTP
     * @return Resposta de erro com status 404
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        log.warn("Recurso não encontrado: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Trata exceções de violação de regra de negócio.
     *
     * @param ex Exceção lançada
     * @param request Requisição HTTP
     * @return Resposta de erro com status 422
     */
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusinessRuleException(
            BusinessRuleException ex,
            HttpServletRequest request) {

        log.warn("Violação de regra de negócio: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .error(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }

    /**
     * Trata exceções de dados inválidos.
     *
     * @param ex Exceção lançada
     * @param request Requisição HTTP
     * @return Resposta de erro com status 400
     */
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDataException(
            InvalidDataException ex,
            HttpServletRequest request) {

        log.warn("Dados inválidos: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Trata exceções de validação de argumentos.
     *
     * @param ex Exceção de validação
     * @param request Requisição HTTP
     * @return Resposta de erro com status 400 e detalhes de validação
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        log.warn("Erro de validação na requisição: {}", request.getRequestURI());

        List<ErrorResponse.ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::mapFieldError)
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Erro de validação nos dados da requisição")
                .path(request.getRequestURI())
                .validationErrors(validationErrors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Trata exceções de mensagem HTTP não legível (JSON malformado).
     *
     * @param ex Exceção lançada
     * @param request Requisição HTTP
     * @return Resposta de erro com status 400
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        log.warn("Requisição com JSON malformado: {}", ex.getMessage());

        String message = "Formato de JSON inválido ou dados incompatíveis";

        // Tenta extrair uma mensagem mais específica se possível
        if (ex.getMessage() != null && ex.getMessage().contains("Cannot deserialize")
                && ex.getMessage().contains("StatusTarefa")) {
            message = "Valor inválido para o campo 'status'. Valores permitidos: PENDENTE, EM_ANDAMENTO, CONCLUIDA";
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Trata exceções de tipo de argumento incompatível.
     *
     * @param ex Exceção lançada
     * @param request Requisição HTTP
     * @return Resposta de erro com status 400
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        log.warn("Tipo de argumento inválido: {} para o parâmetro {}", ex.getValue(), ex.getName());

        String message = String.format(
                "Valor '%s' inválido para o parâmetro '%s'. Tipo esperado: %s",
                ex.getValue(),
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconhecido"
        );

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Trata exceções de endpoint não encontrado.
     *
     * @param ex Exceção lançada
     * @param request Requisição HTTP
     * @return Resposta de erro com status 404
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpServletRequest request) {

        log.warn("Endpoint não encontrado: {} {}", ex.getHttpMethod(), ex.getRequestURL());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message("Endpoint não encontrado: " + ex.getRequestURL())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Trata exceções genéricas e não tratadas especificamente.
     *
     * @param ex Exceção lançada
     * @param request Requisição HTTP
     * @return Resposta de erro com status 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Erro interno no servidor: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("Erro interno no servidor. Por favor, tente novamente mais tarde.")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Mapeia um FieldError para ValidationError.
     *
     * @param fieldError Erro de campo da validação
     * @return ValidationError mapeado
     */
    private ErrorResponse.ValidationError mapFieldError(FieldError fieldError) {
        return ErrorResponse.ValidationError.builder()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .rejectedValue(fieldError.getRejectedValue())
                .build();
    }
}

