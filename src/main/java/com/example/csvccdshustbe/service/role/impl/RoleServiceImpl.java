package com.example.csvccdshustbe.service.role.impl;

import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.repository.role.RoleRepository;
import com.example.csvccdshustbe.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;


    @Override
    public Role findRoleByTitle(String titleRole) {

        return null;
    }
}
