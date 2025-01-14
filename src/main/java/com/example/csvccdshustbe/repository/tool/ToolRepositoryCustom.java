package com.example.csvccdshustbe.repository.tool;

import com.example.csvccdshustbe.dto.tool.FindDetailsToolDto;
import com.example.csvccdshustbe.dto.tool.ToolDto;
import com.example.csvccdshustbe.entity.Tool;
import com.example.csvccdshustbe.request.tool.FindAllToolRequest;
import com.example.csvccdshustbe.request.tool.FindAllToolToInCreaseRequest;
import com.example.csvccdshustbe.response.tool.StatisticToolsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ToolRepositoryCustom {

    Page<ToolDto> findAllToolParentDto(FindAllToolRequest request, Pageable pageable);
    Optional<Tool> findLastToolByIdDepartmentOriginal(Integer idDepartment);
    Optional<Tool> findToolBySaltTool(String salt);
    List<Tool> findAllChildrenToolByIdToolParent(Integer idToolParent);
    List<Tool> findAllToolBySaltsAndIsIncrease(List<String> listSalts, Integer isIncrease);
    Page<ToolDto> findAllChildrenToolDto(FindAllToolRequest request, Pageable pageable);
    Optional<Tool> findToolById(Integer idTool);
    void updateQuantityAndIncreaseAndDecreaseTool(Integer idParent);
    StatisticToolsResponse getStatisticTool();
    Optional<FindDetailsToolDto>findDetailToolBySalt(String saltTool);
    Page<ToolDto> findAllToolDtoToIncrease(FindAllToolToInCreaseRequest request, Pageable pageable);
}
