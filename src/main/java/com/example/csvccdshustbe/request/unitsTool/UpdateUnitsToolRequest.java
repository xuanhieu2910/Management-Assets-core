package com.example.csvccdshustbe.request.unitsTool;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUnitsToolRequest {
    @NonNull
    private Integer idUnitTool;
    @NonNull
    private String name;
    @NonNull
    private Integer status;
}
