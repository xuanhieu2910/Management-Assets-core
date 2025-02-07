package com.example.csvccdshustbe.repository.originalOfFormationTool.impl;

import com.example.csvccdshustbe.dto.originalOfFormation.FindAllOriginalOfFormationDto;
import com.example.csvccdshustbe.entity.OriginalOfFormation;
import com.example.csvccdshustbe.entity.OriginalOfFormationTool;
import com.example.csvccdshustbe.repository.originalOfFormationTool.OriginalOfFormationToolRepositoryCustom;
import com.example.csvccdshustbe.request.originalOfFormation.FindAllOriginalOfFormationVisibleRequest;
import com.example.csvccdshustbe.request.originalOfFormationTool.FindAllOriginalOfFormationToolVisibleRequest;
import com.example.csvccdshustbe.response.originalOfFormationTool.FindAllOriginalOfFormationToolVisibleResponse;
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

public class OriginalOfFormationToolRepositoryImpl implements OriginalOfFormationToolRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    
    @Override
    public Page<FindAllOriginalOfFormationDto> findAllOriginalOfFormationToolVisible(Pageable pageable, FindAllOriginalOfFormationToolVisibleRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("     WITH RECURSIVE cte_projects as (     " +
                "                       select originalOfFormationTool.id_original_of_formation_tool, originalOfFormationTool.name, " +
                "                              originalOfFormationTool.short_name, originalOfFormationTool.code_name,  " +
                "                              originalOfFormationTool.description, originalOfFormationTool.parent,  " +
                "                              originalOfFormationTool.sort_order, originalOfFormationTool.visible,  " +
                "                              originalOfFormationTool.time_created, originalOfFormationTool.time_modified,  " +
                "                              1 as depth,     " +
                "                              CAST(originalOfFormationTool.id_original_of_formation_tool as NCHAR ) as path " +
                "                       from original_of_formation_tool originalOfFormationTool " +
                "                       where originalOfFormationTool.parent is null  " +
                "                       union all     " +
                "                       select originalOfFormationTool.id_original_of_formation_tool, originalOfFormationTool.name, " +
                "                              originalOfFormationTool.short_name, originalOfFormationTool.code_name,  " +
                "                              originalOfFormationTool.description, originalOfFormationTool.parent,  " +
                "                              originalOfFormationTool.sort_order, originalOfFormationTool.visible,  " +
                "                              originalOfFormationTool.time_created, originalOfFormationTool.time_modified,  " +
                "                              cte.depth + 1 as depth,     " +
                "                              concat_ws('/',cte.path,CAST(originalOfFormationTool.id_original_of_formation_tool as NCHAR)) as path " +
                "                       from original_of_formation_tool originalOfFormationTool " +
                "                                INNER JOIN cte_projects cte ON originalOfFormationTool.parent = cte.id_original_of_formation_tool " +
                "                       )     " +
                "                   select cte.id_original_of_formation_tool, cte.name, cte.short_name, cte.code_name, " +
                "                          cte.description, cte.parent, cte.sort_order, cte.visible,  " +
                "                          cte.time_created, cte.time_modified, cte.depth, cte.path  " +
                "                   from cte_projects cte     " +
                "                   where 1 = 1  " +
                "                   and cte.visible = :visible  ");
        setConditionFindAllOriginalOfFormationVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllOriginalOfFormationVisible(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllOriginalOfFormationDto> dtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
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
        return new PageImpl<>(dtos, pageable, countFindAllOriginalOfFormationVisible(request));
    }

    @Override
    public Optional<OriginalOfFormationTool> findOriginalOfFormationToolByName(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select oof.id_original_of_formation_tool, oof.name, " +
                "       oof.short_name, oof.code_name, oof.description, oof.parent, " +
                "oof.sort_order, oof.visible, " +
                "       oof.time_created, oof.time_modified  " +
                "from original_of_formation_tool oof " +
                "where oof.name = :name ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                OriginalOfFormationTool originalOfFormationTool = new OriginalOfFormationTool();
                originalOfFormationTool.setIdOriginalOfFormationTool(ValueUtil.getIntegerByObject(obj[0]));
                originalOfFormationTool.setName(ValueUtil.getStringByObject(obj[1]));
                originalOfFormationTool.setShortName(ValueUtil.getStringByObject(obj[2]));
                originalOfFormationTool.setCodeName(ValueUtil.getStringByObject(obj[3]));
                originalOfFormationTool.setDescription(ValueUtil.getStringByObject(obj[4]));
                originalOfFormationTool.setParent(ValueUtil.getIntegerByObject(obj[5]));
                originalOfFormationTool.setSortOrder(ValueUtil.getStringByObject(obj[6]));
                originalOfFormationTool.setVisible(ValueUtil.getIntegerByObject(obj[7]));
                originalOfFormationTool.setTimeCreated(ValueUtil.getStringByObject(obj[8]));
                originalOfFormationTool.setTimeModified(ValueUtil.getStringByObject(obj[9]));
                return Optional.of(originalOfFormationTool);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<OriginalOfFormationTool> findOriginalOfFormationToolByIdParent(Integer idParent) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select oof.id_original_of_formation_tool, oof.name, " +
                "       oof.short_name, oof.code_name, oof.description, oof.parent, " +
                "oof.sort_order, oof.visible, " +
                "       oof.time_created, oof.time_modified  " +
                "from original_of_formation_tool oof " +
                "where oof.id_original_of_formation_tool = :idParent ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idParent", idParent);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                OriginalOfFormationTool originalOfFormationTool = new OriginalOfFormationTool();
                originalOfFormationTool.setIdOriginalOfFormationTool(ValueUtil.getIntegerByObject(obj[0]));
                originalOfFormationTool.setName(ValueUtil.getStringByObject(obj[1]));
                originalOfFormationTool.setShortName(ValueUtil.getStringByObject(obj[2]));
                originalOfFormationTool.setCodeName(ValueUtil.getStringByObject(obj[3]));
                originalOfFormationTool.setDescription(ValueUtil.getStringByObject(obj[4]));
                originalOfFormationTool.setParent(ValueUtil.getIntegerByObject(obj[5]));
                originalOfFormationTool.setSortOrder(ValueUtil.getStringByObject(obj[6]));
                originalOfFormationTool.setVisible(ValueUtil.getIntegerByObject(obj[7]));
                originalOfFormationTool.setTimeCreated(ValueUtil.getStringByObject(obj[8]));
                originalOfFormationTool.setTimeModified(ValueUtil.getStringByObject(obj[9]));

                return Optional.of(originalOfFormationTool);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<OriginalOfFormationTool> findOriginalOfFormationToolById(Integer idOriginalOfFormationTool) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select oof.id_original_of_formation_tool, oof.name, " +
                "       oof.short_name, oof.code_name, oof.description, oof.parent, " +
                "oof.sort_order, oof.visible, " +
                "       oof.time_created, oof.time_modified  " +
                "from original_of_formation_tool oof " +
                "where oof.id_original_of_formation_tool = :idOriginalOfFormationTool ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idOriginalOfFormationTool", idOriginalOfFormationTool);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                OriginalOfFormationTool originalOfFormationTool = new OriginalOfFormationTool();
                originalOfFormationTool.setIdOriginalOfFormationTool(ValueUtil.getIntegerByObject(obj[0]));
                originalOfFormationTool.setName(ValueUtil.getStringByObject(obj[1]));
                originalOfFormationTool.setShortName(ValueUtil.getStringByObject(obj[2]));
                originalOfFormationTool.setCodeName(ValueUtil.getStringByObject(obj[3]));
                originalOfFormationTool.setDescription(ValueUtil.getStringByObject(obj[4]));
                originalOfFormationTool.setParent(ValueUtil.getIntegerByObject(obj[5]));
                originalOfFormationTool.setSortOrder(ValueUtil.getStringByObject(obj[6]));
                originalOfFormationTool.setVisible(ValueUtil.getIntegerByObject(obj[7]));
                originalOfFormationTool.setTimeCreated(ValueUtil.getStringByObject(obj[8]));
                originalOfFormationTool.setTimeModified(ValueUtil.getStringByObject(obj[9]));
                return Optional.of(originalOfFormationTool);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean checkExitsOriginalOfFormationToolByNameOrShortNameOrCodeName(String name, String codeName, String shortName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * " +
                "from original_of_formation_tool oof " +
                "where 1 = 1 ");
        if (StringUtils.isNotBlank(name)){
            sb.append(" or oof.name = :name ");
        }
        if (StringUtils.isNotBlank(codeName)){
            sb.append(" or oof.code_name = :codeName ");
        }
        if (StringUtils.isNotBlank(shortName)){
            sb.append(" or oof.short_name = :shortName ");
        }
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(name)){
            query.setParameter("name", name);
        }
        if (StringUtils.isNotBlank(codeName)){
            query.setParameter("codeName", codeName);
        }
        if (StringUtils.isNotBlank(shortName)){
            query.setParameter("shortName", shortName);
        }
        List<Object[]> result = query.getResultList();
        return CollectionUtils.isEmpty(result);
    }


    private void setConditionFindAllOriginalOfFormationVisible(FindAllOriginalOfFormationToolVisibleRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (cte.name REGEXP :keyword )  ");
        }
        sb.append(" ORDER BY path ");
    }

    private void setParameterFindAllOriginalOfFormationVisible(FindAllOriginalOfFormationToolVisibleRequest request, Query query) {
        query.setParameter("visible", Constants.ORIGINAL_OF_FORMATION_VISIBLE);
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
    }
    private long countFindAllOriginalOfFormationVisible(FindAllOriginalOfFormationToolVisibleRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("    WITH RECURSIVE cte_projects as (     " +
                "                       select originalOfFormationTool.id_original_of_formation_tool, originalOfFormationTool.name, " +
                "                              originalOfFormationTool.short_name, originalOfFormationTool.code_name,  " +
                "                              originalOfFormationTool.description, originalOfFormationTool.parent,  " +
                "                              originalOfFormationTool.sort_order, originalOfFormationTool.visible,  " +
                "                              originalOfFormationTool.time_created, originalOfFormationTool.time_modified,  " +
                "                              1 as depth,     " +
                "                              CAST(originalOfFormationTool.id_original_of_formation_tool as NCHAR ) as path " +
                "                       from original_of_formation_tool originalOfFormationTool " +
                "                       where originalOfFormationTool.parent is null  " +
                "                       union all     " +
                "                       select originalOfFormationTool.id_original_of_formation_tool, originalOfFormationTool.name, " +
                "                              originalOfFormationTool.short_name, originalOfFormationTool.code_name,  " +
                "                              originalOfFormationTool.description, originalOfFormationTool.parent,  " +
                "                              originalOfFormationTool.sort_order, originalOfFormationTool.visible,  " +
                "                              originalOfFormationTool.time_created, originalOfFormationTool.time_modified,  " +
                "                              cte.depth + 1 as depth,     " +
                "                              concat_ws('/',cte.path,CAST(originalOfFormationTool.id_original_of_formation_tool as NCHAR)) as path " +
                "                       from original_of_formation_tool originalOfFormationTool " +
                "                                INNER JOIN cte_projects cte ON originalOfFormationTool.parent = cte.id_original_of_formation_tool " +
                "                       )     " +
                "                   select  count(0) count " +
                "                   from cte_projects cte     " +
                "                   where 1 = 1  " +
                "                   and cte.visible = :visible  ");
        setConditionFindAllOriginalOfFormationVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllOriginalOfFormationVisible(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

}
