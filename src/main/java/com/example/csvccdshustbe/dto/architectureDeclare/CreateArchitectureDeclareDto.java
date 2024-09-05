package com.example.csvccdshustbe.dto.architectureDeclare;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateArchitectureDeclareDto {

    private Integer idAsset;
    private Double workplace;
    private Double hdsnNoBussiness;
    private Double hdsnBussiness;
    private Double hdsnRent;
    private Double hdsnBonds;
    private Double blankPlace;
    private Double encroachedPlace;
    private Double syntheticUse;
    private Double otherUse;
    private Double acreage;
}
