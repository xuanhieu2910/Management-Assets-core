package com.example.csvccdshustbe.request.tool;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListAllocateToolRequest {

    @NotNull
    private Integer idDepartment;
    private Integer idLocation;
    private String userName;
    private Integer statusUse;
    private Integer quantity;
}
