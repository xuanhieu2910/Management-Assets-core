package com.example.csvccdshustbe.request.toolProcess;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateAllToolProcessRequest {

    private List<ToolProcessRequest> toolProcessRequests;
    private Integer idProcess;
}
