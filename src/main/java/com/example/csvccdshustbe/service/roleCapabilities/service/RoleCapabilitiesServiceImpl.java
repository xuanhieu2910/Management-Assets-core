package com.example.csvccdshustbe.service.roleCapabilities.service;

import com.example.csvccdshustbe.entity.RoleCapabilities;
import com.example.csvccdshustbe.repository.roleCapabilities.RoleCapabilitiesRepository;
import com.example.csvccdshustbe.service.roleCapabilities.RoleCapabilitiesService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class RoleCapabilitiesServiceImpl implements RoleCapabilitiesService {


    @Autowired
    RoleCapabilitiesRepository roleCapabilitiesRepository;


    @Override
    public void saveAllRoleCapabilities(List<RoleCapabilities> roleCapabilitiesList) {
        roleCapabilitiesRepository.saveAll(roleCapabilitiesList);
    }

    @Modifying
    @Transactional
    @Override
    public void deleteRoleCapabilitiesByIdRole(Integer idRole) {
        List<RoleCapabilities> roleCapabilitiesList = roleCapabilitiesRepository.findRoleCapabilitiesByIdRole(idRole);
        if (!CollectionUtils.isEmpty(roleCapabilitiesList)){
            roleCapabilitiesRepository.deleteAll(roleCapabilitiesList);
        }
    }
}
