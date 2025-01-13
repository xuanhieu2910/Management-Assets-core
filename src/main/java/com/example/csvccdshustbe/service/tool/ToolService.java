package com.example.csvccdshustbe.service.tool;

import com.example.csvccdshustbe.entity.Tool;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.tool.CreateNewToolRequest;
import com.example.csvccdshustbe.request.tool.FindAllToolRequest;
import com.example.csvccdshustbe.request.tool.UpdateToolRequest;
import com.example.csvccdshustbe.response.tool.FindAllToolResponse;
import com.example.csvccdshustbe.response.tool.StatisticToolsResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ToolService {

    Page<FindAllToolResponse> findAllToolParentResponse(FindAllToolRequest request);
    String generateCodeTool();
    void createNewTool(CreateNewToolRequest createNewToolRequest) throws ValidateFiledException;
    void updateTool(UpdateToolRequest updateToolRequest) throws ValidateFiledException;
    Tool findToolBySaltTool(String salt);
    Tool findToolByIdTool(Integer idTool);
    List<Tool> findAllChildrenToolByIdToolParent(Integer idToolParent);
    void deleteToolBySalt(String saltTool);
    List<Tool> findAllToolBySaltsAndIsIncrease(List<String> listSalts, Integer isIncrease);
    Page<FindAllToolResponse> findAllToolChildrenResponse(FindAllToolRequest findAllToolRequest);
    StatisticToolsResponse getStatisticTool();
}
