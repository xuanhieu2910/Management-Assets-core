package com.example.csvccdshustbe.request.medicineGroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateMedicineGroupRequest {
    @NonNull
    private Integer idMedicineGroup;
    @NonNull
    private String name;
    private String shortName;
    private String description;
    @NonNull
    private Integer status;
}
