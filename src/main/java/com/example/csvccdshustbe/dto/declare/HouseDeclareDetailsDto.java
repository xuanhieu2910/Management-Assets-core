package com.example.csvccdshustbe.dto.declare;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.index.qual.HasSubsequence;

@Getter
@Setter
public class HouseDeclareDetailsDto {

    @JsonProperty("id_house_declare")
    private Integer idHouseDeclare;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("work_place")
    private Double workplace;
    @JsonProperty("hdsn_no_bussiness")
    private Double hdsnNoBussiness;
    @JsonProperty("hdsn_bussiness")
    private Double hdsnBussiness;
    @JsonProperty("hdsn_rent")
    private Double hdsnRent;
    @JsonProperty("hdsn_bonds")
    private Double hdsnBonds;
    @JsonProperty("live_place")
    private Double livePlace;
    @JsonProperty("blank_place")
    private Double blankPlace;
    @JsonProperty("encroached_place")
    private Double encroachedPlace;
    @JsonProperty("synthetic_use")
    private Double syntheticUse;
    @JsonProperty("other_use")
    private Double otherUse;
    @JsonProperty("acreage")
    private Double acreage;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("id_type_declare_asset")
    private Integer idTypeDeclareAsset;
    @JsonProperty("name_type_declare_asset")
    private String nameTypeDeclareAsset;
}
