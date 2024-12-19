package com.example.csvccdshustbe.dto.process;


import com.example.csvccdshustbe.utility.ValueUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllAssetChildrenToInventoryDto {

    private Integer idAsset;
    private String codeAsset;
    private String nameAsset;
    private Integer idAssetCategory;
    private String codeAssetCategory;
    private String nameAssetCategory;
    private Integer idDepartment;
    private String codeDepartment;
    private String nameDepartment;
    private Integer idLocation;
    private String nameLocation;
    private Long timeCreated;
    private Long timeModified;
    private Integer quantity;
    private String salt;
    private Integer isIncrease;
    private Integer isDecrease;
    private String originalOfFormation;
    private String restValue;
    private String cumulative;
    private String timeIncrease;
    private String value;
    private Integer statusProcessCurrent;
    private Integer statusUse;
    private String yearUse;
    private String unit;
    private Double acreage;
    private Integer typeTarget;

    public FindAllAssetChildrenToInventoryDto(Object[] obj){
        this.idAsset = ValueUtil.getIntegerByObject(obj[7]);
        this.nameAsset = ValueUtil.getStringByObject(obj[8]);
        this.codeAsset = ValueUtil.getStringByObject(obj[9]);
        this.idDepartment = ValueUtil.getIntegerByObject(10);
        this.codeDepartment = ValueUtil.getStringByObject(obj[11]);
        this.nameDepartment = ValueUtil.getStringByObject(obj[12]);
        this.idLocation = ValueUtil.getIntegerByObject(obj[13]);
        this.nameLocation = ValueUtil.getStringByObject(obj[14]);
        this.timeCreated = ValueUtil.getLongByObject(obj[15]);
        this.timeModified = ValueUtil.getLongByObject(obj[16]);
        this.salt = ValueUtil.getStringByObject(obj[18]);
        this.restValue = ValueUtil.getStringByObject(obj[19]);
        this.quantity = ValueUtil.getIntegerByObject(obj[20]);
        this.originalOfFormation = ValueUtil.getStringByObject(obj[21]);
        this.statusUse = ValueUtil.getIntegerByObject(obj[22]);
        this.yearUse = ValueUtil.getStringByObject(obj[23]);
        this.typeTarget = ValueUtil.getIntegerByObject(obj[25]);
        this.unit = ValueUtil.getStringByObject(obj[26]);
        this.acreage = ValueUtil.getDoubleByObject(obj[27]);
        this.isIncrease = ValueUtil.getIntegerByObject(obj[28]);

    }

}
