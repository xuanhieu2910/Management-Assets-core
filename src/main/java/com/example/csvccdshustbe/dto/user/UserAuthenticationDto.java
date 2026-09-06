package com.example.csvccdshustbe.dto.user;

import com.example.csvccdshustbe.response.role.RoleResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserAuthenticationDto {
    private String codeUser;
    private String userName;
    private List<RoleResponse> roles;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Integer isActived;
    private String fullName;
}
