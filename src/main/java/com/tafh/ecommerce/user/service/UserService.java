package com.tafh.ecommerce.user.service;

import com.tafh.ecommerce.common.entity.UserRole;
import com.tafh.ecommerce.common.exception.BadRequestException;
import com.tafh.ecommerce.common.exception.UnauthorizedException;
import com.tafh.ecommerce.security.jwt.JwtService;
import com.tafh.ecommerce.user.dto.UserLoginRequest;
import com.tafh.ecommerce.user.dto.UserLoginResponse;
import com.tafh.ecommerce.user.dto.UserProfileResponse;
import com.tafh.ecommerce.user.dto.UserRegisterRequest;
import com.tafh.ecommerce.user.entity.UserEntity;
import com.tafh.ecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserLoginResponse login(UserLoginRequest request) {

        UserEntity user = userRepository.findByUsernameAndDeletedAtIsNull(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

        if (!user.getIsActive()) {
            throw new UnauthorizedException("Invalid username or password");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getId(), user.getRole());

        return UserLoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .build();
    }

    public UserProfileResponse register(UserRegisterRequest request) {

        if (userRepository.existsByUsernameAndDeletedAtIsNull(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }

        if (userRepository.existsByEmailAndDeletedAtIsNull(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        UserEntity user = new UserEntity(
                UUID.randomUUID().toString(),
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                UserRole.USER.name()
        );

        UserEntity savedUser = userRepository.save(user);

        return UserProfileResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .isActive(savedUser.getIsActive())
                .build();
    }
}
