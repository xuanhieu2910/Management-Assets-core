package com.example.csvccdshustbe.request.asset;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateAssetRequest {
    private String name;
    private String codeAsset;
    private String codeNameAssetCategory;
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
