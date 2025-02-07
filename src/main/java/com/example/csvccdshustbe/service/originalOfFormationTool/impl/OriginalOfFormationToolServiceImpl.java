package com.example.csvccdshustbe.service.originalOfFormationTool.impl;

import com.example.csvccdshustbe.dto.originalOfFormationTool.FindAllOriginalOfFormationToolDto;
import com.example.csvccdshustbe.repository.originalOfFormationTool.OriginalOfFormationToolRepository;
import com.example.csvccdshustbe.service.originalOfFormationTool.OriginalOfFormationToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OriginalOfFormationToolServiceImpl implements OriginalOfFormationToolService {

    @Autowired
    OriginalOfFormationToolRepository originalOfFormationToolRepository;

    @Override
    public List<FindAllOriginalOfFormationToolDto> findAllOriginalOfFormationDtoByVisible(Integer visible) {
        return originalOfFormationToolRepository.findAllOriginalOfFormationDtoByVisible(visible);
    }
}
