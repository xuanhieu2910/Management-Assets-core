package com.example.csvccdshustbe.response.roleAllowAssign;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllRoleAllowAssignResponse {

    @JsonProperty("id_role_allow_assign")
    private Integer idRoleAllowAssign;
    @JsonProperty("id_role_source")
    private Integer idRoleSource;
    @JsonProperty("name_role_source")
    private String nameRoleSource;
    @JsonProperty("name_role_allow")
    private String nameRoleAllow;
    @JsonProperty("id_role_allow")
    private Integer idRoleAllow;
}
