package com.example.csvccdshustbe.repository.noShapeOriginalAssetBuy;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetBuyDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetBuy;

import java.util.Optional;

public interface NoShapeOriginalAssetBuyRepositoryCustom {

    Optional<NoShapeOriginalAssetBuyDetailsDto> findNoShapeOriginalAssetBuyDetailsBuyId(Integer id);

    void deleteNoShapeOriginalAssetBuyById(Integer idInstance);

    Optional<NoShapeOriginalAssetBuy> findNoShapeOriginalAssetBuyById(Integer idInstance);
}
