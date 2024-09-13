package com.example.csvccdshustbe.repository.noShapeOriginalAssetEvaluate;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetEvaluateDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetEvaluate;

import java.util.Optional;

public interface NoShapeOriginalAssetEvaluateRepositoryCustom {

    Optional<NoShapeOriginalAssetEvaluateDetailsDto> findNoShapeOriginalAssetEvaluateDetailsDtoById(Integer id);

    void deleteNoShapeOriginalAssetEvaluateById(Integer idInstance);

    Optional<NoShapeOriginalAssetEvaluate> findNoShapeOriginalAssetEvaluateById(Integer idInstance);
}
