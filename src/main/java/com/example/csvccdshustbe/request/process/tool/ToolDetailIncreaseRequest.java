package com.example.csvccdshustbe.request.process.tool;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ToolDetailIncreaseRequest {

    private Integer idTool;
    private String value;
    private Integer quantityIncrease;

}
