package com.example.csvccdshustbe.dto.modules;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetModulesDto {

    @JsonProperty("blue_print_asset_modules")
    private BluePrintAssetModulesDto bluePrintAssetModules;
    @JsonProperty("data_details")
    private Object dataDetails;
}
