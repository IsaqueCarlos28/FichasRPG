package com.senac.tsi.FichasRPG.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex){

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 500);
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());

        return ResponseEntity.status(500).body(body);
    }

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleTagNotFound(TagNotFoundException ex){
        Map<String,Object> body = new HashMap<>();
        body.put("timeStamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "NotFound");
        body.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(RpgValidationException.class)
    public ResponseEntity<Map<String,Object>> handleBadInput(RpgValidationException ex){
        Map<String,Object> body = new HashMap<>();
        body.put("timeStamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad request");
        body.put("message", ex.getMessage());
        body.put("process",ex.getProcesso());
        body.put("wrong parameter",ex.getParametroErrado());
        body.put("expected parameter", ex.getParametroEsperado());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(RpgAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyExists(RpgAlreadyExistsException ex){

        Map<String, Object> body = new HashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());
        body.put("elemento", ex.getElemento());

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex){

        Map<String, Object> body = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("errors", errors);

        return ResponseEntity.badRequest().body(body);
    }


}
