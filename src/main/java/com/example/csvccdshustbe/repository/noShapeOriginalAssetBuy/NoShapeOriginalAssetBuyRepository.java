package com.example.csvccdshustbe.repository.noShapeOriginalAssetBuy;

import com.example.csvccdshustbe.entity.NoShapeOriginalAssetBuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoShapeOriginalAssetBuyRepository extends JpaRepository<NoShapeOriginalAssetBuy, Integer>,
                NoShapeOriginalAssetBuyRepositoryCustom{
}
