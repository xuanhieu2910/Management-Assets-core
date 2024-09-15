package com.example.csvccdshustbe.dto.declare;


import com.example.csvccdshustbe.dto.assetCurrentUsage.AssetCurrentUsageDetailsDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonDeclareDetailsDto {

    @JsonProperty("id_other_declare")
    private Integer idOtherDeclare;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("specification")
    private String specification;
    @JsonProperty("id_type_declare_asset")
    private Integer idTypeDeclareAsset;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("name_type_declare")
    private String nameTypeDeclare;
    @JsonProperty("current_usage")
    private AssetCurrentUsageDetailsDto assetCurrentUsageDetailsDto;
}
