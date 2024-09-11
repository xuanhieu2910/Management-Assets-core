package com.example.csvccdshustbe.repository.noShapeOriginalAssetTransfer;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetTransferDetailsDto;

import java.util.Optional;

public interface NoShapeOriginalAssetTransferRepositoryCustom {

    Optional<NoShapeOriginalAssetTransferDetailsDto> findNoShapeOriginalAssetTransferDetailsDtoById(Integer id);
}
