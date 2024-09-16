package com.example.csvccdshustbe.request.department;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateStatusDepartmentRequest {

    @NotNull
    private Integer idDepartment;
    @NotNull
    private Integer status;
}
