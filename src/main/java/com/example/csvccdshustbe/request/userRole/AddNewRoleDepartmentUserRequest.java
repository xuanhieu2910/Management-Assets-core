package com.example.csvccdshustbe.request.userRole;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NotNull
public class AddNewRoleDepartmentUserRequest {

    private String codeUser;
    private List<AddNewUserRoleDetailsRequest> roleDepartment;

}
