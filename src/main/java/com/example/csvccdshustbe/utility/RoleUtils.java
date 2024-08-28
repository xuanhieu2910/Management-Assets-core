package com.example.csvccdshustbe.utility;


import com.example.csvccdshustbe.entity.Privilege;
import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.response.role.RoleResponse;

import java.util.ArrayList;
import java.util.List;

public class RoleUtils {

    public static List<RoleResponse> convertToRoleResponse(List<Role> customRoles){
        List<RoleResponse> responses = new ArrayList<>();
        for(Role role : customRoles){
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
