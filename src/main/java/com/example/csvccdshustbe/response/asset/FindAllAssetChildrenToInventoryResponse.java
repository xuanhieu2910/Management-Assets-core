package com.example.csvccdshustbe.response.asset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllAssetChildrenToInventoryResponse {


    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("code_asset")
    private String codeAsset;
    @JsonProperty("name_asset")
    private String nameAsset;
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
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("salt")
    private String salt;
    @JsonProperty("original_of_formation")
    private String originalOfFormation;
    @JsonProperty("rest_value")
    private String restValue;
    @JsonProperty("cumulative")
    private String cumulative;
    @JsonProperty("status_use")
    private Integer statusUse;
    @JsonProperty("year_use")
    private String yearUse;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("is_increase")
    private Integer isIncrease;



}
