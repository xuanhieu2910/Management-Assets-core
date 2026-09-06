package com.example.csvccdshustbe.dto.report.inventory;

import com.example.csvccdshustbe.dto.process.FindAllAssetChildrenToUpdateInventoryDto;
import com.example.csvccdshustbe.utility.ValueUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FindAllAssetForInventoryReportDto {

    private Integer idAssetCategory;
    private String nameAssetCategory;
    private Integer depth;
    private Integer idParentAssetCategory;
    private String codeAssetCategory;
    private String path;
    private String numberCodePattern;
    private Integer isLeaf;
    private Integer typeTarget;
    private Integer idAsset;
    private String salt;
    private String value;
    private Integer idAssetProcess;
    private Integer status;
    private Integer isIncrease;
}
