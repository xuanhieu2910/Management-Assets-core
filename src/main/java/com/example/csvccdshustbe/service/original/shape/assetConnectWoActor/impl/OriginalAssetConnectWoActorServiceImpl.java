package com.example.csvccdshustbe.service.original.shape.assetConnectWoActor.impl;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetConnectWoActorDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetConnectWoActor;
import com.example.csvccdshustbe.repository.shapeOriginalAssetConnectWoActor.ShapeOriginalAssetConnectWoActorRepository;
import com.example.csvccdshustbe.service.original.shape.assetConnectWoActor.OriginalAssetConnectWoActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class OriginalAssetConnectWoActorServiceImpl implements OriginalAssetConnectWoActorService {

    @Autowired
    ShapeOriginalAssetConnectWoActorRepository shapeOriginalAssetConnectWoActorRepository;

    @Override
    public ShapeOriginalAssetConnectWoActor save(ShapeOriginalAssetConnectWoActor connectWoActor) {
        return shapeOriginalAssetConnectWoActorRepository.save(connectWoActor);
    }

    @Override
    public Map<String, Object> findOriginalConnectWoActorById(Integer idInstance) {
        Optional<ShapeOriginalAssetConnectWoActorDetailsDto> detailsDto =
                shapeOriginalAssetConnectWoActorRepository.findShapeOriginalAssetConnectWoActorDetailsDtoById(idInstance);
        if (!detailsDto.isPresent()) {
            throw new NotFoundException("Don't exits original connect without actor!");
        }
        return null;
    }
}
