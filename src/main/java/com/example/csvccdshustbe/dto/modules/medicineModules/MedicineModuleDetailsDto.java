package com.example.csvccdshustbe.dto.modules.medicineModules;

import com.example.csvccdshustbe.dto.modules.medicineModules.medicineGroup.MedicineGroupDetailsDto;
import com.example.csvccdshustbe.dto.modules.medicineModules.medicineType.MedicineTypeDetailsDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MedicineModuleDetailsDto {


    @JsonProperty("id_medicine_module")
    private Integer idMedicineModule;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("medicine_group")
    private MedicineGroupDetailsDto medicineGroupDetailsDto;
    @JsonProperty("medicine_type")
    private MedicineTypeDetailsDto medicineTypeDetailsDto;
    @JsonProperty("publish_date")
    private String publishDate;
    @JsonProperty("expiry_date")
    private String expiryDate;
    @JsonProperty("circulation_number")
    private String circulationNumber;
    @JsonProperty("number_batch_of_goods")
    private String numberBatchOfGoods;
    @JsonProperty("own_name_circulation_number")
    private String ownNameCirculationNumber;
    @JsonProperty("own_address_circulation_number")
    private String ownAddressCirculationNumber;
    @JsonProperty("spare_parts_attack")
    private String sparePartsAttack;
}
