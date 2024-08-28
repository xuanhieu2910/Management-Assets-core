package com.example.csvccdshustbe.response.user;

import com.example.csvccdshustbe.response.role.RoleResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class UserAuthenticationResponse {

    @JsonProperty("codeUser")
    private String codeUser;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("roles")
    private List<RoleResponse> roles;
}
