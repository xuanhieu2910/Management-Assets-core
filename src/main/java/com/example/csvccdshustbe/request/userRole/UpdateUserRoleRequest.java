package com.example.csvccdshustbe.request.userRole;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserRoleRequest {

    private Integer idUserRole;
    private Integer idDepartment;
    private Integer idRole;
}
