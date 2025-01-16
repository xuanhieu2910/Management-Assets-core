package com.example.csvccdshustbe.service.tool;

import com.example.csvccdshustbe.entity.Tool;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.tool.*;
import com.example.csvccdshustbe.response.tool.*;
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
    FindDetailsToolResponse findDetailsToolBySaltTool(String saltTool) throws ValidateFiledException, IllegalAccessException;
    StatisticToolsResponse getStatisticTool();
    Integer countToolIncreasedNotDecreasedOrNotPending(List<Integer> idsTool);
    Page<FindAllToolResponseToIncrease> findAllToolToIncrease(FindAllToolToIncreaseRequest request);
    Page<FindAllToolResponseToDecrease> findAllToolToDecrease(FindAllToolToDecreaseRequest request);
    Integer countToolIncreasedNotDecreased(List<Integer> idsTool);
    List<Tool> findAllToolByIdsTool(List<Integer> idsTool);
    void updateToolStatusProcessCurrentAndIsIncreaseAndIsDecrease(Integer idProcess, Integer status, String code);
    void updateToolParentIsIncreaseAndIsDecrease(Integer idProcess, String code);

    void updateToolStatusProcessCurrentByIdProcessCurrent(Integer idProcessCurrent, Integer status);
}
