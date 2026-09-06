package com.example.csvccdshustbe.request.typeUse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateTypeUseRequest {
    @NonNull
    private Integer idTypeUse;
    @NonNull
    private String name;
    @NonNull
    private Integer status;
}
