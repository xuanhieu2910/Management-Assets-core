package com.example.csvccdshustbe.response.role;

import com.example.csvccdshustbe.response.capabilities.CapabilitiesResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoleResponse {

    @JsonProperty("role")
    private String role;
    @JsonProperty("role_capabilities")
    private List<CapabilitiesResponse> roleCapabilities;
}
