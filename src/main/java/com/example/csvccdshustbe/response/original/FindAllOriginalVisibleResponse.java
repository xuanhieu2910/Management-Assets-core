package com.example.csvccdshustbe.response.original;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FindAllOriginalVisibleResponse {

    @JsonProperty("id_original")
    private Integer idOriginal;
    @JsonProperty("name")
    private String name;
    @JsonProperty("code")
    private String code;
    @JsonProperty("depth")
    private Integer depth;
    @JsonProperty("parent")
    private Integer parent;
    @JsonProperty("visible")
    private Integer visible;
    @JsonProperty("hard_code_dev")
    private String hardCodeDev;
    @JsonProperty("id_asset_category")
    private Integer idAssetCategory;

}
