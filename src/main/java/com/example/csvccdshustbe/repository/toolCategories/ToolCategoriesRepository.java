package com.example.csvccdshustbe.repository.toolCategories;

import com.example.csvccdshustbe.entity.ToolCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolCategoriesRepository extends JpaRepository<ToolCategories, Integer>, ToolCategoriesRepositoryCustom {
}
