package com.example.csvccdshustbe.request.roleAllowAssignt;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateRoleAllowDataRequest {

    private List<UpdateRoleAllowAssignRequest> data;
}
