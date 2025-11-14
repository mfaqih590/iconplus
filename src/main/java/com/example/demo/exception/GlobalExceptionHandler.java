package com.example.demo.exception;

import com.example.demo.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response> handleRuntime(RuntimeException ex) {
        Response res = new Response(ex.getMessage(), false, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception ex) {
        Response res = new Response("Internal Server Error", false, null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response> handleAccessDenied(AccessDeniedException ex) {
        Response res = new Response("Forbidden: Akses ditolak", false, null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
    }
}
