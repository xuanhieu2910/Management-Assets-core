package com.example.csvccdshustbe.response.modules;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllModulesResponse {

    @JsonProperty("id_modules")
    private Integer idModules;
    @JsonProperty("name")
    private String name;
    @JsonProperty("code")
    private String code;
    @JsonProperty("id_asset_category")
    private Integer idAssetCategory;
    @JsonProperty("hard_code")
    private String hardCode;
}
