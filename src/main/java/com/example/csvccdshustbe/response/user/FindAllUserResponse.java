package com.example.csvccdshustbe.response.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindAllUserResponse {

    private String codeUser;
    private String userName;
    private String fullName;
    private Integer idDepartment;
    private String nameDepartment;
    private String roles;
}
