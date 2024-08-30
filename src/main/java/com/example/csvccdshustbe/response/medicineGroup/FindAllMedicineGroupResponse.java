package com.example.csvccdshustbe.response.medicineGroup;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllMedicineGroupResponse {

    @JsonProperty("id_medicine_group")
    private Integer idMedicineGroup;
    @JsonProperty("name")
    private String name;
    @JsonProperty("short_name")
    private String shortName;
}
