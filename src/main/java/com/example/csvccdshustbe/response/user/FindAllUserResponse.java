package com.example.csvccdshustbe.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllUserResponse {

    @JsonProperty("code_user")
    private String codeUser;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("id_department")
    private Integer idDepartment;
    @JsonProperty("name_department")
    private String nameDepartment;
    @JsonProperty("roles")
    private String roles;
    @JsonProperty("id_user_role")
    private Integer idUserRole;
}
