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

    @JsonProperty("code_asset_category")
    private String codeAssetCategory;
    @JsonProperty("id_asset_category")
    private Integer idPAssetCategory;
    @JsonProperty("name_asset_category")
    private String nameAssetCategory;
    @JsonProperty("id_parent")
    private Integer idParent;
    @JsonProperty("common")
    private CommonAssetDto common;
    @JsonProperty("modules")
    private List<AssetModulesDto> modules;
    @JsonProperty("original")
    private AssetOriginalDto original;
    @JsonProperty("declare")
    private AssetDeclareDto declare;
}
