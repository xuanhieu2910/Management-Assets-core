package com.example.csvccdshustbe.dto.modules.groundModules;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroundModulesDetailsDto {


    private Integer idGroundModule;
    private Integer idAsset;
    private String provinceCode;
    private String districtCode;
    private String wardCode;
    private String addressDetail;
    private String nameProvince;
    private String nameDistrict;
    private String nameWard;
}
