package com.example.csvccdshustbe.dto.modules.machineModules;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MachineModuleDetailsDto {

    @JsonProperty("id_machine_module")
    private Integer idMachineModule;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("label_machine")
    private String labelMachine;
    @JsonProperty("model")
    private String model;
    @JsonProperty("serial")
    private String serial;
    @JsonProperty("publish_date")
    private String publishDate;
    @JsonProperty("id_country_producer")
    private Integer idCountryProducer;
    @JsonProperty("code_user")
    private String codeUser;
    @JsonProperty("id_type_use")
    private Integer idTypeUse;
    @JsonProperty("name_country_producer")
    private String nameCountryProducer;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("name_type_use")
    private String nameTypeUse;
    @JsonProperty("spare_parts_attack")
    private String sparePartsAttack;
}
