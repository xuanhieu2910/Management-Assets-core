package com.example.csvccdshustbe.dto.typeProcess;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BluePrintTypeProcessAssetDto {
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("code_asset")
    private String codeAsset;
    @JsonProperty("name_asset")
    private String nameAsset;
}
