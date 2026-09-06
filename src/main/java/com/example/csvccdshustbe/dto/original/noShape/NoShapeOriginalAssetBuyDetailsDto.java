package com.example.csvccdshustbe.dto.original.noShape;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.index.qual.HasSubsequence;

@Getter
@Setter
public class NoShapeOriginalAssetBuyDetailsDto {

    @JsonProperty("id_no_shape_original_asset_buy")
    private Integer idNoShapeOriginalAssetBuy;
    @JsonProperty("id_method_buy_asset")
    private Integer idMethodBuyAsset;
    @JsonProperty("id_type_buy_asset")
    private Integer idTypeBuyAsset;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("value_buy")
    private Double valueBuy;
    @JsonProperty("value_tax")
    private Double valueTax;
    @JsonProperty("value_other")
    private Double valueOther;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("name_method_buy_asset")
    private String nameMethodBuyAsset;
    @JsonProperty("name_type_buy_asset")
    private String nameTypeBuyAsset;
}
