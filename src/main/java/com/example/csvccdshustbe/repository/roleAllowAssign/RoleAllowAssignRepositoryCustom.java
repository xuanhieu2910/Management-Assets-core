package com.example.csvccdshustbe.repository.roleAllowAssign;

import com.example.csvccdshustbe.entity.RoleAllowAssign;
import com.example.csvccdshustbe.request.roleAllowAssignt.FindRestRoleRequest;
import com.example.csvccdshustbe.response.roleAllowAssign.FindAllRoleAllowAssignResponse;
import com.example.csvccdshustbe.response.roleAllowAssign.FindAllRoleAllowResponse;
import com.example.csvccdshustbe.response.roleAllowAssign.FindRestRoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleAllowAssignRepositoryCustom {

    List<FindAllRoleAllowAssignResponse> findAllRoleAllowAssignResponse();

    List<RoleAllowAssign> findAllRoleAllowAssign();

    List<FindAllRoleAllowResponse> findAllRoleAllowAssignByTitleRole(String titleRole);

    Page<FindRestRoleResponse> findRestRoleAssignResponse(Pageable pageable, FindRestRoleRequest request, Integer idRoleCurrent);

    void deleteRoleAssignByIdRole(Integer roleId);
}
