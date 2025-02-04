package com.example.csvccdshustbe.service.toolCategories;

import com.example.csvccdshustbe.dto.toolCategories.FindAllToolCategoryDto;
import com.example.csvccdshustbe.entity.ToolCategories;

import com.example.csvccdshustbe.entity.ToolCategories;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.assetCategories.UpdateStatusAssetCategory;
import com.example.csvccdshustbe.request.toolCategories.CreateToolCategoryRequest;
import com.example.csvccdshustbe.request.toolCategories.FindAllToolCategoriesRequest;
import com.example.csvccdshustbe.request.toolCategories.UpdateStatusToolCategoryRequest;
import com.example.csvccdshustbe.request.toolCategories.UpdateToolCategoryRequest;
import com.example.csvccdshustbe.response.toolCategories.FindAllToolCategoriesResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ToolCategoriesService {

    ToolCategories findToolCategoryByIdToolCategoryAndVisible(Integer idToolCategory, Integer visible);
    Page<FindAllToolCategoriesResponse> findAllToolCategories(FindAllToolCategoriesRequest request);
    void createToolCategory(CreateToolCategoryRequest request) throws ValidateFiledException;
    void updateToolCategory(UpdateToolCategoryRequest request) throws ValidateFiledException;
    void deleteToolCategoryByIdAssetCategory(Integer idToolCategory) throws ValidateFiledException;
    ToolCategories findToolCategoryDetailsResponse(Integer idToolCategory);
    void updateStatusToolCategory(UpdateStatusToolCategoryRequest request) throws ValidateFiledException;
    List<FindAllToolCategoryDto> findAllToolCategoriesToDownloadAndSelected();
    List<FindAllToolCategoryDto> findAllToolCategoriesToDownloadAndView();
}
