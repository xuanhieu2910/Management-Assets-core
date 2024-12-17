package com.example.csvccdshustbe.dto.process;


import com.example.csvccdshustbe.utility.ValueUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllAssetChildrenToUpdateInventoryDto {

    private Integer idAsset;
    private String salt;
    private String value;
    private Integer idAssetProcess;
    private Integer status;
    public FindAllAssetChildrenToUpdateInventoryDto(Object[] obj){
        this.idAsset = ValueUtil.getIntegerByObject(obj[9]);
        this.salt = ValueUtil.getStringByObject(obj[10]);
        this.value = ValueUtil.getStringByObject(obj[11]);
        this.idAssetProcess = ValueUtil.getIntegerByObject(obj[12]);
        this.status = ValueUtil.getIntegerByObject(obj[13]);
    }

}
