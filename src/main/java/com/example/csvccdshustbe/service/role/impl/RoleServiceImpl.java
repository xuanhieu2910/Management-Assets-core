package com.example.csvccdshustbe.service.role.impl;

import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.entity.RoleAllowAssign;
import com.example.csvccdshustbe.entity.RoleCapabilities;
import com.example.csvccdshustbe.exception.RoleException;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.role.RoleRepository;
import com.example.csvccdshustbe.request.role.CreateNewRoleRequest;
import com.example.csvccdshustbe.request.role.FindAllRoleRequest;
import com.example.csvccdshustbe.request.role.UpdateRoleRequest;
import com.example.csvccdshustbe.request.roleCapabilities.CreateNewRoleCapabilitiesRequest;
import com.example.csvccdshustbe.request.roleCapabilities.UpdateRoleCapabilitiesRequest;
import com.example.csvccdshustbe.response.role.FindAllRoleResponse;
import com.example.csvccdshustbe.response.role.FindDetailsRoleCapabilitiesResponse;
import com.example.csvccdshustbe.service.role.RoleService;
import com.example.csvccdshustbe.service.roleAllowAssign.RoleAllowAssignService;
import com.example.csvccdshustbe.service.roleCapabilities.RoleCapabilitiesService;
import com.example.csvccdshustbe.service.userRole.UserRoleService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RoleAllowAssignService roleAllowAssignService;
    @Autowired
    RoleCapabilitiesService roleCapabilitiesService;
    @Autowired
    UserRoleService userRoleService;



    @Override
    public Page<FindAllRoleResponse> findAllRole(FindAllRoleRequest findAllRoleRequest) throws RoleException {
        Pageable pageable = PageUtils.buildPage(findAllRoleRequest.getPage(), findAllRoleRequest.getSize());
        Page<Role> roles = roleRepository.findAllRole(pageable, findAllRoleRequest);
        return new PageImpl<>(convertToFindAllRoleResponse(roles.getContent()),pageable, roles.getTotalElements());
    }

    private List<FindAllRoleResponse> convertToFindAllRoleResponse(List<Role> roles) {
        List<FindAllRoleResponse> responses = new ArrayList<>();
        for (Role role: roles){
            FindAllRoleResponse response = new FindAllRoleResponse();
            response.setIdRole(role.getIdRole());
            response.setTitleRole(role.getTitle());
            response.setContentName(response.getContentName());
            response.setDescription(response.getDescription());
            response.setStatus(response.getStatus());
            responses.add(response);
        }
        return responses;
    }

    @Override
    public void createNewRole(CreateNewRoleRequest request) throws ValidateFiledException {
        validateCreateNewRole(request);
        Role role = constructionRole(request);
        roleRepository.save(role);
        createRoleCapabilities(role, request);
        createRoleAllowAssign(role);

    }

    @Override
    public List<Role> findRoleByIds(List<Integer> ids) throws ValidateFiledException {
        List<Role> roles = roleRepository.findRolesByIds(ids);
        if (roles.size() != ids.size()) {
            throw new ValidateFiledException("Don't exits role by ids");
        }
        return roles;
    }

    @Override
    public void deleteRole(Integer idRole) throws ValidateFiledException {
        Optional<Role> role = roleRepository.findByIdRole(idRole);
        if (role.isEmpty()) {
            throw new NotFoundException("Don't exits role by id role!");
        } else {
            if (role.get().getStatus().equals(Constants.ROLE_DEFAULT)){
                throw new ValidateFiledException("Can't delete role default!");
            }
        }
        roleRepository.delete(role.get());
        roleCapabilitiesService.deleteRoleCapabilitiesByIdRole(idRole);
        roleAllowAssignService.deleteRoleAssignByIdRole(idRole);
        userRoleService.deleteUserRoleByIdRole(idRole);
    }

    @Override
    public FindDetailsRoleCapabilitiesResponse findDetailsRoleCapabilitiesByIdRole(Integer idRole) {
        return roleRepository.findDetailsRoleCapabilitiesByIdRole(idRole);
    }

    @Override
    public void updateRole(UpdateRoleRequest request) throws ValidateFiledException {
        Optional<Role> role = roleRepository.findByIdRole(request.getIdRole());
        if (role.isEmpty()) {
            throw new NotFoundException("Don't exits role by id role!");
        }
        validateDataUpdateRole(request);
        List<RoleCapabilities> roleCapabilitiesList =
                roleCapabilitiesService.findAllRoleCapabilitiesByIdRole(request.getIdRole());
        if (roleCapabilitiesList.size() != request.getRoleCapabilities().size()){
            throw new ValidateFiledException("Validate data role capabilities!");
        }
        updateDataRole(request, role.get());
        updateDataRoleCapabilities(roleCapabilitiesList, request.getRoleCapabilities());
    }

    private void updateDataRoleCapabilities(List<RoleCapabilities> roleCapabilitiesList,
                                            List<UpdateRoleCapabilitiesRequest> roleCapabilitiesRequest) {
        String timeCurrent = String.valueOf(new Date().getTime());
        Integer idUserModified = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdUser();
        for (RoleCapabilities roleCapabilities : roleCapabilitiesList){
            for (UpdateRoleCapabilitiesRequest updateRoleCapabilitiesRequest : roleCapabilitiesRequest){
                if (updateRoleCapabilitiesRequest.getIdRoleCapabilities().equals(roleCapabilities.getIdRoleCapabilities())){
                    roleCapabilities.setPermission(updateRoleCapabilitiesRequest.getStatus());
                    roleCapabilities.setTimeModified(timeCurrent);
                    roleCapabilities.setIdUserModified(idUserModified);
                    break;
                }
            }
        }
        roleCapabilitiesService.saveAllRoleCapabilities(roleCapabilitiesList);
    }

    private void updateDataRole(UpdateRoleRequest request, Role role) {
        role.setShortName(request.getNameRole());
        role.setDescription(request.getDescription());
        role.setStatus(request.getStatus());
        role.setTimeModified(String.valueOf(new Date().getTime()));
        roleRepository.save(role);
    }

    private void validateDataUpdateRole(UpdateRoleRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getNameRole())){
            throw new ValidateFiledException("Validate name role!");
        }
        Optional<Role> role = roleRepository.findByShortNameRole(request.getNameRole());
        if (role.isPresent()){
            throw new ValidateFiledException("Validate name role!");
        }
        if (!request.getStatus().equals(Constants.ROLE_STATUS) ||
            !request.getStatus().equals(Constants.ROLE_UN_STATUS)) {
            throw new ValidateFiledException("Validate status role!");
        }
    }

    private void createRoleCapabilities(Role role, CreateNewRoleRequest request) {
        List<RoleCapabilities> roleCapabilitiesList = new ArrayList<>();
        String currentTime = String.valueOf(new Date().getTime());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (CreateNewRoleCapabilitiesRequest roleCapabilitiesRequest : request.getCapabilities()){
            RoleCapabilities capabilities = new RoleCapabilities();
            capabilities.setIdRole(role.getIdRole());
            capabilities.setIdCapabilities(roleCapabilitiesRequest.getIdCapability());
            capabilities.setPermission(roleCapabilitiesRequest.getStatus());
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
