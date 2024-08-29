package com.example.csvccdshustbe.repository.assetCategories;

import com.example.csvccdshustbe.entity.AssetCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetCategoriesRepository extends JpaRepository<AssetCategories, Integer>, AssetCategoriesRepositoryCustom {
}
