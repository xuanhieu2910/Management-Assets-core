package com.example.csvccdshustbe.dto.original.noShape;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoShapeOriginalAssetGiftDetailsDto {

    private Integer idNoShapeOriginalAssetGift;
    private Integer idAsset;
    private Double valueBuy;
    private Double valueWork;
    private Double valueTax;
    private Double valueOther;
    private String timeCreated;
    private String timeModified;
}
