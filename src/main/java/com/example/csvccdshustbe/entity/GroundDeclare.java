package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ground_declare")
public class GroundDeclare implements IDeclare{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ground_declare")
    private Integer idGroundDeclare;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "id_goals_use_ground")
    private Integer idGoalsUseGround;
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
    @Column(name = "license_certificate_use_ground")
    private String licenseCertificateUseGround;
    @Column(name = "date_license_certificate_use_ground")
    private String dateLicenseCertificateUseGround;
    @Column(name = "number_decision_deliver_ground")
    private String numberDecisionDeliverGround;
    @Column(name = "date_number_decision_deliver_ground")
    private String dateNumberDecisionDeliverGround;
    @Column(name = "contract_number_rent_ground")
    private String contractNumberRentGround;
    @Column(name = "date_contract_number_rent_ground")
    private String dateContractNumberRentGround;
    @Column(name = "another_contract")
    private String anotherContract;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "id_type_declare_asset")
    private Integer idTypeDeclareAsset;
    @Column(name = "contract_number_transfer_ground")
    private String contractNumberTransferGround;
    @Column(name = "date_contract_number_transfer_ground")
    private String dateContractNumberTransferGround;
    @Column(name = "number_decision_rent_ground")
    private String numberDecisionRentGround;
    @Column(name = "date_number_decision_rent_ground")
    private String dateNumberDecisionRentGround;

}
