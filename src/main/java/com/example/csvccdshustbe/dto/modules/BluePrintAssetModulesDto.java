package com.example.csvccdshustbe.dto.modules;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BluePrintAssetModulesDto {


    @JsonProperty("type_modules")
    private String typeModules;
    @JsonProperty("id_modules")
    private Integer idModules;
    @JsonProperty("name_modules")
    private String nameModules;
    @JsonProperty("id_instance")
    private Integer idInstance;
}
