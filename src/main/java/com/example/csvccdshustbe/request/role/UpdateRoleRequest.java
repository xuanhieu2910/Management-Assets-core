package com.example.csvccdshustbe.request.role;

import com.example.csvccdshustbe.request.roleCapabilities.UpdateRoleCapabilitiesRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateRoleRequest {

    @NonNull
    private Integer idRole;
    private String nameRole;
    private String description;
    private Integer status;
    private List<UpdateRoleCapabilitiesRequest> roleCapabilities;
}
