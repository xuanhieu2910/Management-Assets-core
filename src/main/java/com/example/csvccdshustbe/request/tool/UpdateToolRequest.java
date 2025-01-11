package com.example.csvccdshustbe.request.tool;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateToolRequest {

    @NotNull
    private String salt;
    @NotNull
    private String nameTool;
    @NotNull
    private Integer idToolCategory;
    @NotNull
    private String value;
    @NotNull
    private Integer quantity;
    @NotNull
    private String yearUse;
    private List<UpdateListAllocateToolRequest> allocateToolRequestList;

}
