package com.example.csvccdshustbe.dto.original.noShape;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoShapeOriginalAssetBuyDetailsDto {

    private Integer idNoShapeOriginalAssetBuy;
    private Integer idMethodBuyAsset;
    private Integer idTypeBuyAsset;
    private Integer idAsset;
    private Double valueBuy;
    private Double valueTax;
    private Double valueOther;
    private String timeCreated;
    private String timeModified;
    private String nameMethodBuyAsset;
    private String nameTypeBuyAsset;
}
