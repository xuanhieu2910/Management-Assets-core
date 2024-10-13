package com.example.csvccdshustbe.dto.modules.houseModules;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseModuleDetailsDto {

    @JsonProperty("id_house_module")
    private Integer idHouseModule;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("is_manage_ground")
    private Integer isManageGround;
    @JsonProperty("province_code")
    private String provinceCode;
    @JsonProperty("district_code")
    private String districtCode;
    @JsonProperty("ward_code")
    private String wardCode;
    @JsonProperty("address_detail")
    private String addressDetail;
    @JsonProperty("floors_number")
    private Integer floorsNumber;
    @JsonProperty("acreage")
    private Double acreage;
    @JsonProperty("publish_year")
    private String publishYear;
    @JsonProperty("id_instance")
    private Integer idInstance;
    @JsonProperty("name_province")
    private String nameProvince;
    @JsonProperty("name_district")
    private String nameDistrict;
    @JsonProperty("name_ward")
    private String nameWard;
    @JsonProperty("name_instance")
    private String nameInstance;
}
