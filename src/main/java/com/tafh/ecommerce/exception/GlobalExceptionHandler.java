package com.tafh.ecommerce.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  /* =======================
     400 - BAD REQUEST
     ======================= */
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ApiErrorResponse> handleBadRequest(
    BadRequestException ex,
    HttpServletRequest request
  ) {
    return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null, request);
  }

  /* =======================
     401 - UNAUTHORIZED
     ======================= */
  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ApiErrorResponse> handleUnauthorized(
    UnauthorizedException ex,
    HttpServletRequest request
  ) {
    return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), null, request);
  }

  /* =======================
     403 - FORBIDDEN
     ======================= */
  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ApiErrorResponse> handleForbidden(
    ForbiddenException ex,
    HttpServletRequest request
  ) {
    return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage(), null, request);
  }

  /* =======================
     404 - NOT FOUND
     ======================= */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleNotFound(
    ResourceNotFoundException ex,
    HttpServletRequest request
  ) {
    return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null, request);
  }

  /* =======================
     409 - CONFLICT
     ======================= */
  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<ApiErrorResponse> handleConflict(
    ConflictException ex,
    HttpServletRequest request
  ) {
    return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), null, request);
  }

  /* =======================
     VALIDATION ERROR (@Valid)
     ======================= */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponse> handleValidation(
    MethodArgumentNotValidException ex,
    HttpServletRequest request
  ) {
    Map<String, Object> details = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      details.put(error.getField(), error.getDefaultMessage());
    }

    return buildResponse(HttpStatus.BAD_REQUEST, "Validation Failed", details, request);
  }

  /* =======================
     500 - INTERNAL SERVER ERROR
     ======================= */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleGeneric(
    Exception ex,
    HttpServletRequest request
  ) {
    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected Error occurred", null, request);
  }

  /* =======================
     COMMON BUILDERS
     ======================= */
  private ResponseEntity<ApiErrorResponse> buildResponse(
    HttpStatus status,
    String message,
    Map<String, Object> details,
    HttpServletRequest request
  ) {
    ApiErrorResponse response = ApiErrorResponse.builder()
      .code(status.value())
      .error(status.getReasonPhrase())
      .message(message)
      .details(details)
      .path(request.getRequestURI())
      .timestamp(LocalDateTime.now())
      .build();

    return ResponseEntity.status(status).body(response);
  }
}
