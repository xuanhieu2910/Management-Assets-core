package com.example.csvccdshustbe.dto.modules.medicineModules.medicineType;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MedicineTypeDetailsDto {

    @JsonProperty("id_medicine_type")
    private Integer idMedicineType;
    @JsonProperty("name")
    private String name;
}
