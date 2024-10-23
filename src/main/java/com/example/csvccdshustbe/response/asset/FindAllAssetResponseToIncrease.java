package com.example.csvccdshustbe.response.asset;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllAssetResponseToIncrease {
    private String codeAsset;
    private String nameAsset;
    private String nameAssetCategory;
    private String codeAssetCategory;
    private String codeDepartment;
    private String nameDepartment;
    private String timeCreated;
    private String timeModified;
    private Integer idAsset;
}
