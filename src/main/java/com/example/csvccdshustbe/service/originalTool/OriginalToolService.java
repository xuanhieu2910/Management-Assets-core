package com.example.csvccdshustbe.service.originalTool;


import com.example.csvccdshustbe.dto.originalTool.FindAllOriginalToolDto;
import com.example.csvccdshustbe.request.originalTool.FindAllOriginalToolRequest;
import com.example.csvccdshustbe.response.originalTool.FindAllOriginalToolVisibleResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OriginalToolService {
    Page<FindAllOriginalToolVisibleResponse> findAllOriginalToolResponse(FindAllOriginalToolRequest request);
    List<FindAllOriginalToolDto> findAllOriginalToolByVisible(Integer originalVisible);
}
