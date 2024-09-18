package com.example.csvccdshustbe.repository.projects.impl;

import com.example.csvccdshustbe.dto.projects.FindAllProjectsDto;
import com.example.csvccdshustbe.entity.Projects;
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
import java.util.Objects;
import java.util.Optional;

public class ProjectsRepositoryImpl implements ProjectsRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Page<FindAllProjectsDto> findAllProjectVisible(Pageable pageable, FindAllProjectsRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_projects as (       " +
                "        select projects.id_project, projects.name, projects.short_name,       " +
                "               projects.parent, projects.time_created, projects.time_modified,       " +
                "               projects.visible,       " +
                "               1 as depth,       " +
                "               CAST(projects.id_project as NCHAR ) as path ,   " +
                "               case when projects.parent is not null then projects.name end nameParent   " +
                "        from projects projects       " +
                "        where projects.parent is null       " +
                "        union all       " +
                "        select projects.id_project, projects.name, projects.short_name,       " +
                "               projects.parent, projects.time_created, projects.time_modified,       " +
                "               projects.visible,       " +
                "               cte.depth + 1 as depth,       " +
                "               concat_ws('/',cte.path,CAST(projects.id_project as NCHAR)) as path ,   " +
                "               cte.name nameParent   " +
                "        from projects projects       " +
                "                 INNER JOIN cte_projects cte ON projects.parent = cte.id_project       " +
                "        )       " +
                "select cte.id_project, cte.name, cte.short_name,   " +
                "           cte.parent, cte.time_created, cte.time_modified,       " +
                "           cte.visible, cte.depth, cte.path ,   " +
                "           cte.nameParent   " +
                "from cte_projects cte   " +
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
                dto.setNameParent(ValueUtil.getStringByObject(obj[9]));
                findAllProjectsDtos.add(dto);
            }
        }
        return new PageImpl<>(findAllProjectsDtos, pageable, countFindAllProjectVisible(request));
    }

    @Override
    public Page<FindAllProjectsDto> findAllProject(Pageable pageable, FindAllProjectsRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_projects as (      " +
                "      select projects.id_project, projects.name, projects.short_name,      " +
                "             projects.parent, projects.time_created, projects.time_modified,      " +
                "             projects.visible,      " +
                "             1 as depth,      " +
                "             CAST(projects.id_project as NCHAR ) as path,   " +
                "             case when projects.parent is not null then projects.name end nameParent   " +
                "      from projects projects      " +
                "      where projects.parent is null      " +
                "      union all      " +
                "      select projects.id_project, projects.name, projects.short_name,      " +
                "             projects.parent, projects.time_created, projects.time_modified,      " +
                "             projects.visible,      " +
                "             cte.depth + 1 as depth,      " +
                "             concat_ws('/',cte.path,CAST(projects.id_project as NCHAR)) as path,   " +
                "             cte.name nameParent   " +
                "      from projects projects      " +
                "               INNER JOIN cte_projects cte ON projects.parent = cte.id_project      " +
                "      )      " +
                "  select cte.id_project, cte.name, cte.short_name,      " +
                "         cte.parent, cte.time_created, cte.time_modified,      " +
                "         cte.visible, cte.depth, cte.path, cte.nameParent   " +
                "from cte_projects cte   " +
                "where 1 = 1 ");
        setConditionFindAllProject(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProject(request, query);
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
                dto.setNameParent(ValueUtil.getStringByObject(obj[9]));
                findAllProjectsDtos.add(dto);
            }
        }
        return new PageImpl<>(findAllProjectsDtos, pageable, countFindAllProject(request));
    }

    private void setParameterFindAllProjectVisible(FindAllProjectsRequest request, Query query) {
        query.setParameter("visible", Constants.PROJECTS_IS_VISIBLE);
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setParameterFindAllProject(FindAllProjectsRequest request, Query query) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
        if (!Objects.isNull(request.getVisible())) {
            query.setParameter("visible", request.getVisible());
        }
    }

    private void setConditionFindAllProjectVisible(FindAllProjectsRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (cte.name REGEXP :keyword ) ");
        }
        sb.append(" ORDER BY path ");
    }

    private void setConditionFindAllProject(FindAllProjectsRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (cte.name REGEXP :keyword ) ");
        }
        if (!Objects.isNull(request.getVisible())) {
            sb.append(" and cte.visible = :visible  ");
        }
        sb.append(" ORDER BY path ");
    }


    private long countFindAllProjectVisible(FindAllProjectsRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_projects as (       " +
                "        select projects.id_project, projects.name, projects.short_name,       " +
                "               projects.parent, projects.time_created, projects.time_modified,       " +
                "               projects.visible,       " +
                "               1 as depth,       " +
                "               CAST(projects.id_project as NCHAR ) as path ,   " +
                "               case when projects.parent is not null then projects.name end nameParent   " +
                "        from projects projects       " +
                "        where projects.parent is null       " +
                "        union all       " +
                "        select projects.id_project, projects.name, projects.short_name,       " +
                "               projects.parent, projects.time_created, projects.time_modified,       " +
                "               projects.visible,       " +
                "               cte.depth + 1 as depth,       " +
                "               concat_ws('/',cte.path,CAST(projects.id_project as NCHAR)) as path ,   " +
                "               cte.name nameParent   " +
                "        from projects projects       " +
                "                 INNER JOIN cte_projects cte ON projects.parent = cte.id_project       " +
                "        )       " +
                "select count(0) count   " +
                "from cte_projects cte   " +
                "where 1 = 1 and cte.visible = :visible ");
        setConditionFindAllProjectVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProjectVisible(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private long countFindAllProject(FindAllProjectsRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_projects as (      " +
                "      select projects.id_project, projects.name, projects.short_name,      " +
                "             projects.parent, projects.time_created, projects.time_modified,      " +
                "             projects.visible,      " +
                "             1 as depth,      " +
                "             CAST(projects.id_project as NCHAR ) as path,   " +
                "             case when projects.parent is not null then projects.name end nameParent   " +
                "      from projects projects      " +
                "      where projects.parent is null      " +
                "      union all      " +
                "      select projects.id_project, projects.name, projects.short_name,      " +
                "             projects.parent, projects.time_created, projects.time_modified,      " +
                "             projects.visible,      " +
                "             cte.depth + 1 as depth,      " +
                "             concat_ws('/',cte.path,CAST(projects.id_project as NCHAR)) as path,   " +
                "             cte.name nameParent   " +
                "      from projects projects      " +
                "               INNER JOIN cte_projects cte ON projects.parent = cte.id_project      " +
                "      )      " +
                "  select count(0) count " +
                "from cte_projects cte   " +
                "where 1 = 1  ");
        setConditionFindAllProject(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProject(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }


    @Override
    public Optional<Projects> findProjectByName(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select pj.id_project, pj.name, " +
                "  pj.short_name, pj.parent, " +
                "  pj.time_created, pj.time_modified, pj.visible " +
                " from projects pj " +
                "where pj.name = :name ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                Projects projects = new Projects();
                projects.setIdProject(ValueUtil.getIntegerByObject(obj[0]));
                projects.setName(ValueUtil.getStringByObject(obj[1]));
                projects.setShortName(ValueUtil.getStringByObject(obj[2]));
                projects.setShortName(ValueUtil.getStringByObject(obj[3]));
                projects.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                projects.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                projects.setVisible(ValueUtil.getIntegerByObject(obj[6]));
                return Optional.of(projects);
            }
        }
        return Optional.empty();
    }
    @Override
    public Optional<Projects> findProjectByIdParent(Integer idParent) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select pj.id_project, pj.name, " +
                "  pj.short_name, pj.parent, " +
                "  pj.time_created, pj.time_modified, pj.visible " +
                " from projects pj " +
                "where pj.id_project = :idParent ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idParent", idParent);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                Projects projects = new Projects();
                projects.setIdProject(ValueUtil.getIntegerByObject(obj[0]));
                projects.setName(ValueUtil.getStringByObject(obj[1]));
                projects.setShortName(ValueUtil.getStringByObject(obj[2]));
                projects.setParent(ValueUtil.getIntegerByObject(obj[3]));
                projects.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                projects.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                projects.setVisible(ValueUtil.getIntegerByObject(obj[6]));
                return Optional.of(projects);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Projects> findProjectById(Integer idProject) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select pj.id_project, pj.name, " +
                "  pj.short_name, pj.parent, " +
                "  pj.time_created, pj.time_modified, pj.visible " +
                " from projects pj " +
                "where pj.id_project = :idProject ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idProject", idProject);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                Projects projects = new Projects();
                projects.setIdProject(ValueUtil.getIntegerByObject(obj[0]));
                projects.setName(ValueUtil.getStringByObject(obj[1]));
                projects.setShortName(ValueUtil.getStringByObject(obj[2]));
                projects.setParent(ValueUtil.getIntegerByObject(obj[3]));
                projects.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                projects.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                projects.setVisible(ValueUtil.getIntegerByObject(obj[6]));
                return Optional.of(projects);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Projects> findProjectsByIdAndStatus(Integer idProject, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select pj.id_project, pj.name, " +
                "  pj.short_name, pj.parent, " +
                "  pj.time_created, pj.time_modified, pj.visible " +
                " from projects pj " +
                "where pj.id_project = :idProject and pj.visible = :visible ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idProject", idProject);
        query.setParameter("visible", status);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                Projects projects = new Projects();
                projects.setIdProject(ValueUtil.getIntegerByObject(obj[0]));
                projects.setName(ValueUtil.getStringByObject(obj[1]));
                projects.setShortName(ValueUtil.getStringByObject(obj[2]));
                projects.setParent(ValueUtil.getIntegerByObject(obj[3]));
                projects.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                projects.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                projects.setVisible(ValueUtil.getIntegerByObject(obj[6]));
                return Optional.of(projects);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean checkExitsProjectByNameOrCodeOrShortName(String name, String shortName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * " +
                "from projects pj " +
                "where 1 = 1 ");
        if (StringUtils.isNotBlank(name)){
            sb.append(" or pj.name = :name ");
        }

        if (StringUtils.isNotBlank(shortName)){
            sb.append(" or pj.short_name = :shortName ");
        }
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(name)){
            query.setParameter("name", name);
        }
        if (StringUtils.isNotBlank(shortName)){
            query.setParameter("shortName", shortName);
        }
        List<Object[]> result = query.getResultList();
        return CollectionUtils.isEmpty(result);
    }

    @Override
    public boolean isExitsAssetByIdProject(Integer idProject) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select asset.id_projects " +
                "from projects pro  " +
                "    inner join asset asset on pro.id_project = asset.id_projects " +
                "where pro.id_project = :idProject ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idProject", idProject);
        return !CollectionUtils.isEmpty(query.getResultList());
    }

}