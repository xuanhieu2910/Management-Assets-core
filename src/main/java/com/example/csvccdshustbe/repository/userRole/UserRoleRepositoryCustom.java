package com.example.csvccdshustbe.repository.userRole;

import com.example.csvccdshustbe.dto.userRole.DepartmentUserRoleDto;
import com.example.csvccdshustbe.entity.UserRole;
import com.example.csvccdshustbe.response.user.FindAllRolesUserResponse;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepositoryCustom {
    List<FindAllRolesUserResponse> findAllRolesUserByCodeUser(String codeUser);

    List<UserRole> findUserRoleByCodeUser(String codeUser);

    DepartmentUserRoleDto getDepartmentCurrentUserRoleDtoByCodeUser (String codeUser);
    List<UserRole> findUserRoleByIdDepartmentAndIdUser(Integer idDepartment, Integer idUser);

    Optional<UserRole> findUserRoleByIdUserRole(Integer idUserRole);

    List<UserRole> findUserRoleByIdRole(Integer idRole);
    List<UserRole> findUserRoleByNameRoleAndIdDepartment(String nameRole, Integer department);
}
