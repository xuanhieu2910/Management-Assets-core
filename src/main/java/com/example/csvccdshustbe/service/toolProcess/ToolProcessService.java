package com.example.csvccdshustbe.service.toolProcess;

import com.example.csvccdshustbe.entity.ToolProcess;

import java.util.List;

import com.example.csvccdshustbe.request.toolProcess.FindAllToolProcessRequest;
import com.example.csvccdshustbe.response.toolProcess.FindAllToolProcessResponse;
import org.springframework.data.domain.Page;

import com.example.csvccdshustbe.entity.ToolProcess;

import java.util.List;

public interface ToolProcessService {
    List<ToolProcess> saveAllToolProcess(List<ToolProcess> toolProcessList);
    Page<FindAllToolProcessResponse> findAllToolProcess(FindAllToolProcessRequest request);
    List<ToolProcess> findAllToolProcessByIdProcess(Integer idProcess);
}
