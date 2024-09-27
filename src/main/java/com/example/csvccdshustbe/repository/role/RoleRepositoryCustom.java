package com.example.csvccdshustbe.repository.role;

import com.example.csvccdshustbe.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepositoryCustom {

    List<Role> findAllRoleDefault();
    Optional<Role> findByTitleRole(String titleRole);
}
