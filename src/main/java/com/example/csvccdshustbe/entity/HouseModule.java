package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "house_module")
public class HouseModule implements IModules{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_house_module")
    private Integer idHouseModule;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "is_manage_ground")
    private Integer isManageGround;
    @Column(name = "province_code")
    private String provinceCode;
    @Column(name = "district_code")
    private String districtCode;
    @Column(name = "ward_code")
    private String wardCode;
    @Column(name = "address_detail")
    private String addressDetail;
    @Column(name = "floors_number")
    private Integer floorsNumber;
    @Column(name = "acreage")
    private Double acreage;
    @Column(name = "publish_year")
    private String publishYear;
    @Column(name = "id_instance")
    private Integer idInstance;

}
