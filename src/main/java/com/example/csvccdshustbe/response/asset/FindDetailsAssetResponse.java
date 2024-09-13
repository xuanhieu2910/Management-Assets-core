package com.example.csvccdshustbe.response.asset;

import com.example.csvccdshustbe.dto.asset.CommonAssetDto;
import com.example.csvccdshustbe.dto.declare.AssetDeclareDto;
import com.example.csvccdshustbe.dto.modules.AssetModulesDto;
import com.example.csvccdshustbe.dto.original.AssetOriginalDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FindDetailsAssetResponse {

    @JsonProperty("code_parent_asset_category")
    private String codeParentAssetCategory;
    @JsonProperty("id_parent_asset_category")
    private Integer idParentAssetCategory;
    @JsonProperty("name_parent_asset_category")
    private String nameParentAssetCategory;
    @JsonProperty("common")
    private CommonAssetDto common;
    @JsonProperty("modules")
    private List<AssetModulesDto> modules;
    @JsonProperty("original")
    private AssetOriginalDto original;
    @JsonProperty("declare")
    private AssetDeclareDto declare;
}
