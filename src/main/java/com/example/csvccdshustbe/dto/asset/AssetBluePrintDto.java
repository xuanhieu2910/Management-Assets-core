package com.example.csvccdshustbe.dto.asset;

import com.example.csvccdshustbe.dto.assetCategories.BluePrintAssetCategoryDto;
import com.example.csvccdshustbe.dto.assetCategories.BluePrintParentAssetCategoryDto;
import com.example.csvccdshustbe.dto.declare.AssetDeclareDto;
import com.example.csvccdshustbe.dto.department.BluePrintDepartmentDefaultDto;
import com.example.csvccdshustbe.dto.department.BluePrintDepartmentDto;
import com.example.csvccdshustbe.dto.documentAttack.BluePrintDocumentAttackDto;
import com.example.csvccdshustbe.dto.levelTypeAsset.BluePrintLevelTypeAssetDto;
import com.example.csvccdshustbe.dto.location.BluePrintLocationDto;
import com.example.csvccdshustbe.dto.modules.AssetModulesDto;
import com.example.csvccdshustbe.dto.original.AssetOriginalDto;
import com.example.csvccdshustbe.dto.originalOfFormation.AssetOriginalOfFormDto;
import com.example.csvccdshustbe.dto.projects.BluePrintProjectsDto;
import com.example.csvccdshustbe.dto.unit.BluePrintUnitDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssetBluePrintDto {

    private BluePrintParentAssetCategoryDto bluePrintParentAssetCategoryDto;
    private Integer idAsset;
    private String name;
    private String codeAsset;
    private BluePrintAssetCategoryDto assetCategory;
    private BluePrintDepartmentDto department;
    private BluePrintDocumentAttackDto documentAttack;
    private BluePrintLocationDto location;
    private BluePrintUnitDto units;
    private BluePrintProjectsDto projects;
    private String purpose;
    private String notes;
    private String description;
    private Integer quantity;
    private String fileAttack;
    private BluePrintDepartmentDefaultDto departmentDefault;
    private BluePrintLevelTypeAssetDto levelTypeAsset;
    private List<AssetOriginalOfFormDto> originOfFormation;
    private List<AssetModulesDto> modules;
    private AssetOriginalDto original;
    private AssetDeclareDto declare;
    private Integer idInstance;
}
