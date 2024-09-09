package com.example.csvccdshustbe.service.original.noShape.assetGift.impl;

import com.example.csvccdshustbe.entity.NoShapeOriginalAssetGift;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetGift.NoShapeOriginalAssetGifRepository;
import com.example.csvccdshustbe.service.original.noShape.assetGift.NoOriginalAssetGiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoOriginalAssetGiftServiceImpl implements NoOriginalAssetGiftService {

    @Autowired
    NoShapeOriginalAssetGifRepository noShapeOriginalAssetGifRepository;

    @Override
    public NoShapeOriginalAssetGift save(NoShapeOriginalAssetGift gift) {
        return noShapeOriginalAssetGifRepository.save(gift);
    }
}
