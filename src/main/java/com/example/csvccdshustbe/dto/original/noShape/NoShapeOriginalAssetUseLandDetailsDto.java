package com.example.csvccdshustbe.dto.original.noShape;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoShapeOriginalAssetUseLandDetailsDto {

    private Integer idNoShapeOriginalAssetUseLand;
    private Integer idAsset;
    private Double valueBuy;
    private Double valueTax;
    private Double valueOther;
    private String timeCreated;
    private String timeModified;
}
