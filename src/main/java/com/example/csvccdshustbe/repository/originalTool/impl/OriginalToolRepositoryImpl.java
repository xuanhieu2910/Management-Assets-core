package com.example.csvccdshustbe.repository.originalTool.impl;

import com.example.csvccdshustbe.dto.originalTool.FindAllOriginalToolDto;
import com.example.csvccdshustbe.entity.OriginalTool;
import com.example.csvccdshustbe.repository.originalTool.OriginalToolRepositoryCustom;
import com.example.csvccdshustbe.request.originalTool.FindAllOriginalToolRequest;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class OriginalToolRepositoryImpl implements OriginalToolRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public Page<FindAllOriginalToolDto> findAllOriginalToolDto(FindAllOriginalToolRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_originalTool as (          " +
                "                     select originalTool.id_original_tool, originalTool.name, originalTool.short_name,  " +
                "                            originalTool.description, originalTool.parent, originalTool.sort_order,          " +
                "                            originalTool.visible, originalTool.time_created, originalTool.time_modified,          " +
                "                            originalTool.id_user_created, originalTool.id_user_modified, originalTool.id_tool_category,  " +
                "                            1 as depth,   CAST(originalTool.id_original_tool as NCHAR ) as path,originalTool.code " +
                "                     from original_tool originalTool  " +
                "                     where originalTool.parent is null          " +
                "                     union all          " +
                "                     select originalTool.id_original_tool, originalTool.name, originalTool.short_name,  " +
                "                            originalTool.description, originalTool.parent, originalTool.sort_order,          " +
                "                            originalTool.visible, originalTool.time_created, originalTool.time_modified,          " +
                "                            originalTool.id_user_created, originalTool.id_user_modified,          " +
                "                            originalTool.id_tool_category,  " +
                "                            cte.depth + 1 as depth,  " +
                "                            concat_ws('/',cte.path,CAST(originalTool.id_original_tool as NCHAR)) as path ,originalTool.code " +
                "                     from original_tool   originalTool  " +
                "                     INNER JOIN cte_originalTool cte ON originalTool.parent = cte.id_original_tool )  " +
                "                                    select cte.id_original_tool, cte.name, cte.short_name,  " +
                "                      cte.description, cte.parent, cte.sort_order,          " +
                "                      cte.visible, cte.time_created, cte.time_modified,          " +
                "                      cte.id_user_created, cte.id_user_modified,          " +
                "                      cte.id_tool_category,cte.depth, cte.path ,cte.code " +
                "                                    from cte_originalTool cte          " +
                "                                        left join tool_categories toolCategory on cte.id_tool_category = toolCategory.id_tool_category  " +
                "                                    where 1 = 1  ");
        setConditionFindAllOriginalTool(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllOriginalTool(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllOriginalToolDto> originalToolDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] objects : result) {
                FindAllOriginalToolDto findAllOriginalToolDto = new FindAllOriginalToolDto();
                findAllOriginalToolDto.setIdOriginalTool(ValueUtil.getIntegerByObject(objects[0]));
                findAllOriginalToolDto.setName(ValueUtil.getStringByObject(objects[1]));
                findAllOriginalToolDto.setShortName(ValueUtil.getStringByObject(objects[2]));
                findAllOriginalToolDto.setDescription(ValueUtil.getStringByObject(objects[3]));
                findAllOriginalToolDto.setParent(ValueUtil.getIntegerByObject(objects[4]));
                findAllOriginalToolDto.setSortOrder(ValueUtil.getStringByObject(objects[5]));
                findAllOriginalToolDto.setVisible(ValueUtil.getIntegerByObject(objects[6]));
                findAllOriginalToolDto.setTimeCreated(ValueUtil.getStringByObject(objects[7]));
                findAllOriginalToolDto.setTimeModified(ValueUtil.getStringByObject(objects[8]));
                findAllOriginalToolDto.setIdUserCreated(ValueUtil.getIntegerByObject(objects[9]));
                findAllOriginalToolDto.setIdUserModified(ValueUtil.getIntegerByObject(objects[10]));
                findAllOriginalToolDto.setIdToolCategory(ValueUtil.getIntegerByObject(objects[11]));
                findAllOriginalToolDto.setDepth(ValueUtil.getIntegerByObject(objects[12]));
                findAllOriginalToolDto.setPath(ValueUtil.getStringByObject(objects[13]));
                findAllOriginalToolDto.setCode(ValueUtil.getStringByObject(objects[14]));
                originalToolDtos.add(findAllOriginalToolDto);
            }
        }

        return new PageImpl<>(originalToolDtos, pageable, countFindAllVisibleOriginalTool(request));
    }

    private long countFindAllVisibleOriginalTool(FindAllOriginalToolRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_originalTool as (          " +
                "                     select originalTool.id_original_tool, originalTool.name, originalTool.short_name,  " +
                "                            originalTool.description, originalTool.parent, originalTool.sort_order,          " +
                "                            originalTool.visible, originalTool.time_created, originalTool.time_modified,          " +
                "                            originalTool.id_user_created, originalTool.id_user_modified, originalTool.id_tool_category,  " +
                "                            1 as depth,   CAST(originalTool.id_original_tool as NCHAR ) as path,originalTool.code   " +
                "                     from original_tool originalTool  " +
                "                     where originalTool.parent is null          " +
                "                     union all          " +
                "                     select originalTool.id_original_tool, originalTool.name, originalTool.short_name,  " +
                "                            originalTool.description, originalTool.parent, originalTool.sort_order,          " +
                "                            originalTool.visible, originalTool.time_created, originalTool.time_modified,          " +
                "                            originalTool.id_user_created, originalTool.id_user_modified,          " +
                "                            originalTool.id_tool_category,  " +
                "                            cte.depth + 1 as depth,  " +
                "                            concat_ws('/',cte.path,CAST(originalTool.id_original_tool as NCHAR)) as path ,originalTool.code  " +
                "                     from original_tool   originalTool  " +
                "                     INNER JOIN cte_originalTool cte ON originalTool.parent = cte.id_original_tool )  " +
                "                                    select count(0) " +
                "                                    from cte_originalTool cte          " +
                "                                        left join tool_categories toolCategory on cte.id_tool_category = toolCategory.id_tool_category  " +
                "                                    where 1 = 1  ");
        setConditionFindAllOriginalTool(request,sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllOriginalTool(request,query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllOriginalTool(FindAllOriginalToolRequest request, Query query) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
        if (ObjectUtils.isNotEmpty(request.getVisible())) {
            query.setParameter("visible", request.getVisible());
        }
        if (ObjectUtils.isNotEmpty(request.getCode())) {
            query.setParameter("code", request.getCode());
        }
    }

    private void setConditionFindAllOriginalTool(FindAllOriginalToolRequest request, StringBuilder sb) {
        if (ObjectUtils.isNotEmpty(request.getVisible())) {
            sb.append(" and (cte.visible = :visible ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getCode())) {
            sb.append(" and (cte.code = :code ) ");
        }
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (ut.name REGEXP :keyword ) ");
        }
        sb.append(" ORDER BY path ");

    }
}
