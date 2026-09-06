package com.example.csvccdshustbe.repository.shapeOriginalAssetBuy;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetBuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShapeOriginalAssetByRepository extends JpaRepository<ShapeOriginalAssetBuy, Integer>, ShapeOriginalAssetByRepositoryCustom {
}
