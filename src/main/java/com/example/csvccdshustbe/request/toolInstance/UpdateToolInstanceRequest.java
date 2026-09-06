package com.example.csvccdshustbe.request.toolInstance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateToolInstanceRequest {
    private List<ToolInstanceRequest> toolInstanceRequests;
}
