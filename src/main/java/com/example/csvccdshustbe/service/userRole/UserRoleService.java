package com.example.csvccdshustbe.service.userRole;

import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.entity.UserRole;
import com.example.csvccdshustbe.exception.RoleException;

public interface UserRoleService {

    Role findRoleByUserName(String title) throws RoleException;

    UserRole saveUserRole(UserRole userRole);

}
