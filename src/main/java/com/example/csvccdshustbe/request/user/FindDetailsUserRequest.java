package com.example.csvccdshustbe.request.user;

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
    private String codeUser;
    private List<Integer> idsDepartment;
}
