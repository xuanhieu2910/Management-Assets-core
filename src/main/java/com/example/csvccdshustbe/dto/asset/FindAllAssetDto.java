package com.example.csvccdshustbe.dto.asset;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllAssetDto {

    private Integer idAsset;
    private String codeAsset;
    private String nameAsset;
    private Integer idAssetCategory;
    private String codeAssetCategory;
    private String nameAssetCategory;
    private Integer idDepartment;
    private String codeDepartment;
    private String nameDepartment;
    private Integer idLocation;
    private String nameLocation;
    private Long timeCreated;
    private Long timeModified;
    private Integer quantity;
    private Integer parent;
    private String salt;
    private String originalOfFormation;
    private String restValue;
    private String cumulative;
}
