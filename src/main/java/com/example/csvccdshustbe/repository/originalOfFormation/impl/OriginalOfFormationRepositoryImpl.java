package com.example.csvccdshustbe.repository.originalOfFormation.impl;

import com.example.csvccdshustbe.dto.originalOfFormation.FindAllOriginalOfFormationDto;
import com.example.csvccdshustbe.entity.Department;
import com.example.csvccdshustbe.entity.OriginalOfFormation;
import com.example.csvccdshustbe.repository.originalOfFormation.OriginalOfFormationRepositoryCustom;
import com.example.csvccdshustbe.request.originalOfFormation.FindAllOriginalOfFormationRequest;
import com.example.csvccdshustbe.request.originalOfFormation.FindAllOriginalOfFormationVisibleRequest;
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

public class OriginalOfFormationRepositoryImpl implements OriginalOfFormationRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<FindAllOriginalOfFormationDto>
    findAllOriginalOfFormationVisible(Pageable pageable, FindAllOriginalOfFormationVisibleRequest request) {
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
    public Page<FindAllOriginalOfFormationDto> findAllOriginalOfFormation(Pageable pageable, FindAllOriginalOfFormationRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_projects as (       " +
                "      select originalOfFormation.id_original_of_formation, originalOfFormation.name,    " +
                "             originalOfFormation.short_name, originalOfFormation.code_name,    " +
                "             originalOfFormation.description, originalOfFormation.parent,    " +
                "             originalOfFormation.sort_order, originalOfFormation.visible,    " +
                "             originalOfFormation.time_created, originalOfFormation.time_modified,    " +
                "             1 as depth,       " +
                "             CAST(originalOfFormation.id_original_of_formation as NCHAR ) as path,  " +
                "             case when originalOfFormation.parent is not null then originalOfFormation.name end nameParent  " +
                "      from original_of_formation originalOfFormation    " +
                "      where originalOfFormation.parent is null    " +
                "      union all       " +
                "      select originalOfFormation.id_original_of_formation, originalOfFormation.name,    " +
                "             originalOfFormation.short_name, originalOfFormation.code_name,    " +
                "             originalOfFormation.description, originalOfFormation.parent,    " +
                "             originalOfFormation.sort_order, originalOfFormation.visible,    " +
                "             originalOfFormation.time_created, originalOfFormation.time_modified,    " +
                "             cte.depth + 1 as depth,       " +
                "             concat_ws('/',cte.path,CAST(originalOfFormation.id_original_of_formation as NCHAR)) as path,  " +
                "             cte.name nameParent  " +
                "      from original_of_formation originalOfFormation    " +
                "               INNER JOIN cte_projects cte ON originalOfFormation.parent = cte.id_original_of_formation    " +
                "      )       " +
                "select cte.id_original_of_formation, cte.name, cte.short_name, cte.code_name,  " +
                "         cte.description, cte.parent, cte.sort_order, cte.visible,    " +
                "         cte.time_created, cte.time_modified, cte.depth, cte.path,  " +
                "         cte.nameParent  " +
                "from cte_projects cte  " +
                "where 1 = 1 ");
        setConditionFindAllOriginalOfFormation(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllOriginalOfFormation(request, query);
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
                dto.setNameParent(ValueUtil.getStringByObject(obj[12]));
                dtos.add(dto);
            }
        }
        return new PageImpl<>(dtos, pageable, countFindAllOriginalOfFormation(request));
    }

    private void setParameterFindAllOriginalOfFormationVisible(FindAllOriginalOfFormationVisibleRequest request, Query query) {
        query.setParameter("visible", Constants.ORIGINAL_OF_FORMATION_VISIBLE);
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
    }


    private void setParameterFindAllOriginalOfFormation(FindAllOriginalOfFormationRequest request, Query query) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
        if (!Objects.isNull(request.getStatus())) {
            query.setParameter("visible", request.getStatus());
        }
    }

    private void setConditionFindAllOriginalOfFormationVisible(FindAllOriginalOfFormationVisibleRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (cte.name REGEXP :keyword )  ");
        }
        sb.append(" ORDER BY path ");
    }

    private void setConditionFindAllOriginalOfFormation(FindAllOriginalOfFormationRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (cte.name REGEXP :keyword )  ");
        }
        if (!Objects.isNull(request.getStatus())) {
            sb.append(" and cte.visible = :visible ");
        }
        sb.append(" ORDER BY path ");
    }

    private long countFindAllOriginalOfFormationVisible(FindAllOriginalOfFormationVisibleRequest request) {
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
        setConditionFindAllOriginalOfFormationVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllOriginalOfFormationVisible(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private long countFindAllOriginalOfFormation(FindAllOriginalOfFormationRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_projects as (       " +
                "      select originalOfFormation.id_original_of_formation, originalOfFormation.name,    " +
                "             originalOfFormation.short_name, originalOfFormation.code_name,    " +
                "             originalOfFormation.description, originalOfFormation.parent,    " +
                "             originalOfFormation.sort_order, originalOfFormation.visible,    " +
                "             originalOfFormation.time_created, originalOfFormation.time_modified,    " +
                "             1 as depth,       " +
                "             CAST(originalOfFormation.id_original_of_formation as NCHAR ) as path,  " +
                "             case when originalOfFormation.parent is not null then originalOfFormation.name end nameParent  " +
                "      from original_of_formation originalOfFormation    " +
                "      where originalOfFormation.parent is null    " +
                "      union all       " +
                "      select originalOfFormation.id_original_of_formation, originalOfFormation.name,    " +
                "             originalOfFormation.short_name, originalOfFormation.code_name,    " +
                "             originalOfFormation.description, originalOfFormation.parent,    " +
                "             originalOfFormation.sort_order, originalOfFormation.visible,    " +
                "             originalOfFormation.time_created, originalOfFormation.time_modified,    " +
                "             cte.depth + 1 as depth,       " +
                "             concat_ws('/',cte.path,CAST(originalOfFormation.id_original_of_formation as NCHAR)) as path,  " +
                "             cte.name nameParent  " +
                "      from original_of_formation originalOfFormation    " +
                "               INNER JOIN cte_projects cte ON originalOfFormation.parent = cte.id_original_of_formation    " +
                "      )       " +
                "select count(0) count  " +
                "from cte_projects cte  " +
                "where 1 = 1 ");
        setConditionFindAllOriginalOfFormation(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllOriginalOfFormation(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }


    @Override

    public Optional<OriginalOfFormation> findOriginalOfFormationByName(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select oof.id_original_of_formation, oof.name, " +
                "       oof.short_name, oof.code_name, oof.description, oof.parent, " +
                "oof.sort_order, oof.visible, " +
                "       oof.time_created, oof.time_modified  " +
                "from original_of_formation oof " +
                "where oof.name = :name ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                OriginalOfFormation originalOfFormation = new OriginalOfFormation();
                originalOfFormation.setIdOriginalOfFormation(ValueUtil.getIntegerByObject(obj[0]));
                originalOfFormation.setName(ValueUtil.getStringByObject(obj[1]));
                originalOfFormation.setShortName(ValueUtil.getStringByObject(obj[2]));
                originalOfFormation.setCodeName(ValueUtil.getStringByObject(obj[3]));
                originalOfFormation.setDescription(ValueUtil.getStringByObject(obj[4]));
                originalOfFormation.setParent(ValueUtil.getIntegerByObject(obj[5]));
                originalOfFormation.setSortOrder(ValueUtil.getStringByObject(obj[6]));
                originalOfFormation.setVisible(ValueUtil.getIntegerByObject(obj[7]));
                originalOfFormation.setTimeCreated(ValueUtil.getStringByObject(obj[8]));
                originalOfFormation.setTimeModified(ValueUtil.getStringByObject(obj[9]));

                return Optional.of(originalOfFormation);
            }
        }
        return Optional.empty();
    }

    @Override

    public Optional<OriginalOfFormation> findOriginalOfFormationByIdParent(Integer idParent) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select oof.id_original_of_formation, oof.name, " +
                "       oof.short_name, oof.code_name, oof.description, oof.parent, " +
                "oof.sort_order, oof.visible, " +
                "       oof.time_created, oof.time_modified  " +
                "from original_of_formation oof " +
                "where oof.id_original_of_formation = :idParent ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idParent", idParent);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                OriginalOfFormation originalOfFormation = new OriginalOfFormation();
                originalOfFormation.setIdOriginalOfFormation(ValueUtil.getIntegerByObject(obj[0]));
                originalOfFormation.setName(ValueUtil.getStringByObject(obj[1]));
                originalOfFormation.setShortName(ValueUtil.getStringByObject(obj[2]));
                originalOfFormation.setCodeName(ValueUtil.getStringByObject(obj[3]));
                originalOfFormation.setDescription(ValueUtil.getStringByObject(obj[4]));
                originalOfFormation.setParent(ValueUtil.getIntegerByObject(obj[5]));
                originalOfFormation.setSortOrder(ValueUtil.getStringByObject(obj[6]));
                originalOfFormation.setVisible(ValueUtil.getIntegerByObject(obj[7]));
                originalOfFormation.setTimeCreated(ValueUtil.getStringByObject(obj[8]));
                originalOfFormation.setTimeModified(ValueUtil.getStringByObject(obj[9]));

                return Optional.of(originalOfFormation);
            }
        }
        return Optional.empty();
    }

    @Override

    public Optional<OriginalOfFormation> findOriginalOfFormationById(Integer idOriginalOfFormation) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select oof.id_original_of_formation, oof.name, " +
                "       oof.short_name, oof.code_name, oof.description, oof.parent, " +
                "oof.sort_order, oof.visible, " +
                "       oof.time_created, oof.time_modified  " +
                "from original_of_formation oof " +
                "where oof.id_original_of_formation = :idOriginalOfFormation ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idOriginalOfFormation", idOriginalOfFormation);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                OriginalOfFormation originalOfFormation = new OriginalOfFormation();
                originalOfFormation.setIdOriginalOfFormation(ValueUtil.getIntegerByObject(obj[0]));
                originalOfFormation.setName(ValueUtil.getStringByObject(obj[1]));
                originalOfFormation.setShortName(ValueUtil.getStringByObject(obj[2]));
                originalOfFormation.setCodeName(ValueUtil.getStringByObject(obj[3]));
                originalOfFormation.setDescription(ValueUtil.getStringByObject(obj[4]));
                originalOfFormation.setParent(ValueUtil.getIntegerByObject(obj[5]));
                originalOfFormation.setSortOrder(ValueUtil.getStringByObject(obj[6]));
                originalOfFormation.setVisible(ValueUtil.getIntegerByObject(obj[7]));
                originalOfFormation.setTimeCreated(ValueUtil.getStringByObject(obj[8]));
                originalOfFormation.setTimeModified(ValueUtil.getStringByObject(obj[9]));

                return Optional.of(originalOfFormation);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean checkExitsOriginalOfFormationByNameOrShortNameOrCodeName(String name,String codeName,String shortName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * " +
                "from original_of_formation oof " +
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

    @Override
    public boolean isCheckAssetByIdOriginalOfFormation(Integer idOriginalOfFormation) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select asset.id_asset " +
                "from original_of_formation oof  " +
                "    inner join asset_original_of_formation aoof on oof.id_original_of_formation = aoof.id_original_of_formation " +
                "    inner join asset asset on aoof.id_asset = asset.id_asset " +
                "where oof.id_original_of_formation = :idOriginalOfFormation ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idOriginalOfFormation", idOriginalOfFormation);
        return !CollectionUtils.isEmpty(query.getResultList());
    }
}
