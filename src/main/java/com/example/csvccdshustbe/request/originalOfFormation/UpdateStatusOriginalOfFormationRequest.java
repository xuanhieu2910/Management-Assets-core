package com.example.csvccdshustbe.request.originalOfFormation;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusOriginalOfFormationRequest {

    @NotNull
    private Integer idOriginalOfFormation;
    @NotNull
    private Integer status;
}
