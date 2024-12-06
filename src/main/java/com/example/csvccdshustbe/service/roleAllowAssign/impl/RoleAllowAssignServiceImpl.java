package com.example.csvccdshustbe.service.roleAllowAssign.impl;

import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.entity.RoleAllowAssign;
import com.example.csvccdshustbe.repository.roleAllowAssign.RoleAllowAssignRepository;
import com.example.csvccdshustbe.request.roleAllowAssignt.FindRestRoleRequest;
import com.example.csvccdshustbe.request.roleAllowAssignt.ListDestinationRoleAssignRequest;
import com.example.csvccdshustbe.request.roleAllowAssignt.UpdateRoleAllowAssignRequest;
import com.example.csvccdshustbe.request.roleAllowAssignt.UpdateRoleAllowDataRequest;
import com.example.csvccdshustbe.response.roleAllowAssign.FindAllRoleAllowAssignResponse;
import com.example.csvccdshustbe.response.roleAllowAssign.FindAllRoleAllowResponse;
import com.example.csvccdshustbe.response.roleAllowAssign.FindRestRoleResponse;
import com.example.csvccdshustbe.service.roleAllowAssign.RoleAllowAssignService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;


@Service
public class RoleAllowAssignServiceImpl implements RoleAllowAssignService {


    @Autowired
    RoleAllowAssignRepository roleAllowAssignRepository;


    @Override
    public List<FindAllRoleAllowAssignResponse> findAllRoleAllowAssign() {
        return roleAllowAssignRepository.findAllRoleAllowAssignResponse();
    }

    @Override
    public void updateRoleAllowAssign(UpdateRoleAllowDataRequest request) {
        List<RoleAllowAssign> roleAllowAssigns = roleAllowAssignRepository.findAllRoleAllowAssign();
        if (CollectionUtils.isEmpty(roleAllowAssigns)) {
            throw new NotFoundException("Don't exits role allow assign!");
        }
        List<UpdateRoleAllowAssignRequest> data = request.getData();
        for (RoleAllowAssign allowAssign : roleAllowAssigns){
            for (UpdateRoleAllowAssignRequest rq : data) {
                if (allowAssign.getIdRole().equals(rq.getIdSourceRoleAssign())) {
                    for (ListDestinationRoleAssignRequest drq : rq.getDestinationRoleAssign()) {
                        if (allowAssign.getAllowAssign().equals(drq.getIdDestinationRoleAssign())) {
                            allowAssign.setStatus(drq.getStatus());
                        }
                    }
                }
            }
        }
        roleAllowAssignRepository.saveAll(roleAllowAssigns);
    }

    @Override
    public void saveAllRoleAllowAssign(List<RoleAllowAssign> roleAllowAssigns) {
        roleAllowAssignRepository.saveAll(roleAllowAssigns);
    }


    @Override
    public Page<FindRestRoleResponse> findRestRoleResponseAssign(FindRestRoleRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Role> roles = new ArrayList<>(csvcUser.getRole());
        Integer idRoleCurrent = roles.get(0).getIdRole();
            return roleAllowAssignRepository.findRestRoleAssignResponse(pageable, request, idRoleCurrent);
    }

    @Override
    public void deleteRoleAssignByIdRole(Integer roleId) {
        roleAllowAssignRepository.deleteRoleAssignByIdRole(roleId);
    }
}
