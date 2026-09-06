package com.example.csvccdshustbe.service.originalOfFormationTool.impl;

import com.example.csvccdshustbe.dto.originalOfFormation.FindAllOriginalOfFormationDto;
import com.example.csvccdshustbe.dto.originalOfFormationTool.FindAllOriginalOfFormationToolDto;
import com.example.csvccdshustbe.entity.OriginalOfFormation;
import com.example.csvccdshustbe.entity.OriginalOfFormationTool;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.originalOfFormationTool.OriginalOfFormationToolRepository;
import com.example.csvccdshustbe.request.originalOfFormation.CreateOriginalOfFormationRequest;
import com.example.csvccdshustbe.request.originalOfFormation.FindAllOriginalOfFormationVisibleRequest;
import com.example.csvccdshustbe.request.originalOfFormation.UpdateOriginalOfFormationRequest;
import com.example.csvccdshustbe.request.originalOfFormation.UpdateStatusOriginalOfFormationRequest;
import com.example.csvccdshustbe.request.originalOfFormationTool.CreateOriginalOfFormationToolRequest;
import com.example.csvccdshustbe.request.originalOfFormationTool.FindAllOriginalOfFormationToolVisibleRequest;
import com.example.csvccdshustbe.request.originalOfFormationTool.UpdateOriginalOfFormationToolRequest;
import com.example.csvccdshustbe.request.originalOfFormationTool.UpdateStatusOriginalOfFormationToolRequest;
import com.example.csvccdshustbe.response.originalOfFormation.FindAllOriginalOfFormationVisibleResponse;
import com.example.csvccdshustbe.response.originalOfFormationTool.FindAllOriginalOfFormationToolVisibleResponse;
import com.example.csvccdshustbe.service.originalOfFormationTool.OriginalOfFormationToolService;
import com.example.csvccdshustbe.utility.Constants;
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

import java.util.List;

@Service
public class OriginalOfFormationToolServiceImpl implements OriginalOfFormationToolService {

    @Autowired
    OriginalOfFormationToolRepository originalOfFormationToolRepository;

    @Override
    public Page<FindAllOriginalOfFormationToolVisibleResponse> findAllOriginalOfFormationToolVisible(FindAllOriginalOfFormationToolVisibleRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllOriginalOfFormationDto> dtos = originalOfFormationToolRepository.findAllOriginalOfFormationToolVisible(pageable, request);
        return new PageImpl<>(convertToFindAllOriginalOfFormationToolVisible(dtos.get().collect(Collectors.toList())), pageable, dtos.getTotalElements());
    }

    @Override
    public void createOriginalOfFormationToolService(CreateOriginalOfFormationToolRequest request) throws ValidateFiledException {
        validateDataCreateOriginalOfFormationTool(request);
        originalOfFormationToolRepository.save(contructOriginalOfFormationTool(request));
    }

    private OriginalOfFormationTool contructOriginalOfFormationTool(CreateOriginalOfFormationToolRequest request) {
        OriginalOfFormationTool originalOfFormationTool = new OriginalOfFormationTool();
        originalOfFormationTool.setName(request.getName().trim());

        if (StringUtils.isNotBlank(request.getShortName())){
            originalOfFormationTool.setShortName(request.getShortName());
        }
        if (StringUtils.isNotBlank(request.getCodeName())){
            originalOfFormationTool.setCodeName(request.getCodeName());
        }
        if (ObjectUtils.isNotEmpty(request.getParentId())){
            originalOfFormationTool.setParent(request.getParentId());
        }
        if (ObjectUtils.isNotEmpty(request.getDescription())){
            originalOfFormationTool.setDescription(request.getDescription());
        }
        if (ObjectUtils.isNotEmpty(request.getSortOrder())){
            originalOfFormationTool.setSortOrder(request.getSortOrder());
        }
        if (ObjectUtils.isNotEmpty(request.getVisible())) {
            originalOfFormationTool.setVisible(request.getVisible());
        }
        String timeCurrent = String.valueOf(new Date().getTime());
        originalOfFormationTool.setTimeCreated(timeCurrent);
        originalOfFormationTool.setTimeModified(timeCurrent);
        return originalOfFormationTool;
    }

    private void validateDataCreateOriginalOfFormationTool(CreateOriginalOfFormationToolRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        Optional<OriginalOfFormationTool> originalOfFormationTool = originalOfFormationToolRepository.findOriginalOfFormationToolByName(request.getName());


        if (originalOfFormationTool.isPresent()) {
            if (StringUtils.isNotBlank(request.getShortName())) {
                if (request.getShortName().equals(originalOfFormationTool.get().getShortName())) {
                    throw new ValidateFiledException("Exits original Of Formation Tool by short name");
                }
            }
            if (StringUtils.isNotBlank(request.getCodeName())) {
                if (request.getCodeName().equals(originalOfFormationTool.get().getCodeName())) {
                    throw new ValidateFiledException("Exits original Of Formation Tool by code name");
                }
            }
            throw new ValidateFiledException("Exits original Of Formation Tool by name medicine type!");
        }
        if (ObjectUtils.isNotEmpty(request.getParentId())) {
            Optional<OriginalOfFormationTool> originalOfFormationOptional = originalOfFormationToolRepository.findOriginalOfFormationToolByIdParent(request.getParentId());
            if (originalOfFormationOptional.isEmpty()) {
                throw new ValidateFiledException("Don't exits original Of Formation Tool by id parent!");
            }
        }
    }

    @Override
    public void updateOriginalOfFormationToolService(UpdateOriginalOfFormationToolRequest request) throws ValidateFiledException {
        OriginalOfFormationTool originalOfFormationTool =validateDataUpdateOriginalOfFormationTool(request);
        originalOfFormationToolRepository.save(editOriginalOfFormationTool(originalOfFormationTool,request));
    }

    private OriginalOfFormationTool validateDataUpdateOriginalOfFormationTool(UpdateOriginalOfFormationToolRequest request) throws ValidateFiledException {
        Optional<OriginalOfFormationTool> originalOfFormationOptionalTool=originalOfFormationToolRepository.findOriginalOfFormationToolById(request.getIdOriginalOfFormationTool());
        if (originalOfFormationOptionalTool.isEmpty()) {
            throw new NotFoundException("Don't exits original Of Formation by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        if ( (originalOfFormationOptionalTool.get().getName()!= null
                && StringUtils.isNotBlank(request.getName())
                && !originalOfFormationOptionalTool.get().getName().equals(request.getName())) ||
                ( originalOfFormationOptionalTool.get().getCodeName() != null
                        && StringUtils.isNotBlank(request.getCodeName())
                        && !originalOfFormationOptionalTool.get().getCodeName().equals(request.getCodeName())) ||
                ( originalOfFormationOptionalTool.get().getShortName() != null
                        && StringUtils.isNotBlank(request.getShortName())
                        &&!originalOfFormationOptionalTool.get().getShortName().equals(request.getShortName()))) {
            if (originalOfFormationToolRepository.checkExitsOriginalOfFormationToolByNameOrShortNameOrCodeName(request.getName(),
                    request.getCodeName(), request.getShortName())) {
                throw new ValidateFiledException("Exits department by name or code or short name!");
            }
        }
        if (ObjectUtils.isNotEmpty(request.getParentId())) {
            Optional<OriginalOfFormationTool> originalOfFormationTool = originalOfFormationToolRepository.findOriginalOfFormationToolByIdParent(request.getParentId());
            if (originalOfFormationOptionalTool.isEmpty()){
                throw new ValidateFiledException("Don't exits original Of Formation by id parent!");
            }
        }
        return originalOfFormationOptionalTool.get();
    }

    private OriginalOfFormationTool editOriginalOfFormationTool(OriginalOfFormationTool originalOfFormationTool, UpdateOriginalOfFormationToolRequest request) {
        originalOfFormationTool.setName(request.getName());
        originalOfFormationTool.setShortName(request.getShortName());
        originalOfFormationTool.setParent(request.getParentId());
        originalOfFormationTool.setCodeName(request.getCodeName());
        originalOfFormationTool.setSortOrder(request.getSortOrder());
        originalOfFormationTool.setVisible(request.getVisible());
        String timeModified = String.valueOf(new Date().getTime());
        originalOfFormationTool.setTimeModified(timeModified);
        return originalOfFormationTool;
    }

    @Override
    public void deleteOriginalOfFormationToolServiceById(Integer idOriginalOfFormationTool) throws ValidateFiledException {
        Optional<OriginalOfFormationTool> originalOfFormationTool= originalOfFormationToolRepository.findOriginalOfFormationToolById(idOriginalOfFormationTool);
        if (originalOfFormationTool.isEmpty()) {
            throw new NotFoundException("Don't exits original Of Formation by id!");
        }
        originalOfFormationToolRepository.delete(originalOfFormationTool.get());
    }

    @Override
    public void updateStatusOriginalOfFormationToolService(UpdateStatusOriginalOfFormationToolRequest request) throws ValidateFiledException {
        Optional<OriginalOfFormationTool> originalOfFormationTool =
                originalOfFormationToolRepository.findOriginalOfFormationToolById(request.getIdOriginalOfFormationTool());
        if (originalOfFormationTool.isEmpty()) {
            throw new NotFoundException("Don't exits original Of Formation by id!");
        }
        if (!request.getVisible().equals(Constants.ORIGINAL_OF_FORMATION_VISIBLE) &&
                !request.getVisible().equals(Constants.ORIGINAL_OF_FORMATION_UN_VISIBLE)){
            throw new ValidateFiledException("Don't exits status in original of formation");
        }
        originalOfFormationTool.get().setVisible(request.getVisible());
        originalOfFormationToolRepository.save(originalOfFormationTool.get());
    }

    @Override
    public List<FindAllOriginalOfFormationToolDto> findAllOriginalOfFormationDtoByVisible(Integer visible) {
        return originalOfFormationToolRepository.findAllOriginalOfFormationDtoByVisible(visible);
    }

    private List<FindAllOriginalOfFormationToolVisibleResponse> convertToFindAllOriginalOfFormationToolVisible(List<FindAllOriginalOfFormationDto> collect) {
        List<FindAllOriginalOfFormationToolVisibleResponse> responses = new ArrayList<>();
        for (FindAllOriginalOfFormationDto originalOfFormationDto: collect){
            FindAllOriginalOfFormationToolVisibleResponse response = new FindAllOriginalOfFormationToolVisibleResponse();
            response.setIdOriginalOfFormationTool(originalOfFormationDto.getIdOriginalOfFormation());
            response.setName(originalOfFormationDto.getName());
            response.setParent(originalOfFormationDto.getParent());
            response.setVisible(originalOfFormationDto.getVisible());
            response.setDepth(originalOfFormationDto.getDepth());
            response.setPath(originalOfFormationDto.getPath());
            responses.add(response);
        }
        return responses;
    }
}
