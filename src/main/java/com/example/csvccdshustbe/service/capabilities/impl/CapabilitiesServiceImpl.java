package com.example.csvccdshustbe.service.capabilities.impl;

import com.example.csvccdshustbe.entity.Capabilities;
import com.example.csvccdshustbe.repository.capabilities.CapabilitiesRepository;
import com.example.csvccdshustbe.response.capabilities.FindAllCapabilitiesResponse;
import com.example.csvccdshustbe.service.capabilities.CapabilitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CapabilitiesServiceImpl implements CapabilitiesService {

    @Autowired
    CapabilitiesRepository capabilitiesRepository;


    @Override
    public List<FindAllCapabilitiesResponse> findAllCapabilities() {
        List<Capabilities> capabilities = capabilitiesRepository.findAllCapabilities();
        if (CollectionUtils.isEmpty(capabilities)){
            throw new NotFoundException("Don't exits capabilities!");
        }
        return convertToFindAllCapabilities(capabilities);
    }

    private List<FindAllCapabilitiesResponse> convertToFindAllCapabilities(List<Capabilities> capabilities) {
        List<FindAllCapabilitiesResponse> responses = new ArrayList<>();
        for (Capabilities capability : capabilities){
            FindAllCapabilitiesResponse response = new FindAllCapabilitiesResponse();
            response.setIdCapability(capability.getIdCapability());
            response.setNameCapability(capability.getName());
            response.setMethod(capability.getCapType());
            response.setStatus(capability.getStatus());
            response.setComponent(capability.getComponent());
            responses.add(response);
        }
        return responses;
    }

}
