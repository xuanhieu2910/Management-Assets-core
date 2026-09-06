package com.example.csvccdshustbe.dto.original.shape;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShapeOriginalAssetGiftDetailsDto {

    @JsonProperty("id_shape_original_asset_gift")
    private Integer idShapeOriginalAssetGift;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("value_buy")
    private Double valueBuy;
    @JsonProperty("value_work")
    private Double valueWork;
    @JsonProperty("value_recall_work")
    private Double valueRecallWork;
    @JsonProperty("value_tax")
    private Double valueTax;
    @JsonProperty("value_other")
    private Double valueOther;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
}
