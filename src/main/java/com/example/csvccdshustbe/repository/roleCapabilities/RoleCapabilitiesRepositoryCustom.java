package com.example.csvccdshustbe.repository.roleCapabilities;

import com.example.csvccdshustbe.entity.RoleCapabilities;

import java.util.List;

public interface RoleCapabilitiesRepositoryCustom {

    List<RoleCapabilities> findRoleCapabilitiesByIdRole(Integer idRole);

}
