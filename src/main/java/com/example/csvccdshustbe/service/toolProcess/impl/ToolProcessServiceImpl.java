package com.example.csvccdshustbe.service.toolProcess.impl;

import com.example.csvccdshustbe.entity.AssetProcess;
import com.example.csvccdshustbe.entity.ToolProcess;
import com.example.csvccdshustbe.repository.toolProcess.ToolProcessRepository;
import com.example.csvccdshustbe.dto.asset.FindAllAssetDto;
import com.example.csvccdshustbe.dto.toolProcess.FindAllToolProcessDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.repository.toolProcess.ToolProcessRepository;
import com.example.csvccdshustbe.request.toolProcess.FindAllToolProcessRequest;
import com.example.csvccdshustbe.request.toolProcess.ToolProcessRequest;
import com.example.csvccdshustbe.request.toolProcess.UpdateAllToolProcessRequest;
import com.example.csvccdshustbe.response.toolProcess.FindAllToolProcessResponse;
import com.example.csvccdshustbe.service.toolProcess.ToolProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.csvccdshustbe.utility.DateUtil;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;

import java.util.List;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ToolProcessServiceImpl implements ToolProcessService {

    @Autowired
    ToolProcessRepository toolProcessRepository;


    @Override
    public List<ToolProcess> saveAllToolProcess(List<ToolProcess> toolProcessList) {
        return toolProcessRepository.saveAll(toolProcessList);
    }

    @Override
    public Page<FindAllToolProcessResponse> findAllToolProcess(FindAllToolProcessRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        List<Integer> idsDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdsDepartmentCurrent();
        request.setIdsDepartmentOriginal(idsDepartment);
        Page<FindAllToolProcessDto> findAllToolProcessDtos  = toolProcessRepository.findAllToolProcess(request, pageable);
        return new PageImpl<>(convertToFindAllToolProcess(findAllToolProcessDtos.getContent()),
                pageable, findAllToolProcessDtos.getTotalElements());
    }

    @Override
    public List<ToolProcess> findAllToolProcessByIdProcess(Integer idProcess) {
        List<ToolProcess> toolProcesses = toolProcessRepository.findAllToolProcessByIdProcess(idProcess);
        if (!CollectionUtils.isEmpty(toolProcesses)){
            throw new NotFoundException("Don't exits tool process by id process!");
        }
        return toolProcesses;
    }

    @Override
    public void updateListToolProcessByIdProcess(UpdateAllToolProcessRequest toolProcess, Integer idDocument) {
        List<Integer> idsTool = getIdsToolFromUpdateToolProcessRequest(toolProcess.getToolProcessRequests());
        List<ToolProcess> assetProcessList =
                toolProcessRepository.findListToolProcessByIdsToolAndIdProcess(idsTool, toolProcess.getIdProcess());
    }

    private List<Integer> getIdsToolFromUpdateToolProcessRequest(List<ToolProcessRequest> toolProcessRequests) {
        List<Integer> idsTool = new ArrayList<>();
        toolProcessRequests.forEach(x->idsTool.add(x.getIdTool()));
        return idsTool;
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
