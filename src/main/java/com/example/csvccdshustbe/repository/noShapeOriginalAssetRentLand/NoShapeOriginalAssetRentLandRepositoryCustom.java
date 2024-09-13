package com.example.csvccdshustbe.repository.noShapeOriginalAssetRentLand;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetRentLandDetailsDto;

import java.util.Optional;

public interface NoShapeOriginalAssetRentLandRepositoryCustom {

    Optional<NoShapeOriginalAssetRentLandDetailsDto> findNoShapeOriginalAssetRentLandDetailsDtoById(Integer id);

    void deleteNoShapeOriginalAssetRentLandById(Integer idInstance);
}
