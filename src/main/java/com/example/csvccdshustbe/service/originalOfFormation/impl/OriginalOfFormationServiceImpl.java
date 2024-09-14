package com.example.csvccdshustbe.service.originalOfFormation.impl;

import com.example.csvccdshustbe.dto.originalOfFormation.AssetOriginalOfFormDto;
import com.example.csvccdshustbe.dto.originalOfFormation.FindAllOriginalOfFormationDto;
import com.example.csvccdshustbe.entity.Department;
import com.example.csvccdshustbe.entity.MedicineType;
import com.example.csvccdshustbe.entity.OriginalOfFormation;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.originalOfFormation.OriginalOfFormationRepository;
import com.example.csvccdshustbe.request.originalOfFormation.CreateOriginalOfFormationReuqest;
import com.example.csvccdshustbe.request.originalOfFormation.FindAllOriginalOfFormationRequest;
import com.example.csvccdshustbe.request.originalOfFormation.UpdateOriginalOfFormationRequest;
import com.example.csvccdshustbe.response.originalOfFormation.FindAllOriginalOfFormationResponse;
import com.example.csvccdshustbe.service.originalOfFormation.OriginalOfFormationService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OriginalOfFormationServiceImpl implements OriginalOfFormationService {

    @Autowired
    OriginalOfFormationRepository originalOfFormationRepository;


    @Override
    public Page<FindAllOriginalOfFormationResponse> findAllOriginalOfFormation(FindAllOriginalOfFormationRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllOriginalOfFormationDto> dtos = originalOfFormationRepository.findAllOriginalOfFormationVisible(pageable, request);
        return new PageImpl<>(convertToFindAllOriginalOfFormation(dtos.get().collect(Collectors.toList())), pageable, dtos.getTotalElements());
    }

    private List<FindAllOriginalOfFormationResponse> convertToFindAllOriginalOfFormation(List<FindAllOriginalOfFormationDto> dtos) {
        List<FindAllOriginalOfFormationResponse> responses = new ArrayList<>();
        for (FindAllOriginalOfFormationDto originalOfFormationDto: dtos){
            FindAllOriginalOfFormationResponse response = new FindAllOriginalOfFormationResponse();
            response.setIdOriginalOfFormation(originalOfFormationDto.getIdOriginalOfFormation());
            response.setName(originalOfFormationDto.getName());
            response.setParent(originalOfFormationDto.getParent());
            response.setVisible(originalOfFormationDto.getVisible());
            response.setDepth(originalOfFormationDto.getDepth());
            response.setPath(originalOfFormationDto.getPath());
            responses.add(response);
        }
        return responses;
    }

    @Override
    public  void createOriginalOfFormationService(CreateOriginalOfFormationReuqest request) throws ValidateFiledException {
        validateDataCreateOriginalOfFormation(request);
        originalOfFormationRepository.save(contructOriginalOfFormation(request));
    }

    private void validateDataCreateOriginalOfFormation(CreateOriginalOfFormationReuqest request) throws ValidateFiledException{
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        Optional<OriginalOfFormation> originalOfFormation=originalOfFormationRepository.findOriginalOfFormationByName(request.getName());


        if (originalOfFormation.isPresent()) {
            throw new ValidateFiledException("Exits original Of Formation by name medicine type!");
        }
        if (StringUtils.isNotBlank(request.getShortName())) {
            if (request.getShortName().equals(originalOfFormation.get().getShortName())) {
                throw new ValidateFiledException("Exits original Of Formation by short name");
            }
        }
        if (StringUtils.isNotBlank(request.getCodeName())) {
            if (request.getCodeName().equals(originalOfFormation.get().getCodeName())) {
                throw new ValidateFiledException("Exits original Of Formation by code name");
            }
        }
        if (ObjectUtils.isNotEmpty(request.getParentId())) {
            Optional<OriginalOfFormation> originalOfFormationOptional = originalOfFormationRepository.findOriginalOfFormationByIdParent(request.getParentId());
            if (originalOfFormationOptional.isEmpty()) {
                throw new ValidateFiledException("Don't exits original Of Formation by id parent!");
            }
        }
    }

    private OriginalOfFormation contructOriginalOfFormation(CreateOriginalOfFormationReuqest request){
        OriginalOfFormation originalOfFormation=new OriginalOfFormation();
        originalOfFormation.setName(request.getName().trim());

        if (StringUtils.isNotBlank(request.getShortName())){
            originalOfFormation.setShortName(request.getShortName());
        }
        if (StringUtils.isNotBlank(request.getCodeName())){
            originalOfFormation.setCodeName(request.getCodeName());
        }
        if (ObjectUtils.isNotEmpty(request.getParentId())){
            originalOfFormation.setParent(request.getParentId());
        }
        if (ObjectUtils.isNotEmpty(request.getDescription())){
            originalOfFormation.setDescription(request.getDescription());
        }
        if (ObjectUtils.isNotEmpty(request.getSortOrder())){
            originalOfFormation.setSortOrder(request.getSortOrder());
        }
        if (ObjectUtils.isNotEmpty(request.getVisible())) {
            originalOfFormation.setVisible(request.getVisible());
        }
        String timeCurrent = String.valueOf(new Date().getTime());
        originalOfFormation.setTimeCreated(timeCurrent);
        originalOfFormation.setTimeModified(timeCurrent);
        return originalOfFormation;
    }


    @Override
    public void updateOriginalOfFormationService(UpdateOriginalOfFormationRequest request) throws ValidateFiledException{
        OriginalOfFormation originalOfFormation=validateDataUpdateOriginalOfFormation(request);
        originalOfFormationRepository.save(editOriginalOfFormation(originalOfFormation,request));
    }

    private OriginalOfFormation validateDataUpdateOriginalOfFormation(UpdateOriginalOfFormationRequest request) throws ValidateFiledException{
        Optional<OriginalOfFormation> originalOfFormationOptional=originalOfFormationRepository.findOriginalOfFormationById(request.getIdOriginalOfFormation());
        if (originalOfFormationOptional.isEmpty()) {
            throw new NotFoundException("Don't exits original Of Formation by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        if (!originalOfFormationOptional.get().getName().equals(request.getName()) ||
                !originalOfFormationOptional.get().getCodeName().equals(request.getCodeName()) ||
                !originalOfFormationOptional.get().getShortName().equals(request.getShortName())) {
            if (originalOfFormationRepository.checkExitsOriginalOfFormationByNameOrShortNameOrCodeName(request.getName(),
                    request.getCodeName(), request.getShortName())) {
                throw new ValidateFiledException("Exits department by name or code or short name!");
            }
        }
        if (ObjectUtils.isNotEmpty(request.getParentId())) {
            Optional<OriginalOfFormation> originalOfFormation = originalOfFormationRepository.findOriginalOfFormationByIdParent(request.getParentId());
            if (originalOfFormation.isEmpty()){
                throw new ValidateFiledException("Don't exits original Of Formation by id parent!");
            }
        }
        return originalOfFormationOptional.get();
    }
    private OriginalOfFormation editOriginalOfFormation(OriginalOfFormation originalOfFormation,UpdateOriginalOfFormationRequest request){
        originalOfFormation.setName(request.getName());
        originalOfFormation.setShortName(request.getShortName());
        originalOfFormation.setParent(request.getParentId());
        originalOfFormation.setCodeName(request.getCodeName());
        originalOfFormation.setSortOrder(request.getSortOrder());
        originalOfFormation.setVisible(request.getVisible());
        String timeModified = String.valueOf(new Date().getTime());
        originalOfFormation.setTimeModified(timeModified);
        return originalOfFormation;
    }

    @Override
    public void deleteOriginalOfFormationServiceById(Integer idOriginalOfFormation){
        Optional<OriginalOfFormation> originalOfFormation= originalOfFormationRepository.findOriginalOfFormationById(idOriginalOfFormation);
        if (originalOfFormation.isEmpty()) {
            throw new NotFoundException("Don't exits original Of Formation by id!");
        }
        originalOfFormationRepository.delete(originalOfFormation.get());

    }
}
