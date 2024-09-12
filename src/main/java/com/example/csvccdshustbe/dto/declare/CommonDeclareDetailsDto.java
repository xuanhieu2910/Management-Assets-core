package com.example.csvccdshustbe.dto.declare;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonDeclareDetailsDto {

    private Integer idOtherDeclare;
    private Integer idAsset;
    private String specification;
    private Integer idTypeDeclareAsset;
    private String timeCreated;
    private String timeModified;
    private String nameTypeDeclare;
}
