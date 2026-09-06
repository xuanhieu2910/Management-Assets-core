package com.example.csvccdshustbe.repository.noShapeOriginalAssetUseLand;

import com.example.csvccdshustbe.entity.NoShapeOriginalAssetUseLand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoShapeOriginalAssetUseLandRepository extends JpaRepository<NoShapeOriginalAssetUseLand, Integer>,
                NoShapeOriginalAssetUseLandRepositoryCustom{
}
