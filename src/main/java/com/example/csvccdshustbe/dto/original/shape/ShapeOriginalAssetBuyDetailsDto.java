package com.example.csvccdshustbe.dto.original.shape;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShapeOriginalAssetBuyDetailsDto {

    @JsonProperty("id_shape_original_asset_buy")
    private Integer idShapeOriginalAssetBuy;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("value_buy")
    private Double valueBuy;
    @JsonProperty("value_discount")
    private Double valueDiscount;
    @JsonProperty("value_work")
    private Double valueWork;
    @JsonProperty("value_recall_work")
    private Double valueRecallWork;
    @JsonProperty("value_tax")
    private Double valueTax;
    @JsonProperty("value_other")
    private Double valueOther;
    @JsonProperty("id_method_buy_asset")
    private Integer idMethodBuyAsset;
    @JsonProperty("id_type_buy_asset")
    private Integer idTypeBuyAsset;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("name_method_buy_asset")
    private String nameMethodBuyAsset;
    @JsonProperty("name_type_buy_asset")
    private String nameTypeBuyAsset;
}
