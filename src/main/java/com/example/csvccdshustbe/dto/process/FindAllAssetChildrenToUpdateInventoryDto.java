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
    public FindAllAssetChildrenToUpdateInventoryDto(Object[] obj){
        this.idAsset = ValueUtil.getIntegerByObject(obj[9]);
        this.salt = ValueUtil.getStringByObject(obj[10]);
        this.idAssetProcess = ValueUtil.getIntegerByObject(obj[11]);
        this.value = ValueUtil.getStringByObject(obj[12]);
    }

}
