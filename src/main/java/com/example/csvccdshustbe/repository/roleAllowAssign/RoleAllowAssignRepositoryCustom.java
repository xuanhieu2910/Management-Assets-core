package com.example.csvccdshustbe.repository.roleAllowAssign;

import com.example.csvccdshustbe.entity.RoleAllowAssign;
import com.example.csvccdshustbe.response.roleAllowAssign.FindAllRoleAllowAssignResponse;
import com.example.csvccdshustbe.response.roleAllowAssign.FindAllRoleAllowResponse;

import java.util.List;

public interface RoleAllowAssignRepositoryCustom {

    List<FindAllRoleAllowAssignResponse> findAllRoleAllowAssignResponse();

    List<RoleAllowAssign> findAllRoleAllowAssign();

    List<FindAllRoleAllowResponse> findAllRoleAllowAssignByTitleRole(String titleRole);

}
