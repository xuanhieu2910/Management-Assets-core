package com.example.csvccdshustbe.repository.projects.impl;

import com.example.csvccdshustbe.dto.projects.FindAllProjectsDto;
import com.example.csvccdshustbe.repository.projects.ProjectsRepositoryCustom;
import com.example.csvccdshustbe.request.projects.FindAllProjectsRequest;
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

public class ProjectsRepositoryImpl implements ProjectsRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Page<FindAllProjectsDto> findAllProjectVisible(Pageable pageable, FindAllProjectsRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_projects as ( " +
                "    select projects.id_project, projects.name, projects.short_name, " +
                "           projects.parent, projects.time_created, projects.time_modified, " +
                "           projects.visible, " +
                "           1 as depth, " +
                "           CAST(projects.id_project as NCHAR ) as path " +
                "    from projects projects " +
                "    where projects.parent is null " +
                "    union all " +
                "    select projects.id_project, projects.name, projects.short_name, " +
                "           projects.parent, projects.time_created, projects.time_modified, " +
                "           projects.visible, " +
                "           cte.depth + 1 as depth, " +
                "           concat_ws('/',cte.path,CAST(projects.id_project as NCHAR)) as path " +
                "    from projects projects " +
                "             INNER JOIN cte_projects cte ON projects.parent = cte.id_project " +
                "    ) " +
                "select cte.id_project, cte.name, cte.short_name, " +
                "       cte.parent, cte.time_created, cte.time_modified, " +
                "       cte.visible, cte.depth, cte.path " +
                "from cte_projects cte " +
                "where 1 = 1 and cte.visible = :visible ");
        setConditionFindAllProjectVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProjectVisible(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllProjectsDto> findAllProjectsDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllProjectsDto dto = new FindAllProjectsDto();
                dto.setIdProject(ValueUtil.getIntegerByObject(obj[0]));
                dto.setName(ValueUtil.getStringByObject(obj[1]));
                dto.setShortName(ValueUtil.getStringByObject(obj[2]));
                dto.setParent(ValueUtil.getIntegerByObject(obj[3]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                dto.setVisible(ValueUtil.getIntegerByObject(obj[6]));
                dto.setDepth(ValueUtil.getIntegerByObject(obj[7]));
                dto.setPath(ValueUtil.getStringByObject(obj[8]));
                findAllProjectsDtos.add(dto);
            }
        }
        return new PageImpl<>(findAllProjectsDtos, pageable, countFindAllProjectVisible(request));
    }

    private void setParameterFindAllProjectVisible(FindAllProjectsRequest request, Query query) {
        query.setParameter("visible", Constants.PROJECTS_IS_VISIBLE);
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllProjectVisible(FindAllProjectsRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (cte.name REGEXP '[' + :keyword + ']') ");
        }
    }

    private long countFindAllProjectVisible(FindAllProjectsRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_projects as (      " +
                "       select projects.id_project, projects.name, projects.short_name,      " +
                "              projects.parent, projects.time_created, projects.time_modified,      " +
                "              projects.visible,      " +
                "              1 as depth,      " +
                "              CAST(projects.id_project as NCHAR ) as path      " +
                "       from projects projects      " +
                "       where projects.parent is null      " +
                "       union all      " +
                "       select projects.id_project, projects.name, projects.short_name,      " +
                "              projects.parent, projects.time_created, projects.time_modified,      " +
                "              projects.visible,      " +
                "              cte.depth + 1 as depth,      " +
                "              concat_ws('/',cte.path,CAST(projects.id_project as NCHAR)) as path      " +
                "       from projects projects      " +
                "                INNER JOIN cte_projects cte ON projects.parent = cte.id_project      " +
                "       )      " +
                "   select count(cte.id_project) count   " +
                "   from cte_projects cte      " +
                "   where 1 = 1 and cte.visible = :visible  ");
        setConditionFindAllProjectVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProjectVisible(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }
}