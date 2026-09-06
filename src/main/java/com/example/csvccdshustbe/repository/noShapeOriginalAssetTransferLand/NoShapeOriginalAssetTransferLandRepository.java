package com.example.csvccdshustbe.repository.noShapeOriginalAssetTransferLand;

import com.example.csvccdshustbe.entity.NoShapeOriginalAssetTransferLand;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetTransfer.NoShapeOriginalAssetTransferRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoShapeOriginalAssetTransferLandRepository extends JpaRepository<NoShapeOriginalAssetTransferLand, Integer>,
        NoShapeOriginalAssetTransferLandRepositoryCustom {
}
