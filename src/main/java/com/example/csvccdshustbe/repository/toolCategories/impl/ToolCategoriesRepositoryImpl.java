package com.example.csvccdshustbe.repository.toolCategories.impl;

import com.example.csvccdshustbe.entity.ToolCategories;
import com.example.csvccdshustbe.dto.toolCategories.FindAllToolCategoryDto;
import com.example.csvccdshustbe.entity.AssetCategories;
import com.example.csvccdshustbe.entity.ToolCategories;
import com.example.csvccdshustbe.repository.toolCategories.ToolCategoriesRepositoryCustom;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import com.example.csvccdshustbe.request.assetCategories.FindAllDocumentAssetCategoriesRequest;
import com.example.csvccdshustbe.request.toolCategories.FindAllToolCategoriesRequest;

import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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


    @Override
    public Page<FindAllToolCategoryDto>
    findAllToolCategories(Pageable pageable, FindAllToolCategoriesRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_tool_categories as (  " +
                "                     select toolCategires.id_tool_category,toolCategires.name,  " +
                "                            toolCategires.code_tool, toolCategires.short_name,  " +
                "                            toolCategires.description, toolCategires.parent,  " +
                "                            toolCategires.sort_order, toolCategires.tool_count,  " +
                "                            toolCategires.visible, toolCategires.time_created,  " +
                "                            toolCategires.time_modified,  " +
                "                            1 as depth,     " +
                "                            CAST(toolCategires.id_tool_category as NCHAR ) as path,  " +
                "                            case when toolCategires.parent is not null then toolCategires.name end nameParent,  " +
                "                            toolCategires.id_department_original  " +
                "                     from tool_categories toolCategires  " +
                "                     where toolCategires.parent is null  " +
                "                     union all     " +
                "                     select toolCategires.id_tool_category,toolCategires.name,  " +
                "                            toolCategires.code_tool, toolCategires.short_name,  " +
                "                            toolCategires.description, toolCategires.parent,  " +
                "                            toolCategires.sort_order, toolCategires.tool_count,  " +
                "                            toolCategires.visible, toolCategires.time_created,  " +
                "                            toolCategires.time_modified,  " +
                "                            cte.depth + 1 as depth,     " +
                "                            concat_ws('/',cte.path,CAST(toolCategires.id_tool_category as NCHAR)) as path,  " +
                "                            cte.name nameParent,  " +
                "                            toolCategires.id_department_original  " +
                "                     from tool_categories toolCategires  " +
                "                              INNER JOIN cte_tool_categories cte ON toolCategires.parent = cte.id_tool_category  " +
                "                     )       " +
                "                                select cte.id_tool_category, cte.name,  " +
                "                        cte.code_tool, cte.short_name, cte.description,  " +
                "                        cte.parent, cte.sort_order, cte.tool_count,  " +
                "                        cte.visible, cte.time_created, cte.time_modified,       " +
                "                        cte.depth, cte.path,  " +
                "                        cte.nameParent, cte.id_department_original,  " +
                "                        CASE WHEN EXISTS (     " +
                "                            SELECT 1     " +
                "                            FROM tool_categories toolCategoies  " +
                "                            WHERE toolCategoies.parent = cte.id_tool_category  " +
                "                        ) THEN 0 ELSE 1 END AS is_leaf     " +
                "                from cte_tool_categories cte  " +
                "                 where 1 = 1     " +
                "                 and cte.id_department_original in (:idsDepartmentOriginal) ");
        setConditionFindAllToolCategories(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllToolCategories(request,query);
        PageUtils.buildQuery(pageable, query);
        List<FindAllToolCategoryDto> dtos = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                FindAllToolCategoryDto dto = new FindAllToolCategoryDto();
                dto.setIdToolCategory(ValueUtil.getIntegerByObject(obj[0]));
                dto.setName(ValueUtil.getStringByObject(obj[1]));
                dto.setCodeTool(ValueUtil.getStringByObject(obj[2]));
                dto.setShortName(ValueUtil.getStringByObject(obj[3]));
                dto.setDescription(ValueUtil.getStringByObject(obj[4]));
                dto.setParent(ValueUtil.getIntegerByObject(obj[5]));
                dto.setSortOrder(ValueUtil.getStringByObject(obj[6]));
                dto.setToolCount(ValueUtil.getIntegerByObject(obj[7]));
                dto.setVisible(ValueUtil.getIntegerByObject(obj[8]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[9]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[10]));
                dto.setDepth(ValueUtil.getIntegerByObject(obj[11]));
                dto.setPath(ValueUtil.getStringByObject(obj[12]));
                dto.setNameParent(ValueUtil.getStringByObject(obj[13]));
                dto.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[14]));
                dto.setIsLeaf(ValueUtil.getIntegerByObject(obj[15]));
                dtos.add(dto);
            }
        }
        return new PageImpl<>(dtos, pageable, countFindAllToolCategories(request));
    }

    private long countFindAllToolCategories(FindAllToolCategoriesRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_tool_categories as (  " +
                "                     select toolCategires.id_tool_category,toolCategires.name,  " +
                "                            toolCategires.code_tool, toolCategires.short_name,  " +
                "                            toolCategires.description, toolCategires.parent,  " +
                "                            toolCategires.sort_order, toolCategires.tool_count,  " +
                "                            toolCategires.visible, toolCategires.time_created,  " +
                "                            toolCategires.time_modified,  " +
                "                            1 as depth,     " +
                "                            CAST(toolCategires.id_tool_category as NCHAR ) as path,  " +
                "                            case when toolCategires.parent is not null then toolCategires.name end nameParent,  " +
                "                            toolCategires.id_department_original  " +
                "                     from tool_categories toolCategires  " +
                "                     where toolCategires.parent is null  " +
                "                     union all     " +
                "                     select toolCategires.id_tool_category,toolCategires.name,  " +
                "                            toolCategires.code_tool, toolCategires.short_name,  " +
                "                            toolCategires.description, toolCategires.parent,  " +
                "                            toolCategires.sort_order, toolCategires.tool_count,  " +
                "                            toolCategires.visible, toolCategires.time_created,  " +
                "                            toolCategires.time_modified,  " +
                "                            cte.depth + 1 as depth,     " +
                "                            concat_ws('/',cte.path,CAST(toolCategires.id_tool_category as NCHAR)) as path,  " +
                "                            cte.name nameParent,  " +
                "                            toolCategires.id_department_original  " +
                "                     from tool_categories toolCategires  " +
                "                              INNER JOIN cte_tool_categories cte ON toolCategires.parent = cte.id_tool_category  " +
                "                     )       " +
                "                                select count(0) count " +
                "                from cte_tool_categories cte  " +
                "                 where 1 = 1     " +
                "                 and cte.id_department_original in (:idsDepartmentOriginal) ");
        setConditionCountFindAllToolCategories(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterCountFindAllToolCategories(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }
    private void setConditionFindAllToolCategories(FindAllToolCategoriesRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (cte.name REGEXP :keyword ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            sb.append(" and cte.visible = :visible ");
        }
        sb.append(" group by cte.id_tool_category, cte.name,   " +
                "   cte.code_tool, cte.short_name, cte.description,   " +
                "   cte.parent, cte.sort_order, cte.tool_count,   " +
                "   cte.visible, cte.time_created, cte.time_modified,     " +
                "   cte.depth, cte.path,   " +
                "   cte.nameParent, cte.id_department_original, is_leaf ");
        sb.append(" ORDER BY cte.id_tool_category ");
    }
    private void setParameterFindAllToolCategories(FindAllToolCategoriesRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            query.setParameter("visible", request.getStatus());
        }
    }

    private void setConditionCountFindAllToolCategories(FindAllToolCategoriesRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (cte.name REGEXP :keyword ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            sb.append(" and cte.visible = :visible ");
        }
        sb.append(" ORDER BY cte.id_tool_category ");
    }
    private void setParameterCountFindAllToolCategories(FindAllToolCategoriesRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            query.setParameter("visible", request.getStatus());
        }
    }

    @Override
    public Optional<ToolCategories> findToolCategoryParentByParentId(Integer parentId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select toolCategories.id_tool_category, toolCategories.name,    " +
                "       toolCategories.short_name, toolCategories.code_tool,    " +
                "       toolCategories.description, toolCategories.parent,    " +
                "       toolCategories.sort_order, toolCategories.tool_count,    " +
                "       toolCategories.visible, toolCategories.time_created,    " +
                "       toolCategories.time_modified,    " +
                "       toolCategories.id_department_original         " +
                "       from tool_categories toolCategories           " +
                "       where toolCategories.id_tool_category = :parentId ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("parentId", parentId);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                ToolCategories toolCategories = new ToolCategories();
                toolCategories.setIdToolCategory(ValueUtil.getIntegerByObject(obj[0]));
                toolCategories.setName(ValueUtil.getStringByObject(obj[1]));
                toolCategories.setShortName(ValueUtil.getStringByObject(obj[2]));
                toolCategories.setCodeTool(ValueUtil.getStringByObject(obj[3]));
                toolCategories.setDescription(ValueUtil.getStringByObject(obj[4]));
                toolCategories.setParent(ValueUtil.getIntegerByObject(obj[5]));
                toolCategories.setSortOrder(ValueUtil.getIntegerByObject(obj[6]));
                toolCategories.setToolCount(ValueUtil.getIntegerByObject(obj[7]));
                toolCategories.setVisible(ValueUtil.getIntegerByObject(obj[8]));
                toolCategories.setTimeCreated(ValueUtil.getStringByObject(obj[9]));
                toolCategories.setTimeModified(ValueUtil.getStringByObject(obj[10]));
                toolCategories.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[11]));
                return Optional.of(toolCategories);
            }
        }
        return Optional.empty();
    }

    public boolean checkToolCategoriesByParentIdAndName(Integer parentId, String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select * " +
                "from tool_categories toolCategories " +
                "where toolCategories.parent = :parentId " +
                "and toolCategories.name = :name " );
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("parentId", parentId);
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        return CollectionUtils.isEmpty(result);
    }
    @Override
    public Optional<ToolCategories> findToolCategoryById(Integer toolCategoryId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select toolCategories.id_tool_category, toolCategories.name,    " +
                "       toolCategories.short_name, toolCategories.code_tool,    " +
                "       toolCategories.description, toolCategories.parent,    " +
                "       toolCategories.sort_order, toolCategories.tool_count,    " +
                "       toolCategories.visible, toolCategories.time_created,    " +
                "       toolCategories.time_modified,    " +
                "       toolCategories.id_department_original         " +
                "       from tool_categories toolCategories           " +
                "       where toolCategories.id_tool_category = :toolCategoryId ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("toolCategoryId", toolCategoryId);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                ToolCategories toolCategories = new ToolCategories();
                toolCategories.setIdToolCategory(ValueUtil.getIntegerByObject(obj[0]));
                toolCategories.setName(ValueUtil.getStringByObject(obj[1]));
                toolCategories.setShortName(ValueUtil.getStringByObject(obj[2]));
                toolCategories.setCodeTool(ValueUtil.getStringByObject(obj[3]));
                toolCategories.setDescription(ValueUtil.getStringByObject(obj[4]));
                toolCategories.setParent(ValueUtil.getIntegerByObject(obj[5]));
                toolCategories.setSortOrder(ValueUtil.getIntegerByObject(obj[6]));
                toolCategories.setToolCount(ValueUtil.getIntegerByObject(obj[7]));
                toolCategories.setVisible(ValueUtil.getIntegerByObject(obj[8]));
                toolCategories.setTimeCreated(ValueUtil.getStringByObject(obj[9]));
                toolCategories.setTimeModified(ValueUtil.getStringByObject(obj[10]));
                toolCategories.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[11]));
                return Optional.of(toolCategories);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean  checkExitsToolCategoriesByNameOrShortName(String name, String shortName) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select * " +
                "from tool_categories toolCategories " +
                "where toolCategories.name = :name " +
                "and toolCategories.short_name = :shortName " );
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("name", name);
        query.setParameter("shortName", shortName);
        List<Object[]> result = query.getResultList();
        return CollectionUtils.isEmpty(result);
    }

    @Override
    public List<FindAllToolCategoryDto> findAllToolCategoriesLeafByIdsDepartment(List<Integer> idsDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select result.id_tool_category, result.name, result.code_tool,  " +
                "       result.short_name, result.description, result.parent,  " +
                "       result.sort_order, result.tool_count, result.visible,  " +
                "       result.time_created, result.time_modified, result.depth,  " +
                "       result.path, result.nameParent, result.id_department_original,  " +
                "       result.is_leaf  " +
                "from (WITH RECURSIVE cte_tool_categories as (  " +
                "       select toolCategires.id_tool_category,toolCategires.name,   " +
                "              toolCategires.code_tool, toolCategires.short_name,  " +
                "              toolCategires.description, toolCategires.parent,   " +
                "              toolCategires.sort_order, toolCategires.tool_count,  " +
                "              toolCategires.visible, toolCategires.time_created,  " +
                "              toolCategires.time_modified,  " +
                "              1 as depth,  " +
                "              CAST(toolCategires.id_tool_category as NCHAR ) as path,  " +
                "              case when toolCategires.parent is not null then toolCategires.name end nameParent,  " +
                "              toolCategires.id_department_original  " +
                "       from tool_categories toolCategires  " +
                "       where toolCategires.parent is null  " +
                "       union all  " +
                "       select toolCategires.id_tool_category,toolCategires.name,  " +
                "              toolCategires.code_tool, toolCategires.short_name,  " +
                "              toolCategires.description, toolCategires.parent,  " +
                "              toolCategires.sort_order, toolCategires.tool_count,  " +
                "              toolCategires.visible, toolCategires.time_created,  " +
                "              toolCategires.time_modified,  " +
                "              cte.depth + 1 as depth,  " +
                "              concat_ws('/',cte.path,CAST(toolCategires.id_tool_category as NCHAR)) as path,  " +
                "              cte.name nameParent,  " +
                "              toolCategires.id_department_original  " +
                "       from tool_categories toolCategires  " +
                "                INNER JOIN cte_tool_categories cte ON toolCategires.parent = cte.id_tool_category  " +
                "       )  " +
                "       select cte.id_tool_category, cte.name,  " +
                "          cte.code_tool, cte.short_name, cte.description,  " +
                "          cte.parent, cte.sort_order, cte.tool_count,  " +
                "          cte.visible, cte.time_created, cte.time_modified,  " +
                "          cte.depth, cte.path,  " +
                "          cte.nameParent, cte.id_department_original,  " +
                "          CASE WHEN EXISTS (  " +
                "              SELECT 1  " +
                "              FROM tool_categories toolCategoies  " +
                "              WHERE toolCategoies.parent = cte.id_tool_category  " +
                "          ) THEN 0 ELSE 1 END AS is_leaf   " +
                "  from cte_tool_categories cte  " +
                "   where 1 = 1 and visible = :visible " +
                "   and cte.id_department_original in (:idsDepartmentOriginal) ) result  " +
                "where result.is_leaf = 1 order by result.id_tool_category ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("visible", Constants.TOOL_CATEGORY_IS_VISIBLE);
        query.setParameter("idsDepartmentOriginal", idsDepartment);
        List<FindAllToolCategoryDto> responses = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                responses.add(writeDataFindAllToolCategoryDto(obj));
            }
        }
        return responses;
    }

    @Override
    public List<FindAllToolCategoryDto> findAllToolCategoriesToDownloadAndViewByIdsDepartment(List<Integer> idsDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_tool_categories as (  " +
                "       select toolCategires.id_tool_category,toolCategires.name,   " +
                "              toolCategires.code_tool, toolCategires.short_name,  " +
                "              toolCategires.description, toolCategires.parent,   " +
                "              toolCategires.sort_order, toolCategires.tool_count,  " +
                "              toolCategires.visible, toolCategires.time_created,  " +
                "              toolCategires.time_modified,  " +
                "              1 as depth,  " +
                "              CAST(toolCategires.id_tool_category as NCHAR ) as path,  " +
                "              case when toolCategires.parent is not null then toolCategires.name end nameParent,  " +
                "              toolCategires.id_department_original  " +
                "       from tool_categories toolCategires  " +
                "       where toolCategires.parent is null  " +
                "       union all  " +
                "       select toolCategires.id_tool_category,toolCategires.name,  " +
                "              toolCategires.code_tool, toolCategires.short_name,  " +
                "              toolCategires.description, toolCategires.parent,  " +
                "              toolCategires.sort_order, toolCategires.tool_count,  " +
                "              toolCategires.visible, toolCategires.time_created,  " +
                "              toolCategires.time_modified,  " +
                "              cte.depth + 1 as depth,  " +
                "              concat_ws('/',cte.path,CAST(toolCategires.id_tool_category as NCHAR)) as path,  " +
                "              cte.name nameParent,  " +
                "              toolCategires.id_department_original  " +
                "       from tool_categories toolCategires  " +
                "                INNER JOIN cte_tool_categories cte ON toolCategires.parent = cte.id_tool_category  " +
                "       )  " +
                "       select cte.id_tool_category, cte.name,  " +
                "          cte.code_tool, cte.short_name, cte.description,  " +
                "          cte.parent, cte.sort_order, cte.tool_count,  " +
                "          cte.visible, cte.time_created, cte.time_modified,  " +
                "          cte.depth, cte.path,  " +
                "          cte.nameParent, cte.id_department_original,  " +
                "          CASE WHEN EXISTS (  " +
                "              SELECT 1  " +
                "              FROM tool_categories toolCategoies  " +
                "              WHERE toolCategoies.parent = cte.id_tool_category  " +
                "          ) THEN 0 ELSE 1 END AS is_leaf   " +
                "  from cte_tool_categories cte  " +
                "   where 1 = 1  " +
                "   and cte.id_department_original in (:idsDepartmentOriginal)  " +
                "   and visible = :visible  " +
                "order by path ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsDepartmentOriginal", idsDepartment);
        query.setParameter("visible", Constants.TOOL_CATEGORY_IS_VISIBLE);
        List<FindAllToolCategoryDto> responses = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                responses.add(writeDataFindAllToolCategoryDto(obj));
            }
        }
        return responses;
    }

    private FindAllToolCategoryDto writeDataFindAllToolCategoryDto(Object[] obj) {
        FindAllToolCategoryDto findAllToolCategoryDto = new FindAllToolCategoryDto();
        findAllToolCategoryDto.setIdToolCategory(ValueUtil.getIntegerByObject(obj[0]));
        findAllToolCategoryDto.setName(ValueUtil.getStringByObject(obj[1]));
        findAllToolCategoryDto.setCodeTool(ValueUtil.getStringByObject(obj[2]));
        findAllToolCategoryDto.setShortName(ValueUtil.getStringByObject(obj[3]));
        findAllToolCategoryDto.setDescription(ValueUtil.getStringByObject(obj[4]));
        findAllToolCategoryDto.setParent(ValueUtil.getIntegerByObject(obj[5]));
        findAllToolCategoryDto.setSortOrder(ValueUtil.getStringByObject(obj[6]));
        findAllToolCategoryDto.setToolCount(ValueUtil.getIntegerByObject(obj[7]));
        findAllToolCategoryDto.setVisible(ValueUtil.getIntegerByObject(obj[8]));
        findAllToolCategoryDto.setTimeCreated(ValueUtil.getStringByObject(obj[9]));
        findAllToolCategoryDto.setTimeModified(ValueUtil.getStringByObject(obj[10]));
        findAllToolCategoryDto.setDepth(ValueUtil.getIntegerByObject(obj[11]));
        findAllToolCategoryDto.setPath(ValueUtil.getStringByObject(obj[12]));
        findAllToolCategoryDto.setNameParent(ValueUtil.getStringByObject(obj[13]));
        findAllToolCategoryDto.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[14]));
        findAllToolCategoryDto.setIsLeaf(ValueUtil.getIntegerByObject(obj[15]));
        return findAllToolCategoryDto;
    }

}
