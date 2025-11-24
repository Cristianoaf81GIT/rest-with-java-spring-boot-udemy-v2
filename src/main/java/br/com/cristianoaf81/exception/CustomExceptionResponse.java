package br.com.cristianoaf81.exception;

import java.time.LocalDateTime;

public record CustomExceptionResponse(LocalDateTime date, String message, String details) {}
