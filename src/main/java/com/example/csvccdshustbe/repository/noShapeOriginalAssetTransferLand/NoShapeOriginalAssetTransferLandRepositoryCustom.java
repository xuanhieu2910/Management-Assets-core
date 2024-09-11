package com.example.csvccdshustbe.repository.noShapeOriginalAssetTransferLand;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetTransferLandDetailsDto;

import java.util.Optional;

public interface NoShapeOriginalAssetTransferLandRepositoryCustom {

    Optional<NoShapeOriginalAssetTransferLandDetailsDto> findNoShapeOriginalAssetTransferLandDetailsDtoById(Integer id);
}
