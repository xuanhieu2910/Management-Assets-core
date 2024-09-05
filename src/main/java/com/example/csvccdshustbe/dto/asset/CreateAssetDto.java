package com.example.csvccdshustbe.dto.asset;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAssetDto {
    private String name;
    private String codeAsset;
    private Integer idAssetCategory;
    private Integer idDocumentAttack;
    private Integer idDepartment;
    private Integer idLocation;
    private Integer idUnit;
    private Integer idOriginal;
    private Integer idOriginalOfFormation;
    private Integer idProject;
    private String purpose;
    private String notes;
    private String fileAttack;
    private Integer idDepartmentDefault;
}
