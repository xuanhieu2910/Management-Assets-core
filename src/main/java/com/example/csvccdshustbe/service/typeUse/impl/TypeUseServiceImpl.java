package com.example.csvccdshustbe.service.typeUse.impl;

import com.example.csvccdshustbe.entity.TypeUse;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.typeUse.TypeUseRepository;
import com.example.csvccdshustbe.request.typeUse.CreateTypeUseRequest;
import com.example.csvccdshustbe.request.typeUse.UpdateTypeUseRequest;
import com.example.csvccdshustbe.response.typeUse.FindAllTypeUseResponse;
import com.example.csvccdshustbe.service.typeUse.TypeUseService;
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
public class TypeUseServiceImpl implements TypeUseService{
    @Autowired
    TypeUseRepository typeUseRepository;
    @Override
    public List<FindAllTypeUseResponse>findAllTypeUseResponseByStatus(Integer status){

        return convertToFindAllTypeUse(typeUseRepository.findAllTypeUseResponseByStatus(status));
    }

    private List<FindAllTypeUseResponse>convertToFindAllTypeUse(List<TypeUse>allTypeUseByStatus){
        List<FindAllTypeUseResponse> responses =new ArrayList<>();
        for (TypeUse typeUse : allTypeUseByStatus){
            FindAllTypeUseResponse response=new FindAllTypeUseResponse();
            response.setIdTypeUse(typeUse.getIdTypeUse());
            response.setName(typeUse.getName());
            responses.add(response);
        }
        return responses;
    }
    @Override
    public void createTypeUse(CreateTypeUseRequest request) throws ValidateFiledException {
        validateDataCreateTypeUse(request);
        typeUseRepository.save(contructTypeUse(request));
    }
    @Override
    public void updateTypeUse(UpdateTypeUseRequest request) throws ValidateFiledException {
        TypeUse typeUse = validateDataUpdateTypeUse(request);
        typeUseRepository.save(editTypeUse(typeUse, request));
    }



    @Override
    public void deleteTypeUseByIdTypeUse(Integer idTypeUse) {
        Optional<TypeUse> typeUseOptional = typeUseRepository.findTypeUseById(idTypeUse);
        if (!typeUseOptional.isPresent()){
            throw new NotFoundException("Don't exits Type use by id type use!");
        }
        typeUseRepository.delete(typeUseOptional.get());
    }

    private void validateDataCreateTypeUse(CreateTypeUseRequest request) throws ValidateFiledException {

        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        Optional<TypeUse> typeUse = typeUseRepository.findTypeUseByName(request.getName());
        if (typeUse.isPresent()){
            throw new ValidateFiledException("Exits type use by name of Type use!");
        }
    }

    private TypeUse contructTypeUse(CreateTypeUseRequest request) {
        TypeUse typeUse = new TypeUse();
        typeUse.setName(request.getName().trim());
        typeUse.setStatus(request.getStatus());
        String timeCurrent = String.valueOf(new Date().getTime());
        typeUse.setTimeCreated(timeCurrent);
        typeUse.setTimeModified(timeCurrent);
        return typeUse;
    }
    private TypeUse validateDataUpdateTypeUse(UpdateTypeUseRequest request) throws ValidateFiledException {
        Optional<TypeUse> typeUseOptional = typeUseRepository.findTypeUseById(request.getIdTypeUse());
        if (!typeUseOptional.isPresent()) {
            throw new NotFoundException("Don't exits Type use by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        return typeUseOptional.get();
    }
    private TypeUse editTypeUse(TypeUse typeUse, UpdateTypeUseRequest request) {
        typeUse.setName(request.getName());
        typeUse.setStatus(request.getStatus());
        String timeModified = String.valueOf(new Date().getTime());
        typeUse.setTimeModified(timeModified);
        return typeUse;
    }




}
