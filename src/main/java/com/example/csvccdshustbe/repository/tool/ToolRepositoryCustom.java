package com.example.csvccdshustbe.repository.tool;

import com.example.csvccdshustbe.dto.tool.ToolDto;
import com.example.csvccdshustbe.entity.Tool;
import com.example.csvccdshustbe.request.tool.FindAllToolRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ToolRepositoryCustom {

    Page<ToolDto> findAllToolDto(FindAllToolRequest request, Pageable pageable);
    Optional<Tool> findLastToolByIdDepartmentOriginal(Integer idDepartment);
    Optional<Tool> findToolBySaltTool(String salt);
    List<Tool> findAllChildrenToolByIdToolParent(Integer idToolParent);
}
