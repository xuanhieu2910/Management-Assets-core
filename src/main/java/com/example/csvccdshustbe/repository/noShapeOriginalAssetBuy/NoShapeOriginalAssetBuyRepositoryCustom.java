package com.example.csvccdshustbe.repository.noShapeOriginalAssetBuy;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetBuyDetailsDto;

import java.util.Optional;

public interface NoShapeOriginalAssetBuyRepositoryCustom {

    Optional<NoShapeOriginalAssetBuyDetailsDto> findNoShapeOriginalAssetBuyDetailsBuyId(Integer id);
}
