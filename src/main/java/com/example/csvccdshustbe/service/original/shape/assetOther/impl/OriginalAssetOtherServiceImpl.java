package com.example.csvccdshustbe.service.original.shape.assetOther.impl;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetOtherDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetOther;
import com.example.csvccdshustbe.repository.shapeOriginalAssetOther.ShapeOriginalAssetOtherRepository;
import com.example.csvccdshustbe.service.original.shape.assetOther.OriginalAssetOtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
public class OriginalAssetOtherServiceImpl implements OriginalAssetOtherService {

    @Autowired
    ShapeOriginalAssetOtherRepository shapeOriginalAssetOtherRepository;

    @Override
    public ShapeOriginalAssetOther save(ShapeOriginalAssetOther shapeOriginalAssetOther) {
        return shapeOriginalAssetOtherRepository.save(shapeOriginalAssetOther);
    }

    @Override
    public ShapeOriginalAssetOtherDetailsDto findOriginalConnectActorById(Integer idInstance) throws IllegalAccessException {
        Optional<ShapeOriginalAssetOtherDetailsDto> detailsDto = shapeOriginalAssetOtherRepository.findOriginalConnectActorDetailsDtoById(idInstance);
        if (detailsDto.isEmpty()) {
            throw new NotFoundException("Don't exits original other!");
        }
        return detailsDto.get();
    }

    @Override
    public void deleteShapeOriginalAssetOtherById(Integer idInstance) {
        shapeOriginalAssetOtherRepository.deleteShapeOriginalAssetOtherById(idInstance);
    }

    @Override
    public ShapeOriginalAssetOther findShapeOriginalAssetOtherById(Integer idInstance) {
        Optional<ShapeOriginalAssetOther> shapeOriginalAssetOther = shapeOriginalAssetOtherRepository.findOriginalConnectActorDetailsById(idInstance);
        if (shapeOriginalAssetOther.isEmpty()) {
            throw new NotFoundException("Don't exits original other!");
        }
        return shapeOriginalAssetOther.get();
    }
}
