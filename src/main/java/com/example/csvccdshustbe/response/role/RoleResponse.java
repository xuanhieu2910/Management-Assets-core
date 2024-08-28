package com.example.csvccdshustbe.response.role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoleResponse {

    private String role;
    private List<String> privileges;
}
