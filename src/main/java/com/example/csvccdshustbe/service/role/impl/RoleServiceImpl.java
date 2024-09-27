package com.example.csvccdshustbe.service.role.impl;

import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.exception.RoleException;
import com.example.csvccdshustbe.repository.role.RoleRepository;
import com.example.csvccdshustbe.response.role.FindAllRoleResponse;
import com.example.csvccdshustbe.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;


    @Override
    public List<FindAllRoleResponse> findAllRole() throws RoleException {
        List<Role> roles = roleRepository.findAllRoleDefault();
        if (CollectionUtils.isEmpty(roles)){
            throw new RoleException("Don't exits role default!");
        }
        return convertToFindAllRoleResponse(roles);
    }

    private List<FindAllRoleResponse> convertToFindAllRoleResponse(List<Role> roles) {
        List<FindAllRoleResponse> responses = new ArrayList<>();
        for (Role role: roles){
            FindAllRoleResponse response = new FindAllRoleResponse();
            response.setIdRole(role.getIdRole());
            response.setTitleRole(role.getTitle());
            response.setContentName(response.getContentName());
            response.setDescription(response.getDescription());
            responses.add(response);
        }
        return responses;
    }

    @Override
    public Role findRoleByTitle(String titleRole) {

        return null;
    }
}
