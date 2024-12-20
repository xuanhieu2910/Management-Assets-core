package com.example.csvccdshustbe.dto.process;


import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.ValueUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllAssetChildrenToInventoryInReportDto {
    private Integer idAsset;
    private String salt;
    private String nameAsset;
    private String codeAsset;
    private Integer idAssetCategory;
    private String codeAssetCategory;
    private Integer typeTarget;
    private String value;
    private Integer depth;
    public FindAllAssetChildrenToInventoryInReportDto(Object[] obj){
        this.idAsset = ValueUtil.getIntegerByObject(obj[9]);
        this.salt = ValueUtil.getStringByObject(obj[10]);
        this.nameAsset = ValueUtil.getStringByObject(obj[12]);
        this.codeAsset = ValueUtil.getStringByObject(obj[13]);
        this.idAssetCategory = ValueUtil.getIntegerByObject(obj[0]);
        this.codeAssetCategory = ValueUtil.getStringByObject(obj[3]);
        this.typeTarget = ValueUtil.getIntegerByObject(obj[8]);
        this.value = ValueUtil.getStringByObject(obj[11]);
        this.depth = ValueUtil.getIntegerByObject(obj[4]) + Constants.DEPTH_DEFAULT;
    }
}
