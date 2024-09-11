package com.example.csvccdshustbe.dto.original.shape;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShapeOriginalAssetBuyDetailsDto {

    private Integer idShapeOriginalAssetBuy;
    private Integer idAsset;
    private Double valueBuy;
    private Double valueDiscount;
    private Double valueWork;
    private Double valueRecallWork;
    private Double valueTax;
    private Double valueOther;
    private Integer idMethodBuyAsset;
    private Integer idTypeBuyAsset;
    private String timeCreated;
    private String timeModified;
    private String nameMethodBuyAsset;
    private String nameTypeBuyAsset;
}
