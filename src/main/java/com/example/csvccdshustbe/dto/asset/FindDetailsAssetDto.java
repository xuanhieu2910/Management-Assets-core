package com.example.csvccdshustbe.dto.asset;

import com.example.csvccdshustbe.dto.modules.AssetModulesDto;
import com.example.csvccdshustbe.dto.original.AssetOriginalDto;
import com.example.csvccdshustbe.entity.AssetDeclare;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindDetailsAssetDto {

    private String codeParentAssetCategory;
    private Integer idParentAssetCategory;
    private CommonAssetDto common;
    private AssetModulesDto modules;
    private AssetOriginalDto original;
    private AssetDeclare declare;
}
