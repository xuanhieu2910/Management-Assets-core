package com.example.csvccdshustbe.dto.original.shape;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShapeOriginalAssetInvestDetailsDto {

    private Integer idShapeOriginalAssetInvest;
    private Integer idAsset;
    private Double valueBuy;
    private String timeCreated;
    private String timeModified;
}
