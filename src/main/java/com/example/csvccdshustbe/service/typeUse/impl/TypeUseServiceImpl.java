package com.example.csvccdshustbe.service.typeUse.impl;

import com.example.csvccdshustbe.entity.TypeUse;
import com.example.csvccdshustbe.repository.typeUse.TypeUseRepository;
import com.example.csvccdshustbe.response.typeUse.FindAllTypeUseResponse;
import com.example.csvccdshustbe.service.typeUse.TypeUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

}
