package com.example.csvccdshustbe.repository.noShapeOriginalAssetGift;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetGiftDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetGift;

import java.util.Optional;

public interface NoShapeOriginalAssetGifRepositoryCustom {

    Optional<NoShapeOriginalAssetGiftDetailsDto> findShapeOriginalAssetGiftDetailsDtoById(Integer id);

    void deleteNoShapeOriginalAssetGiftById(Integer idInstance);

    Optional<NoShapeOriginalAssetGift> findShapeOriginalAssetGiftById(Integer idInstance);
}
