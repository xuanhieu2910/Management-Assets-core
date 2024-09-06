package com.example.csvccdshustbe.response.assetCategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllAssetCategoriesPickedResponse {

    @JsonProperty("name")
    private String name;
    @JsonSetter("code_name")
    private String codeName;
    @JsonProperty("path_image")
    private String pathImage;
    @JsonProperty("id_asset_category")
    private Integer idAssetCategory;
    @JsonProperty("hard_code")
    private String hardCodeDev;
}
