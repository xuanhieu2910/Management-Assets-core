package com.example.csvccdshustbe.dto.process;

import com.example.csvccdshustbe.utility.ValueUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FindAllAssetParentToInventoryDto {

    private Integer idAssetCategory;
    private String nameAssetCategory;
    private Integer depth;
    private Integer idParentAssetCategory;
    private String codeAssetCategory;
    private String path;
    private String numberCodePattern;
    private Integer isLeaf;
    private Integer typeTarget;
    @Getter
    private List<FindAllAssetChildrenToInventoryDto> assetLeaves = new ArrayList<>();

    public FindAllAssetParentToInventoryDto(Object[] obj){
        this.idAssetCategory = ValueUtil.getIntegerByObject(obj[0]);
        this.nameAssetCategory = ValueUtil.getStringByObject(obj[1]);
        this.idParentAssetCategory = ValueUtil.getIntegerByObject(obj[2]);
        this.setCodeAssetCategory(ValueUtil.getStringByObject(obj[3]));
        this.depth = ValueUtil.getIntegerByObject(obj[4]);
        this.path = ValueUtil.getStringByObject(obj[5]);
        this.numberCodePattern = ValueUtil.getStringByObject(obj[6]);
        this.isLeaf = ValueUtil.getIntegerByObject(obj[24]);
        this.typeTarget = ValueUtil.getIntegerByObject(obj[25]);
    }
}
