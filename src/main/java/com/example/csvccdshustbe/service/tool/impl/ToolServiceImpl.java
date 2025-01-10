package com.example.csvccdshustbe.service.tool.impl;

import com.example.csvccdshustbe.dto.tool.ToolDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.repository.tool.ToolRepository;
import com.example.csvccdshustbe.request.tool.FindAllToolRequest;
import com.example.csvccdshustbe.response.tool.FindAllToolResponse;
import com.example.csvccdshustbe.service.tool.ToolService;
import com.example.csvccdshustbe.utility.DateUtil;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToolServiceImpl implements ToolService {

    @Autowired
    ToolRepository toolRepository;

    @Override
    public Page<FindAllToolResponse> findAllToolResponse(FindAllToolRequest request) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<ToolDto> toolDtos = toolRepository.findAllToolDto(request, pageable);
        return new PageImpl<>(convertToFindAllToolResponse(toolDtos), pageable, toolDtos.getTotalElements());
    }

    private List<FindAllToolResponse> convertToFindAllToolResponse(Page<ToolDto> toolDtos) {
        List<FindAllToolResponse> responses = new ArrayList<>();
        for (ToolDto toolDto : toolDtos){
            responses.add(constructionFindAllToolResponse(toolDto));
        }
        return responses;
    }

    private FindAllToolResponse constructionFindAllToolResponse(ToolDto toolDto) {
        FindAllToolResponse allToolResponse = new FindAllToolResponse();
        allToolResponse.setCodeTool(toolDto.getCodeTool());
        allToolResponse.setNameTool(toolDto.getName());
        allToolResponse.setNameToolCategory(toolDto.getNameToolCategory());
        allToolResponse.setCodeDepartment(toolDto.getCodeDepartment());
        allToolResponse.setNameDepartment(toolDto.getNameDepartment());
        allToolResponse.setTimeCreated(DateUtil.formatToPattern(DateUtil.formatDatePattern(toolDto.getTimeCreated(),
                DateUtil.DDMMYYYY),DateUtil.DDMMYYYY));
        allToolResponse.setTimeModified(DateUtil.formatToPattern(DateUtil.formatDatePattern(toolDto.getTimeModified(),
                DateUtil.DDMMYYYY),DateUtil.DDMMYYYY));
        allToolResponse.setSalt(toolDto.getSalt());
        allToolResponse.setQuantity(toolDto.getQuantity());
        allToolResponse.setIsIncrease(toolDto.getIsIncrease());
        allToolResponse.setIsDecrease(toolDto.getIsDecrease());
        allToolResponse.setValue(toolDto.getValue());
        allToolResponse.setYearUse(toolDto.getYearUse());
        allToolResponse.setStatusUse(toolDto.getStatusUse());
        return allToolResponse;
    }
}
