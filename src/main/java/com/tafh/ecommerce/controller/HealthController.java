package com.tafh.ecommerce.controller;

import com.tafh.ecommerce.common.util.ResponseHelper;
import com.tafh.ecommerce.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

  @GetMapping
  public ResponseEntity<ApiResponse<String>> health() {
    return ResponseHelper.ok("Ok");
  }

}
