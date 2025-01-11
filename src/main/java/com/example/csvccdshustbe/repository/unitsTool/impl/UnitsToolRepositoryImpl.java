package com.example.csvccdshustbe.repository.unitsTool.impl;

import com.example.csvccdshustbe.entity.Units;
import com.example.csvccdshustbe.entity.UnitsTool;
import com.example.csvccdshustbe.repository.unitsTool.UnitsToolRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UnitsToolRepositoryImpl implements UnitsToolRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public  List<UnitsTool> findAllUnitsTool() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ut.id_unit_tool, ut.name, ut.time_created,  " +
                " ut.time_modified, ut.status " +
                " from units_tool ut ");
        Query query = entityManager.createNativeQuery(sb.toString());
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
        return unitsTools;
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
