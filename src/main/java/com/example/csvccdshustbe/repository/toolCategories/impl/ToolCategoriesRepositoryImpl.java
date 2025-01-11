package com.example.csvccdshustbe.repository.toolCategories.impl;

import com.example.csvccdshustbe.entity.ToolCategories;
import com.example.csvccdshustbe.repository.toolCategories.ToolCategoriesRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class ToolCategoriesRepositoryImpl implements ToolCategoriesRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<ToolCategories> findToolCategoryByIdToolCategoryAndVisible(Integer idToolCategory, Integer visible) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_tool_category, name, short_name,   " +
                "       code_tool, description, parent,   " +
                "       sort_order, tool_count, visible,   " +
                "       time_created, time_modified,  " +
                "       id_department_original  " +
                "from tool_categories  " +
                "where id_tool_category = :idToolCategory  " +
                "and visible = :visible ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idToolCategory", idToolCategory);
        query.setParameter("visible", visible);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                ToolCategories toolCategory = new ToolCategories();
                toolCategory.setIdToolCategory(ValueUtil.getIntegerByObject(obj[0]));
                toolCategory.setName(ValueUtil.getStringByObject(obj[1]));
                toolCategory.setShortName(ValueUtil.getStringByObject(obj[2]));
                toolCategory.setCodeTool(ValueUtil.getStringByObject(obj[3]));
                toolCategory.setDescription(ValueUtil.getStringByObject(obj[4]));
                toolCategory.setParent(ValueUtil.getIntegerByObject(obj[5]));
                toolCategory.setSortOrder(ValueUtil.getIntegerByObject(obj[6]));
                toolCategory.setToolCount(ValueUtil.getIntegerByObject(obj[7]));
                toolCategory.setVisible(ValueUtil.getIntegerByObject(obj[8]));
                toolCategory.setTimeCreated(ValueUtil.getStringByObject(obj[9]));
                toolCategory.setTimeModified(ValueUtil.getStringByObject(obj[10]));
                toolCategory.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[11]));
                return Optional.of(toolCategory);
            }
        }
        return Optional.empty();
    }
}
