package com.example.csvccdshustbe.dto.original.noShape;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoShapeOriginalAssetOtherDetailsDto {
    @JsonProperty("id_no_shape_original_asset_other")
    private Integer idNoShapeOriginalAssetOther;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
}
