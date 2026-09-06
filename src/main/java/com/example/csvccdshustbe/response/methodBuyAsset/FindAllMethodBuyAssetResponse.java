package com.example.csvccdshustbe.response.methodBuyAsset;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllMethodBuyAssetResponse {

    @JsonProperty("id_method_buy_asset")
    private Integer idMethodBuyAsset;
    @JsonProperty("name")
    private String name;
}
