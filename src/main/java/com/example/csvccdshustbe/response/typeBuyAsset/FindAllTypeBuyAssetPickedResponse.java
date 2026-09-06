package com.example.csvccdshustbe.response.typeBuyAsset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllTypeBuyAssetPickedResponse {

    @JsonProperty("id_type_buy_asset")
    private Integer idTypeBuyAsset;
    @JsonProperty("name")
    private String name;
}
