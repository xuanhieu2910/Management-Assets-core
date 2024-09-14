package com.example.csvccdshustbe.service.levelTypeAsset.impl;


import com.example.csvccdshustbe.entity.LevelTypeAsset;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.levelTypeAsset.LevelTypeAssetRepository;

import com.example.csvccdshustbe.request.levelTypeAsset.CreateLevelTypeAssetRequest;
import com.example.csvccdshustbe.request.levelTypeAsset.UpdateLevelTypeAssetRequest;
import com.example.csvccdshustbe.response.levelTypeAsset.FindAllLevelTypeAssetResponse;
import com.example.csvccdshustbe.service.levelTypeAsset.LevelTypeAssetService;
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
public class LevelTypeAssetServiceImpl implements LevelTypeAssetService {

    @Autowired
    LevelTypeAssetRepository levelTypeAssetRepository;
    @Override
    public List<FindAllLevelTypeAssetResponse> findAllLevelTypeAssetResponseByStatus(Integer status){
        return convertToFindAllLevelTypeAsset(levelTypeAssetRepository.findAllLevelTypeAssetByStatus(status));
    }

    private List<FindAllLevelTypeAssetResponse> convertToFindAllLevelTypeAsset(List<LevelTypeAsset> allLevelTypeAssetByStatus) {
        List<FindAllLevelTypeAssetResponse>responses = new ArrayList<>();
        for (LevelTypeAsset levelTypeAsset : allLevelTypeAssetByStatus){
            FindAllLevelTypeAssetResponse response = new FindAllLevelTypeAssetResponse();
            response.setIdLevelTypeAsset(levelTypeAsset.getIdLevelTypeAsset());
            response.setName(levelTypeAsset.getName());
            responses.add(response);
        }
        return responses;
    }


    @Override
    public void createLevelTypeAsset(CreateLevelTypeAssetRequest request) throws ValidateFiledException {
        validateDataCreateLevelTypeAsset(request);
        levelTypeAssetRepository.save(contructLevelTypeAsset(request));
    }




    @Override
    public void updateLevelTypeAsset(UpdateLevelTypeAssetRequest request) throws ValidateFiledException {
        LevelTypeAsset levelTypeAsset = validateDataUpdateLevelTypeAsset(request);
        levelTypeAssetRepository.save(editLevelTypeAsset(levelTypeAsset, request));
    }



    @Override
    public void deleteLevelTypeAssetsByIdLTA(Integer idLevelTypeAsset) {
        Optional<LevelTypeAsset> levelTypeAssetOptional = levelTypeAssetRepository.findLevelTypeAssetById(idLevelTypeAsset);
        if (levelTypeAssetOptional.isEmpty()){
            throw new NotFoundException("Don't exits  Level type asset by id !");
        }
        levelTypeAssetRepository.delete(levelTypeAssetOptional.get());
    }


    private void validateDataCreateLevelTypeAsset(CreateLevelTypeAssetRequest request) throws ValidateFiledException{

        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        Optional<LevelTypeAsset> levelTypeAsset = levelTypeAssetRepository.findLevelTypeAssetByName(request.getName());
        if (levelTypeAsset.isPresent()){
            throw new ValidateFiledException("Exits Level type asset by name of Level type asset!");
        }
    }
    private LevelTypeAsset contructLevelTypeAsset(CreateLevelTypeAssetRequest request) {
        LevelTypeAsset levelTypeAsset = new LevelTypeAsset();
        levelTypeAsset.setName(request.getName().trim());
        levelTypeAsset.setLevel(request.getLevel());
        levelTypeAsset.setDescription(request.getDescription());
        levelTypeAsset.setStatus(request.getStatus());
        String timeCurrent = String.valueOf(new Date().getTime());
        levelTypeAsset.setTimeCreated(timeCurrent);
        levelTypeAsset.setTimeModified(timeCurrent);
        return levelTypeAsset;
    }

    private LevelTypeAsset validateDataUpdateLevelTypeAsset(UpdateLevelTypeAssetRequest request) throws ValidateFiledException{

        Optional<LevelTypeAsset> levelTypeAssetOptional = levelTypeAssetRepository.findLevelTypeAssetById(request.getIdLevelTypeAsset());
        if (levelTypeAssetOptional.isEmpty()) {
            throw new NotFoundException("Don't exits Level Type Asset by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        return levelTypeAssetOptional.get();
    }

    private LevelTypeAsset editLevelTypeAsset(LevelTypeAsset levelTypeAsset, UpdateLevelTypeAssetRequest request) {
        levelTypeAsset.setName(request.getName().trim());
        levelTypeAsset.setLevel(request.getLevel());
        levelTypeAsset.setDescription(request.getDescription());
        levelTypeAsset.setStatus(request.getStatus());
        String timeModified = String.valueOf(new Date().getTime());
        levelTypeAsset.setTimeModified(timeModified);
        return levelTypeAsset;
    }

}
