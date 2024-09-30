package com.example.csvccdshustbe.request.roleAllowAssignt;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateRoleAllowAssignRequest {


    private Integer idSourceRoleAssign;
    private Integer nameSourceRoleAssign;
    private List<ListDestinationRoleAssignRequest> destinationRoleAssign;
}
