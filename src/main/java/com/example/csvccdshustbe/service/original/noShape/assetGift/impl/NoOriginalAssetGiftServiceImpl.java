package com.example.csvccdshustbe.service.original.noShape.assetGift.impl;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetGiftDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetGift;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetGift.NoShapeOriginalAssetGifRepository;
import com.example.csvccdshustbe.service.original.noShape.assetGift.NoOriginalAssetGiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
public class NoOriginalAssetGiftServiceImpl implements NoOriginalAssetGiftService {

    @Autowired
    NoShapeOriginalAssetGifRepository noShapeOriginalAssetGifRepository;

    @Override
    public NoShapeOriginalAssetGift save(NoShapeOriginalAssetGift gift) {
        return noShapeOriginalAssetGifRepository.save(gift);
    }

    @Override
    public NoShapeOriginalAssetGiftDetailsDto findNoOriginalAssetGiftById(Integer idInstance) throws IllegalAccessException {
        Optional<NoShapeOriginalAssetGiftDetailsDto> detailsDto =
                noShapeOriginalAssetGifRepository.findShapeOriginalAssetGiftDetailsDtoById(idInstance);
        if (detailsDto.isEmpty()) {
            throw new NotFoundException("Don't exits no shape original asset gift!");
        }
        return detailsDto.get();
    }

    @Override
    public void deleteNoShapeOriginalAssetGiftById(Integer idInstance) {
        noShapeOriginalAssetGifRepository.deleteNoShapeOriginalAssetGiftById(idInstance);
    }

    @Override
    public NoShapeOriginalAssetGift findNoShapeOriginalAssetGiftById(Integer idInstance) {
        Optional<NoShapeOriginalAssetGift> noAssetGift =
                noShapeOriginalAssetGifRepository.findShapeOriginalAssetGiftById(idInstance);
        if (noAssetGift.isEmpty()) {
            throw new NotFoundException("Don't exits no shape original asset gift!");
        }
        return noAssetGift.get();
    }
}
