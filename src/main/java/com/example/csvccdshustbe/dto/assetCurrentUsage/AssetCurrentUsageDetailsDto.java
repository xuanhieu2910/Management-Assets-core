package com.example.csvccdshustbe.dto.assetCurrentUsage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetCurrentUsageDetailsDto {


    @JsonProperty("id_current_usage")
    private Integer idCurrentUsage;
    @JsonProperty("name")
    private String name;
    @JsonProperty("code")
    private String code;
}
