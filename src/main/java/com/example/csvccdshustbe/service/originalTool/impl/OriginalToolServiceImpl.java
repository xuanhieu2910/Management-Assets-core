package com.example.csvccdshustbe.service.originalTool.impl;

import com.example.csvccdshustbe.dto.originalTool.FindAllOriginalToolDto;
import com.example.csvccdshustbe.repository.originalTool.OriginalToolRepository;
import com.example.csvccdshustbe.request.originalTool.FindAllOriginalToolRequest;
import com.example.csvccdshustbe.response.originalTool.FindAllOriginalToolVisibleResponse;
import com.example.csvccdshustbe.service.originalTool.OriginalToolService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OriginalToolServiceImpl implements OriginalToolService {

    @Autowired
    OriginalToolRepository originalToolRepository;
    @Override
    public Page<FindAllOriginalToolVisibleResponse> findAllOriginalToolResponse(FindAllOriginalToolRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllOriginalToolDto> findAllOriginalTool = originalToolRepository.findAllOriginalToolDto(request, pageable);
        return new PageImpl<>(convertToFindAllOriginalToolVisibleResponse(findAllOriginalTool.get().collect(Collectors.toList())),
                pageable, findAllOriginalTool.getTotalElements());
    }

    @Override
    public List<FindAllOriginalToolDto> findAllOriginalToolByVisible(Integer originalVisible) {
        return originalToolRepository.findAllOriginalToolByVisible(originalVisible);
    }

    private List<FindAllOriginalToolVisibleResponse> convertToFindAllOriginalToolVisibleResponse(List<FindAllOriginalToolDto> collect) {
    List<FindAllOriginalToolVisibleResponse> responses = new ArrayList<>();
    for (FindAllOriginalToolDto findAllOriginalToolDto : collect) {
        FindAllOriginalToolVisibleResponse findAllOriginalToolVisibleResponse = new FindAllOriginalToolVisibleResponse();
        findAllOriginalToolVisibleResponse.setIdOriginalTool(findAllOriginalToolDto.getIdOriginalTool());
        findAllOriginalToolVisibleResponse.setName(findAllOriginalToolDto.getName());
        findAllOriginalToolVisibleResponse.setParent(findAllOriginalToolDto.getParent());
        findAllOriginalToolVisibleResponse.setVisible(findAllOriginalToolDto.getVisible());
        findAllOriginalToolVisibleResponse.setDepth(findAllOriginalToolDto.getDepth());
        findAllOriginalToolVisibleResponse.setIdToolCategory(findAllOriginalToolDto.getIdToolCategory());
        findAllOriginalToolVisibleResponse.setCode(findAllOriginalToolDto.getCode());
        responses.add(findAllOriginalToolVisibleResponse);
    }
    return responses;
    }
}
