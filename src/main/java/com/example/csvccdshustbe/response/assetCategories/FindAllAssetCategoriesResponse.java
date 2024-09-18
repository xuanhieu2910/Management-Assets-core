package com.example.csvccdshustbe.response.assetCategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllAssetCategoriesResponse {
    @JsonProperty("id_asset_category")
    private Integer idAssetCategory;
    @JsonProperty("name")
    private String name;
    @JsonProperty("code_name")
    private String codeName;
    @JsonProperty("depth")
    private Integer depth;
    @JsonProperty("path")
    private String path;
    @JsonProperty("parent")
    private Integer parent;
    @JsonProperty("is_picked")
    private Integer isPicked;
    @JsonProperty("value_wear_tear")
    private String valueWearTear;
    @JsonProperty("year_used_wear_tear")
    private String yearUsedWearTear;
    @JsonProperty("minimum_time_depreciation")
    private String minimumTimeDepreciation;
    @JsonProperty("maximum_time_depreciation")
    private String maximumTimeDepreciation;
    @JsonProperty("name_parent")
    private String nameParent;
    @JsonProperty("visible")
    private Integer visible;
}
