package com.example.csvccdshustbe.service.roleCapabilities.service;

import com.example.csvccdshustbe.entity.RoleCapabilities;
import com.example.csvccdshustbe.repository.roleCapabilities.RoleCapabilitiesRepository;
import com.example.csvccdshustbe.service.roleCapabilities.RoleCapabilitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleCapabilitiesServiceImpl implements RoleCapabilitiesService {


    @Autowired
    RoleCapabilitiesRepository roleCapabilitiesRepository;


    @Override
    public void saveAllRoleCapabilities(List<RoleCapabilities> roleCapabilitiesList) {
        roleCapabilitiesRepository.saveAll(roleCapabilitiesList);
    }
}
