package com.example.csvccdshustbe.response.asset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllGroundAssetResponse {

    @JsonProperty("id_ground_asset")
    private Integer idGroundAsset;
    @JsonProperty("name_ground_asset")
    private String nameGroundAsset;
    @JsonProperty("code_ground_asset")
    private String codeGroundAsset;
    @JsonProperty("salt")
    private String salt;
}
