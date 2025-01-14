package com.example.csvccdshustbe.service.toolProcess;

import com.example.csvccdshustbe.request.toolProcess.FindAllToolProcessRequest;
import com.example.csvccdshustbe.response.toolProcess.FindAllToolProcessResponse;
import org.springframework.data.domain.Page;

public interface ToolProcessService {

    Page<FindAllToolProcessResponse> findAllToolProcess(FindAllToolProcessRequest request);

}
