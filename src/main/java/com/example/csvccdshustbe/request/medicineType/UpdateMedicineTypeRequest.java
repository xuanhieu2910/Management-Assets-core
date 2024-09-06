package com.example.csvccdshustbe.request.medicineType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateMedicineTypeRequest {
    @NonNull
    private  Integer idMedicineType;
    private Integer parentId;
    @NonNull
    private String name;
    private String shortName;
    private String code;
    private Integer visible;
    private String notes;
}
