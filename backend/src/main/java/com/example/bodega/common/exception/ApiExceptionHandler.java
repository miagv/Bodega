package com.example.bodega.common.exception;

import com.example.bodega.common.response.ApiErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(NotFoundException.class) ResponseEntity<ApiErrorResponse> notFound(NotFoundException e) { return response(HttpStatus.NOT_FOUND, e.getMessage(), Map.of()); }
    @ExceptionHandler({ConflictException.class, DataIntegrityViolationException.class}) ResponseEntity<ApiErrorResponse> conflict(Exception e) { return response(HttpStatus.CONFLICT, "El recurso entra en conflicto con datos existentes.", Map.of()); }
    @ExceptionHandler(BusinessException.class) ResponseEntity<ApiErrorResponse> business(BusinessException e) { return response(HttpStatus.BAD_REQUEST, e.getMessage(), Map.of()); }
    @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ApiErrorResponse> validation(MethodArgumentNotValidException e) {
        Map<String,String> fields = e.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(f -> f.getField(), f -> f.getDefaultMessage(), (a,b) -> a));
        return response(HttpStatus.BAD_REQUEST, "Datos de solicitud invalidos.", fields);
    }
    @ExceptionHandler(AccessDeniedException.class) ResponseEntity<ApiErrorResponse> denied(AccessDeniedException e) { return response(HttpStatus.FORBIDDEN, "No tiene permisos para esta operacion.", Map.of()); }
    @ExceptionHandler(Exception.class) ResponseEntity<ApiErrorResponse> unexpected(Exception e) { return response(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error interno.", Map.of()); }
    private ResponseEntity<ApiErrorResponse> response(HttpStatus status, String message, Map<String,String> fields) { return ResponseEntity.status(status).body(new ApiErrorResponse(Instant.now(), status.value(), status.getReasonPhrase(), message, fields)); }
}
