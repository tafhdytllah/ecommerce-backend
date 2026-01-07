package com.tafh.ecommerce.common.exception;

import com.tafh.ecommerce.common.response.ApiResponse;
import com.tafh.ecommerce.common.util.ResponseHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  /* =======================
   VALIDATION ERROR
   ======================= */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Object>> handleValidation(
    MethodArgumentNotValidException ex
  ) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult()
      .getFieldErrors()
      .forEach(e ->
        errors.put(e.getField(), e.getDefaultMessage())
      );

    return ResponseHelper.error(HttpStatus.BAD_REQUEST, errors);
  }

  /* =======================
     BUSINESS ERRORS
     ======================= */
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ApiResponse<Object>> handleBadRequest(
    BadRequestException ex
  ) {
    return ResponseHelper.error(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ApiResponse<Object>> handleUnauthorized(
    UnauthorizedException ex
  ) {
    return ResponseHelper.error(HttpStatus.UNAUTHORIZED, ex.getMessage());
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ApiResponse<Object>> handleForbidden(
    ForbiddenException ex
  ) {
    return ResponseHelper.error(HttpStatus.FORBIDDEN, ex.getMessage());
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiResponse<Object>> handleNotFound(
    ResourceNotFoundException ex
  ) {
    return ResponseHelper.error(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  /* =======================
     FALLBACK
     ======================= */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Object>> handleGeneric(
    Exception ex
  ) {
    return ResponseHelper.error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
  }

}
