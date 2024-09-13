package com.example.csvccdshustbe.dto.asset;

import com.example.csvccdshustbe.dto.assetCategories.BluePrintAssetCategoryDto;
import com.example.csvccdshustbe.dto.department.BluePrintDepartmentDefaultDto;
import com.example.csvccdshustbe.dto.department.BluePrintDepartmentDto;
import com.example.csvccdshustbe.dto.documentAttack.BluePrintDocumentAttackDto;
import com.example.csvccdshustbe.dto.levelTypeAsset.BluePrintLevelTypeAssetDto;
import com.example.csvccdshustbe.dto.location.BluePrintLocationDto;
import com.example.csvccdshustbe.dto.originalOfFormation.AssetOriginalOfFormDto;
import com.example.csvccdshustbe.dto.projects.BluePrintProjectsDto;
import com.example.csvccdshustbe.dto.unit.BluePrintUnitDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommonAssetDto {

    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("name")
    private String name;
    @JsonProperty("code_asset")
    private String codeAsset;
    @JsonProperty("asset_category")
    private BluePrintAssetCategoryDto assetCategory;
    @JsonProperty("department")
    private BluePrintDepartmentDto department;
    @JsonProperty("document_attack")
    private BluePrintDocumentAttackDto documentAttack;
    @JsonProperty("location")
    private BluePrintLocationDto location;
    @JsonProperty("units")
    private BluePrintUnitDto units;
    @JsonProperty("projects")
    private BluePrintProjectsDto projects;
    @JsonProperty("purpose")
    private String purpose;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("description")
    private String description;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("file_attack")
    private String fileAttack;
    @JsonProperty("department_default")
    private BluePrintDepartmentDefaultDto departmentDefault;
    @JsonProperty("level_type_asset")
    private BluePrintLevelTypeAssetDto levelTypeAsset;
    @JsonProperty("original_of_formation")
    private List<AssetOriginalOfFormDto> originOfFormation;
    @JsonProperty("id_instance")
    private Integer idInstance;
}
