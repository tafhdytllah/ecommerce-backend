package com.tafh.ecommerce.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileResponse {

    private String id;

    private String username;

    private String email;

    private String role;

    private boolean isActive;

}
