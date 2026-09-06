package com.example.csvccdshustbe.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllUserRoleDepartmentResponse {

    @JsonProperty("id_user_role")
    private Integer idUserRole;
    @JsonProperty("code_department")
    private String codeDepartment;
    @JsonProperty("id_department")
    private Integer idDepartment;
    @JsonProperty("id_role")
    private Integer idRole;
    @JsonProperty("name_department")
    private String nameDepartment;
    @JsonProperty("name_role")
    private String nameRole;

}
