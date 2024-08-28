package com.example.csvccdshustbe.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationRequest {

    @JsonProperty("username")
    private String userName;
    @JsonProperty("password")
    private String password;
}
