package com.example.csvccdshustbe.service.userRole.impl;

import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.entity.UserRole;
import com.example.csvccdshustbe.exception.RoleException;
import com.example.csvccdshustbe.repository.role.RoleRepository;
import com.example.csvccdshustbe.repository.userRole.UserRoleRepository;
import com.example.csvccdshustbe.response.user.FindAllRolesUserResponse;
import com.example.csvccdshustbe.service.userRole.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;

import javax.management.Query;
import java.util.List;
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

    @Override
    public List<FindAllRolesUserResponse> findAllRolesUserByCodeUser(String codeUser) {
        return userRoleRepository.findAllRolesUserByCodeUser(codeUser);
    }

    @Override
    public List<UserRole> findUserRoleByCodeUser(String codeUser) {
        List<UserRole> userRoles = userRoleRepository.findUserRoleByCodeUser(codeUser);
        if (CollectionUtils.isEmpty(userRoles)){
            throw new NotFoundException("Don't exits user roles by code user");
        }
        return userRoles;
    }

    @Override
    public void saveAllUserRole(List<UserRole> userRoles) {
        userRoleRepository.saveAll(userRoles);
    }
}
