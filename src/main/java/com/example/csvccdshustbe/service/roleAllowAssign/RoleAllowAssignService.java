package com.example.csvccdshustbe.service.roleAllowAssign;

import com.example.csvccdshustbe.entity.RoleAllowAssign;
import com.example.csvccdshustbe.request.roleAllowAssignt.UpdateRoleAllowAssignRequest;
import com.example.csvccdshustbe.response.roleAllowAssign.FindAllRoleAllowAssignResponse;
import com.example.csvccdshustbe.response.roleAllowAssign.FindAllRoleAllowResponse;

import java.util.List;

public interface RoleAllowAssignService {

    List<FindAllRoleAllowAssignResponse> findAllRoleAllowAssign();

    void updateRoleAllowAssign(List<UpdateRoleAllowAssignRequest> request);

    void saveAllRoleAllowAssign(List<RoleAllowAssign> roleAllowAssigns);

    List<FindAllRoleAllowResponse> findAllRoleAllowAssignByTitleRole();
}
