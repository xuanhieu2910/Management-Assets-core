package com.example.csvccdshustbe.request.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssignRoleUserRequest {

    private List<String> users;
    private List<AssignRoleDetailsRequest> roleAssignDetails;

}
