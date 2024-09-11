package com.example.csvccdshustbe.dto.modules.houseModules;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseModuleDetailsDto {

    private Integer idHouseModule;
    private Integer idAsset;
    private Integer isManageGround;
    private String provinceCode;
    private String districtCode;
    private String wardCode;
    private String addressDetail;
    private int floorsNumber;
    private Double acreage;
    private String publishYear;
    private Integer idInstance;
    private String nameProvince;
    private String nameDistrict;
    private String nameWard;
    private String nameInstance;
}
