package com.example.csvccdshustbe.repository.tool;

import com.example.csvccdshustbe.dto.tool.AllocateToolDto;
import com.example.csvccdshustbe.dto.tool.FindDetailsToolDto;
import com.example.csvccdshustbe.dto.tool.ToolDto;
import com.example.csvccdshustbe.entity.Tool;
import com.example.csvccdshustbe.request.tool.FindAllToolRequest;
import com.example.csvccdshustbe.request.tool.FindAllToolToDecreaseRequest;
import com.example.csvccdshustbe.request.tool.FindAllToolToIncreaseRequest;
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
    Page<ToolDto> findAllToolDtoToIncrease(FindAllToolToIncreaseRequest request, Pageable pageable);
    Page<ToolDto> findAllToolDtoToDecrease(FindAllToolToDecreaseRequest request, Pageable pageable);
    List<AllocateToolDto> findListAllocateToolByIdToolParent(Integer idToolParent);
    Integer countToolIncreasedNotDecreasedByIdsTool(List<Integer> idsTool);
    List<Tool> findAllToolByIdsTool(List<Integer> idsTool);
    void updateStatusProcessCurrentAndIsIncrease(Integer idProcess, Integer status);
    void updateStatusProcessCurrentAndIsDecrease(Integer idProcess, Integer status);
    List<Integer> getAllIdsToolParentByIdProcess(Integer idProcess);
    void updateIsIncreaseAndQuantityIncreaseCurrentByIdsTool(List<Integer> idsToolParent);
    void updateIsDecreaseAndQuantityDecreaseCurrentByIdsTool(List<Integer> idsToolParent);
    void updateToolIsIncreaseWhenNotApproved(Integer idProcessCurrent, Integer status);
    void updateToolIsDecreaseWhenNotApproved(Integer idProcessCurrent, Integer status);
    Integer countToolIsNotIncreaseOrDecreaseOrPendingByIdsTool(List<Integer> idsTool);
}
