package com.example.csvccdshustbe.service.originalTool;


import com.example.csvccdshustbe.request.originalTool.FindAllOriginalToolRequest;
import com.example.csvccdshustbe.response.originalTool.FindAllOriginalToolVisibleResponse;
import org.springframework.data.domain.Page;

public interface OriginalToolService {
    Page<FindAllOriginalToolVisibleResponse> findAllOriginalToolResponse(FindAllOriginalToolRequest request);
}
