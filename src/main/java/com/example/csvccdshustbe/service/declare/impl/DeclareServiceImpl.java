package com.example.csvccdshustbe.service.declare.impl;

import com.example.csvccdshustbe.entity.Declare;
import com.example.csvccdshustbe.repository.declare.DeclareRepository;
import com.example.csvccdshustbe.response.declare.FindAllDeclareVisibleResponse;
import com.example.csvccdshustbe.service.declare.DeclareService;
import com.example.csvccdshustbe.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeclareServiceImpl implements DeclareService {

    @Autowired
    DeclareRepository declareRepository;

    @Override
    public List<FindAllDeclareVisibleResponse> findAllDeclareVisibleByIdAssetCategory(Integer idAssetCategory) {
        List<Declare> declares = declareRepository.findAllDeclareByIdAssetCategoryAndVisible(idAssetCategory,
                Constants.DECLARE_VISIBLE);
        return convertToFindAllDeclareVisible(declares);
    }

    private List<FindAllDeclareVisibleResponse> convertToFindAllDeclareVisible(List<Declare> declares) {
        List<FindAllDeclareVisibleResponse> responses = new ArrayList<>();
        for (Declare declare : declares){
            FindAllDeclareVisibleResponse response = new FindAllDeclareVisibleResponse();
            response.setIdDeclare(declare.getIdDeclare());
            response.setNameDeclare(declare.getName());
            response.setHardCode(declare.getHardCode());
            responses.add(response);
        }
        return responses;
    }
}
