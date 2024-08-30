package com.example.csvccdshustbe.response.levelTypeAsset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllLevelTypeAssetResponse {
    @JsonProperty("id_type_use")
    private Integer idLevelTypeAsset;
    @JsonProperty("name")
    private String name;
}
