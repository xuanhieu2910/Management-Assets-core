package com.example.csvccdshustbe.service.toolCategories.impl;

import com.example.csvccdshustbe.entity.ToolCategories;
import com.example.csvccdshustbe.repository.toolCategories.ToolCategoriesRepository;
import com.example.csvccdshustbe.service.toolCategories.ToolCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
public class ToolCategoriesServiceImpl implements ToolCategoriesService {

    @Autowired
    ToolCategoriesRepository toolCategoriesRepository;

    @Override
    public ToolCategories findToolCategoryByIdToolCategoryAndVisible(Integer idToolCategory, Integer visible) {
        Optional<ToolCategories> toolCategory =
                toolCategoriesRepository.findToolCategoryByIdToolCategoryAndVisible(idToolCategory, visible);
        if (toolCategory.isEmpty()) {
            throw new NotFoundException("Don't exits tool category by id tool category and visible");
        }
        return toolCategory.get();
    }
}
