package com.example.csvccdshustbe.service.roleCapabilities;

import com.example.csvccdshustbe.entity.RoleCapabilities;

import java.util.List;

public interface RoleCapabilitiesService {

    void saveAllRoleCapabilities(List<RoleCapabilities> roleCapabilitiesList);

    void deleteRoleCapabilitiesByIdRole(Integer idRole);
}
