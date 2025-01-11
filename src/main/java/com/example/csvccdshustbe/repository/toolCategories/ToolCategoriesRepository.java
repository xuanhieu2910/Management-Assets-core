package com.example.csvccdshustbe.repository.toolCategories;

import com.example.csvccdshustbe.dto.assetCategories.FindAllAssetCategoryDto;
import com.example.csvccdshustbe.dto.toolCategories.FindAllToolCategoryDto;
import com.example.csvccdshustbe.entity.ToolCategories;

import com.example.csvccdshustbe.request.toolCategories.FindAllToolCategoriesRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolCategoriesRepository extends JpaRepository<ToolCategories, Integer>, ToolCategoriesRepositoryCustom {


}
