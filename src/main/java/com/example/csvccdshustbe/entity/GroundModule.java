package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ground_module")
public class GroundModule implements IModules{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ground_module")
    private Integer idGroundModule;
    @Column(name = "asset_id")
    private Integer idAsset;
    @Column(name = "province_code")
    private String provinceCode;
    @Column(name = "district_code")
    private String districtCode;
    @Column(name = "ward_code")
    private String wardCode;
    @Column(name = "address_detail")
    private String addressDetail;


}
