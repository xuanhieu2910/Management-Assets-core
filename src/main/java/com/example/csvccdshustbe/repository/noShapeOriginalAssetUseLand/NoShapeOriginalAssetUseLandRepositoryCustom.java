package com.example.csvccdshustbe.repository.noShapeOriginalAssetUseLand;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetUseLandDetailsDto;

import java.util.Optional;

public interface NoShapeOriginalAssetUseLandRepositoryCustom {

    Optional<NoShapeOriginalAssetUseLandDetailsDto> findNoShapeOriginalAssetUseLandById(Integer id);

    void deleteNoShapeOriginalAssetUseLandById(Integer idInstance);
}
