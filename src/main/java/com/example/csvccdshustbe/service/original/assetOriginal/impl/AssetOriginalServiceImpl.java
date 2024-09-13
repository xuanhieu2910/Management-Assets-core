package com.example.csvccdshustbe.service.original.assetOriginal.impl;

import com.example.csvccdshustbe.dto.original.BluePrintOriginalDto;
import com.example.csvccdshustbe.entity.AssetOriginal;
import com.example.csvccdshustbe.repository.assetOriginal.AssetOriginalRepository;
import com.example.csvccdshustbe.service.original.assetOriginal.AssetOriginalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
public class AssetOriginalServiceImpl implements AssetOriginalService {

    @Autowired
    AssetOriginalRepository assetOriginalRepository;

    @Override
    public AssetOriginal save(AssetOriginal assetOriginal) {
        return assetOriginalRepository.save(assetOriginal);
    }

    @Override
    public BluePrintOriginalDto findBluePrintAssetOriginalByIdAsset(Integer idAsset) {
        Optional<BluePrintOriginalDto> bluePrintOriginalDto = assetOriginalRepository.findBluePrintAssetOriginalByIdAsset(idAsset);
        if (bluePrintOriginalDto.isEmpty()){
            throw new NotFoundException("Don't exits asset original by id asset!");
        }
        return bluePrintOriginalDto.get();
    }

    @Override
    public void deleteAssetOriginalByIdOriginalAndIdInstance(Integer idOriginal, Integer idInstance) {
        assetOriginalRepository.deleteAssetOriginalByIdOriginalAndIdInstance(idOriginal, idInstance);
    }
}
