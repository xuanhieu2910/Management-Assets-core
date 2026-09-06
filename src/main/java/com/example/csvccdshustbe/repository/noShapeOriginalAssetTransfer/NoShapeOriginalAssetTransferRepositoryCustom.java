package com.example.csvccdshustbe.repository.noShapeOriginalAssetTransfer;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetTransferDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetTransfer;

import java.util.Optional;

public interface NoShapeOriginalAssetTransferRepositoryCustom {

    Optional<NoShapeOriginalAssetTransferDetailsDto> findNoShapeOriginalAssetTransferDetailsDtoById(Integer id);

    void deleteNoShapeOriginalAssetTransferById(Integer idInstance);

    Optional<NoShapeOriginalAssetTransfer> findNoShapeOriginalAssetTransferById(Integer idInstance);
}
