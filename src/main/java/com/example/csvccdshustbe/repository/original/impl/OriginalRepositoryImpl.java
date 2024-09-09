package com.example.csvccdshustbe.repository.original.impl;

import com.example.csvccdshustbe.dto.original.FindAllOriginalDto;
import com.example.csvccdshustbe.entity.Original;
import com.example.csvccdshustbe.repository.original.OriginalRepositoryCustom;
import com.example.csvccdshustbe.request.original.FindAllOriginalVisibleRequest;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OriginalRepositoryImpl implements OriginalRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Page<FindAllOriginalDto> findAllOriginalDtoByIdAssetCategory(FindAllOriginalVisibleRequest request,
                                                                        Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_original as (        " +
                "     select original.id_original, original.name, original.short_name,        " +
                "            original.description, original.parent, original.sort_order,        " +
                "            original.visible, original.time_created, original.time_modified,        " +
                "            original.id_user_created, original.id_user_modified, original.id_asset_category,        " +
                "            original.hard_code_dev, original.is_default,        " +
                "            1 as depth,   CAST(original.id_original as NCHAR ) as path        " +
                "     from original        " +
                "     where original.parent is null        " +
                "     union all        " +
                "     select original.id_original, original.name, original.short_name,        " +
                "            original.description, original.parent, original.sort_order,        " +
                "            original.visible, original.time_created, original.time_modified,        " +
                "            original.id_user_created, original.id_user_modified,        " +
                "            original.id_asset_category, original.hard_code_dev,        " +
                "            original.is_default,        " +
                "            cte.depth + 1 as depth,        " +
                "            concat_ws('/',cte.path,CAST(original.id_original as NCHAR)) as path        " +
                "     from original        " +
                "     INNER JOIN cte_original cte ON original.parent = cte.id_original )        " +
                "                    select cte.id_original, cte.name, cte.short_name,        " +
                "      cte.description, cte.parent, cte.sort_order,        " +
                "      cte.visible, cte.time_created, cte.time_modified,        " +
                "      cte.id_user_created, cte.id_user_modified,        " +
                "      cte.id_asset_category, cte.hard_code_dev,        " +
                "      cte.is_default, cte.depth, cte.path        " +
                "                    from cte_original cte        " +
                "                        inner join asset_categories assetCategory on cte.id_asset_category = assetCategory.id_asset_category        " +
                "                    where 1 = 1 and cte.visible = :visible and assetCategory.id_asset_category = :idParentAssetCategory ");
        setConditionFindAllVisibleOriginalByIdAssetCategory(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllVisibleOriginalByIdAsssetCategory(request,query);
        PageUtils.buildQuery(pageable,query);
        List<Object[]> result = query.getResultList();
        List<FindAllOriginalDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                FindAllOriginalDto dto = new FindAllOriginalDto();
                dto.setIdOriginal(ValueUtil.getIntegerByObject(obj[0]));
                dto.setName(ValueUtil.getStringByObject(obj[1]));
                dto.setShortName(ValueUtil.getStringByObject(obj[2]));
                dto.setDescription(ValueUtil.getStringByObject(obj[3]));
                dto.setParent(ValueUtil.getIntegerByObject(obj[4]));
                dto.setSortOrder(ValueUtil.getStringByObject(obj[5]));
                dto.setVisible(ValueUtil.getIntegerByObject(obj[6]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[7]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[8]));
                dto.setIdUserCreated(ValueUtil.getIntegerByObject(obj[9]));
                dto.setIdUserModified(ValueUtil.getIntegerByObject(obj[10]));
                dto.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[11]));
                dto.setHardCodeDev(ValueUtil.getStringByObject(obj[12]));
                dto.setIsDefault(ValueUtil.getIntegerByObject(obj[13]));
                dto.setDepth(ValueUtil.getIntegerByObject(obj[14]));
                dto.setPath(ValueUtil.getStringByObject(obj[15]));
                responses.add(dto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllVisibleOriginalByIdAssetCategory(request));
    }

    @Override
    public Optional<Original> findOriginalByHardCodeAndStatus(String hardCode, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ori.id_original, ori.name, ori.short_name, " +
                "       ori.description, ori.parent, ori.sort_order, " +
                "       ori.visible, ori.time_created, ori.time_modified, " +
                "       ori.id_user_created, ori.id_user_modified, " +
                "       ori.id_asset_category, ori.hard_code_dev, ori.is_default " +
                "from original ori " +
                "where ori.hard_code_dev = :hardCode " +
                "and ori.visible = :visible ");
        return Optional.empty();
    }

    private void setParameterFindAllVisibleOriginalByIdAsssetCategory(FindAllOriginalVisibleRequest request, Query query) {
        query.setParameter("visible", Constants.ORIGINAL_VISIBLE);
        query.setParameter("idParentAssetCategory", request.getIdParentAssetCategory());
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllVisibleOriginalByIdAssetCategory(FindAllOriginalVisibleRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (cte.name REGEXP :keyword )  ");
        }
        sb.append(" ORDER BY path ");
    }

    private long countFindAllVisibleOriginalByIdAssetCategory(FindAllOriginalVisibleRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_original as ( " +
                "  select original.id_original, original.name, original.short_name, " +
                "         original.description, original.parent, original.sort_order, " +
                "         original.visible, original.time_created, original.time_modified, " +
                "         original.id_user_created, original.id_user_modified, original.id_asset_category, " +
                "         original.hard_code_dev, original.is_default,  " +
                "         1 as depth,   CAST(original.id_original as NCHAR ) as path   " +
                "  from original   " +
                "  where original.parent is null  " +
                "  union all  " +
                "  select original.id_original, original.name, original.short_name,  " +
                "         original.description, original.parent, original.sort_order,  " +
                "         original.visible, original.time_created, original.time_modified, " +
                "         original.id_user_created, original.id_user_modified,  " +
                "         original.id_asset_category, original.hard_code_dev, " +
                "         original.is_default, " +
                "         cte.depth + 1 as depth,  " +
                "         concat_ws('/',cte.path,CAST(original.id_original as NCHAR)) as path " +
                "  from original  " +
                "  INNER JOIN cte_original cte ON original.parent = cte.id_original )  " +
                "                 select count(0)   " +
                "                 from cte_original cte  " +
                "                     inner join asset_categories assetCategory on cte.id_asset_category = assetCategory.id_asset_category  " +
                "                 where 1 = 1 and cte.visible = :visible and assetCategory.id_asset_category = :idParentAssetCategory  ");
        setConditionFindAllVisibleOriginalByIdAssetCategory(request,sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllVisibleOriginalByIdAsssetCategory(request,query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }
}
