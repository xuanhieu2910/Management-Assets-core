package com.example.csvccdshustbe.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindDetailsUserRequest {

    @NotNull
    @JsonProperty(namespace = "code-user")
    private String codeUser;
    private List<Integer> idsDepartment;
}
