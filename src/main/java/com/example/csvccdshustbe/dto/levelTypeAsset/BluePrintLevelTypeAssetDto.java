package com.example.csvccdshustbe.dto.levelTypeAsset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BluePrintLevelTypeAssetDto {

    @JsonProperty("name")
    private String nameLevelTypeAsset;
    @JsonProperty("id_level_type_asset")
    private Integer idLevelTypeAsset;
}
