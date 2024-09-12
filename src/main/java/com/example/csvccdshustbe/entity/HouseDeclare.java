package com.example.csvccdshustbe.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "house_declare")
public class HouseDeclare implements IDeclare{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_house_declare")
    private Integer idHouseDeclare;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "work_place")
    private Double workplace;
    @Column(name = "hdsn_no_bussiness")
    private Double hdsnNoBussiness;
    @Column(name = "hdsn_bussiness")
    private Double hdsnBussiness;
    @Column(name = "hdsn_rent")
    private Double hdsnRent;
    @Column(name = "hdsn_bonds")
    private Double hdsnBonds;
    @Column(name = "live_place")
    private Double livePlace;
    @Column(name = "blank_place")
    private Double blankPlace;
    @Column(name = "encroached_place")
    private Double encroachedPlace;
    @Column(name = "synthetic_use")
    private Double syntheticUse;
    @Column(name = "other_use")
    private Double otherUse;
    @Column(name = "acreage")
    private Double acreage;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "id_type_declare_asset")
    private Integer idTypeDeclareAsset;

}
