package com.example.csvccdshustbe.repository.tool;

import com.example.csvccdshustbe.dto.tool.ToolDto;
import com.example.csvccdshustbe.request.tool.FindAllToolRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ToolRepositoryCustom {

    Page<ToolDto> findAllToolDto(FindAllToolRequest request, Pageable pageable);

}
