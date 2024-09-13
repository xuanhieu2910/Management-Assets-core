package com.example.csvccdshustbe.repository.noShapeOriginalAssetTransferLand;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetTransferLandDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetTransferLand;

import java.util.Optional;

public interface NoShapeOriginalAssetTransferLandRepositoryCustom {

    Optional<NoShapeOriginalAssetTransferLandDetailsDto> findNoShapeOriginalAssetTransferLandDetailsDtoById(Integer id);

    void deleteNoShapeOriginalAssetTransferLandById(Integer idInstance);

    Optional<NoShapeOriginalAssetTransferLand> findNoShapeOriginalAssetTransferLandById(Integer idInstance);
}
