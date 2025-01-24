package com.example.csvccdshustbe.service.toolProcess.impl;

import com.example.csvccdshustbe.dto.toolProcess.FindAllToolProcessDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Process;
import com.example.csvccdshustbe.entity.Tool;
import com.example.csvccdshustbe.entity.ToolProcess;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.toolProcess.ToolProcessRepository;
import com.example.csvccdshustbe.request.tool.CreateNewToolRequest;
import com.example.csvccdshustbe.request.toolProcess.FindAllToolProcessRequest;
import com.example.csvccdshustbe.request.toolProcess.ToolProcessNotDeclareWhenInventoryRequest;
import com.example.csvccdshustbe.request.toolProcess.ToolProcessRequest;
import com.example.csvccdshustbe.response.toolProcess.FindAllToolProcessResponse;
import com.example.csvccdshustbe.service.process.ProcessService;
import com.example.csvccdshustbe.service.tool.ToolService;
import com.example.csvccdshustbe.service.toolProcess.ToolProcessService;
import com.example.csvccdshustbe.utility.DateUtil;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ToolProcessServiceImpl implements ToolProcessService {

    @Autowired
    ToolProcessRepository toolProcessRepository;
    @Lazy
    @Autowired
    ProcessService processService;
    @Lazy
    @Autowired
    ToolService toolService;

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
        if (CollectionUtils.isEmpty(toolProcesses)){
            throw new NotFoundException("Don't exits tool process by id process!");
        }
        return toolProcesses;
    }

    @Override
    public void updateListToolProcessByIdProcess(List<ToolProcessRequest> toolProcessRequests, Integer idProcess) {
        List<Integer> idsTool = getIdsToolFromUpdateToolProcessRequest(toolProcessRequests);
        List<ToolProcess> toolProcessList =
                toolProcessRepository.findListToolProcessByIdsToolAndIdProcess(idsTool, idProcess);
        if (toolProcessList.size() != idsTool.size()){
            throw new NotFoundException("Don't exist tool in process!");
        }
        updateChangeToolProcessByToolProcessRequest(toolProcessList, toolProcessRequests);
    }

    @Override
    public List<ToolProcess> findToolProcessByIdProcessAndStatusFluctuationSituation(Integer idProcess,
                                                                                     List<Integer> fluctuationSituation) {
        return toolProcessRepository.findToolProcessByIdProcessAndStatusFluctuationSituation(idProcess, fluctuationSituation);
    }

    @Override
    public List<ToolProcess> createNewToolNotDeclareWhenInventory(ToolProcessNotDeclareWhenInventoryRequest request) throws ValidateFiledException {
        Process process = processService.findProcessByIdProcess(request.getIdProcess());
        List<ToolProcess> toolProcessList = new ArrayList<>();
        for (CreateNewToolRequest createNewToolRequest : request.getListToolDeclare()){
            Tool tool = toolService.createNewTool(createNewToolRequest).get(0);
            toolProcessList.add(createNewToolProcess(process, tool));
        }
        return null;
    }

    private ToolProcess createNewToolProcess(Process process, Tool tool) {
        return null;
    }


    private void updateChangeToolProcessByToolProcessRequest(List<ToolProcess> toolProcessList,
                                                             List<ToolProcessRequest> toolProcessRequests) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String timeCurrent = String.valueOf(new Date().getTime());
        for (ToolProcessRequest toolProcessRequest : toolProcessRequests){
            toolProcessList.stream()
                    .filter(x->x.getIdTool().equals(toolProcessRequest.getIdTool()))
                    .findFirst()
                    .ifPresent(x->{
                        x.setValue(toolProcessRequest.getValue());
                        x.setIdUserModified(csvcUser.getIdUser());
                        x.setTimeModified(timeCurrent);
                        x.setStatus(toolProcessRequest.getStatus());
                    });
        }
        toolProcessRepository.saveAll(toolProcessList);
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
            findAllToolProcessResponse.setValue(findAllToolProcessDto.getValue());
            result.add(findAllToolProcessResponse);
        }
        return result;
    }
}
