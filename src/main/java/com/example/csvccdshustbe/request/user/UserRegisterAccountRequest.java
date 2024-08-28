package com.example.csvccdshustbe.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterAccountRequest {

    @JsonProperty(namespace = "username",required = true)
    private String username;
    @JsonProperty(namespace = "password", required = true)
    private String password;
}
