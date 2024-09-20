package com.example.csvccdshustbe.repository.assetCategories.impl;


import com.example.csvccdshustbe.dto.assetCategories.BluePrintParentAssetCategoryDto;
import com.example.csvccdshustbe.dto.assetCategories.FindAllAssetCategoriesByCodeAndVisibleDto;
import com.example.csvccdshustbe.dto.assetCategories.FindAllAssetCategoriesPickedDto;
import com.example.csvccdshustbe.dto.assetCategories.FindAllAssetCategoryDto;
import com.example.csvccdshustbe.entity.AssetCategories;
import com.example.csvccdshustbe.repository.assetCategories.AssetCategoriesRepositoryCustom;
import com.example.csvccdshustbe.request.assetCategories.FindAllAssetCategoriesByCodeRequest;
import com.example.csvccdshustbe.request.assetCategories.FindAllDocumentAssetCategoriesRequest;
import com.example.csvccdshustbe.response.assetCategories.FindAssetCategoryDetailsResponse;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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
        sb.append(" WITH RECURSIVE cte_asset_category as (    " +
                "      select assetCategories.id_asset_category, assetCategories.name, assetCategories.short_name,    " +
                "             assetCategories.code_name, assetCategories.description, assetCategories.parent,    " +
                "             assetCategories.sort_order, assetCategories.asset_count,    " +
                "             assetCategories.visible, assetCategories.time_created, assetCategories.time_modified,    " +
                "             assetCategories.path_image,assetCategories.is_pick ,    " +
                "             assetCategories.id_asset_category as idParent    " +
                "      from asset_categories   assetCategories    " +
                "      where assetCategories.parent is null    " +
                "      union all        " +
                "      select assetCategories.id_asset_category, assetCategories.name,    " +
                "             assetCategories.short_name, assetCategories.code_name,    " +
                "             assetCategories.description, assetCategories.parent,    " +
                "             assetCategories.sort_order, assetCategories.asset_count,    " +
                "             assetCategories.visible, assetCategories.time_created,    " +
                "             assetCategories.time_modified, assetCategories.path_image,    " +
                "             assetCategories.is_pick,    " +
                "             cte.id_asset_category as idParent    " +
                "                   from asset_categories assetCategories    " +
                "               INNER JOIN cte_asset_category cte ON assetCategories.parent = cte.id_asset_category    " +
                "      where  assetCategories.is_pick = :isPicked and assetCategories.visible = :isVisible    " +
                "                   )        " +
                "select cte.id_asset_category, cte.name, cte.short_name,    " +
                "       cte.code_name, cte.path_image,  cte.idParent    " +
                "from cte_asset_category cte ");
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
    findAllAssetCategoriesByCodeAndVisible(Pageable pageable, FindAllAssetCategoriesByCodeRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_asset_categories as (    " +
                "      select assetCategires.id_asset_category,assetCategires.name,    " +
                "             assetCategires.code_name, assetCategires.short_name,    " +
                "             assetCategires.description, assetCategires.parent,    " +
                "             assetCategires.sort_order, assetCategires.asset_count,    " +
                "             assetCategires.visible, assetCategires.time_created,    " +
                "             assetCategires.time_modified, assetCategires.is_pick,    " +
                "             1 as depth,    " +
                "             CAST(assetCategires.id_asset_category as NCHAR ) as path, " +
                "             assetCategires.value_wear_tear, assetCategires.year_used_wear_tear, " +
                "             assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation " +
                "      from asset_categories assetCategires    " +
                "      where assetCategires.code_name = :codeName    " +
                "      and assetCategires.visible = :visible    " +
                "      union all    " +
                "      select assetCategires.id_asset_category,assetCategires.name,    " +
                "             assetCategires.code_name, assetCategires.short_name,    " +
                "             assetCategires.description, assetCategires.parent,    " +
                "             assetCategires.sort_order, assetCategires.asset_count,    " +
                "             assetCategires.visible, assetCategires.time_created,    " +
                "             assetCategires.time_modified, assetCategires.is_pick,    " +
                "             cte.depth + 1 as depth,    " +
                "             concat_ws('/',cte.path,CAST(assetCategires.id_asset_category as NCHAR)) as path, " +
                "             assetCategires.value_wear_tear, assetCategires.year_used_wear_tear, " +
                "             assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation " +
                "      from asset_categories assetCategires    " +
                "               INNER JOIN cte_asset_categories cte ON assetCategires.parent = cte.id_asset_category    " +
                "      )    " +
                " select cte.id_asset_category, cte.name,    " +
                "         cte.code_name, cte.short_name, cte.description,    " +
                "         cte.parent, cte.sort_order, cte.asset_count,    " +
                "         cte.visible, cte.time_created, cte.time_modified,    " +
                "         cte.is_pick, cte.depth, cte.path, " +
                "         cte.value_wear_tear, cte.year_used_wear_tear,  " +
                "         cte.minimum_time_depreciation, cte.maximum_time_depreciation " +
                "  from cte_asset_categories cte    " +
                "  where 1 = 1  ");
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
                dtos.add(dto);
            }
        }
        return new PageImpl<>(dtos, pageable, countFindAllAssetCategoriesByCodeAndVisible(request));
    }

    @Override
    public Page<FindAllAssetCategoryDto> findAllAssetCategories(Pageable pageable, FindAllDocumentAssetCategoriesRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_asset_categories as (       " +
                "     select assetCategires.id_asset_category,assetCategires.name,       " +
                "            assetCategires.code_name, assetCategires.short_name,       " +
                "            assetCategires.description, assetCategires.parent,       " +
                "            assetCategires.sort_order, assetCategires.asset_count,       " +
                "            assetCategires.visible, assetCategires.time_created,       " +
                "            assetCategires.time_modified, assetCategires.is_pick,       " +
                "            1 as depth,       " +
                "            CAST(assetCategires.id_asset_category as NCHAR ) as path,    " +
                "            assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,    " +
                "            assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,  " +
                "            case when assetCategires.parent is not null then assetCategires.name end nameParent  " +
                "     from asset_categories assetCategires       " +
                "     where assetCategires.parent is null    " +
                "     union all       " +
                "     select assetCategires.id_asset_category,assetCategires.name,       " +
                "            assetCategires.code_name, assetCategires.short_name,       " +
                "            assetCategires.description, assetCategires.parent,       " +
                "            assetCategires.sort_order, assetCategires.asset_count,       " +
                "            assetCategires.visible, assetCategires.time_created,       " +
                "            assetCategires.time_modified, assetCategires.is_pick,       " +
                "            cte.depth + 1 as depth,       " +
                "            concat_ws('/',cte.path,CAST(assetCategires.id_asset_category as NCHAR)) as path,    " +
                "            assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,    " +
                "            assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,  " +
                "            cte.name nameParent  " +
                "     from asset_categories assetCategires       " +
                "              INNER JOIN cte_asset_categories cte ON assetCategires.parent = cte.id_asset_category       " +
                "     )       " +
                " select cte.id_asset_category, cte.name,       " +
                "        cte.code_name, cte.short_name, cte.description,       " +
                "        cte.parent, cte.sort_order, cte.asset_count,       " +
                "        cte.visible, cte.time_created, cte.time_modified,       " +
                "        cte.is_pick, cte.depth, cte.path,    " +
                "        cte.value_wear_tear, cte.year_used_wear_tear,    " +
                "        cte.minimum_time_depreciation, cte.maximum_time_depreciation,  " +
                "        cte.nameParent  " +
                " from cte_asset_categories cte       " +
                " where 1 = 1  ");
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
                dtos.add(dto);
            }
        }
        return new PageImpl<>(dtos, pageable, countFindAllAssetCategories(request));
    }

    private long countFindAllAssetCategories(FindAllDocumentAssetCategoriesRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_asset_categories as (    " +
                "      select assetCategires.id_asset_category,assetCategires.name,    " +
                "             assetCategires.code_name, assetCategires.short_name,    " +
                "             assetCategires.description, assetCategires.parent,    " +
                "             assetCategires.sort_order, assetCategires.asset_count,    " +
                "             assetCategires.visible, assetCategires.time_created,    " +
                "             assetCategires.time_modified, assetCategires.is_pick,    " +
                "             1 as depth,    " +
                "             CAST(assetCategires.id_asset_category as NCHAR ) as path, " +
                "             assetCategires.value_wear_tear, assetCategires.year_used_wear_tear, " +
                "             assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation " +
                "      from asset_categories assetCategires    " +
                "      where assetCategires.parent is null " +
                "      union all    " +
                "      select assetCategires.id_asset_category,assetCategires.name,    " +
                "             assetCategires.code_name, assetCategires.short_name,    " +
                "             assetCategires.description, assetCategires.parent,    " +
                "             assetCategires.sort_order, assetCategires.asset_count,    " +
                "             assetCategires.visible, assetCategires.time_created,    " +
                "             assetCategires.time_modified, assetCategires.is_pick,    " +
                "             cte.depth + 1 as depth,    " +
                "             concat_ws('/',cte.path,CAST(assetCategires.id_asset_category as NCHAR)) as path, " +
                "             assetCategires.value_wear_tear, assetCategires.year_used_wear_tear, " +
                "             assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation " +
                "      from asset_categories assetCategires    " +
                "               INNER JOIN cte_asset_categories cte ON assetCategires.parent = cte.id_asset_category    " +
                "      )    " +
                "  select count(cte.id_asset_category) count " +
                "  from cte_asset_categories cte    " +
                "  where 1 = 1 ");
        setConditionFindAllAssetCategories(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetCategories(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllAssetCategories(FindAllDocumentAssetCategoriesRequest request, Query query) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllAssetCategories(FindAllDocumentAssetCategoriesRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (cte.name REGEXP :keyword ) ");
        }
        sb.append(" ORDER BY path ");
    }

    @Override
    public Optional<AssetCategories> findAssetCategoriesVisibleByCodeName(String codeName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select assetCategory.id_asset_category, assetCategory.name,   " +
                "        assetCategory.short_name, assetCategory.code_name,   " +
                "        assetCategory.description, assetCategory.parent,   " +
                "        assetCategory.sort_order, assetCategory.asset_count,   " +
                "        assetCategory.visible, assetCategory.time_created,   " +
                "        assetCategory.time_modified, assetCategory.path_image,   " +
                "        assetCategory.is_pick, assetCategory.value_wear_tear, " +
                "        assetCategory.year_used_wear_tear, assetCategory.minimum_time_depreciation, " +
                "        assetCategory.maximum_time_depreciation " +
                "from asset_categories assetCategory   " +
                "where assetCategory.code_name = :codeName   " +
                "and assetCategory.visible = :visible  ");
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
                return Optional.of(categories);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<AssetCategories> findAssetCategoriesByVisibleAndIdAssetCategory(Integer idAssetCategory,
                                                                                    Integer visible) {
        StringBuilder sb = new StringBuilder();
        sb.append("select assetCategory.id_asset_category,   " +
                "        assetCategory.name,   " +
                "        assetCategory.short_name,   " +
                "        assetCategory.code_name,   " +
                "        assetCategory.description,   " +
                "        assetCategory.parent,   " +
                "        assetCategory.sort_order,   " +
                "        assetCategory.asset_count,   " +
                "        assetCategory.visible,   " +
                "        assetCategory.time_created,   " +
                "        assetCategory.time_modified,   " +
                "        assetCategory.path_image,   " +
                "        assetCategory.is_pick, " +
                "        assetCategory.value_wear_tear, " +
                "        assetCategory.year_used_wear_tear, " +
                "        assetCategory.minimum_time_depreciation, " +
                "        assetCategory.maximum_time_depreciation " +
                "from asset_categories assetCategory   " +
                "where assetCategory.id_asset_category = :idAssetCategory   " +
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
    public Map<String, List<FindAllAssetCategoriesByCodeAndVisibleDto>> findAllAssetCategoriesByVisibleToDownload() {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_asset_categories as (       " +
                "      select assetCategires.id_asset_category,assetCategires.name,       " +
                "             assetCategires.code_name, assetCategires.short_name,       " +
                "             assetCategires.description, assetCategires.parent,       " +
                "             assetCategires.sort_order, assetCategires.asset_count,       " +
                "             assetCategires.visible, assetCategires.time_created,       " +
                "             assetCategires.time_modified, assetCategires.is_pick,       " +
                "             1 as depth,       " +
                "             CAST(assetCategires.id_asset_category as NCHAR ) as path,    " +
                "             assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,    " +
                "             assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation    " +
                "      from asset_categories assetCategires  " +
                "      where parent is null  " +
                "      union all       " +
                "      select assetCategires.id_asset_category,assetCategires.name,       " +
                "             assetCategires.code_name, assetCategires.short_name,       " +
                "             assetCategires.description, assetCategires.parent,       " +
                "             assetCategires.sort_order, assetCategires.asset_count,       " +
                "             assetCategires.visible, assetCategires.time_created,       " +
                "             assetCategires.time_modified, assetCategires.is_pick,       " +
                "             cte.depth + 1 as depth,       " +
                "             concat_ws('/',cte.path,CAST(assetCategires.id_asset_category as NCHAR)) as path,    " +
                "             assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,    " +
                "             assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation    " +
                "      from asset_categories assetCategires       " +
                "               INNER JOIN cte_asset_categories cte ON assetCategires.parent = cte.id_asset_category       " +
                "      )       " +
                "select cte.id_asset_category, cte.name,  " +
                "         cte.code_name, cte.is_pick,  " +
                "         cte.value_wear_tear, cte.year_used_wear_tear,     " +
                "         cte.minimum_time_depreciation, cte.maximum_time_depreciation    " +
                "from cte_asset_categories cte  " +
                "where 1 = 1  " +
                "and cte.visible = :visible  " +
                "and cte.parent is not null  " +
                "order by path ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("visible", Constants.ASSET_CATEGORY_IS_VISIBLE);
        List<Object[]> result = query.getResultList();
        Map<String, List<FindAllAssetCategoriesByCodeAndVisibleDto>> mapAssetCategory = new HashMap<>();
        String key = null;
        Integer idAssetCategory ;
        String nameAssetCategory ;
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                idAssetCategory = ValueUtil.getIntegerByObject(obj[0]);
                nameAssetCategory = ValueUtil.getStringByObject(obj[1]);
                if (ValueUtil.getIntegerByObject(obj[3]) != null && ValueUtil.getIntegerByObject(obj[3]).equals(Constants.ASSET_CATEGORY_IS_PICK)){
                    key = "Stt" + idAssetCategory + "_" + nameAssetCategory.replace(" ","").replace(",","");
                    List<FindAllAssetCategoriesByCodeAndVisibleDto> dtos = new ArrayList<>();
                    dtos.add(contructionData(obj));
                    mapAssetCategory.put(key, dtos);
                } else {
                    mapAssetCategory.containsKey(key);
                    mapAssetCategory.get(key).add(contructionData(obj));

                }
            }
        }
        return mapAssetCategory;
    }

    private FindAllAssetCategoriesByCodeAndVisibleDto contructionData(Object[] obj) {
        FindAllAssetCategoriesByCodeAndVisibleDto categories = new FindAllAssetCategoriesByCodeAndVisibleDto();
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
        sb.append("select assetCategories.id_asset_category, assetCategories.name,   " +
                "        assetCategories.short_name, assetCategories.code_name,   " +
                "        assetCategories.description, assetCategories.parent,   " +
                "        assetCategories.sort_order, assetCategories.asset_count,   " +
                "        assetCategories.visible, assetCategories.time_created,   " +
                "        assetCategories.time_modified, assetCategories.path_image,   " +
                "        assetCategories.is_pick, assetCategories.value_wear_tear, " +
                "        assetCategories.year_used_wear_tear, assetCategories.minimum_time_depreciation, " +
                "        assetCategories.maximum_time_depreciation " +
                "from asset_categories assetCategories   " +
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
                return Optional.of(categories);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<AssetCategories> findAssetCategoryById(Integer idAssetCategory) {
        StringBuilder sb = new StringBuilder();
        sb.append("select assetCategories.id_asset_category, assetCategories.name,   " +
                "        assetCategories.short_name, assetCategories.code_name,   " +
                "        assetCategories.description, assetCategories.parent,   " +
                "        assetCategories.sort_order, assetCategories.asset_count,   " +
                "        assetCategories.visible, assetCategories.time_created,   " +
                "        assetCategories.time_modified, assetCategories.path_image,   " +
                "        assetCategories.is_pick, assetCategories.value_wear_tear, " +
                "        assetCategories.year_used_wear_tear, assetCategories.minimum_time_depreciation, " +
                "        assetCategories.maximum_time_depreciation " +
                "from asset_categories assetCategories   " +
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
                return Optional.of(categories);
            }
        }
        return Optional.empty();
    }


    private void setParameterFindAllAssetCategoriesByCodeAndVisible(FindAllAssetCategoriesByCodeRequest request, Query query) {
        query.setParameter("codeName", request.getCodeName().trim());
        query.setParameter("visible", Constants.IS_VISIBLE);
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllAssetCategoriesByCodeAndVisible(FindAllAssetCategoriesByCodeRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (cte.name REGEXP :keyword ) ");
        }
        sb.append(" ORDER BY path ");
    }

    private long countFindAllAssetCategoriesByCodeAndVisible(FindAllAssetCategoriesByCodeRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_asset_categories as (    " +
                "      select assetCategires.id_asset_category,assetCategires.name,    " +
                "             assetCategires.code_name, assetCategires.short_name,    " +
                "             assetCategires.description, assetCategires.parent,    " +
                "             assetCategires.sort_order, assetCategires.asset_count,    " +
                "             assetCategires.visible, assetCategires.time_created,    " +
                "             assetCategires.time_modified, assetCategires.is_pick,    " +
                "             1 as depth,    " +
                "             CAST(assetCategires.id_asset_category as NCHAR ) as path, " +
                "             assetCategires.value_wear_tear, assetCategires.year_used_wear_tear, " +
                "             assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation " +
                "      from asset_categories assetCategires    " +
                "      where assetCategires.code_name = :codeName    " +
                "      and assetCategires.visible = :visible    " +
                "      union all    " +
                "      select assetCategires.id_asset_category,assetCategires.name,    " +
                "             assetCategires.code_name, assetCategires.short_name,    " +
                "             assetCategires.description, assetCategires.parent,    " +
                "             assetCategires.sort_order, assetCategires.asset_count,    " +
                "             assetCategires.visible, assetCategires.time_created,    " +
                "             assetCategires.time_modified, assetCategires.is_pick,    " +
                "             cte.depth + 1 as depth,    " +
                "             concat_ws('/',cte.path,CAST(assetCategires.id_asset_category as NCHAR)) as path, " +
                "             assetCategires.value_wear_tear, assetCategires.year_used_wear_tear, " +
                "             assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation " +
                "      from asset_categories assetCategires    " +
                "               INNER JOIN cte_asset_categories cte ON assetCategires.parent = cte.id_asset_category    " +
                "      )    " +
                " select count(cte.id_asset_category) count " +
                "from cte_asset_categories cte " +
                "  where 1 = 1  ");
        setConditionFindAllAssetCategoriesByCodeAndVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetCategoriesByCodeAndVisible(request, query);
        return  ValueUtil.getLongByObject(query.getSingleResult());
    }
}
