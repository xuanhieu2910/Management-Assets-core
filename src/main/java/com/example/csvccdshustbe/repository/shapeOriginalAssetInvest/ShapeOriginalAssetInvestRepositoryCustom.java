package com.example.csvccdshustbe.repository.shapeOriginalAssetInvest;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetInvestDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetInvest;

import java.util.Optional;

public interface ShapeOriginalAssetInvestRepositoryCustom {

    Optional<ShapeOriginalAssetInvestDetailsDto> findOriginalAssetInvestDetailsDtoById(Integer id);

    void deleteShapeOriginalAssetInvestById(Integer idInstance);

    Optional<ShapeOriginalAssetInvest> findOriginalAssetInvestById(Integer idInstance);
}
