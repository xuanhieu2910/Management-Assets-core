package com.example.csvccdshustbe.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllUserUsedResponse {


    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("code_user")
    private String coderUser;
    @JsonProperty("full_name")
    private String fullName;
}
