package com.example.csvccdshustbe.response.asset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllAssetChildrenToUpdateInventoryResponse {
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("salt")
    private String salt;
    @JsonProperty("value")
    private String value;
}
