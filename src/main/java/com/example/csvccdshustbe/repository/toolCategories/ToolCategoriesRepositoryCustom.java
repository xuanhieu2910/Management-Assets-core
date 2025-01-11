package com.example.csvccdshustbe.repository.toolCategories;

import com.example.csvccdshustbe.entity.ToolCategories;

import java.util.Optional;

public interface ToolCategoriesRepositoryCustom {
    Optional<ToolCategories> findToolCategoryByIdToolCategoryAndVisible(Integer idToolCategory, Integer visible);
}
