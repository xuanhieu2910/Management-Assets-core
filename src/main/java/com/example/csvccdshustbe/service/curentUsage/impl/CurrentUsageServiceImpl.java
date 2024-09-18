package com.example.csvccdshustbe.service.curentUsage.impl;

import com.example.csvccdshustbe.entity.CurrentUsage;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.currentUsage.CurrentUsageRepository;
import com.example.csvccdshustbe.request.currentUsage.CreateCurrentUsageRequest;
import com.example.csvccdshustbe.request.currentUsage.UpdateCurrentUsageRequest;
import com.example.csvccdshustbe.response.curentUsage.FindAllCurrentUsageResponse;
import com.example.csvccdshustbe.service.curentUsage.CurrentUsageService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CurrentUsageServiceImpl implements CurrentUsageService {
    @Autowired
    CurrentUsageRepository currentUsageRepository;
    @Override
    public List<FindAllCurrentUsageResponse> findAllCurrentUsage(){
        return convertToFindAllCurrentUsage(currentUsageRepository.findAllCurrentUsage());
    }

    private List<FindAllCurrentUsageResponse> convertToFindAllCurrentUsage(List<CurrentUsage> allCurrentUsage) {
        List<FindAllCurrentUsageResponse> responses = new ArrayList<>();
        for (CurrentUsage usage: allCurrentUsage) {
            FindAllCurrentUsageResponse response = new FindAllCurrentUsageResponse();
            response.setIdCurrentUsage(usage.getIdCurrentUsage());
            response.setName(usage.getName());
            response.setCode(usage.getCode());
            responses.add(response);
        }
        return responses;
    }


    @Override
    public void createCurrentUsage(CreateCurrentUsageRequest request) throws ValidateFiledException {
        validateDataCreateCurrentUsage(request);
        currentUsageRepository.save(contructCurrentUsage(request));
    }



    @Override
    public void updateCurrentUsage(UpdateCurrentUsageRequest request) throws ValidateFiledException {
        CurrentUsage currentUsage = validateDataUpdateCurrentUsage(request);
        currentUsageRepository.save(editLevelCurrentUsage(currentUsage, request));
    }



    @Override
    public void deleteCurrentUsageByIdCU(Integer idCurrentUsage) {
        Optional<CurrentUsage> currentUsageOptional = currentUsageRepository.findCurrentUsageById(idCurrentUsage);
        if (currentUsageOptional.isEmpty()){
            throw new NotFoundException("Don't exits Current Usage by id");
        }
        currentUsageRepository.delete(currentUsageOptional.get());
    }

    private void validateDataCreateCurrentUsage(CreateCurrentUsageRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        Optional<CurrentUsage> currentUsage = currentUsageRepository.findCurrentUsageByName(request.getName());
        if (currentUsage.isPresent()){
            if (StringUtils.isNotBlank(request.getCode())) {
                if (request.getCode().equals(currentUsage.get().getCode())){
                    throw new ValidateFiledException("Exits Current Usage by code");
                }
            }
            throw new ValidateFiledException("Exits Current Usage by name!");
        }
    }
    private CurrentUsage contructCurrentUsage(CreateCurrentUsageRequest request){
        CurrentUsage currentUsage = new CurrentUsage();
        currentUsage.setName(request.getName().trim());

        if (StringUtils.isNotBlank(request.getCode())){
            currentUsage.setCode(request.getCode());
        }
        String timeCurrent = String.valueOf(new Date().getTime());
        currentUsage.setTimeCreated(timeCurrent);
        currentUsage.setTimeModified(timeCurrent);
        return currentUsage;
    }

    private CurrentUsage validateDataUpdateCurrentUsage(UpdateCurrentUsageRequest request) throws ValidateFiledException {
        Optional<CurrentUsage> currentUsageOptional = currentUsageRepository.findCurrentUsageById(request.getIdCurrentUsage());
        if (currentUsageOptional.isEmpty()) {
            throw new NotFoundException("Don't exits Current Usage by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        return currentUsageOptional.get();
    }

    private CurrentUsage editLevelCurrentUsage(CurrentUsage currentUsage, UpdateCurrentUsageRequest request) {
        currentUsage.setName(request.getName());
        currentUsage.setCode(request.getCode());
        String timeModified = String.valueOf(new Date().getTime());
        currentUsage.setTimeModified(timeModified);
        return currentUsage;
    }

}
