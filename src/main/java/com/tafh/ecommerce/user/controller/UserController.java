package com.tafh.ecommerce.user.controller;

import com.tafh.ecommerce.common.response.ApiResponse;
import com.tafh.ecommerce.common.util.ResponseHelper;
import com.tafh.ecommerce.user.dto.UserLoginRequest;
import com.tafh.ecommerce.user.dto.UserLoginResponse;
import com.tafh.ecommerce.user.dto.UserProfileResponse;
import com.tafh.ecommerce.user.dto.UserRegisterRequest;
import com.tafh.ecommerce.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserLoginResponse>> login(
            @Valid @RequestBody UserLoginRequest request) {

        UserLoginResponse response = userService.login(request);

        return ResponseHelper.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserProfileResponse>> register(
            @Valid @RequestBody UserRegisterRequest request
    ) {
        log.info("REQUEST : {}", request);
        return ResponseHelper.created(userService.register(request));
    }
}
