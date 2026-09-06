package com.example.csvccdshustbe.request.tool;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateNewToolRequest {

    @NotNull
    private String nameTool;
    @NotNull
    private String codeTool;
    @NotNull
    private Integer idToolCategory;
    @NotNull
    private String value;
    @NotNull
    private Integer quantity;
    @NotNull
    private String yearUse;

    private List<ListAllocateToolRequest> allocateToolRequestList;
}
