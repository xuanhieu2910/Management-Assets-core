package com.example.csvccdshustbe.response.asset;

import com.example.csvccdshustbe.dto.process.FindAllAssetChildrenToInventoryDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FindAllAssetResponseToInventory {


    @JsonProperty("id_asset_category")
    private Integer idAssetCategory;
    @JsonProperty("name_asset_category")
    private String nameAssetCategory;
    @JsonProperty("depth")
    private Integer depth;
    @JsonProperty("id_parent_asset_category")
    private Integer idParentAssetCategory;
    @JsonProperty("code_asset_category")
    private String codeAssetCategory;
    @JsonProperty("path")
    private String path;
    @JsonProperty("number_code_pattern")
    private String numberCodePattern;
    @JsonProperty("is_leaf")
    private Integer isLeaf;
    @JsonProperty("type_target")
    private Integer typeTarget;
    @JsonProperty("asset_leaves")
    private List<FindAllAssetChildrenToInventoryResponse> assetLeaves;

}
