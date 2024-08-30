package com.example.csvccdshustbe.dto.medicine;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllMedicineTypeDto {

    private Integer idMedicineType;
    private String name;
    private String shortName;
    private String code;
    private Integer parent;
    private Integer visible;
    private String notes;
    private String timeCreated;
    private String timeModified;
    private Integer depth;
    private String path;
}
