package com.example.csvccdshustbe.repository.noShapeOriginalAssetOther;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetOtherDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetOther;

import java.util.Optional;

public interface NoShapeOriginalAssetOtherRepositoryCustom {
    void deleteNoShapeOriginalAssetOtherById(Integer idInstance);

    Optional<NoShapeOriginalAssetOtherDetailsDto> findNoOriginalConnectActorDetailsById(Integer idInstance);

    Optional<NoShapeOriginalAssetOther> findNoShapeOriginalAssetOtherById(Integer idInstance);
}
