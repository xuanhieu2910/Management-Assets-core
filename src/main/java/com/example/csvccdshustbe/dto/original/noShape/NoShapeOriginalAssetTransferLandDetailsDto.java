package com.example.csvccdshustbe.dto.original.noShape;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoShapeOriginalAssetTransferLandDetailsDto {

    @JsonProperty("id_no_shape_original_asset_transfer_land")
    private Integer idNoShapeOriginalAssetTransferLand;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("value_use")
    private Double valueUse;
    @JsonProperty("value_tax")
    private Double valueTax;
    @JsonProperty("value_other")
    private Double valueOther;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
}
