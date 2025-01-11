package com.example.csvccdshustbe.service.toolCategories;

import com.example.csvccdshustbe.entity.ToolCategories;

public interface ToolCategoriesService {

    ToolCategories findToolCategoryByIdToolCategoryAndVisible(Integer idToolCategory, Integer visible);

}
