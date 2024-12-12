package com.example.csvccdshustbe.repository.assetCategories.impl;


import com.example.csvccdshustbe.dto.assetCategories.*;
import com.example.csvccdshustbe.entity.AssetCategories;
import com.example.csvccdshustbe.repository.assetCategories.AssetCategoriesRepositoryCustom;
import com.example.csvccdshustbe.request.assetCategories.FindAllAssetCategoriesVisibleRequest;
import com.example.csvccdshustbe.request.assetCategories.FindAllDocumentAssetCategoriesRequest;
import com.example.csvccdshustbe.response.assetCategories.FindAssetCategoryDetailsResponse;
import com.example.csvccdshustbe.utility.Constants;
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

import java.util.*;

public class AssetCategoriesRepositoryImpl implements AssetCategoriesRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<FindAllAssetCategoriesPickedDto> findAllAssetCategoriesIsPickedAndVisible() {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_asset_category as (        " +
                "       select assetCategories.id_asset_category, assetCategories.name, assetCategories.short_name,        " +
                "              assetCategories.code_name, assetCategories.description, assetCategories.parent,        " +
                "              assetCategories.sort_order, assetCategories.asset_count,        " +
                "              assetCategories.visible, assetCategories.time_created, assetCategories.time_modified,        " +
                "              assetCategories.path_image,assetCategories.is_pick ,        " +
                "              assetCategories.id_asset_category as idParent        " +
                "       from asset_categories   assetCategories        " +
                "       where assetCategories.parent is null  " +
                "       union all            " +
                "       select assetCategories.id_asset_category, assetCategories.name,        " +
                "              assetCategories.short_name, assetCategories.code_name,        " +
                "              assetCategories.description, assetCategories.parent,        " +
                "              assetCategories.sort_order, assetCategories.asset_count,        " +
                "              assetCategories.visible, assetCategories.time_created,        " +
                "              assetCategories.time_modified, assetCategories.path_image,        " +
                "              assetCategories.is_pick,        " +
                "              cte.id_asset_category as idParent        " +
                "       from asset_categories assetCategories  " +
                "                INNER JOIN cte_asset_category cte ON assetCategories.parent = cte.id_asset_category  " +
                "                    )            " +
                "select cte.id_asset_category, cte.name, cte.short_name,  " +
                "        cte.code_name, cte.path_image,  cte.idParent        " +
                "from cte_asset_category cte  " +
                "      where cte.is_pick = :isPicked  " +
                "      and cte.visible = :isVisible ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("isPicked", Constants.IS_PICKED);
        query.setParameter("isVisible", Constants.IS_VISIBLE);
        List<Object[]> result = query.getResultList();
        List<FindAllAssetCategoriesPickedDto> assetCategories = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                FindAllAssetCategoriesPickedDto pickedDto = new FindAllAssetCategoriesPickedDto();
                pickedDto.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[0]));
                pickedDto.setName(ValueUtil.getStringByObject(obj[1]));
                pickedDto.setShortName(ValueUtil.getStringByObject(obj[2]));
                pickedDto.setCodeName(ValueUtil.getStringByObject(obj[3]));
                pickedDto.setPathImage(ValueUtil.getStringByObject(obj[4]));
                pickedDto.setIdParent(ValueUtil.getIntegerByObject(obj[5]));
                assetCategories.add(pickedDto);
            }
        }
        return assetCategories;
    }

    @Override
    public Page<FindAllAssetCategoriesByCodeAndVisibleDto>
    findAllAssetCategoriesByCodeAndVisible(Pageable pageable, FindAllAssetCategoriesVisibleRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_asset_categories as (        " +
                "       select assetCategires.id_asset_category,assetCategires.name,        " +
                "              assetCategires.code_name, assetCategires.short_name,        " +
                "              assetCategires.description, assetCategires.parent,        " +
                "              assetCategires.sort_order, assetCategires.asset_count,        " +
                "              assetCategires.visible, assetCategires.time_created,        " +
                "              assetCategires.time_modified, assetCategires.is_pick,        " +
                "              1 as depth,        " +
                "              CAST(assetCategires.id_asset_category as NCHAR ) as path,     " +
                "              assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,     " +
                "              assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,  " +
                "              assetCategires.id_department_original,  " +
                "              assetCategires.number_code_pattern " +
                "       from asset_categories assetCategires        " +
                "       where assetCategires.code_name = :codeName        " +
                "       and assetCategires.visible = :visible        " +
                "       union all        " +
                "       select assetCategires.id_asset_category,assetCategires.name,        " +
                "              assetCategires.code_name, assetCategires.short_name,        " +
                "              assetCategires.description, assetCategires.parent,        " +
                "              assetCategires.sort_order, assetCategires.asset_count,        " +
                "              assetCategires.visible, assetCategires.time_created,        " +
                "              assetCategires.time_modified, assetCategires.is_pick,        " +
                "              cte.depth + 1 as depth,        " +
                "              concat_ws('/',cte.path,CAST(assetCategires.id_asset_category as NCHAR)) as path,     " +
                "              assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,     " +
                "              assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,  " +
                "              assetCategires.id_department_original,  " +
                "              assetCategires.number_code_pattern  " +
                "       from asset_categories assetCategires        " +
                "                INNER JOIN cte_asset_categories cte ON assetCategires.parent = cte.id_asset_category        " +
                "       )        " +
                "select cte.id_asset_category, cte.name,  " +
                "       cte.code_name, cte.short_name, cte.description,  " +
                "       cte.parent, cte.sort_order, cte.asset_count,  " +
                "       cte.visible, cte.time_created, cte.time_modified,  " +
                "       cte.is_pick, cte.depth, cte.path,  " +
                "       cte.value_wear_tear, cte.year_used_wear_tear,  " +
                "       cte.minimum_time_depreciation, cte.maximum_time_depreciation,  " +
                "       cte.number_code_pattern " +
                "from cte_asset_categories cte  " +
                "where 1 = 1 and id_department_original in (:idsDepartment) ");
        setConditionFindAllAssetCategoriesByCodeAndVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetCategoriesByCodeAndVisible(request,query);
        PageUtils.buildQuery(pageable, query);
        List<FindAllAssetCategoriesByCodeAndVisibleDto> dtos = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                FindAllAssetCategoriesByCodeAndVisibleDto dto = new FindAllAssetCategoriesByCodeAndVisibleDto();
                dto.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[0]));
                dto.setName(ValueUtil.getStringByObject(obj[1]));
                dto.setCodeName(ValueUtil.getStringByObject(obj[2]));
                dto.setShortName(ValueUtil.getStringByObject(obj[3]));
                dto.setDescription(ValueUtil.getStringByObject(obj[4]));
                dto.setParent(ValueUtil.getIntegerByObject(obj[5]));
                dto.setSortOrder(ValueUtil.getStringByObject(obj[6]));
                dto.setAssetCount(ValueUtil.getIntegerByObject(obj[7]));
                dto.setVisible(ValueUtil.getIntegerByObject(obj[8]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[9]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[10]));
                dto.setIsPick(ValueUtil.getIntegerByObject(obj[11]));
                dto.setDepth(ValueUtil.getIntegerByObject(obj[12]));
                dto.setPath(ValueUtil.getStringByObject(obj[13]));
                dto.setValueWearTear(ValueUtil.getStringByObject(obj[14]));
                dto.setYearUsedWearTear(ValueUtil.getStringByObject(obj[15]));
                dto.setMinimumTimeDepreciation(ValueUtil.getStringByObject(obj[16]));
                dto.setMaximumTimeDepreciation(ValueUtil.getStringByObject(obj[17]));
                dto.setNumberCodePattern(ValueUtil.getStringByObject(obj[18]));
                dtos.add(dto);
            }
        }
        return new PageImpl<>(dtos, pageable, countFindAllAssetCategoriesByCodeAndVisible(request));
    }

    @Override
    public Page<FindAllAssetCategoriesByCodeAndVisibleDto>
    findAllAssetCategoriesVisible(Pageable pageable, FindAllAssetCategoriesVisibleRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_asset_categories as (        " +
                "       select assetCategires.id_asset_category,assetCategires.name,        " +
                "              assetCategires.code_name, assetCategires.short_name,        " +
                "              assetCategires.description, assetCategires.parent,        " +
                "              assetCategires.sort_order, assetCategires.asset_count,        " +
                "              assetCategires.visible, assetCategires.time_created,        " +
                "              assetCategires.time_modified, assetCategires.is_pick,        " +
                "              1 as depth,        " +
                "              CAST(assetCategires.id_asset_category as NCHAR ) as path,     " +
                "              assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,     " +
                "              assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,  " +
                "              assetCategires.id_department_original  " +
                "       from asset_categories assetCategires        " +
                "       where assetCategires.parent is null      " +
                "       and assetCategires.visible = :visible        " +
                "       union all        " +
                "       select assetCategires.id_asset_category,assetCategires.name,        " +
                "              assetCategires.code_name, assetCategires.short_name,        " +
                "              assetCategires.description, assetCategires.parent,        " +
                "              assetCategires.sort_order, assetCategires.asset_count,        " +
                "              assetCategires.visible, assetCategires.time_created,        " +
                "              assetCategires.time_modified, assetCategires.is_pick,        " +
                "              cte.depth + 1 as depth,        " +
                "              concat_ws('/',cte.path,CAST(assetCategires.id_asset_category as NCHAR)) as path,     " +
                "              assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,     " +
                "              assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,  " +
                "              assetCategires.id_department_original  " +
                "       from asset_categories assetCategires        " +
                "                INNER JOIN cte_asset_categories cte ON assetCategires.parent = cte.id_asset_category        " +
                "       )        " +
                "  select cte.id_asset_category, cte.name,        " +
                "          cte.code_name, cte.short_name, cte.description,        " +
                "          cte.parent, cte.sort_order, cte.asset_count,        " +
                "          cte.visible, cte.time_created, cte.time_modified,        " +
                "          cte.is_pick, cte.depth, cte.path,     " +
                "          cte.value_wear_tear, cte.year_used_wear_tear,      " +
                "          cte.minimum_time_depreciation, cte.maximum_time_depreciation     " +
                "   from cte_asset_categories cte        " +
                "   where 1 = 1 and cte.id_department_original in (:idsDepartment) ");
        setConditionFindAllAssetCategoriesVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetCategoriesVisible(request,query);
        PageUtils.buildQuery(pageable, query);
        List<FindAllAssetCategoriesByCodeAndVisibleDto> dtos = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                FindAllAssetCategoriesByCodeAndVisibleDto dto = new FindAllAssetCategoriesByCodeAndVisibleDto();
                dto.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[0]));
                dto.setName(ValueUtil.getStringByObject(obj[1]));
                dto.setCodeName(ValueUtil.getStringByObject(obj[2]));
                dto.setShortName(ValueUtil.getStringByObject(obj[3]));
                dto.setDescription(ValueUtil.getStringByObject(obj[4]));
                dto.setParent(ValueUtil.getIntegerByObject(obj[5]));
                dto.setSortOrder(ValueUtil.getStringByObject(obj[6]));
                dto.setAssetCount(ValueUtil.getIntegerByObject(obj[7]));
                dto.setVisible(ValueUtil.getIntegerByObject(obj[8]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[9]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[10]));
                dto.setIsPick(ValueUtil.getIntegerByObject(obj[11]));
                dto.setDepth(ValueUtil.getIntegerByObject(obj[12]));
                dto.setPath(ValueUtil.getStringByObject(obj[13]));
                dto.setValueWearTear(ValueUtil.getStringByObject(obj[14]));
                dto.setYearUsedWearTear(ValueUtil.getStringByObject(obj[15]));
                dto.setMinimumTimeDepreciation(ValueUtil.getStringByObject(obj[16]));
                dto.setMaximumTimeDepreciation(ValueUtil.getStringByObject(obj[17]));
                dtos.add(dto);
            }
        }
        return new PageImpl<>(dtos, pageable, countFindAllAssetCategoriesVisible(request));
    }

    private long countFindAllAssetCategoriesVisible(FindAllAssetCategoriesVisibleRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_asset_categories as (           " +
                "      select assetCategires.id_asset_category,assetCategires.name,           " +
                "             assetCategires.code_name, assetCategires.short_name,           " +
                "             assetCategires.description, assetCategires.parent,           " +
                "             assetCategires.sort_order, assetCategires.asset_count,           " +
                "             assetCategires.visible, assetCategires.time_created,           " +
                "             assetCategires.time_modified, assetCategires.is_pick,           " +
                "             1 as depth,           " +
                "             CAST(assetCategires.id_asset_category as NCHAR ) as path,        " +
                "             assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,        " +
                "             assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,     " +
                "             assetCategires.id_department_original     " +
                "      from asset_categories assetCategires           " +
                "      where assetCategires.parent is null         " +
                "      and assetCategires.visible = :visible           " +
                "      union all           " +
                "      select assetCategires.id_asset_category,assetCategires.name,           " +
                "             assetCategires.code_name, assetCategires.short_name,           " +
                "             assetCategires.description, assetCategires.parent,           " +
                "             assetCategires.sort_order, assetCategires.asset_count,           " +
                "             assetCategires.visible, assetCategires.time_created,           " +
                "             assetCategires.time_modified, assetCategires.is_pick,           " +
                "             cte.depth + 1 as depth,           " +
                "             concat_ws('/',cte.path,CAST(assetCategires.id_asset_category as NCHAR)) as path,        " +
                "             assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,        " +
                "             assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,     " +
                "             assetCategires.id_department_original     " +
                "      from asset_categories assetCategires           " +
                "               INNER JOIN cte_asset_categories cte ON assetCategires.parent = cte.id_asset_category           " +
                "      )           " +
                " select count(0) count  " +
                "  from cte_asset_categories cte           " +
                "  where 1 = 1 and cte.id_department_original in (:idsDepartment)  ");
        setConditionFindAllAssetCategoriesVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetCategoriesVisible(request,query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllAssetCategoriesVisible(FindAllAssetCategoriesVisibleRequest request, Query query) {
        query.setParameter("visible", Constants.IS_VISIBLE);
        query.setParameter("idsDepartment", request.getIdsDepartment());
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
    }

    @Override
    public Page<FindAllAssetCategoryDto> findAllAssetCategories(Pageable pageable, FindAllDocumentAssetCategoriesRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_asset_categories as (  " +
                "      select assetCategires.id_asset_category,assetCategires.name,  " +
                "             assetCategires.code_name, assetCategires.short_name,  " +
                "             assetCategires.description, assetCategires.parent,  " +
                "             assetCategires.sort_order, assetCategires.asset_count,  " +
                "             assetCategires.visible, assetCategires.time_created,  " +
                "             assetCategires.time_modified, assetCategires.is_pick,  " +
                "             1 as depth,  " +
                "             CAST(assetCategires.id_asset_category as NCHAR ) as path,  " +
                "             assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,  " +
                "             assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,  " +
                "             case when assetCategires.parent is not null then assetCategires.name end nameParent,  " +
                "             assetCategires.id_department_original,   " +
                "             assetCategires.number_code_pattern  " +
                "      from asset_categories assetCategires  " +
                "      where assetCategires.parent is null  " +
                "      union all  " +
                "      select assetCategires.id_asset_category,assetCategires.name,            " +
                "             assetCategires.code_name, assetCategires.short_name,            " +
                "             assetCategires.description, assetCategires.parent,            " +
                "             assetCategires.sort_order, assetCategires.asset_count,            " +
                "             assetCategires.visible, assetCategires.time_created,            " +
                "             assetCategires.time_modified, assetCategires.is_pick,            " +
                "             cte.depth + 1 as depth,  " +
                "             concat_ws('/',cte.path,CAST(assetCategires.id_asset_category as NCHAR)) as path,  " +
                "             assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,  " +
                "             assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,  " +
                "             cte.name nameParent,  " +
                "             assetCategires.id_department_original,   " +
                "             assetCategires.number_code_pattern  " +
                "      from asset_categories assetCategires  " +
                "               INNER JOIN cte_asset_categories cte ON assetCategires.parent = cte.id_asset_category  " +
                "      )  " +
                "select cte.id_asset_category, cte.name,  " +
                "         cte.code_name, cte.short_name, cte.description,  " +
                "         cte.parent, cte.sort_order, cte.asset_count,  " +
                "         cte.visible, cte.time_created, cte.time_modified,  " +
                "         cte.is_pick, cte.depth, cte.path,  " +
                "         cte.value_wear_tear, cte.year_used_wear_tear,  " +
                "         cte.minimum_time_depreciation, cte.maximum_time_depreciation,  " +
                "         cte.nameParent, cte.id_department_original, cte.number_code_pattern,  " +
                "         group_concat(concat_ws(':', units.id_unit, units.name) SEPARATOR  '/') nameUnits  " +
                "from cte_asset_categories cte  " +
                "         left join (select id_unit, id_asset_category,name from units un where un.is_display = :isDisplay" +
                "                   and un.status = :status )  units  " +
                "             on cte.id_asset_category = units.id_asset_category  " +
                "where 1 = 1  " +
                "  and cte.id_department_original in (:idsDepartmentOriginal) ");
        setConditionFindAllAssetCategories(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetCategories(request,query);
        PageUtils.buildQuery(pageable, query);
        List<FindAllAssetCategoryDto> dtos = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                FindAllAssetCategoryDto dto = new FindAllAssetCategoryDto();
                dto.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[0]));
                dto.setName(ValueUtil.getStringByObject(obj[1]));
                dto.setCodeName(ValueUtil.getStringByObject(obj[2]));
                dto.setShortName(ValueUtil.getStringByObject(obj[3]));
                dto.setDescription(ValueUtil.getStringByObject(obj[4]));
                dto.setParent(ValueUtil.getIntegerByObject(obj[5]));
                dto.setSortOrder(ValueUtil.getStringByObject(obj[6]));
                dto.setAssetCount(ValueUtil.getIntegerByObject(obj[7]));
                dto.setVisible(ValueUtil.getIntegerByObject(obj[8]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[9]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[10]));
                dto.setIsPick(ValueUtil.getIntegerByObject(obj[11]));
                dto.setDepth(ValueUtil.getIntegerByObject(obj[12]));
                dto.setPath(ValueUtil.getStringByObject(obj[13]));
                dto.setValueWearTear(ValueUtil.getStringByObject(obj[14]));
                dto.setYearUsedWearTear(ValueUtil.getStringByObject(obj[15]));
                dto.setMinimumTimeDepreciation(ValueUtil.getStringByObject(obj[16]));
                dto.setMaximumTimeDepreciation(ValueUtil.getStringByObject(obj[17]));
                dto.setNameParent(ValueUtil.getStringByObject(obj[18]));
                dto.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[19]));
                dto.setNumberCodePattern(ValueUtil.getStringByObject(obj[20]));
                dto.setNameUnit(ValueUtil.getStringByObject(obj[21]));
                dtos.add(dto);
            }
        }
        return new PageImpl<>(dtos, pageable, countFindAllAssetCategories(request));
    }

    private long countFindAllAssetCategories(FindAllDocumentAssetCategoriesRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_asset_categories as (  " +
                "       select assetCategires.id_asset_category,assetCategires.name,  " +
                "              assetCategires.code_name, assetCategires.short_name,  " +
                "              assetCategires.description, assetCategires.parent,  " +
                "              assetCategires.sort_order, assetCategires.asset_count,   " +
                "              assetCategires.visible, assetCategires.time_created,  " +
                "              assetCategires.time_modified, assetCategires.is_pick,  " +
                "              1 as depth,   " +
                "              CAST(assetCategires.id_asset_category as NCHAR ) as path,   " +
                "              assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,  " +
                "              assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,  " +
                "              case when assetCategires.parent is not null then assetCategires.name end nameParent,  " +
                "              assetCategires.id_department_original,  " +
                "              assetCategires.number_code_pattern  " +
                "       from asset_categories assetCategires   " +
                "       where assetCategires.parent is null  " +
                "       union all   " +
                "       select assetCategires.id_asset_category,assetCategires.name,   " +
                "              assetCategires.code_name, assetCategires.short_name,  " +
                "              assetCategires.description, assetCategires.parent,   " +
                "              assetCategires.sort_order, assetCategires.asset_count,   " +
                "              assetCategires.visible, assetCategires.time_created,  " +
                "              assetCategires.time_modified, assetCategires.is_pick,  " +
                "              cte.depth + 1 as depth,   " +
                "              concat_ws('/',cte.path,CAST(assetCategires.id_asset_category as NCHAR)) as path,  " +
                "              assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,  " +
                "              assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,  " +
                "              cte.name nameParent,  " +
                "              assetCategires.id_department_original,  " +
                "              assetCategires.number_code_pattern  " +
                "       from asset_categories assetCategires  " +
                "                INNER JOIN cte_asset_categories cte ON assetCategires.parent = cte.id_asset_category  " +
                "       )  " +
                "select count(0) count  " +
                "from cte_asset_categories cte  " +
                "where 1 = 1  " +
                "  and cte.id_department_original in (:idsDepartmentOriginal) ");
        setConditionCountFindAllAssetCategories(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterCountFindAllAssetCategories(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllAssetCategories(FindAllDocumentAssetCategoriesRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("isDisplay", Constants.UNITES_IS_DISPLAY);
        query.setParameter("status", Constants.UNITS_IS_ACTIVE);
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            query.setParameter("visible", request.getStatus());
        }
    }

    private void setParameterCountFindAllAssetCategories(FindAllDocumentAssetCategoriesRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            query.setParameter("visible", request.getStatus());
        }
    }

    private void setConditionFindAllAssetCategories(FindAllDocumentAssetCategoriesRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (cte.name REGEXP :keyword ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            sb.append(" and cte.visible = :visible ");
        }
        sb.append(" group by cte.id_asset_category, cte.name,  " +
                "         cte.code_name, cte.short_name, cte.description,  " +
                "         cte.parent, cte.sort_order, cte.asset_count,  " +
                "         cte.visible, cte.time_created, cte.time_modified,  " +
                "         cte.is_pick, cte.depth, cte.path,  " +
                "         cte.value_wear_tear, cte.year_used_wear_tear,  " +
                "         cte.minimum_time_depreciation, cte.maximum_time_depreciation,  " +
                "         cte.nameParent, cte.id_department_original, cte.number_code_pattern ");
        sb.append(" ORDER BY path ");
    }

    private void setConditionCountFindAllAssetCategories(FindAllDocumentAssetCategoriesRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (cte.name REGEXP :keyword ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            sb.append(" and cte.visible = :visible ");
        }
        sb.append(" ORDER BY path ");
    }

    @Override
    public Optional<AssetCategories> findAssetCategoriesVisibleByCodeName(String codeName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select assetCategory.id_asset_category, assetCategory.name,       " +
                "         assetCategory.short_name, assetCategory.code_name,       " +
                "         assetCategory.description, assetCategory.parent,       " +
                "         assetCategory.sort_order, assetCategory.asset_count,       " +
                "         assetCategory.visible, assetCategory.time_created,       " +
                "         assetCategory.time_modified, assetCategory.path_image,       " +
                "         assetCategory.is_pick, assetCategory.value_wear_tear,     " +
                "         assetCategory.year_used_wear_tear, assetCategory.minimum_time_depreciation,     " +
                "         assetCategory.maximum_time_depreciation,   " +
                "         assetCategory.id_department_original  " +
                "from asset_categories assetCategory       " +
                "where assetCategory.code_name = :codeName       " +
                "and assetCategory.visible = :visible   ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("codeName", codeName);
        query.setParameter("visible", Constants.IS_VISIBLE);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for(Object[] obj: result){
                AssetCategories categories = new AssetCategories();
                categories.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[0]));
                categories.setName(ValueUtil.getStringByObject(obj[1]));
                categories.setShortName(ValueUtil.getStringByObject(obj[2]));
                categories.setCodeName(ValueUtil.getStringByObject(obj[3]));
                categories.setDescription(ValueUtil.getStringByObject(obj[4]));
                categories.setParent(ValueUtil.getIntegerByObject(obj[5]));
                categories.setSortOrder(ValueUtil.getStringByObject(obj[6]));
                categories.setAssetCount(ValueUtil.getIntegerByObject(obj[7]));
                categories.setVisible(ValueUtil.getIntegerByObject(obj[8]));
                categories.setTimeCreated(ValueUtil.getStringByObject(obj[9]));
                categories.setTimeModified(ValueUtil.getStringByObject(obj[10]));
                categories.setPathImage(ValueUtil.getStringByObject(obj[11]));
                categories.setIsPick(ValueUtil.getIntegerByObject(obj[12]));
                categories.setValueWearTear(ValueUtil.getStringByObject(obj[13]));
                categories.setYearUsedWearTear(ValueUtil.getStringByObject(obj[14]));
                categories.setMinimumTimeDepreciation(ValueUtil.getStringByObject(obj[15]));
                categories.setMaximumTimeDepreciation(ValueUtil.getStringByObject(obj[16]));
                categories.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[17]));
                return Optional.of(categories);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<AssetCategories> findAssetCategoriesByVisibleAndIdAssetCategory(Integer idAssetCategory,
                                                                                    Integer visible) {
        StringBuilder sb = new StringBuilder();
        sb.append("select assetCategory.id_asset_category,     " +
                "       assetCategory.name,     " +
                "       assetCategory.short_name,     " +
                "       assetCategory.code_name,     " +
                "       assetCategory.description,     " +
                "       assetCategory.parent,     " +
                "       assetCategory.sort_order,     " +
                "       assetCategory.asset_count,     " +
                "       assetCategory.visible,     " +
                "       assetCategory.time_created,     " +
                "       assetCategory.time_modified,     " +
                "       assetCategory.path_image,     " +
                "       assetCategory.is_pick,   " +
                "       assetCategory.value_wear_tear,   " +
                "       assetCategory.year_used_wear_tear,   " +
                "       assetCategory.minimum_time_depreciation,   " +
                "       assetCategory.maximum_time_depreciation,  " +
                "       assetCategory.id_department_original  " +
                "from asset_categories assetCategory     " +
                "where assetCategory.id_asset_category = :idAssetCategory     " +
                "and assetCategory.visible = :visible  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAssetCategory", idAssetCategory);
        query.setParameter("visible", visible);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for(Object[] obj: result){
                AssetCategories categories = new AssetCategories();
                categories.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[0]));
                categories.setName(ValueUtil.getStringByObject(obj[1]));
                categories.setShortName(ValueUtil.getStringByObject(obj[2]));
                categories.setCodeName(ValueUtil.getStringByObject(obj[3]));
                categories.setDescription(ValueUtil.getStringByObject(obj[4]));
                categories.setParent(ValueUtil.getIntegerByObject(obj[5]));
                categories.setSortOrder(ValueUtil.getStringByObject(obj[6]));
                categories.setAssetCount(ValueUtil.getIntegerByObject(obj[7]));
                categories.setVisible(ValueUtil.getIntegerByObject(obj[8]));
                categories.setTimeCreated(ValueUtil.getStringByObject(obj[9]));
                categories.setTimeModified(ValueUtil.getStringByObject(obj[10]));
                categories.setPathImage(ValueUtil.getStringByObject(obj[11]));
                categories.setIsPick(ValueUtil.getIntegerByObject(obj[12]));
                categories.setValueWearTear(ValueUtil.getStringByObject(obj[13]));
                categories.setYearUsedWearTear(ValueUtil.getStringByObject(obj[14]));
                categories.setMinimumTimeDepreciation(ValueUtil.getStringByObject(obj[15]));
                categories.setMaximumTimeDepreciation(ValueUtil.getStringByObject(obj[16]));
                categories.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[17]));
                return Optional.of(categories);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean checkAssetCategoriesByParentIdAndName(Integer parentId, String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select * " +
                "from asset_categories assetCategories " +
                "where assetCategories.parent = :parentId " +
                "and assetCategories.name = :name " );
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("parentId", parentId);
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        return CollectionUtils.isEmpty(result);
    }

    @Override
    public boolean  checkExitsAssetCategoriesByNameOrShortName(String name, String shortName) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select * " +
                "from asset_categories assetCategories " +
                "where assetCategories.name = :name " +
                "and assetCategories.short_name = :shortName " );
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("name", name);
        query.setParameter("shortName", shortName);
        List<Object[]> result = query.getResultList();
        return CollectionUtils.isEmpty(result);
    }

    @Override
    public Optional<BluePrintParentAssetCategoryDto> findBluePrintAssetCategoryDtoById(Integer idAssetCategory) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select assetCategory.code_name, assetCategory.id_asset_category, " +
                "       assetCategory.name nameAsset, assetCategory.parent " +
                "from asset_categories assetCategory " +
                "where assetCategory.id_asset_category = :idAssetCategory ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAssetCategory", idAssetCategory);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                BluePrintParentAssetCategoryDto dto = new BluePrintParentAssetCategoryDto();
                dto.setCodeAssetCategory(ValueUtil.getStringByObject(obj[0]));
                dto.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[1]));
                dto.setNameAssetCategory(ValueUtil.getStringByObject(obj[2]));
                dto.setIdParent(ValueUtil.getIntegerByObject(obj[3]));
                return Optional.of(dto);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<FindAssetCategoryDetailsResponse> findAssetCategoryDetailsPickedResponseByCode(String code) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select asset.id_asset_category, asset.code_name,  " +
                "       asset.name, asset.parent " +
                "from asset_categories asset " +
                "where asset.code_name = :codeName " +
                "and asset.is_pick = :isPicked ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("codeName", code);
        query.setParameter("isPicked", Constants.ASSET_CATEGORY_IS_PICK);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result) {
                FindAssetCategoryDetailsResponse response = new FindAssetCategoryDetailsResponse();
                response.setIdInstance(ValueUtil.getIntegerByObject(obj[0]));
                response.setCodeAssetCategory(ValueUtil.getStringByObject(obj[1]));
                response.setNameAssetCategory(ValueUtil.getStringByObject(obj[2]));
                if (Objects.isNull(obj[3])){
                    response.setIdParentAssetCategory(response.getIdInstance());
                } else {
                    response.setIdParentAssetCategory(ValueUtil.getIntegerByObject(obj[3]));
                }
                return Optional.of(response);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean isCheckExitsAssetByIdAssetCategory(Integer idAssetCategory) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select asset.id_asset " +
                "from asset_categories assetCategor " +
                "    inner join asset asset on asset.id_asset_category = assetCategor.id_asset_category " +
                "where assetCategor.id_asset_category = :idAssetCategory ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAssetCategory", idAssetCategory);
        return !CollectionUtils.isEmpty(query.getResultList());
    }

    @Override
    public List<FindAllAssetCategoriesToDownloadDto>
    findAllAssetCategoriesByCodeParentVisibleToDownload(List<Integer> idsDepartment, String codeParent) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_asset_categories as (             " +
                "     select assetCategires.id_asset_category,assetCategires.name, " +
                "            assetCategires.code_name, assetCategires.short_name, " +
                "            assetCategires.description, assetCategires.parent, " +
                "            assetCategires.sort_order, assetCategires.asset_count, " +
                "            assetCategires.visible, assetCategires.time_created, " +
                "            assetCategires.time_modified, assetCategires.is_pick, " +
                "            1 as depth, " +
                "            CAST(assetCategires.id_asset_category as NCHAR ) as path, " +
                "            assetCategires.value_wear_tear, assetCategires.year_used_wear_tear, " +
                "            assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation, " +
                "            assetCategires.id_department_original " +
                "     from asset_categories assetCategires " +
                "     where assetCategires.code_name = :codeName " +
                "     and assetCategires.visible = :visible " +
                "     union all " +
                "     select assetCategires.id_asset_category,assetCategires.name, " +
                "            assetCategires.code_name, assetCategires.short_name, " +
                "            assetCategires.description, assetCategires.parent, " +
                "            assetCategires.sort_order, assetCategires.asset_count, " +
                "            assetCategires.visible, assetCategires.time_created, " +
                "            assetCategires.time_modified, assetCategires.is_pick, " +
                "            cte.depth + 1 as depth, " +
                "            concat_ws('/',cte.path,CAST(assetCategires.id_asset_category as NCHAR)) as path, " +
                "            assetCategires.value_wear_tear, assetCategires.year_used_wear_tear, " +
                "            assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation, " +
                "            assetCategires.id_department_original " +
                "     from asset_categories assetCategires " +
                "              INNER JOIN cte_asset_categories cte ON assetCategires.parent = cte.id_asset_category " +
                "     )             " +
                "     select cte.id_asset_category, cte.name, " +
                "     cte.code_name,cte.is_pick, cte.value_wear_tear, cte.year_used_wear_tear, " +
                "     cte.minimum_time_depreciation, cte.maximum_time_depreciation " +
                "from cte_asset_categories cte " +
                "where 1 = 1 " +
                "and id_department_original in (:idsDepartment)  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("visible", Constants.ASSET_CATEGORY_IS_VISIBLE);
        query.setParameter("idsDepartment",idsDepartment);
        query.setParameter("codeName", codeParent);
        List<Object[]> result = query.getResultList();
        List<FindAllAssetCategoriesToDownloadDto> assetCategories = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                assetCategories.add(contructionData(obj));
            }
        }
        return assetCategories;
    }

    private FindAllAssetCategoriesToDownloadDto contructionData(Object[] obj) {
        FindAllAssetCategoriesToDownloadDto categories = new FindAllAssetCategoriesToDownloadDto();
        categories.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[0]));
        categories.setName(ValueUtil.getStringByObject(obj[1]));
        categories.setCodeName(ValueUtil.getStringByObject(obj[2]));
        categories.setIsPick(ValueUtil.getIntegerByObject(obj[3]));
        categories.setValueWearTear(ValueUtil.getStringByObject(obj[4]));
        categories.setYearUsedWearTear(ValueUtil.getStringByObject(obj[5]));
        categories.setMinimumTimeDepreciation(ValueUtil.getStringByObject(obj[6]));
        categories.setMaximumTimeDepreciation(ValueUtil.getStringByObject(obj[7]));
        return categories;
    }

    @Override
    public Optional<AssetCategories> findAssetCategoryParentByParentId(Integer parentId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select assetCategories.id_asset_category, assetCategories.name,     " +
                "       assetCategories.short_name, assetCategories.code_name,     " +
                "       assetCategories.description, assetCategories.parent,     " +
                "       assetCategories.sort_order, assetCategories.asset_count,     " +
                "       assetCategories.visible, assetCategories.time_created,     " +
                "       assetCategories.time_modified, assetCategories.path_image,     " +
                "       assetCategories.is_pick, assetCategories.value_wear_tear,   " +
                "       assetCategories.year_used_wear_tear, assetCategories.minimum_time_depreciation,   " +
                "       assetCategories.maximum_time_depreciation,  " +
                "       assetCategories.id_department_original  " +
                "from asset_categories assetCategories     " +
                "where assetCategories.id_asset_category = :parentId ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("parentId", parentId);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                AssetCategories categories = new AssetCategories();
                categories.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[0]));
                categories.setName(ValueUtil.getStringByObject(obj[1]));
                categories.setShortName(ValueUtil.getStringByObject(obj[2]));
                categories.setCodeName(ValueUtil.getStringByObject(obj[3]));
                categories.setDescription(ValueUtil.getStringByObject(obj[4]));
                categories.setParent(ValueUtil.getIntegerByObject(obj[5]));
                categories.setSortOrder(ValueUtil.getStringByObject(obj[6]));
                categories.setAssetCount(ValueUtil.getIntegerByObject(obj[7]));
                categories.setVisible(ValueUtil.getIntegerByObject(obj[8]));
                categories.setTimeCreated(ValueUtil.getStringByObject(obj[9]));
                categories.setTimeModified(ValueUtil.getStringByObject(obj[10]));
                categories.setPathImage(ValueUtil.getStringByObject(obj[11]));
                categories.setIsPick(ValueUtil.getIntegerByObject(obj[12]));
                categories.setValueWearTear(ValueUtil.getStringByObject(obj[13]));
                categories.setYearUsedWearTear(ValueUtil.getStringByObject(obj[14]));
                categories.setMinimumTimeDepreciation(ValueUtil.getStringByObject(obj[15]));
                categories.setMaximumTimeDepreciation(ValueUtil.getStringByObject(obj[16]));
                categories.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[17]));
                return Optional.of(categories);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<AssetCategories> findAssetCategoryById(Integer idAssetCategory) {
        StringBuilder sb = new StringBuilder();
        sb.append("select assetCategories.id_asset_category, assetCategories.name,     " +
                "       assetCategories.short_name, assetCategories.code_name,     " +
                "       assetCategories.description, assetCategories.parent,     " +
                "       assetCategories.sort_order, assetCategories.asset_count,     " +
                "       assetCategories.visible, assetCategories.time_created,     " +
                "       assetCategories.time_modified, assetCategories.path_image,     " +
                "       assetCategories.is_pick, assetCategories.value_wear_tear,   " +
                "       assetCategories.year_used_wear_tear, assetCategories.minimum_time_depreciation,   " +
                "       assetCategories.maximum_time_depreciation,  " +
                "       assetCategories.id_department_original  " +
                "from asset_categories assetCategories     " +
                "where assetCategories.id_asset_category = :idAssetCategory  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAssetCategory", idAssetCategory);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                AssetCategories categories = new AssetCategories();
                categories.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[0]));
                categories.setName(ValueUtil.getStringByObject(obj[1]));
                categories.setShortName(ValueUtil.getStringByObject(obj[2]));
                categories.setCodeName(ValueUtil.getStringByObject(obj[3]));
                categories.setDescription(ValueUtil.getStringByObject(obj[4]));
                categories.setParent(ValueUtil.getIntegerByObject(obj[5]));
                categories.setSortOrder(ValueUtil.getStringByObject(obj[6]));
                categories.setAssetCount(ValueUtil.getIntegerByObject(obj[7]));
                categories.setVisible(ValueUtil.getIntegerByObject(obj[8]));
                categories.setTimeCreated(ValueUtil.getStringByObject(obj[9]));
                categories.setTimeModified(ValueUtil.getStringByObject(obj[10]));
                categories.setPathImage(ValueUtil.getStringByObject(obj[11]));
                categories.setIsPick(ValueUtil.getIntegerByObject(obj[12]));
                categories.setValueWearTear(ValueUtil.getStringByObject(obj[13]));
                categories.setYearUsedWearTear(ValueUtil.getStringByObject(obj[14]));
                categories.setMinimumTimeDepreciation(ValueUtil.getStringByObject(obj[15]));
                categories.setMaximumTimeDepreciation(ValueUtil.getStringByObject(obj[16]));
                categories.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[17]));
                return Optional.of(categories);
            }
        }
        return Optional.empty();
    }


    private void setParameterFindAllAssetCategoriesByCodeAndVisible(FindAllAssetCategoriesVisibleRequest request, Query query) {
        query.setParameter("codeName", request.getCodeName().trim());
        query.setParameter("visible", Constants.IS_VISIBLE);
        query.setParameter("idsDepartment", request.getIdsDepartment());
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllAssetCategoriesByCodeAndVisible(FindAllAssetCategoriesVisibleRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (cte.name REGEXP :keyword ) ");
        }
        sb.append(" ORDER BY path ");
    }

    private void setConditionFindAllAssetCategoriesVisible(FindAllAssetCategoriesVisibleRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (cte.name REGEXP :keyword ) ");
        }
        sb.append(" ORDER BY path ");
    }

    private long countFindAllAssetCategoriesByCodeAndVisible(FindAllAssetCategoriesVisibleRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_asset_categories as (        " +
                "       select assetCategires.id_asset_category,assetCategires.name,        " +
                "              assetCategires.code_name, assetCategires.short_name,        " +
                "              assetCategires.description, assetCategires.parent,        " +
                "              assetCategires.sort_order, assetCategires.asset_count,        " +
                "              assetCategires.visible, assetCategires.time_created,        " +
                "              assetCategires.time_modified, assetCategires.is_pick,        " +
                "              1 as depth,        " +
                "              CAST(assetCategires.id_asset_category as NCHAR ) as path,     " +
                "              assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,     " +
                "              assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,  " +
                "              assetCategires.id_department_original,  " +
                "              assetCategires.number_code_pattern " +
                "       from asset_categories assetCategires        " +
                "       where assetCategires.code_name = :codeName        " +
                "       and assetCategires.visible = :visible        " +
                "       union all        " +
                "       select assetCategires.id_asset_category,assetCategires.name,        " +
                "              assetCategires.code_name, assetCategires.short_name,        " +
                "              assetCategires.description, assetCategires.parent,        " +
                "              assetCategires.sort_order, assetCategires.asset_count,        " +
                "              assetCategires.visible, assetCategires.time_created,        " +
                "              assetCategires.time_modified, assetCategires.is_pick,        " +
                "              cte.depth + 1 as depth,        " +
                "              concat_ws('/',cte.path,CAST(assetCategires.id_asset_category as NCHAR)) as path,     " +
                "              assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,     " +
                "              assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,  " +
                "              assetCategires.id_department_original,  " +
                "              assetCategires.number_code_pattern " +
                "       from asset_categories assetCategires        " +
                "                INNER JOIN cte_asset_categories cte ON assetCategires.parent = cte.id_asset_category        " +
                "       )        " +
                "  select count(cte.id_asset_category) count     " +
                "                  from cte_asset_categories cte     " +
                "   where 1 = 1 and cte.id_department_original in (:idsDepartment) ");
        setConditionFindAllAssetCategoriesByCodeAndVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetCategoriesByCodeAndVisible(request, query);
        return  ValueUtil.getLongByObject(query.getSingleResult());
    }

    @Override
    public Optional<AssetCategories> findAssetCategoryByName(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("select assetCategory.id_asset_category, assetCategory.name,       " +
                "         assetCategory.short_name, assetCategory.code_name,       " +
                "         assetCategory.description, assetCategory.parent,       " +
                "         assetCategory.sort_order, assetCategory.asset_count,       " +
                "         assetCategory.visible, assetCategory.time_created,       " +
                "         assetCategory.time_modified, assetCategory.path_image,       " +
                "         assetCategory.is_pick, assetCategory.value_wear_tear,     " +
                "         assetCategory.year_used_wear_tear, assetCategory.minimum_time_depreciation,     " +
                "         assetCategory.maximum_time_depreciation,   " +
                "         assetCategory.id_department_original  " +
                "from asset_categories assetCategory       " +
                "where assetCategory.name = :name       " );
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for(Object[] obj: result){
                AssetCategories categories = new AssetCategories();
                categories.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[0]));
                categories.setName(ValueUtil.getStringByObject(obj[1]));
                categories.setShortName(ValueUtil.getStringByObject(obj[2]));
                categories.setCodeName(ValueUtil.getStringByObject(obj[3]));
                categories.setDescription(ValueUtil.getStringByObject(obj[4]));
                categories.setParent(ValueUtil.getIntegerByObject(obj[5]));
                categories.setSortOrder(ValueUtil.getStringByObject(obj[6]));
                categories.setAssetCount(ValueUtil.getIntegerByObject(obj[7]));
                categories.setVisible(ValueUtil.getIntegerByObject(obj[8]));
                categories.setTimeCreated(ValueUtil.getStringByObject(obj[9]));
                categories.setTimeModified(ValueUtil.getStringByObject(obj[10]));
                categories.setPathImage(ValueUtil.getStringByObject(obj[11]));
                categories.setIsPick(ValueUtil.getIntegerByObject(obj[12]));
                categories.setValueWearTear(ValueUtil.getStringByObject(obj[13]));
                categories.setYearUsedWearTear(ValueUtil.getStringByObject(obj[14]));
                categories.setMinimumTimeDepreciation(ValueUtil.getStringByObject(obj[15]));
                categories.setMaximumTimeDepreciation(ValueUtil.getStringByObject(obj[16]));
                categories.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[17]));
                return Optional.of(categories);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<AssetCategories> findAllAssetCategoriesByIdIn(List<Integer> idCategory) {
        StringBuilder sb = new StringBuilder();
        sb.append("select assetCategory.id_asset_category, assetCategory.name,       " +
                "         assetCategory.short_name, assetCategory.code_name,       " +
                "         assetCategory.description, assetCategory.parent,       " +
                "         assetCategory.sort_order, assetCategory.asset_count,       " +
                "         assetCategory.visible, assetCategory.time_created,       " +
                "         assetCategory.time_modified, assetCategory.path_image,       " +
                "         assetCategory.is_pick, assetCategory.value_wear_tear,     " +
                "         assetCategory.year_used_wear_tear, assetCategory.minimum_time_depreciation,     " +
                "         assetCategory.maximum_time_depreciation,   " +
                "         assetCategory.id_department_original  " +
                "from asset_categories assetCategory       " +
                "where assetCategory.id_asset_category in :idCategory");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idCategory", idCategory);

        List<Object[]> result = query.getResultList();
        List<AssetCategories> categoriesList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                AssetCategories categories = new AssetCategories();
                categories.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[0]));
                categories.setName(ValueUtil.getStringByObject(obj[1]));
                categories.setShortName(ValueUtil.getStringByObject(obj[2]));
                categories.setCodeName(ValueUtil.getStringByObject(obj[3]));
                categories.setDescription(ValueUtil.getStringByObject(obj[4]));
                categories.setParent(ValueUtil.getIntegerByObject(obj[5]));
                categories.setSortOrder(ValueUtil.getStringByObject(obj[6]));
                categories.setAssetCount(ValueUtil.getIntegerByObject(obj[7]));
                categories.setVisible(ValueUtil.getIntegerByObject(obj[8]));
                categories.setTimeCreated(ValueUtil.getStringByObject(obj[9]));
                categories.setTimeModified(ValueUtil.getStringByObject(obj[10]));
                categories.setPathImage(ValueUtil.getStringByObject(obj[11]));
                categories.setIsPick(ValueUtil.getIntegerByObject(obj[12]));
                categories.setValueWearTear(ValueUtil.getStringByObject(obj[13]));
                categories.setYearUsedWearTear(ValueUtil.getStringByObject(obj[14]));
                categories.setMinimumTimeDepreciation(ValueUtil.getStringByObject(obj[15]));
                categories.setMaximumTimeDepreciation(ValueUtil.getStringByObject(obj[16]));
                categories.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[17]));
                categoriesList.add(categories);
            }
        }

        return categoriesList;
    }
}
