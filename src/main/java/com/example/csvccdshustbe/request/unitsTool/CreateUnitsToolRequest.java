package com.example.csvccdshustbe.request.unitsTool;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateUnitsToolRequest {
    @NonNull
    private String name;
    @NonNull
    private Integer status;
}
