package com.example.csvccdshustbe.service.original.noShape.assetBuy.impl;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetBuyDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetBuy;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetBuy.NoShapeOriginalAssetBuyRepository;
import com.example.csvccdshustbe.service.original.noShape.assetBuy.NoOriginalAssetBuyService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class NoOriginalAssetBuyServiceImpl implements NoOriginalAssetBuyService {

    @Autowired
    NoShapeOriginalAssetBuyRepository noShapeOriginalAssetBuyRepository;


    @Override
    public NoShapeOriginalAssetBuy save(NoShapeOriginalAssetBuy assetBuy) {
        return noShapeOriginalAssetBuyRepository.save(assetBuy);
    }

    @Override
    public Map<String, Object> findNoOriginalAssetBuyId(Integer idInstance) throws IllegalAccessException {
        Optional<NoShapeOriginalAssetBuyDetailsDto> detailsDto =
                noShapeOriginalAssetBuyRepository.findNoShapeOriginalAssetBuyDetailsBuyId(idInstance);
        if (!detailsDto.isPresent()){
            throw new NotFoundException("Don't exits no shape original asset buy!");
        }
        return ValueUtil.convertObjectToMap(detailsDto.get());
    }

    @Override
    public void deleteNoShapeOriginalAssetById(Integer idInstance) {
        noShapeOriginalAssetBuyRepository.deleteNoShapeOriginalAssetBuyById(idInstance);
    }
}
