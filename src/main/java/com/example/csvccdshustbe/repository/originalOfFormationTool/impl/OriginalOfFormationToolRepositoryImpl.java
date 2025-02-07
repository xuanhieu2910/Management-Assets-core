package com.example.csvccdshustbe.repository.originalOfFormationTool.impl;

import com.example.csvccdshustbe.dto.originalOfFormationTool.FindAllOriginalOfFormationToolDto;
import com.example.csvccdshustbe.repository.originalOfFormationTool.OriginalOfFormationToolRepositoryCustom;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class OriginalOfFormationToolRepositoryImpl implements OriginalOfFormationToolRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<FindAllOriginalOfFormationToolDto> findAllOriginalOfFormationDtoByVisible(Integer visible) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_projects as (        " +
                "       select originalOfFormation.id_original_of_formation_tool, originalOfFormation.name,  " +
                "              originalOfFormation.short_name, originalOfFormation.code_name,  " +
                "              originalOfFormation.description, originalOfFormation.parent,  " +
                "              originalOfFormation.sort_order, originalOfFormation.visible,  " +
                "              originalOfFormation.time_created, originalOfFormation.time_modified,  " +
                "              1 as depth,  " +
                "              CAST(originalOfFormation.id_original_of_formation_tool as NCHAR ) as path  " +
                "       from original_of_formation_tool originalOfFormation  " +
                "       where originalOfFormation.parent is null  " +
                "       union all  " +
                "       select originalOfFormation.id_original_of_formation_tool, originalOfFormation.name,  " +
                "              originalOfFormation.short_name, originalOfFormation.code_name,  " +
                "              originalOfFormation.description, originalOfFormation.parent,  " +
                "              originalOfFormation.sort_order, originalOfFormation.visible,  " +
                "              originalOfFormation.time_created, originalOfFormation.time_modified,  " +
                "              cte.depth + 1 as depth,  " +
                "              concat_ws('/',cte.path,CAST(originalOfFormation.id_original_of_formation_tool as NCHAR)) as path  " +
                "       from original_of_formation_tool originalOfFormation  " +
                "                INNER JOIN cte_projects cte ON originalOfFormation.parent = cte.id_original_of_formation_tool  " +
                "       )  " +
                "   select cte.id_original_of_formation_tool, cte.name, cte.short_name, cte.code_name,  " +
                "          cte.description, cte.parent, cte.sort_order, cte.visible,  " +
                "          cte.time_created, cte.time_modified, cte.depth, cte.path  " +
                "   from cte_projects cte  " +
                "   where 1 = 1  " +
                "   and cte.visible = :visible ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("visible", Constants.ORIGINAL_OF_FORMATION_VISIBLE);
        List<Object[]> result = query.getResultList();
        List<FindAllOriginalOfFormationToolDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                FindAllOriginalOfFormationToolDto response = new FindAllOriginalOfFormationToolDto();
                response.setIdOriginalOfFormationTool(ValueUtil.getIntegerByObject(obj[0]));
                response.setName(ValueUtil.getStringByObject(obj[1]));
                response.setShortName(ValueUtil.getStringByObject(obj[2]));
                response.setCodeName(ValueUtil.getStringByObject(obj[3]));
                response.setDescription(ValueUtil.getStringByObject(obj[4]));
                response.setParent(ValueUtil.getIntegerByObject(obj[5]));
                response.setSortOrder(ValueUtil.getStringByObject(obj[6]));
                response.setVisible(ValueUtil.getIntegerByObject(obj[7]));
                response.setTimeCreated(ValueUtil.getStringByObject(obj[8]));
                response.setTimeModified(ValueUtil.getStringByObject(obj[9]));
                response.setDepth(ValueUtil.getIntegerByObject(obj[10]));
                response.setPath(ValueUtil.getStringByObject(obj[11]));
                responses.add(response);
            }
        }
        return responses;
    }
}
