package com.example.csvccdshustbe.dto.original.shape;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShapeOriginalAssetConnectWoActorDetailsDto {

    private Integer idShapeOriginalAssetConnectWoActor;
    private Integer idAsset;
    private Double valueBuy;
    private Double valueTax;
    private Double valueOther;
    private String timeCreated;
    private String timeModified;
}
