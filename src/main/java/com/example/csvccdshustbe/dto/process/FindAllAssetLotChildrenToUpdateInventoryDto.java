package com.example.csvccdshustbe.dto.process;


import com.example.csvccdshustbe.utility.ValueUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllAssetLotChildrenToUpdateInventoryDto {

    private Integer idAsset;
    private String salt;
    private String value;
    private Integer idAssetProcess;
    public FindAllAssetLotChildrenToUpdateInventoryDto(Object[] obj){
        this.idAsset = ValueUtil.getIntegerByObject(obj[9]);
        this.salt = ValueUtil.getStringByObject(obj[10]);
    }

}
