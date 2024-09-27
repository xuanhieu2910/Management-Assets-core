package com.example.csvccdshustbe.utility;


import com.example.csvccdshustbe.entity.Capabilities;
import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.response.capabilities.CapabilitiesResponse;
import com.example.csvccdshustbe.response.role.RoleResponse;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoleUtils {

    public final static String SEPARATE_ROLE_CAPABILITY = ";";

    public static List<RoleResponse> convertToRoleResponse(List<Role> customRoles){
        List<RoleResponse> responses = new ArrayList<>();
        for(Role role : customRoles){
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setRole(role.getTitle());
            roleResponse.setRoleCapabilities(convertRoleCapabilities(role));
            responses.add(roleResponse);
        }
        return responses;
    }

    private static List<CapabilitiesResponse> convertRoleCapabilities(Role role) {
        Map<String, CapabilitiesResponse> mapCapa =  new HashMap<>();
        for (Capabilities capa : role.getCapabilities()){
            if (mapCapa.containsKey(capa.getComponent())){
                mapCapa.get(capa.getComponent()).getCapabilities().add(concatCapabilities(capa.getName(), capa.getCapType()));
            } else {
                CapabilitiesResponse capabilitiesResponse = new CapabilitiesResponse();
                capabilitiesResponse.setComponent(capa.getComponent());
                List<String> capabilities = new ArrayList<>();
                capabilities.add(concatCapabilities(capa.getName(), capa.getCapType()));
                capabilitiesResponse.setCapabilities(capabilities);
                mapCapa.put(capa.getComponent(), capabilitiesResponse);
            }
        }
        return new ArrayList<>(mapCapa.values());
    }

    private static String concatCapabilities(String nameCapability, String typeMethod){
        return String.join(SEPARATE_ROLE_CAPABILITY,nameCapability, typeMethod);
    }
}
