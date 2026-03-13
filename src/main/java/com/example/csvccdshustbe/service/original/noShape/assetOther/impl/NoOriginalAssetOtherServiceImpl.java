package com.example.csvccdshustbe.service.original.noShape.assetOther.impl;


import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetOtherDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetOther;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetOther.NoShapeOriginalAssetOtherRepository;
import com.example.csvccdshustbe.service.original.noShape.assetOther.NoOriginalAssetOtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
public class NoOriginalAssetOtherServiceImpl implements NoOriginalAssetOtherService {

    @Autowired
    NoShapeOriginalAssetOtherRepository noShapeOriginalAssetOtherRepository;

    @Override
    public NoShapeOriginalAssetOther save(NoShapeOriginalAssetOther noShapeOriginalAssetOther) {
        return noShapeOriginalAssetOtherRepository.save(noShapeOriginalAssetOther);
    }

    @Override
    public NoShapeOriginalAssetOtherDetailsDto findNoOriginalConnectActorById(Integer idInstance) throws IllegalAccessException {
        Optional<NoShapeOriginalAssetOtherDetailsDto> detailsDto = noShapeOriginalAssetOtherRepository.findNoOriginalConnectActorDetailsById(idInstance);
        if (detailsDto.isEmpty()) {
            throw new NotFoundException("Don't exits original other actor!");
        }
        return detailsDto.get();
    }

    @Override
    public void deleteNoShapeOriginalAssetOtherById(Integer idInstance) {
        noShapeOriginalAssetOtherRepository.deleteNoShapeOriginalAssetOtherById(idInstance);
    }

    @Override
    public NoShapeOriginalAssetOther findNoShapeOriginalAssetOtherById(Integer idInstance) {
        Optional<NoShapeOriginalAssetOther> noShapeOriginalAssetOther = noShapeOriginalAssetOtherRepository.findNoShapeOriginalAssetOtherById(idInstance);
        if (noShapeOriginalAssetOther.isEmpty()){
            throw new NotFoundException("Don't exits no shape original other by id!");
        }
        return noShapeOriginalAssetOther.get();
    }
}
