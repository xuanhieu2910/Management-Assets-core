package com.example.csvccdshustbe.dto.modules.medicineModules.medicineGroup;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MedicineGroupDetailsDto {

    @JsonProperty("id_medicine_group")
    private Integer idMedicineGroup;
    @JsonProperty("name")
    private String name;
}
