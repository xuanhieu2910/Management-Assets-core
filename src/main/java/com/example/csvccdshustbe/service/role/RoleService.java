package com.example.csvccdshustbe.service.role;

import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.exception.RoleException;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.role.CreateNewRoleRequest;
import com.example.csvccdshustbe.request.role.FindAllRoleRequest;
import com.example.csvccdshustbe.response.role.FindAllRoleResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
    Page<FindAllRoleResponse> findAllRole(FindAllRoleRequest findAllRoleRequest) throws RoleException;
    void createNewRole (CreateNewRoleRequest request) throws ValidateFiledException;
    List<Role> findRoleByIds(List<Integer> ids) throws ValidateFiledException;
    void deleteRole(Integer idRole) throws ValidateFiledException;
}
