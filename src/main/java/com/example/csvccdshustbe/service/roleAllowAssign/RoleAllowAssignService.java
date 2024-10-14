package com.example.csvccdshustbe.service.roleAllowAssign;

import com.example.csvccdshustbe.entity.RoleAllowAssign;
import com.example.csvccdshustbe.request.roleAllowAssignt.FindRestRoleRequest;
import com.example.csvccdshustbe.request.roleAllowAssignt.UpdateRoleAllowAssignRequest;
import com.example.csvccdshustbe.request.roleAllowAssignt.UpdateRoleAllowDataRequest;
import com.example.csvccdshustbe.response.roleAllowAssign.FindAllRoleAllowAssignResponse;
import com.example.csvccdshustbe.response.roleAllowAssign.FindAllRoleAllowResponse;
import com.example.csvccdshustbe.response.roleAllowAssign.FindRestRoleResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleAllowAssignService {

    List<FindAllRoleAllowAssignResponse> findAllRoleAllowAssign();

    void updateRoleAllowAssign(UpdateRoleAllowDataRequest request);

    void saveAllRoleAllowAssign(List<RoleAllowAssign> roleAllowAssigns);

    List<FindAllRoleAllowResponse> findAllRoleAllowAssignByTitleRole();

    Page<FindRestRoleResponse> findRestRoleResponseAssign(FindRestRoleRequest request);

    void deleteRoleAssignByIdRole(Integer roleId);
}
