package com.example.csvccdshustbe.repository.noShapeOriginalAssetTransfer;

import com.example.csvccdshustbe.entity.NoShapeOriginalAssetTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoShapeOriginalAssetTransferRepository extends JpaRepository<NoShapeOriginalAssetTransfer, Integer>,
                 NoShapeOriginalAssetTransferRepositoryCustom{
}
