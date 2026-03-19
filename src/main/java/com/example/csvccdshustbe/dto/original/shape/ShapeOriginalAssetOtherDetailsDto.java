package com.example.csvccdshustbe.dto.original.shape;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShapeOriginalAssetOtherDetailsDto {
    @JsonProperty("id_shape_original_asset_other")
    private Integer idShapeOriginalAssetOther;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
}
