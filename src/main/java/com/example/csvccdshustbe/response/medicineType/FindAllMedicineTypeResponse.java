package com.example.csvccdshustbe.response.medicineType;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllMedicineTypeResponse {

    @JsonProperty("id_medicine_type")
    private Integer idMedicineType;
    @JsonProperty("name")
    private String name;
    @JsonProperty("code")
    private String code;
    @JsonProperty("parent")
    private Integer parent;
    @JsonProperty("depth")
    private Integer depth;
    @JsonProperty("path")
    private String path;
}
