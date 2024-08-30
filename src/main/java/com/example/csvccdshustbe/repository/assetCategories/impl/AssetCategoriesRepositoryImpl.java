package com.example.csvccdshustbe.repository.assetCategories.impl;

import com.example.csvccdshustbe.dto.assetCategories.FindAllAssetCategoriesByCodeAndVisibleDto;
import com.example.csvccdshustbe.entity.AssetCategories;
import com.example.csvccdshustbe.repository.assetCategories.AssetCategoriesRepositoryCustom;
import com.example.csvccdshustbe.request.assetCategories.FindAllAssetCategoriesRequest;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AssetCategoriesRepositoryImpl implements AssetCategoriesRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<AssetCategories> findAllAssetCategoriesIsPickedAndVisible() {
        StringBuilder sb = new StringBuilder();
        sb.append("select assetCategories.id_asset_category, assetCategories.name, " +
                "       assetCategories.short_name, assetCategories.code_name, " +
                "       assetCategories.description, assetCategories.parent, " +
                "       assetCategories.sort_order, assetCategories.asset_count, " +
                "       assetCategories.visible, assetCategories.time_created, " +
                "       assetCategories.time_modified, assetCategories.path_image, " +
                "       assetCategories.is_pick " +
                "from asset_categories assetCategories " +
                "where assetCategories.is_pick = :isPicked " +
                "and assetCategories.visible = :isVisible ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("isPicked", Constants.IS_PICKED);
        query.setParameter("isVisible", Constants.IS_VISIBLE);
        List<Object[]> result = query.getResultList();
        List<AssetCategories> assetCategories = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
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
                assetCategories.add(categories);
            }
        }
        return assetCategories;
    }

    @Override
    public Page<FindAllAssetCategoriesByCodeAndVisibleDto>
    findAllAssetCategoriesByCodeAndVisible(Pageable pageable, FindAllAssetCategoriesRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_asset_categories as (  " +
                "    select assetCategires.id_asset_category,assetCategires.name,  " +
                "           assetCategires.code_name, assetCategires.short_name,  " +
                "           assetCategires.description, assetCategires.parent,  " +
                "           assetCategires.sort_order, assetCategires.asset_count,  " +
                "           assetCategires.visible, assetCategires.time_created,  " +
                "           assetCategires.time_modified, assetCategires.is_pick,  " +
                "           1 as depth,  " +
                "           CAST(assetCategires.id_asset_category as NCHAR ) as path  " +
                "    from asset_categories assetCategires  " +
                "    where assetCategires.code_name = :codeName  " +
                "    and assetCategires.visible = :visible  " +
                "    union all  " +
                "    select assetCategires.id_asset_category,assetCategires.name,  " +
                "           assetCategires.code_name, assetCategires.short_name,  " +
                "           assetCategires.description, assetCategires.parent,  " +
                "           assetCategires.sort_order, assetCategires.asset_count,  " +
                "           assetCategires.visible, assetCategires.time_created,  " +
                "           assetCategires.time_modified, assetCategires.is_pick,  " +
                "           cte.depth + 1 as depth,  " +
                "           concat_ws('/',cte.path,CAST(assetCategires.id_asset_category as NCHAR)) as path  " +
                "    from asset_categories assetCategires  " +
                "             INNER JOIN cte_asset_categories cte ON assetCategires.parent = cte.id_asset_category  " +
                "    )  " +
                "select cte.id_asset_category, cte.name,  " +
                "       cte.code_name, cte.short_name, cte.description,  " +
                "       cte.parent, cte.sort_order, cte.asset_count,  " +
                "       cte.visible, cte.time_created, cte.time_modified,  " +
                "       cte.is_pick, cte.depth, cte.path  " +
                "from cte_asset_categories cte  " +
                "where 1 = 1 ");
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
                dtos.add(dto);
            }
        }
        return new PageImpl<>(dtos, pageable, countFindAllAssetCategoriesByCodeAndVisible(request));
    }

    @Override
    public Optional<AssetCategories> findAssetCategoriesVisibleByCodeName(String codeName) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select assetCategory.id_asset_category, assetCategory.name, " +
                "       assetCategory.short_name, assetCategory.code_name, " +
                "       assetCategory.description, assetCategory.parent, " +
                "       assetCategory.sort_order, assetCategory.asset_count, " +
                "       assetCategory.visible, assetCategory.time_created, " +
                "       assetCategory.time_modified, assetCategory.path_image, " +
                "       assetCategory.is_pick " +
                "from asset_categories assetCategory " +
                "where assetCategory.code_name = :codeName " +
                "and assetCategory.visible = :visible ");
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
                "and assetCategories.name = :name " +
                "and assetCategories.id_asset_category = :parentId ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("parentId", parentId);
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        return CollectionUtils.isEmpty(result);
    }

    @Override
    public Optional<AssetCategories> findAssetCategoryParentByParentId(Integer parentId) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select assetCategories.id_asset_category, assetCategories.name, " +
                "       assetCategories.short_name, assetCategories.code_name, " +
                "       assetCategories.description, assetCategories.parent, " +
                "       assetCategories.sort_order, assetCategories.asset_count, " +
                "       assetCategories.visible, assetCategories.time_created, " +
                "       assetCategories.time_modified, assetCategories.path_image, " +
                "       assetCategories.is_pick " +
                "from asset_categories assetCategories " +
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
                return Optional.of(categories);
            }
        }
        return Optional.empty();
    }

    private void setParameterFindAllAssetCategoriesByCodeAndVisible(FindAllAssetCategoriesRequest request, Query query) {
        query.setParameter("codeName", request.getCodeName().trim());
        query.setParameter("visible", Constants.IS_VISIBLE);
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllAssetCategoriesByCodeAndVisible(FindAllAssetCategoriesRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (cte.name REGEXP '[' + :keyword + ']') ");
        }
    }

    private long countFindAllAssetCategoriesByCodeAndVisible(FindAllAssetCategoriesRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_asset_categories as (  " +
                "    select assetCategires.id_asset_category,assetCategires.name,  " +
                "           assetCategires.code_name, assetCategires.short_name,  " +
                "           assetCategires.description, assetCategires.parent,  " +
                "           assetCategires.sort_order, assetCategires.asset_count,  " +
                "           assetCategires.visible, assetCategires.time_created,  " +
                "           assetCategires.time_modified, assetCategires.is_pick,  " +
                "           1 as depth,  " +
                "           CAST(assetCategires.id_asset_category as NCHAR ) as path  " +
                "    from asset_categories assetCategires  " +
                "    where assetCategires.code_name = :codeName  " +
                "    and assetCategires.visible = :visible  " +
                "    union all  " +
                "    select assetCategires.id_asset_category,assetCategires.name,  " +
                "           assetCategires.code_name, assetCategires.short_name,  " +
                "           assetCategires.description, assetCategires.parent,  " +
                "           assetCategires.sort_order, assetCategires.asset_count,  " +
                "           assetCategires.visible, assetCategires.time_created,  " +
                "           assetCategires.time_modified, assetCategires.is_pick,  " +
                "           cte.depth + 1 as depth,  " +
                "           concat_ws('/',cte.path,CAST(assetCategires.id_asset_category as NCHAR)) as path  " +
                "    from asset_categories assetCategires  " +
                "             INNER JOIN cte_asset_categories cte ON assetCategires.parent = cte.id_asset_category  " +
                "    )  " +
                "select count(cte.id_asset_category) count  " +
                "from cte_asset_categories cte  " +
                "where 1 = 1 ");
        setConditionFindAllAssetCategoriesByCodeAndVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetCategoriesByCodeAndVisible(request, query);
        return  ValueUtil.getLongByObject(query.getSingleResult());
    }
}
