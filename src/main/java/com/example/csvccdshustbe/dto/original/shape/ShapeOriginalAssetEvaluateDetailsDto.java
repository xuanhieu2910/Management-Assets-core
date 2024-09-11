package com.example.csvccdshustbe.dto.original.shape;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShapeOriginalAssetEvaluateDetailsDto {

    private Integer idShapeOriginalAssetEvaluate;
    private Integer idAsset;
    private Double valueBuy;
    private Double valueTax;
    private Double valueOther;
    private String timeCreated;
    private String timeModified;
}
