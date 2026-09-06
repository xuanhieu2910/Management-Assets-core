package com.example.csvccdshustbe.service.original.shape.assetInvest.impl;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetInvestDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetInvest;
import com.example.csvccdshustbe.repository.shapeOriginalAssetInvest.ShapeOriginalAssetInvestRepository;
import com.example.csvccdshustbe.service.original.shape.assetInvest.OriginalAssetInvestService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class OriginalAssetInvestServiceImpl implements OriginalAssetInvestService {

    @Autowired
    ShapeOriginalAssetInvestRepository shapeOriginalAssetInvestRepository;

    @Override
    public ShapeOriginalAssetInvest save(ShapeOriginalAssetInvest shapeOriginalAssetInvest) {
        return shapeOriginalAssetInvestRepository.save(shapeOriginalAssetInvest);
    }

    @Override
    public ShapeOriginalAssetInvestDetailsDto findOriginalAssetInvestById(Integer idInstance) throws IllegalAccessException {
        Optional<ShapeOriginalAssetInvestDetailsDto> detailsDto =
                shapeOriginalAssetInvestRepository.findOriginalAssetInvestDetailsDtoById(idInstance);
        if (detailsDto.isEmpty()) {
            throw new NotFoundException("Don't exits original asset invest!");
        }
        return detailsDto.get();
    }

    @Override
    public void deleteShapeOriginalAssetInvestById(Integer idInstance) {
        shapeOriginalAssetInvestRepository.deleteShapeOriginalAssetInvestById(idInstance);
    }

    @Override
    public ShapeOriginalAssetInvest findShapeOriginalAssetInvestById(Integer idInstance) {
        Optional<ShapeOriginalAssetInvest> assetInvest =
                shapeOriginalAssetInvestRepository.findOriginalAssetInvestById(idInstance);
        if (assetInvest.isEmpty()){
            throw new NotFoundException("Don't exits original asset invest!");
        }
        return assetInvest.get();
    }
}
