package com.example.csvccdshustbe.request.medicineType;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateMedicineStatusRequest {

    @NotNull
    private Integer idMedicineType;
    @NotNull
    private Integer visible;
}
