package com.example.csvccdshustbe.service.tool;

import com.example.csvccdshustbe.request.tool.FindAllToolRequest;
import com.example.csvccdshustbe.response.tool.FindAllToolResponse;
import org.springframework.data.domain.Page;

public interface ToolService {

    Page<FindAllToolResponse> findAllToolResponse(FindAllToolRequest request);

}
