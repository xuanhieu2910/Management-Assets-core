package com.example.csvccdshustbe.dto.declare;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseDeclareDetailsDto {


    private Integer idHouseDeclare;
    private Integer idAsset;
    private Double workplace;
    private Double hdsnNoBussiness;
    private Double hdsnBussiness;
    private Double hdsnRent;
    private Double hdsnBonds;
    private Double livePlace;
    private Double blankPlace;
    private Double encroachedPlace;
    private Double syntheticUse;
    private Double otherUse;
    private Double acreage;
    private String timeCreated;
    private String timeModified;
    private Integer idTypeDeclareAsset;
    private String nameTypeDeclareAsset;
}
