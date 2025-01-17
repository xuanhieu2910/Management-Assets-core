package com.example.csvccdshustbe.request.process.tool;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToolDetailDecreaseRequest {

    private Integer idTool;
    private String value;
    private Integer quantityDecrease;

}
