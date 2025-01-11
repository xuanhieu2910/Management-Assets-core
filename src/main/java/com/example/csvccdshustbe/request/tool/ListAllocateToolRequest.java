package com.example.csvccdshustbe.request.tool;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListAllocateToolRequest {

    private Integer idDepartment;
    private Integer idLocation;
    private String userName;
    private Integer statusUse;
    private Integer quantity;
}
