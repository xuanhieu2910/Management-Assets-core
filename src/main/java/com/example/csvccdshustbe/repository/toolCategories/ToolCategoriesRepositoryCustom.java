package com.example.csvccdshustbe.repository.toolCategories;

import com.example.csvccdshustbe.entity.ToolCategories;

import java.util.List;
import java.util.Optional;

import com.example.csvccdshustbe.dto.toolCategories.FindAllToolCategoryDto;
import com.example.csvccdshustbe.entity.ToolCategories;
import com.example.csvccdshustbe.request.toolCategories.FindAllToolCategoriesRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ToolCategoriesRepositoryCustom {
    Optional<ToolCategories> findToolCategoryByIdToolCategoryAndVisible(Integer idToolCategory, Integer visible);

    Page<FindAllToolCategoryDto>
    findAllToolCategories(Pageable pageable, FindAllToolCategoriesRequest request);
    Optional<ToolCategories> findToolCategoryParentByParentId(Integer parentId);
    boolean checkToolCategoriesByParentIdAndName(Integer parentId, String name);
    Optional<ToolCategories> findToolCategoryById(Integer toolCategoryId);
    boolean checkExitsToolCategoriesByNameOrShortName(String name, String shortName);
    List<FindAllToolCategoryDto> findAllToolCategoriesLeafByIdsDepartment(List<Integer> idsDepartment);
    List<FindAllToolCategoryDto> findAllToolCategoriesToDownloadAndViewByIdsDepartment(List<Integer> idsDepartment);
}
