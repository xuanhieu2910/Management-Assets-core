package com.example.csvccdshustbe.request.toolProcess;

import com.example.csvccdshustbe.request.tool.CreateNewToolRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ToolProcessNotDeclareWhenInventoryRequest {

    @NotNull
    private Integer idProcess;
    private List<CreateNewToolRequest> listToolDeclare;

}
