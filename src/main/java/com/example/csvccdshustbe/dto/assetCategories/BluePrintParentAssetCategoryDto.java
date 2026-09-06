package com.example.csvccdshustbe.dto.assetCategories;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BluePrintParentAssetCategoryDto {

    private String codeAssetCategory;
    private Integer idAssetCategory;
    private String nameAssetCategory;
    private Integer idParent;
}
