package com.example.csvccdshustbe.repository.shapeOriginalAssetInvest;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetInvestDetailsDto;

import java.util.Optional;

public interface ShapeOriginalAssetInvestRepositoryCustom {

    Optional<ShapeOriginalAssetInvestDetailsDto> findOriginalAssetInvestDetailsDtoById(Integer id);

    void deleteShapeOriginalAssetInvestById(Integer idInstance);
}
