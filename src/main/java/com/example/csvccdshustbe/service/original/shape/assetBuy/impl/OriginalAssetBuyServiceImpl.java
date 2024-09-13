package com.example.csvccdshustbe.service.original.shape.assetBuy.impl;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetBuyDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetBuy;
import com.example.csvccdshustbe.repository.shapeOriginalAssetBuy.ShapeOriginalAssetByRepository;
import com.example.csvccdshustbe.service.original.shape.assetBuy.OriginalAssetBuyService;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class OriginalAssetBuyServiceImpl implements OriginalAssetBuyService {

    @Autowired
    ShapeOriginalAssetByRepository shapeOriginalAssetByRepository;
    @Override
    public ShapeOriginalAssetBuy save(ShapeOriginalAssetBuy assetBuy) {
        return shapeOriginalAssetByRepository.save(assetBuy);
    }

    @Override
    public Map<String, Object> findOriginalAssetBuyId(Integer idOriginalAssetBuy) throws IllegalAccessException {
        Optional<ShapeOriginalAssetBuyDetailsDto> assetBuyDetailsDto =
                shapeOriginalAssetByRepository.findOriginalAssetBuyDetailsDtoById(idOriginalAssetBuy);
        if (!assetBuyDetailsDto.isPresent()) {
            throw new NotFoundException("Don't exits original asset buy!");
        }
        return ValueUtil.convertObjectToMap(assetBuyDetailsDto.get());
    }


    @Override
    public void deleteShapeOriginalAssetById(Integer idInstance) {
        shapeOriginalAssetByRepository.deleteShapeOriginalAssetById(idInstance);
    }

    @Override
    public ShapeOriginalAssetBuy findShapeOriginalAssetBuyById(Integer idInstance) {
        Optional<ShapeOriginalAssetBuy> shapeOriginalAssetBuy = shapeOriginalAssetByRepository.findShapeOriginalAssetBuyById(idInstance);
        if (shapeOriginalAssetBuy.isEmpty()){
            throw new NotFoundException("Don't exits shape original asset buy by id!");
        }
        return shapeOriginalAssetBuy.get();
    }
}
