package com.example.csvccdshustbe.repository.role;

import com.example.csvccdshustbe.entity.Role;

import java.util.Optional;

public interface RoleRepositoryCustom {

    Optional<Role> findByTitleRole(String titleRole);
}
