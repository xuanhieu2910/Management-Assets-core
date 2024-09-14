package com.example.csvccdshustbe.dto.modules.groundModules;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroundModulesDetailsDto {


    @JsonProperty("id_ground_module")
    private Integer idGroundModule;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("province_code")
    private String provinceCode;
    @JsonProperty("district_code")
    private String districtCode;
    @JsonProperty("ward_code")
    private String wardCode;
    @JsonProperty("address_detail")
    private String addressDetail;
    @JsonProperty("name_province")
    private String nameProvince;
    @JsonProperty("name_district")
    private String nameDistrict;
    @JsonProperty("name_ward")
    private String nameWard;
}
