package com.example.csvccdshustbe.response.asset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllAssetResponseToRevaluation {

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
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("salt")
    private String salt;
    @JsonProperty("quantity_original")
    private Integer quantityOriginal;
    @JsonProperty("total_original_of_formation_original")
    private String totalOriginalOfFormationOriginal;
    @JsonProperty("total_rest_value_original")
    private String restValueOriginal;
    @JsonProperty("quantity_revaluation")
    private Integer quantityInventory;
    @JsonProperty("total_original_of_formation_revaluation")
    private String totalOriginalOfFormationRevaluation;
    @JsonProperty("rest_value_revaluation")
    private String restValueRevaluation;
}
