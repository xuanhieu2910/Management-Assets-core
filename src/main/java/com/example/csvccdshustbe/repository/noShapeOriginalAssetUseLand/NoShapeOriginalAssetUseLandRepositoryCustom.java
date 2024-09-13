package com.example.csvccdshustbe.repository.noShapeOriginalAssetUseLand;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetUseLandDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetUseLand;

import java.util.Optional;

public interface NoShapeOriginalAssetUseLandRepositoryCustom {

    Optional<NoShapeOriginalAssetUseLandDetailsDto> findNoShapeOriginalAssetUseLandDetailsById(Integer id);

    void deleteNoShapeOriginalAssetUseLandById(Integer idInstance);

    Optional<NoShapeOriginalAssetUseLand> findNoShapeOriginalAssetUseLandById(Integer idInstance);
}
