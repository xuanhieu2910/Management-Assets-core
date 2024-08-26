package com.example.csvccdshustbe.utility;

import teamit.hust.ktxcdshustbe.entity.CustomRole;
import teamit.hust.ktxcdshustbe.entity.Privilege;
import teamit.hust.ktxcdshustbe.response.role.RoleResponse;

import java.util.ArrayList;
import java.util.List;

public class RoleUtils {

    public static List<RoleResponse> convertToRoleResponse(List<CustomRole> customRoles){
        List<RoleResponse> responses = new ArrayList<>();
        for(CustomRole role : customRoles){
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setRole(role.getTitle());
            List<String> rolePrivilege = new ArrayList<>();
            for(Privilege privilege : role.getPrivileges()) {
                rolePrivilege.add(privilege.getTitle());
            }
            roleResponse.setPrivileges(rolePrivilege);
            responses.add(roleResponse);
        }
        return responses;
    }
}
