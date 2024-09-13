package com.example.csvccdshustbe.repository.noShapeOriginalAssetEvaluate;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetEvaluateDetailsDto;

import java.util.Optional;

public interface NoShapeOriginalAssetEvaluateRepositoryCustom {

    Optional<NoShapeOriginalAssetEvaluateDetailsDto> findNoShapeOriginalAssetEvaluateDetailsDto(Integer id);

    void deleteNoShapeOriginalAssetEvaluateById(Integer idInstance);
}
