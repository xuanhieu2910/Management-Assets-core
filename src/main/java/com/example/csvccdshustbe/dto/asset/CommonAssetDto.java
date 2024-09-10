package com.example.csvccdshustbe.dto.asset;

import com.example.csvccdshustbe.dto.originalOfFormation.AssetOriginalOfFormDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonAssetDto {

    private Integer idAsset;
    private String name;
    private String codeAsset;
    private Integer idAssetCategory;
    private String nameAssetCategory;
    private String codeDepartment;
    private Integer idDepartment;
    private Integer idDocumentAttack;
    private String nameDocumentAttack;
    private Integer idLocation;
    private String nameLocation;
    private Integer idUnit;
    private String nameUnit;
    private Integer idOriginal;
    private String nameOriginal;
    private Integer idProjects;
    private String nameProjects;
    private String purpose;
    private String notes;
    private String description;
    private Integer quantity;
    private String fileAttack;
    private String nameDefaultDepartment;
    private Integer idDefaultDepartment;
    private String nameLevelTypeAsset;
    private Integer idLevelTypeAsset;
    private AssetOriginalOfFormDto originOfFormation;

}
