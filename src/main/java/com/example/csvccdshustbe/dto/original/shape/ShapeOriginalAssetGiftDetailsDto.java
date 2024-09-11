package com.example.csvccdshustbe.dto.original.shape;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShapeOriginalAssetGiftDetailsDto {

    private Integer idShapeOriginalAssetGift;
    private Integer idAsset;
    private Double valueBuy;
    private Double valueWork;
    private Double valueRecallWork;
    private Double valueTax;
    private Double valueOther;
    private String timeCreated;
    private String timeModified;
}
