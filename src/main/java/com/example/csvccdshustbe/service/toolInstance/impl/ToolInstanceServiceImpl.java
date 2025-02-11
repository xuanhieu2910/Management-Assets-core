package com.example.csvccdshustbe.service.toolInstance.impl;

import com.example.csvccdshustbe.entity.ToolInstance;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.toolInstance.ToolInstanceRepository;
import com.example.csvccdshustbe.request.toolInstance.*;
import com.example.csvccdshustbe.response.toolInstance.FindAllToolInstanceResponse;
import com.example.csvccdshustbe.service.tool.ToolService;
import com.example.csvccdshustbe.service.toolInstance.ToolInstanceService;
import com.example.csvccdshustbe.utility.PageUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ToolInstanceServiceImpl implements ToolInstanceService {

    @Autowired
    ToolInstanceRepository toolInstanceRepository;
    @Autowired
    ToolService toolService;

    @Override
    public Page<FindAllToolInstanceResponse> findAllToolInstance(FindAllToolInstanceRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<ToolInstance> toolInstances = toolInstanceRepository.findAllToolInstance(request,pageable);
        long totalError = toolInstanceRepository.totalErrorToolInstance();
        return new PageImpl<>(convertToolInstance(toolInstances.get().collect(Collectors.toList()),totalError),pageable,toolInstances.getTotalElements());
    }

    @Override
    public void createToolInstance(CreateToolInstanceRequest request) throws ValidateFiledException, JsonProcessingException {
        List<Integer> idsToolInstance = new ArrayList<>();
        List<ToolInstanceRequest> toolInstanceRequests = request.getToolInstanceRequests();
        toolInstanceRequests.forEach(x->idsToolInstance.add(x.getIdToolInstance()));
        List<ToolInstance> toolInstances = toolInstanceRepository.findAllToolInstanceByIds(idsToolInstance);
        if (CollectionUtils.isEmpty(toolInstances) || idsToolInstance.size() != toolInstances.size()) {
            throw new NotFoundException("Don't exits tool instance by ids!");
        }
        for (ToolInstance toolInstance : toolInstances) {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> createToolRequest = objectMapper.readValue((toolInstance.getValue()), new TypeReference<Map<String, Object>>() {});
//            toolService.createToolFromFile(createToolRequest)
        }
        toolInstanceRepository.deleteAll(toolInstances);
    }

    @Override
    public void updateToolInstance(UpdateToolInstanceRequest request) {
        List<Integer> idsToolInstance = new ArrayList<>();
        List<ToolInstanceRequest> toolInstanceRequests = request.getToolInstanceRequests();
        toolInstanceRequests.forEach(x->idsToolInstance.add(x.getIdToolInstance()));
        List<ToolInstance> toolInstances = toolInstanceRepository.findAllToolInstanceByIds(idsToolInstance);
        if (CollectionUtils.isEmpty(toolInstances) || toolInstances.size() != idsToolInstance.size()){
            throw new NotFoundException("Don't exits tool instance by ids!");
        }
        for (ToolInstanceRequest toolInstanceRequest : toolInstanceRequests) {
            toolInstances.stream().filter(x->x.getIdToolInstance()
                    .equals(toolInstanceRequest.getIdToolInstance())).findFirst().
                    ifPresent(toolInstance->{
                        toolInstance.setValue(toolInstanceRequest.getValue());
                        toolInstance.setError(toolInstanceRequest.getError());
            });
        }
        toolInstanceRepository.saveAll(toolInstances);
    }

    @Override
    public void deleteToolInstance(DeleteToolInstanceRequest request) {
        List<Integer> idsToolInstance = new ArrayList<>();
        List<ToolInstanceRequest> toolInstanceRequests = request.getToolInstanceRequests();
        toolInstanceRequests.forEach(x->idsToolInstance.add(x.getIdToolInstance()));
        List<ToolInstance> toolInstances = toolInstanceRepository.findAllToolInstanceByIds(idsToolInstance);
        if (CollectionUtils.isEmpty(toolInstances) || toolInstances.size() != idsToolInstance.size()){
            throw new NotFoundException("Don't exits tool instance by ids!");
        }
        toolInstanceRepository.deleteAll(toolInstances);
    }

    private List<FindAllToolInstanceResponse> convertToolInstance(List<ToolInstance> collect, long totalError) {
        List<FindAllToolInstanceResponse> result = new ArrayList<>();
        for (ToolInstance toolInstance : collect) {
            FindAllToolInstanceResponse findAllToolInstanceResponse = new FindAllToolInstanceResponse();
            findAllToolInstanceResponse.setIdToolInstance(toolInstance.getIdToolInstance());
            findAllToolInstanceResponse.setValue(toolInstance.getValue());
            findAllToolInstanceResponse.setTotalError(totalError);
            result.add(findAllToolInstanceResponse);
        }
        return result;
    }
}
