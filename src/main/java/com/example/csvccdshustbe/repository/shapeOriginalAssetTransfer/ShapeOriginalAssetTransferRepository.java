package com.example.csvccdshustbe.repository.shapeOriginalAssetTransfer;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShapeOriginalAssetTransferRepository extends JpaRepository<ShapeOriginalAssetTransfer, Integer>,
        ShapeOriginalAssetTransferRepositoryCustom{
}
