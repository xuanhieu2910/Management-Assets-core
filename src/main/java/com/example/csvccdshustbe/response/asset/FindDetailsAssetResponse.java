package com.example.csvccdshustbe.response.asset;

import com.example.csvccdshustbe.dto.asset.CommonAssetDto;
import com.example.csvccdshustbe.dto.declare.AssetDeclareDto;
import com.example.csvccdshustbe.dto.modules.AssetModulesDto;
import com.example.csvccdshustbe.dto.original.AssetOriginalDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FindDetailsAssetResponse {

    private String codeParentAssetCategory;
    private Integer idParentAssetCategory;
    private String nameParentAssetCategory;
    private CommonAssetDto common;
    private List<AssetModulesDto> modules;
    private AssetOriginalDto original;
    private AssetDeclareDto declare;
}
