package com.example.csvccdshustbe.response.assetInstance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllAssetInstanceResponse {

    @JsonProperty("id_asset_instance")
    private Integer idAssetInstance;
    @JsonProperty("value")
    private String value;


}
