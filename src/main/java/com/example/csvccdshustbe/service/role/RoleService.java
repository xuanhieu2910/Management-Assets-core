package com.example.csvccdshustbe.service.role;

import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.exception.RoleException;
import com.example.csvccdshustbe.response.role.FindAllRoleResponse;

import java.util.List;

public interface RoleService {
    List<FindAllRoleResponse> findAllRole() throws RoleException;
    Role findRoleByTitle(String titleRole);
}
