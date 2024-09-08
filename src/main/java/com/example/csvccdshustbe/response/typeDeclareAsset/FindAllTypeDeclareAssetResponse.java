package com.example.csvccdshustbe.response.typeDeclareAsset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllTypeDeclareAssetResponse {

    @JsonProperty("id_type_declare_asset")
    private Integer idTypeDeclareAsset;
    @JsonProperty("name")
    private String name;
}
