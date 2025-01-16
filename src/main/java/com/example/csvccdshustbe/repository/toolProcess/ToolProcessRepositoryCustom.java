package com.example.csvccdshustbe.repository.toolProcess;


import com.example.csvccdshustbe.dto.toolProcess.FindAllToolProcessDto;
import com.example.csvccdshustbe.request.toolProcess.FindAllToolProcessRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ToolProcessRepositoryCustom {
    Page<FindAllToolProcessDto> findAllToolProcess(FindAllToolProcessRequest request, Pageable pageable);
}
