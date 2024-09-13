package com.example.csvccdshustbe.service.declare.assetDeclare.impl;

import com.example.csvccdshustbe.dto.declare.BluePrintDeclareDto;
import com.example.csvccdshustbe.entity.AssetDeclare;
import com.example.csvccdshustbe.repository.assetDeclare.AssetDeclareRepository;
import com.example.csvccdshustbe.service.declare.assetDeclare.AssetDeclareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
public class AssetDeclareServiceImpl implements AssetDeclareService {

    @Autowired
    AssetDeclareRepository assetDeclareRepository;
    @Override
    public AssetDeclare save(AssetDeclare declare) {
        return assetDeclareRepository.save(declare);
    }

    @Override
    public BluePrintDeclareDto findBluePrintAssetDeclareByIdAsset(Integer idAsset) {
        Optional<BluePrintDeclareDto> declareDto = assetDeclareRepository.findBluePrintAssetDeclareDtoByIdAsset(idAsset);
        if (declareDto.isEmpty()){
            throw new NotFoundException("Don't exits blue print declare dto by id asset!");
        }
        return declareDto.get();
    }

    @Override
    public void deleteAssetDeclareByIdInstanceAndIdDeclare(Integer idInstance, Integer idDeclare) {
        assetDeclareRepository.deleteAssetDeclareByIdInstanceAndIdDeclare(idInstance, idDeclare);
    }
}
