package com.example.csvccdshustbe.service.toolProcess.impl;

import com.example.csvccdshustbe.entity.ToolProcess;
import com.example.csvccdshustbe.repository.toolProcess.ToolProcessRepository;
import com.example.csvccdshustbe.service.toolProcess.ToolProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolProcessServiceImpl implements ToolProcessService {

    @Autowired
    ToolProcessRepository toolProcessRepository;


    @Override
    public List<ToolProcess> saveAllToolProcess(List<ToolProcess> toolProcessList) {
        return toolProcessRepository.saveAll(toolProcessList);
    }
}
