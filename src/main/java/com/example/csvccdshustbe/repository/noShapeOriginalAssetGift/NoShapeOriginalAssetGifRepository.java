package com.example.csvccdshustbe.repository.noShapeOriginalAssetGift;

import com.example.csvccdshustbe.entity.NoShapeOriginalAssetGift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoShapeOriginalAssetGifRepository extends JpaRepository<NoShapeOriginalAssetGift, Integer>,
                NoShapeOriginalAssetGifRepositoryCustom{
}
