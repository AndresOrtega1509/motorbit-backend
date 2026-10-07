package com.motorbit.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.motorbit.response.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, 
                                                                    HttpServletRequest request) {
        List<String> validationErrors = ex.getBindingResult().getFieldErrors()
                                            .stream()
                                            .map(error -> formatError(error))
                                            .toList();

        ErrorResponse response = ErrorResponse.builder()
                                                .timestamp(LocalDateTime.now())
                                                .status(HttpStatus.BAD_REQUEST.value())
                                                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                                .message("Error en la validación de los datos enviados")
                                                .path(request.getRequestURI())
                                                .details(validationErrors)
                                                .build();
        log.debug(
            "Error de validación en {} {}: {}",
            request.getMethod(),
            request.getRequestURI(),
            validationErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(RecursoNoEncontradoException ex, 
                                                                    HttpServletRequest request) {
        log.warn(
            "Recurso no encontrado en {} {}: {}",
            request.getMethod(),
            request.getRequestURI(),
            ex.getMessage()
        );      

        ErrorResponse response = ErrorResponse.builder()
                                                .timestamp(LocalDateTime.now())
                                                .status(HttpStatus.NOT_FOUND.value())
                                                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                                                .message(ex.getMessage())
                                                .path(request.getRequestURI())
                                                .build();                                  
                                                
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(RecursoExistenteException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(RecursoExistenteException ex, 
                                                                    HttpServletRequest request) {
        log.warn(
            "Conflicto de recurso en {} {}: {}",
            request.getMethod(),
            request.getRequestURI(),
            ex.getMessage()
        );

        ErrorResponse response = ErrorResponse.builder()
                                                .timestamp(LocalDateTime.now())
                                                .status(HttpStatus.CONFLICT.value())
                                                .error(HttpStatus.CONFLICT.getReasonPhrase())
                                                .message(ex.getMessage())
                                                .path(request.getRequestURI())
                                                .build();
                                                
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(TransicionEstadoInvalidaException.class)
    public ResponseEntity<ErrorResponse> handleTransicionEstadoInvalidaException(TransicionEstadoInvalidaException ex, 
                                                                    HttpServletRequest request) {
        log.warn(
            "Transición de estado inválida en {} {}: {}",
            request.getMethod(),
            request.getRequestURI(),
            ex.getMessage()
        );

        ErrorResponse response = ErrorResponse.builder()
                                                .timestamp(LocalDateTime.now())
                                                .status(HttpStatus.BAD_REQUEST.value())
                                                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                                .message(ex.getMessage())
                                                .path(request.getRequestURI())
                                                .build();
                                                
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex, 
                                                                    HttpServletRequest request) {
        log.warn(
            "Intento de autenticación fallido en {} {}",
            request.getMethod(),
            request.getRequestURI()
        );

        ErrorResponse response = ErrorResponse.builder()
                                                .timestamp(LocalDateTime.now())
                                                .status(HttpStatus.UNAUTHORIZED.value())
                                                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                                                .message("Usuario o contraseña incorrectos")
                                                .path(request.getRequestURI())
                                                .build();
                                            
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex,
                                                             HttpServletRequest request) {
        log.warn(
            "Error de autenticación en {} {}",
            request.getMethod(),
            request.getRequestURI()
        );

        ErrorResponse response = ErrorResponse.builder()
                                    .timestamp(LocalDateTime.now())
                                    .status(HttpStatus.UNAUTHORIZED.value())
                                    .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                                    .message("Ocurrió un error durante el proceso de autenticación")
                                    .path(request.getRequestURI())
                                    .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAnonymousAccessException(InsufficientAuthenticationException ex,
                                                             HttpServletRequest request) {
        log.warn(
            "Acceso sin autenticación a {} {}",
            request.getMethod(),
            request.getRequestURI()
        );

        ErrorResponse response = ErrorResponse.builder()
                                    .timestamp(LocalDateTime.now())
                                    .status(HttpStatus.UNAUTHORIZED.value())
                                    .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                                    .message("No se proporcionaron credenciales de autenticación. Por favor, inicia sesión")
                                    .path(request.getRequestURI())
                                    .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccesDeniedException(AccessDeniedException ex, 
                                                                    HttpServletRequest request) {
        log.warn(
            "Acceso denegado a {} {}",
            request.getMethod(),
            request.getRequestURI()
        );

        ErrorResponse response = ErrorResponse.builder()
                                                .timestamp(LocalDateTime.now())
                                                .status(HttpStatus.FORBIDDEN.value())
                                                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                                                .message("No tienes permiso para acceder a este recurso")
                                                .path(request.getRequestURI())
                                                .build();
                                                
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
        HttpMessageNotReadableException ex,
        HttpServletRequest request) {

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("El cuerpo de la solicitud contiene datos inválidos o con un formato incorrecto")
                .path(request.getRequestURI())
                .build();

        log.debug(
            "Cuerpo de solicitud inválido en {} {}",
            request.getMethod(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("El parámetro '" + ex.getName() + "' tiene un valor inválido")
                .path(request.getRequestURI())
                .build();

        log.debug(
            "Valor inválido para parámetro '{}' en {} {}",
            ex.getName(),
            request.getMethod(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        List<String> validationErrors = ex.getConstraintViolations()
                .stream()
                .map(violation ->
                        violation.getPropertyPath() + ": " + violation.getMessage())
                .toList();

        log.debug(
            "Error de validación de parámetros en {} {}: {}",
            request.getMethod(),
            request.getRequestURI(),
            validationErrors
        );

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Error en la validación de los parámetros enviados")
                .path(request.getRequestURI())
                .details(validationErrors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        log.warn(
            "Violación de integridad de datos en {} {}",
            request.getMethod(),
            request.getRequestURI()
        );

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message("La operación no puede realizarse porque genera un conflicto con los datos existentes")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, 
                                                                    HttpServletRequest request) {
        log.error(
            "Error inesperado en {} {}",
            request.getMethod(),
            request.getRequestURI(),
            ex
        );

        ErrorResponse response = ErrorResponse.builder()
                                                .timestamp(LocalDateTime.now())
                                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                                                .message("Ocurrió un error inesperado")
                                                .path(request.getRequestURI())
                                                .build();
                                                
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private String formatError(FieldError error) {
		return  error.getField() + ": " + error.getDefaultMessage();
	}
}

