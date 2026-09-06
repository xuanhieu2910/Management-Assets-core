package com.example.csvccdshustbe.repository.originalTool;

import com.example.csvccdshustbe.dto.original.FindAllOriginalDto;
import com.example.csvccdshustbe.dto.originalTool.FindAllOriginalToolDto;
import com.example.csvccdshustbe.entity.OriginalTool;
import com.example.csvccdshustbe.request.originalTool.FindAllOriginalToolRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OriginalToolRepositoryCustom {
    Page<FindAllOriginalToolDto> findAllOriginalToolDto(FindAllOriginalToolRequest request, Pageable pageable);
    List<FindAllOriginalToolDto> findAllOriginalToolByVisible(Integer originalVisible);
}
