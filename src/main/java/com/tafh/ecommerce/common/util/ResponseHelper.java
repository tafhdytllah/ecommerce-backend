package com.tafh.ecommerce.common.util;

import com.tafh.ecommerce.common.response.ApiResponse;
import com.tafh.ecommerce.common.response.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class ResponseHelper {

  public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
    return ResponseEntity.ok(
      ApiResponse.<T>builder()
        .data(data)
        .build());

  }

  public static <T> ResponseEntity<ApiResponse<T>> ok(T data, PagingResponse paging) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ApiResponse.<T>builder()
        .data(data)
        .paging(paging)
        .build());
  }

  public static ResponseEntity<ApiResponse<String>> created() {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(ApiResponse.<String>builder()
        .data("Ok")
        .build());
  }

  public static ResponseEntity<ApiResponse<String>> noContent() {
    return ResponseEntity
      .status(HttpStatus.NO_CONTENT)
      .body(ApiResponse.<String>builder()
        .data("Ok")
        .build());
  }



  public static ResponseEntity<ApiResponse<Object>> error(
    HttpStatus status,
    Object errors
  ) {
    return ResponseEntity.status(status)
      .body(ApiResponse.builder()
        .errors(errors)
        .build());
  }

}
