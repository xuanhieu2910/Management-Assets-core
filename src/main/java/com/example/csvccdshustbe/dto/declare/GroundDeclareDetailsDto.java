package com.example.csvccdshustbe.dto.declare;


import com.example.csvccdshustbe.utility.DateUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroundDeclareDetailsDto {

    private Integer idGroundDeclare;
    private Integer idAsset;
    private Integer idGoalsUseGround;
    private Double workplace;
    private Double hdsnNoBussiness;
    private Double hdsnBussiness;
    private Double hdsnRent;
    private Double hdsnBonds;
    private Double livePlace;
    private Double blankPlace;
    private Double encroachedPlace;
    private Double syntheticUse;
    private Double otherUse;
    private Double acreage;
    private String licenseCertificateUseGround;
    private String dateLicenseCertificateUseGround;
    private String numberDecisionDeliverGround;
    private String dateNumberDecisionDeliverGround;
    private String contractNumberRentGround;
    private String dateContractNumberRentGround;
    private String anotherContract;
    private String timeCreated;
    private String timeModified;
    private Integer idTypeDeclareAsset;
    private String contractNumberTransferGround;
    private String dateContractNumberTransferGround;
    private String numberDecisionRentGround;
    private String dateNumberDecisionRentGround;
    private String nameGoalUseGround;
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
