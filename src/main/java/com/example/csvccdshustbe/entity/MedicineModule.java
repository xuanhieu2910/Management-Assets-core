package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "medicine_module")
public class MedicineModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicine_module")
    private Integer idMedicineModule;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "id_medicine_type")
    private Integer idMedicineType;
    @Column(name = "publish_date")
    private String publishDate;
    @Column(name = "expiry_date")
    private String expiryDate;
    @Column(name = "circulation_number")
    private String circulationNumber;
    @Column(name = "number_batch_of_goods")
    private String numberBatchOfGoods;

    @Column(name = "own_name_circulation_number")
    private String ownNameCirculationNumber;
    @Column(name = "own_address_circulation_number")
    private String ownAddressCirculationNumber;
    @Column(name = "spare_parts_attack")
    private String sparePartsAttack;
}
