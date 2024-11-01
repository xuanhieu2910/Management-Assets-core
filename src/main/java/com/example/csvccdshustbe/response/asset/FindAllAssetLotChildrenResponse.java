package com.example.csvccdshustbe.response.asset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllAssetLotChildrenResponse {

    @JsonProperty("code_asset")
    private String codeAsset;
    @JsonProperty("name_asset")
    private String nameAsset;
    @JsonProperty("name_asset_category")
    private String nameAssetCategory;
    @JsonProperty("code_asset_category")
    private String codeAssetCategory;
    @JsonProperty("code_department")
    private String codeDepartment;
    @JsonProperty("name_department")
    private String nameDepartment;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("parent")
    private Integer parent;

}
