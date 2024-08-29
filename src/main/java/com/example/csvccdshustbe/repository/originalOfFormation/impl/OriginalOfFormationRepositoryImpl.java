package com.example.csvccdshustbe.repository.originalOfFormation.impl;

import com.example.csvccdshustbe.dto.originalOfFormation.FindAllOriginalOfFormationDto;
import com.example.csvccdshustbe.repository.originalOfFormation.OriginalOfFormationRepositoryCustom;
import com.example.csvccdshustbe.request.originalOfFormation.FindAllOriginalOfFormationRequest;
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

public class OriginalOfFormationRepositoryImpl implements OriginalOfFormationRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<FindAllOriginalOfFormationDto>
    findAllOriginalOfFormationVisible(Pageable pageable, FindAllOriginalOfFormationRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_projects as (    " +
                "       select originalOfFormation.id_original_of_formation, originalOfFormation.name, " +
                "              originalOfFormation.short_name, originalOfFormation.code_name, " +
                "              originalOfFormation.description, originalOfFormation.parent, " +
                "              originalOfFormation.sort_order, originalOfFormation.visible, " +
                "              originalOfFormation.time_created, originalOfFormation.time_modified, " +
                "              1 as depth,    " +
                "              CAST(originalOfFormation.id_original_of_formation as NCHAR ) as path " +
                "       from original_of_formation originalOfFormation " +
                "       where originalOfFormation.parent is null " +
                "       union all    " +
                "       select originalOfFormation.id_original_of_formation, originalOfFormation.name, " +
                "              originalOfFormation.short_name, originalOfFormation.code_name, " +
                "              originalOfFormation.description, originalOfFormation.parent, " +
                "              originalOfFormation.sort_order, originalOfFormation.visible, " +
                "              originalOfFormation.time_created, originalOfFormation.time_modified, " +
                "              cte.depth + 1 as depth,    " +
                "              concat_ws('/',cte.path,CAST(originalOfFormation.id_original_of_formation as NCHAR)) as path " +
                "       from original_of_formation originalOfFormation " +
                "                INNER JOIN cte_projects cte ON originalOfFormation.parent = cte.id_original_of_formation " +
                "       )    " +
                "   select cte.id_original_of_formation, cte.name, cte.short_name, cte.code_name, " +
                "          cte.description, cte.parent, cte.sort_order, cte.visible, " +
                "          cte.time_created, cte.time_modified, cte.depth, cte.path " +
                "   from cte_projects cte    " +
                "   where 1 = 1 " +
                "   and cte.visible = :visible ");
        setConditionFindAllOriginalOfFormation(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllOriginalOfFormation(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllOriginalOfFormationDto> dtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                FindAllOriginalOfFormationDto dto = new FindAllOriginalOfFormationDto();
                dto.setIdOriginalOfFormation(ValueUtil.getIntegerByObject(obj[0]));
                dto.setName(ValueUtil.getStringByObject(obj[1]));
                dto.setShortName(ValueUtil.getStringByObject(obj[2]));
                dto.setCodeName(ValueUtil.getStringByObject(obj[3]));
                dto.setDescription(ValueUtil.getStringByObject(obj[4]));
                dto.setParent(ValueUtil.getIntegerByObject(obj[5]));
                dto.setSortOrder(ValueUtil.getStringByObject(obj[6]));
                dto.setVisible(ValueUtil.getIntegerByObject(obj[7]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[8]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[9]));
                dto.setDepth(ValueUtil.getIntegerByObject(obj[10]));
                dto.setPath(ValueUtil.getStringByObject(obj[11]));
                dtos.add(dto);
            }
        }
        return new PageImpl<>(dtos, pageable, countFindAllOriginalOfFormation(request));
    }

    private void setParameterFindAllOriginalOfFormation(FindAllOriginalOfFormationRequest request, Query query) {
        query.setParameter("visible", Constants.ORIGINAL_OF_FORMATION_VISIBLE);
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllOriginalOfFormation(FindAllOriginalOfFormationRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (cte.name REGEXP '[' + :keyword + ']')  ");
        }
    }

    private long countFindAllOriginalOfFormation(FindAllOriginalOfFormationRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_projects as (    " +
                "       select originalOfFormation.id_original_of_formation, originalOfFormation.name, " +
                "              originalOfFormation.short_name, originalOfFormation.code_name, " +
                "              originalOfFormation.description, originalOfFormation.parent, " +
                "              originalOfFormation.sort_order, originalOfFormation.visible, " +
                "              originalOfFormation.time_created, originalOfFormation.time_modified, " +
                "              1 as depth,    " +
                "              CAST(originalOfFormation.id_original_of_formation as NCHAR ) as path " +
                "       from original_of_formation originalOfFormation " +
                "       where originalOfFormation.parent is null " +
                "       union all    " +
                "       select originalOfFormation.id_original_of_formation, originalOfFormation.name, " +
                "              originalOfFormation.short_name, originalOfFormation.code_name, " +
                "              originalOfFormation.description, originalOfFormation.parent, " +
                "              originalOfFormation.sort_order, originalOfFormation.visible, " +
                "              originalOfFormation.time_created, originalOfFormation.time_modified, " +
                "              cte.depth + 1 as depth,    " +
                "              concat_ws('/',cte.path,CAST(originalOfFormation.id_original_of_formation as NCHAR)) as path " +
                "       from original_of_formation originalOfFormation " +
                "                INNER JOIN cte_projects cte ON originalOfFormation.parent = cte.id_original_of_formation " +
                "       )    " +
                "   select count(0) count " +
                "   from cte_projects cte    " +
                "   where 1 = 1 " +
                "   and cte.visible = :visible  ");
        setConditionFindAllOriginalOfFormation(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllOriginalOfFormation(request, query);
        return  ValueUtil.getLongByObject(query.getSingleResult());
    }

}
