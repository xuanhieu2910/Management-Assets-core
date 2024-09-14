package com.example.csvccdshustbe.service.positionName.impl;

import com.example.csvccdshustbe.dto.positionName.FindAllPositionNameDto;
import com.example.csvccdshustbe.entity.PositionName;
import com.example.csvccdshustbe.entity.Projects;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.positionName.PositionNameRepository;
import com.example.csvccdshustbe.request.positionName.CreatePositionNameRequest;
import com.example.csvccdshustbe.request.positionName.FindAllPositionNameRequest;
import com.example.csvccdshustbe.request.positionName.UpdatePositionNameRequest;
import com.example.csvccdshustbe.request.projects.UpdateProjectsRequest;
import com.example.csvccdshustbe.response.positionName.FindAllPositionNameResponse;
import com.example.csvccdshustbe.service.positionName.PositionNameService;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
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
public class PositionNameServiceImpl implements PositionNameService {

    @Autowired
    PositionNameRepository positionNameRepository;

    @Override
    public Page<FindAllPositionNameResponse> findAllPositionNameResponseByName(FindAllPositionNameRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllPositionNameDto> dtos = positionNameRepository.findAllPositionNameStatus(pageable, request);
        return new PageImpl<>(convertToFindAllPositionNameResponse(dtos.get().collect(Collectors.toList())),
                pageable, dtos.getTotalElements());
    }
    private List<FindAllPositionNameResponse> convertToFindAllPositionNameResponse(List<FindAllPositionNameDto> collect) {
        List<FindAllPositionNameResponse> responses = new ArrayList<>();
        for (FindAllPositionNameDto allPositionNameDto: collect){
            FindAllPositionNameResponse res = new FindAllPositionNameResponse();
            res.setIdPositionName(allPositionNameDto.getIdPositionName());
            res.setName(allPositionNameDto.getName());
            responses.add(res);
        }
        return responses;
    }
    @Override
    public void createPositionName(CreatePositionNameRequest request) throws ValidateFiledException {
        validateDataCreatePositionName(request);
        positionNameRepository.save(contructPositionName(request));
    }

    private void validateDataCreatePositionName(CreatePositionNameRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
//        ValueUtil.validateNumberOrCharacter(request.getName());
        Optional<PositionName> positionNameOptional = positionNameRepository.findPositionNameByName(request.getName());
        if (positionNameOptional.isPresent()){
            throw new ValidateFiledException("Exits Position by name of Position!");
        }

    }
    private PositionName contructPositionName(CreatePositionNameRequest request) {
        PositionName positionName = new PositionName();
        positionName.setName(request.getName().trim());

        if (ObjectUtils.isNotEmpty(request.getStatus())) {
            positionName.setStatus(request.getStatus());
        }
        String timeCurrent = String.valueOf(new Date().getTime());
        positionName.setTimeCreated(timeCurrent);
        positionName.setTimeModified(timeCurrent);
        return positionName;
    }

    @Override
    public void updatePositionName(UpdatePositionNameRequest request) throws ValidateFiledException {
        PositionName positionName = validateDataUpdatePositionName(request);
        positionNameRepository.save(editPositonName(positionName, request));
    }

    private PositionName validateDataUpdatePositionName(UpdatePositionNameRequest request) throws ValidateFiledException{
        Optional<PositionName> positionNameOptional = positionNameRepository.findPositionNameById(request.getIdPositionName());
        if (positionNameOptional.isEmpty()) {
            throw new NotFoundException("Don't exits Position by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
//        if (!positionNameOptional.get().getName().equals(request.getName())) {
//
//            if (StringUtils.isNotBlank(request.getName())){
//                ValueUtil.validateNumberOrCharacter(request.getName());
//            }
//        }
        return positionNameOptional.get();
    }


    private PositionName editPositonName(PositionName positionName, UpdatePositionNameRequest request) {
        positionName.setName(request.getName());
        positionName.setStatus(request.getStatus());
        String timeModified = String.valueOf(new Date().getTime());
        positionName.setTimeModified(timeModified);
        return positionName;
    }
    @Override
    public void deletePositionNameByIdPositionName(Integer idPositionName) {

    }
}
