package com.example.csvccdshustbe.service.original.shape.assetConnectActor.impl;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetConnectActorDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetConnectActor;
import com.example.csvccdshustbe.repository.shapeOriginalAssetConnectActor.ShapeOriginalAssetConnectActorRepository;
import com.example.csvccdshustbe.service.original.shape.assetConnectActor.OriginalAssetConnectActorService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class OriginalAssetConnectActorServiceImpl implements OriginalAssetConnectActorService {

    @Autowired
    ShapeOriginalAssetConnectActorRepository shapeOriginalAssetConnectActorRepository;


    @Override
    public ShapeOriginalAssetConnectActor save(ShapeOriginalAssetConnectActor shapeOriginalAssetConnectActor) {
        return shapeOriginalAssetConnectActorRepository.save(shapeOriginalAssetConnectActor);
    }

    @Override
    public ShapeOriginalAssetConnectActorDetailsDto findOriginalConnectActorById(Integer idInstance) throws IllegalAccessException {
        Optional<ShapeOriginalAssetConnectActorDetailsDto> detailsDto = shapeOriginalAssetConnectActorRepository.
                findShapeOriginalAssetConnectActorDetailsDtoById(idInstance);
        if (detailsDto.isEmpty()) {
            throw new NotFoundException("Don't exits original connect actor!");
        }
        return detailsDto.get();
    }

    @Override
    public void deleteShapeOriginalAssetConnectActorById(Integer idInstance) {
        shapeOriginalAssetConnectActorRepository.deleteShapeOriginalAssetConnectActorById(idInstance);
    }

    @Override
    public ShapeOriginalAssetConnectActor findShapeOriginalAssetConnectActorById(Integer idInstance) {
        Optional<ShapeOriginalAssetConnectActor> actor =
                shapeOriginalAssetConnectActorRepository.findShapeOriginalAssetConnectActorById(idInstance);
        if (actor.isEmpty()){
            throw new NotFoundException("Don't exits shape original asset connect by id!");
        }
        return actor.get();
    }
}
