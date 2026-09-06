package com.example.csvccdshustbe.repository.shapeOriginalAssetInvest;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetInvest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShapeOriginalAssetInvestRepository extends JpaRepository<ShapeOriginalAssetInvest, Integer>,
        ShapeOriginalAssetInvestRepositoryCustom {
}
