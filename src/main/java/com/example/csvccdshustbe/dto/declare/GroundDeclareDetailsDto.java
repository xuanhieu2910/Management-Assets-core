package com.example.csvccdshustbe.dto.declare;


import com.example.csvccdshustbe.utility.DateUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroundDeclareDetailsDto {

    @JsonProperty("id_ground_declare")
    private Integer idGroundDeclare;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("id_goals_use_ground")
    private Integer idGoalsUseGround;
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
    @JsonProperty("license_certificate_use_ground")
    private String licenseCertificateUseGround;
    @JsonProperty("date_license_certificate_use_ground")
    private String dateLicenseCertificateUseGround;
    @JsonProperty("number_decision_deliver_ground")
    private String numberDecisionDeliverGround;
    @JsonProperty("date_number_decision_deliver_ground")
    private String dateNumberDecisionDeliverGround;
    @JsonProperty("contract_number_rent_ground")
    private String contractNumberRentGround;
    @JsonProperty("date_contract_number_rent_ground")
    private String dateContractNumberRentGround;
    @JsonProperty("another_contract")
    private String anotherContract;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("id_type_declare_asset")
    private Integer idTypeDeclareAsset;
    @JsonProperty("contract_number_transfer_ground")
    private String contractNumberTransferGround;
    @JsonProperty("date_contract_number_transfer_ground")
    private String dateContractNumberTransferGround;
    @JsonProperty("number_decision_rent_ground")
    private String numberDecisionRentGround;
    @JsonProperty("date_number_decision_rent_ground")
    private String dateNumberDecisionRentGround;
    @JsonProperty("name_goal_use_ground")
    private String nameGoalUseGround;
    @JsonProperty("name_type_declare_asset")
    private String nameTypeDeclareAsset;

    public void setDateLicenseCertificateUseGround(String dateLicenseCertificateUseGround){
        this.dateLicenseCertificateUseGround = DateUtil.formatToPattern(DateUtil.formatDatePattern(dateLicenseCertificateUseGround, DateUtil.DDMMYYYY),DateUtil.DDMMYYYY);
    }

    public void setDateNumberDecisionDeliverGround (String dateNumberDecisionDeliverGround) {
        this.dateNumberDecisionDeliverGround = DateUtil.formatToPattern(DateUtil.formatDatePattern(dateNumberDecisionDeliverGround, DateUtil.DDMMYYYY),DateUtil.DDMMYYYY);
    }

    public void setDateContractNumberRentGround(String dateContractNumberRentGround) {
        this.dateContractNumberRentGround = DateUtil.formatToPattern(DateUtil.formatDatePattern(dateContractNumberRentGround, DateUtil.DDMMYYYY),DateUtil.DDMMYYYY);
    }

    public void setDateContractNumberTransferGround(String dateContractNumberTransferGround) {
        this.dateContractNumberTransferGround = DateUtil.formatToPattern(DateUtil.formatDatePattern(dateContractNumberTransferGround, DateUtil.DDMMYYYY),DateUtil.DDMMYYYY);
    }

    public void setDateNumberDecisionRentGround (String dateNumberDecisionRentGround){
        this.dateNumberDecisionRentGround = DateUtil.formatToPattern(DateUtil.formatDatePattern(dateNumberDecisionRentGround, DateUtil.DDMMYYYY),DateUtil.DDMMYYYY);
    }
}
