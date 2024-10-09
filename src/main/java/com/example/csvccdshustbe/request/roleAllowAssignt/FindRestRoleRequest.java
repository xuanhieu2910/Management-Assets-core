package com.example.csvccdshustbe.request.roleAllowAssignt;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindRestRoleRequest extends RequestPageBase {

    private Integer idDepartment;
    private String codeUser;
}
