package com.example.csvccdshustbe.service.userRole;

import com.example.csvccdshustbe.dto.userRole.DepartmentUserRoleDto;
import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.entity.UserRole;
import com.example.csvccdshustbe.exception.RoleException;
import com.example.csvccdshustbe.request.userRole.AddNewRoleDepartmentUserRequest;
import com.example.csvccdshustbe.request.userRole.UpdateUserRoleRequest;
import com.example.csvccdshustbe.response.user.FindAllRolesUserResponse;

import java.util.List;

public interface UserRoleService {

    Role findRoleByUserName(String title) throws RoleException;

    UserRole saveUserRole(UserRole userRole);

    List<FindAllRolesUserResponse> findAllRolesUserByCodeUser(String codeUser);

    List<UserRole> findUserRoleByCodeUser(String codeUser);

    void saveAllUserRole(List<UserRole> userRoles);

    DepartmentUserRoleDto getDepartmentCurrentUserRoleByCodeUser (String codeUser);

    void removeUserByIdDepartmentAndIdUser(Integer idDepartment, Integer idUser);

    void updateUserRole(UpdateUserRoleRequest request);

    void deleteUserRole(Integer idUserRole);
    void createNewUserRole(AddNewRoleDepartmentUserRequest request);

    void deleteUserRoleByIdRole(Integer idRole);
}
