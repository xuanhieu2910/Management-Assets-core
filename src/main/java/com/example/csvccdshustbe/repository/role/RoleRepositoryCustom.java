package com.example.csvccdshustbe.repository.role;

import com.example.csvccdshustbe.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepositoryCustom {

    List<Role> findAllRole();
    Optional<Role> findByTitleRole(String titleRole);

    Optional<Role> findRoleByTitleOrShortName(String title, String shortName);

    List<Role> findRestRoleWithoutCurrentRole(Role role);
}
