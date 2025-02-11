package com.example.csvccdshustbe.request.toolInstance;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateToolInstanceRequest {
    private List<ToolInstanceRequest> toolInstanceRequests;
}
