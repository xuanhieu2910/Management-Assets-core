package com.example.csvccdshustbe.repository.noShapeOriginalAssetGift;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetGiftDetailsDto;

import java.util.Optional;

public interface NoShapeOriginalAssetGifRepositoryCustom {

    Optional<NoShapeOriginalAssetGiftDetailsDto> findShapeOriginalAssetGiftDetailsDtoById(Integer id);

}
