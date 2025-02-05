package com.example.csvccdshustbe.request.tool;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateListAllocateToolRequest {

    private String salt;
    private Integer idLocation;
    private String userName;
    private Integer statusUse;
    private Integer quantity;
}
