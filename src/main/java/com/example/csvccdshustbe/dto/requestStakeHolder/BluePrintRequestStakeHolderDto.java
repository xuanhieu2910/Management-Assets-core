package com.example.csvccdshustbe.dto.requestStakeHolder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BluePrintRequestStakeHolderDto {
    @JsonProperty("id_user")
    private Integer idUser;
    @JsonProperty("name_user")
    private Integer nameUser;
    @JsonProperty("code_department")
    private Integer codeDepartment;
    @JsonProperty("name_department")
    private Integer nameDepartment;
    @JsonProperty("time_created")
    private int timeCreated;
}
