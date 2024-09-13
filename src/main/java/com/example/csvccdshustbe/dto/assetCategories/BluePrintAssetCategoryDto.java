package com.example.csvccdshustbe.dto.assetCategories;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BluePrintAssetCategoryDto {

    @JsonProperty("id_asset_category")
    private Integer idAssetCategory;
    @JsonProperty("name")
    private String nameAssetCategory;
}
