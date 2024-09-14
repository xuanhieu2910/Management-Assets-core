package com.example.csvccdshustbe.dto.original.shape;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShapeOriginalAssetInvestDetailsDto {

    @JsonProperty("id_shape_original_asset_invest")
    private Integer idShapeOriginalAssetInvest;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("value_buy")
    private Double valueBuy;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
}
