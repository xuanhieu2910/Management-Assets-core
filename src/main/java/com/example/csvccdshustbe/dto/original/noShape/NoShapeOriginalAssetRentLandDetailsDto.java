package com.example.csvccdshustbe.dto.original.noShape;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoShapeOriginalAssetRentLandDetailsDto {

    @JsonProperty("id_no_shape_original_asset_rent_land")
    private Integer idNoShapeOriginalAssetRentLand;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("value_rent")
    private Double valueRent;
    @JsonProperty("value_work")
    private Double valueWork;
    @JsonProperty("value_other")
    private Double valueOther;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
}
