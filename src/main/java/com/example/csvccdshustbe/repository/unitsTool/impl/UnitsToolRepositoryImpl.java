package com.example.csvccdshustbe.repository.unitsTool.impl;

import com.example.csvccdshustbe.entity.UnitsTool;
import com.example.csvccdshustbe.repository.unitsTool.UnitsToolRepositoryCustom;
import com.example.csvccdshustbe.request.unitsTool.FindAllUnitsToolRequest;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UnitsToolRepositoryImpl implements UnitsToolRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public Page<UnitsTool> findAllUnitsTool(FindAllUnitsToolRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ut.id_unit_tool, ut.name, ut.time_created,  " +
                " ut.time_modified, ut.status " +
                " from units_tool ut where 1=1 ");
        setConditionFindAllUnitsTool(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllUnitsTool(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<UnitsTool> unitsTools = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                UnitsTool unitsTool = new UnitsTool();
                unitsTool.setIdUnitTool(ValueUtil.getIntegerByObject(obj[0]));
                unitsTool.setName(ValueUtil.getStringByObject(obj[1]));
                unitsTool.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                unitsTool.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                unitsTool.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                unitsTools.add(unitsTool);
            }
        }
        return new PageImpl<>(unitsTools, pageable, countFindAllUnitsTool(request));
    }
    private long countFindAllUnitsTool(FindAllUnitsToolRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "  from units_tool ut where 1=1 ");
        setConditionFindAllUnitsTool(request,sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllUnitsTool(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }
    private void setParameterFindAllUnitsTool(FindAllUnitsToolRequest request, Query query) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())) {
            query.setParameter("status", request.getStatus());
        }
    }

    private void setConditionFindAllUnitsTool(FindAllUnitsToolRequest request, StringBuilder sb) {
        if (ObjectUtils.isNotEmpty(request.getStatus())) {
            sb.append(" and (ut.status = :status ) ");
        }
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (ut.name REGEXP :keyword ) ");
        }
    }

    @Override
    public Optional<UnitsTool> findUnitToolByName(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ut.id_unit_tool, ut.name, " +
                "ut.time_created, ut.time_modified ,ut.status " +
                "from units_tool ut " +
                "where ut.name = :name ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                UnitsTool unitsTool = new UnitsTool();
                unitsTool.setIdUnitTool(ValueUtil.getIntegerByObject(obj[0]));
                unitsTool.setName(ValueUtil.getStringByObject(obj[1]));
                unitsTool.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                unitsTool.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                unitsTool.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                return Optional.of(unitsTool);
            }
        }
        return Optional.empty();
    }
    @Override
    public Optional<UnitsTool> findUnitToolByIdUnitTool(Integer idUnitTool) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ut.id_unit_tool, ut.name, " +
                "ut.time_created, ut.time_modified ,ut.status " +
                "from units_tool ut " +
                "where ut.id_unit_tool = :idUnitTool ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idUnitTool", idUnitTool);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                UnitsTool unitsTool = new UnitsTool();
                unitsTool.setIdUnitTool(ValueUtil.getIntegerByObject(obj[0]));
                unitsTool.setName(ValueUtil.getStringByObject(obj[1]));
                unitsTool.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                unitsTool.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                unitsTool.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                return Optional.of(unitsTool);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<UnitsTool> findAllUnitsToolByStatus(Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_unit_tool, name,   " +
                "       time_created, time_modified,  " +
                "       status  " +
                "from units_tool ut   " +
                "where ut.status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();
        List<UnitsTool>  unitsTools = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                UnitsTool unitsTool = new UnitsTool();
                unitsTool.setIdUnitTool(ValueUtil.getIntegerByObject(obj[0]));
                unitsTool.setName(ValueUtil.getStringByObject(obj[1]));
                unitsTool.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                unitsTool.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                unitsTool.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                unitsTools.add(unitsTool);
            }
        }
        return unitsTools;
    }

    @Override
    public Optional<UnitsTool> findUnitByIdUnitToolAndStatus(Integer idUnitTool, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ut.id_unit_tool, ut.name, " +
                "ut.time_created, ut.time_modified ,ut.status " +
                "from units_tool ut " +
                "where ut.id_unit_tool = :idUnitTool and ut.status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idUnitTool", idUnitTool);
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                UnitsTool unitsTool = new UnitsTool();
                unitsTool.setIdUnitTool(ValueUtil.getIntegerByObject(obj[0]));
                unitsTool.setName(ValueUtil.getStringByObject(obj[1]));
                unitsTool.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                unitsTool.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                unitsTool.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                return Optional.of(unitsTool);
            }
        }
        return Optional.empty();
    }
}
