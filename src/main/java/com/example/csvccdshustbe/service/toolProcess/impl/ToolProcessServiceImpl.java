package com.example.csvccdshustbe.service.toolProcess.impl;

import com.example.csvccdshustbe.dto.asset.FindAllAssetDto;
import com.example.csvccdshustbe.dto.toolProcess.FindAllToolProcessDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.repository.toolProcess.ToolProcessRepository;
import com.example.csvccdshustbe.request.toolProcess.FindAllToolProcessRequest;
import com.example.csvccdshustbe.response.toolProcess.FindAllToolProcessResponse;
import com.example.csvccdshustbe.service.toolProcess.ToolProcessService;
import com.example.csvccdshustbe.utility.DateUtil;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ToolProcessServiceImpl implements ToolProcessService {
    @Autowired
    ToolProcessRepository toolProcessRepository;

    @Override
    public Page<FindAllToolProcessResponse> findAllToolProcess(FindAllToolProcessRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        List<Integer> idsDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdsDepartmentCurrent();
        request.setIdsDepartmentOriginal(idsDepartment);
        Page<FindAllToolProcessDto> findAllToolProcessDtos  = toolProcessRepository.findAllToolProcess(request, pageable);
        return new PageImpl<>(convertToFindAllToolProcess(findAllToolProcessDtos.getContent()),
                pageable, findAllToolProcessDtos.getTotalElements());
    }

    private List<FindAllToolProcessResponse> convertToFindAllToolProcess(List<FindAllToolProcessDto> content) {
        List<FindAllToolProcessResponse> result = new ArrayList<>();
        for (FindAllToolProcessDto findAllToolProcessDto : content) {
            FindAllToolProcessResponse findAllToolProcessResponse = new FindAllToolProcessResponse();
            findAllToolProcessResponse.setCodeTool(findAllToolProcessDto.getCodeTool());
            findAllToolProcessResponse.setNameTool(findAllToolProcessDto.getNameTool());
            findAllToolProcessResponse.setNameToolCategory(findAllToolProcessDto.getNameToolCategory());
            findAllToolProcessResponse.setCodeToolCategory(findAllToolProcessDto.getCodeToolCategory());
            findAllToolProcessResponse.setCodeDepartment(findAllToolProcessDto.getCodeDepartment());
            findAllToolProcessResponse.setNameDepartment(findAllToolProcessDto.getNameDepartment());
            findAllToolProcessResponse.setTimeCreated(DateUtil.formatToPattern(new Date(findAllToolProcessDto.getTimeCreated()), DateUtil.DATE_FORMAT));
            findAllToolProcessResponse.setTimeModified(DateUtil.formatToPattern( new Date(findAllToolProcessDto.getTimeModified()),DateUtil.DATE_FORMAT));
            findAllToolProcessResponse.setIdTool(findAllToolProcessDto.getIdTool());
            findAllToolProcessResponse.setSalt(findAllToolProcessDto.getSalt());
            findAllToolProcessResponse.setQuantity(findAllToolProcessDto.getQuantity());
            findAllToolProcessResponse.setIdToolProcess(findAllToolProcessDto.getIdToolProcess());
            result.add(findAllToolProcessResponse);
        }
        return result;
    }
}
