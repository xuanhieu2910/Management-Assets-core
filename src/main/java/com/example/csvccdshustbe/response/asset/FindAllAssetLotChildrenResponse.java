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
    @JsonProperty("id_department")
    private Integer idDepartment;
    @JsonProperty("code_department")
    private String codeDepartment;
    @JsonProperty("name_department")
    private String nameDepartment;
    @JsonProperty("id_location")
    private Integer idLocation;
    @JsonProperty("name_location")
    private String nameLocation;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("parent")
    private Integer parent;
    @JsonProperty("salt")
    private String salt;
    @JsonProperty("is_increase")
    private Integer isIncrease;
    @JsonProperty("is_decrease")
    private Integer isDecrease;

}
