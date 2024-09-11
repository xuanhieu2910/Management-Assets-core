package com.example.csvccdshustbe.dto.modules.medicineModules;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MedicineModuleDetailsDto {

    private Integer idMedicineModule;
    private Integer idAsset;
    private Integer idMedicineType;
    private String nameMedicineType;
    private Integer idMedicineGroup;
    private String nameMedicineGroup;
    private String publishDate;
    private String expiryDate;
    private String circulationNumber;
    private String numberBatchOfGoods;
    private String ownNameCirculationNumber;
    private String ownAddressCirculationNumber;
    private String sparePartsAttack;
}
