package com.example.csvccdshustbe.service.role.impl;

import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.entity.RoleAllowAssign;
import com.example.csvccdshustbe.entity.RoleCapabilities;
import com.example.csvccdshustbe.exception.RoleException;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.role.RoleRepository;
import com.example.csvccdshustbe.request.role.CreateNewRoleRequest;
import com.example.csvccdshustbe.request.roleCapabilities.CreateNewRoleCapabilitiesRequest;
import com.example.csvccdshustbe.response.role.FindAllRoleResponse;
import com.example.csvccdshustbe.service.role.RoleService;
import com.example.csvccdshustbe.service.roleAllowAssign.RoleAllowAssignService;
import com.example.csvccdshustbe.service.roleCapabilities.RoleCapabilitiesService;
import com.example.csvccdshustbe.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RoleAllowAssignService roleAllowAssignService;
    @Autowired
    RoleCapabilitiesService roleCapabilitiesService;



    @Override
    public List<FindAllRoleResponse> findAllRole() throws RoleException {
        List<Role> roles = roleRepository.findAllRole();
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

    @Override
    public void createNewRole(CreateNewRoleRequest request) throws ValidateFiledException {
        validateCreateNewRole(request);
        Role role = constructionRole(request);
        roleRepository.save(role);
        createRoleCapabilities(role, request);
        createRoleAllowAssign(role);

    }

    private void createRoleCapabilities(Role role, CreateNewRoleRequest request) {
        List<RoleCapabilities> roleCapabilitiesList = new ArrayList<>();
        String currentTime = String.valueOf(new Date().getTime());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (CreateNewRoleCapabilitiesRequest roleCapabilitiesRequest : request.getCapabilities()){
            RoleCapabilities capabilities = new RoleCapabilities();
            capabilities.setIdRole(role.getIdRole());
            capabilities.setIdCapabilities(roleCapabilitiesRequest.getIdCapability());
            capabilities.setPermission(roleCapabilitiesRequest.getPermission());
            capabilities.setTimeCreated(currentTime);
            capabilities.setTimeModified(currentTime);
            capabilities.setIdUserModified(csvcUser.getIdUser());
            roleCapabilitiesList.add(capabilities);
        }
        roleCapabilitiesService.saveAllRoleCapabilities(roleCapabilitiesList);
    }

    private void createRoleAllowAssign(Role currentRole) {
        List<Role> restRole = roleRepository.findRestRoleWithoutCurrentRole(currentRole);
        createSourceAllowAssign(restRole, currentRole.getIdRole());
        createDestinationRoleAllowAssign(restRole, currentRole.getIdRole());
    }

    private void createDestinationRoleAllowAssign(List<Role> restRole, Integer idRole) {
        List<RoleAllowAssign>  destinationRoleAllowAssign = new ArrayList<>();
        for (Role role : restRole){
            RoleAllowAssign allowAssign = new RoleAllowAssign();
            allowAssign.setIdRole(idRole);
            allowAssign.setStatus(Constants.ROLE_ALLOW_ASSIGN_UN_STATUS);
            allowAssign.setAllowAssign(role.getIdRole());
            destinationRoleAllowAssign.add(allowAssign);
        }
        roleAllowAssignService.saveAllRoleAllowAssign(destinationRoleAllowAssign);
    }

    private void createSourceAllowAssign(List<Role> restRole, Integer idRole) {
        List<RoleAllowAssign>  sourceAllowAssign = new ArrayList<>();
        for (Role role : restRole){
            RoleAllowAssign allowAssign = new RoleAllowAssign();
            allowAssign.setIdRole(role.getIdRole());
            allowAssign.setStatus(Constants.ROLE_ALLOW_ASSIGN_UN_STATUS);
            allowAssign.setAllowAssign(idRole);
            sourceAllowAssign.add(allowAssign);
        }
        roleAllowAssignService.saveAllRoleAllowAssign(sourceAllowAssign);
    }

    private Role constructionRole(CreateNewRoleRequest request) {
        Role role = new Role();
        role.setTitle(request.getTitle());
        role.setStatus(request.getStatus());
        role.setContent(request.getContent());
        role.setShortName(request.getShortName());
        role.setDescription(request.getDescription());
        String timeCurrent = String.valueOf(new Date().getTime());
        role.setTimeCreated(timeCurrent);
        role.setTimeModified(timeCurrent);
        return role;
    }

    private void validateCreateNewRole(CreateNewRoleRequest request) throws ValidateFiledException {
        if (roleRepository.findRoleByTitleOrShortName(request.getTitle(), request.getShortName()).isEmpty()) {
            throw new ValidateFiledException("Exits role by title or short name, please choice another role!");
        }
        if (!request.getStatus().equals(Constants.ROLE_STATUS) ||
            !request.getStatus().equals(Constants.ROLE_UN_STATUS))   {
            throw new ValidateFiledException("Validate data!");
        }
    }
}
