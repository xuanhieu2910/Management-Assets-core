package com.example.csvccdshustbe.dto.assetCategories;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllAssetCategoriesPickedDto {

    private Integer idAssetCategory;
    private String name;
    private String shortName;
    private String codeName;
    private String pathImage;
    private Integer idParent;
    private String numberCodePattern;
    private Integer typeTarget;
}
