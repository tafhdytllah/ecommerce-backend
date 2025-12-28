package com.tafh.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiErrorResponse {
  private int code;
  private String error;
  private String message;
  private Map<String, Object> details;
  private String path;
  private LocalDateTime timestamp;
}


