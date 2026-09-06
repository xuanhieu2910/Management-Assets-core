package com.example.csvccdshustbe.request.originalOfFormationTool;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusOriginalOfFormationToolRequest {
    @NotNull
    private Integer idOriginalOfFormationTool;
    @NotNull
    private Integer visible;
}
