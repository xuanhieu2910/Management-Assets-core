package com.example.csvccdshustbe.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindDetailsUserResponse {

    @JsonProperty("path_avatar")
    private String pathAvatar;
    @JsonProperty("code_user")
    private String codeUser;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("role_department")
    private List<FindAllUserRoleDepartmentResponse> userRoleDepartment;
}
