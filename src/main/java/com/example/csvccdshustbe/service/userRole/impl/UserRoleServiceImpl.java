package com.example.csvccdshustbe.service.userRole.impl;

import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.entity.UserRole;
import com.example.csvccdshustbe.exception.RoleException;
import com.example.csvccdshustbe.repository.role.RoleRepository;
import com.example.csvccdshustbe.repository.userRole.UserRoleRepository;
import com.example.csvccdshustbe.service.userRole.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public Role findRoleByUserName(String title) throws RoleException {
        Optional<Role> role = roleRepository.findByTitleRole(title.trim());
        if (role.isEmpty()){
            throw new RoleException("Not found role by title role: " + title);
        }
        return role.get();
    }

    @Override
    public UserRole saveUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }
}
