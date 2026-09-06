package com.example.csvccdshustbe.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoveUserDepartmentRequest {

    private Integer idDepartment;
    private String codeUser;
}
