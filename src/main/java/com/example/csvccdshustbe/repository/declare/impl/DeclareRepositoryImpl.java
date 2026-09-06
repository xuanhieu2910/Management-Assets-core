package com.example.csvccdshustbe.repository.declare.impl;

import com.example.csvccdshustbe.entity.Declare;
import com.example.csvccdshustbe.repository.declare.DeclareRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeclareRepositoryImpl implements DeclareRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Declare> findAllDeclareByIdAssetCategoryAndVisible(Integer idAssetCategory, Integer visible) {
        StringBuilder sb = new StringBuilder();
        sb.append("select de.id_declare, de.name, de.code, " +
                "       de.visible, de.time_created, " +
                "       de.time_modified, de.id_category, " +
                "       de.hard_code " +
                "from `declare` de " +
                "    inner join asset_categories assetCategory on de.id_category = assetCategory.id_asset_category " +
                "where de.visible = :visible " +
                "and assetCategory.id_asset_category = :idAssetCategory ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("visible", visible);
        query.setParameter("idAssetCategory", idAssetCategory);
        List<Object[]> result = query.getResultList();
        List<Declare> declares = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                Declare declare = new Declare();
                declare.setIdDeclare(ValueUtil.getIntegerByObject(obj[0]));
                declare.setName(ValueUtil.getStringByObject(obj[1]));
                declare.setCode(ValueUtil.getStringByObject(obj[2]));
                declare.setVisible(ValueUtil.getIntegerByObject(obj[3]));
                declare.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                declare.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                declare.setIdCategory(ValueUtil.getIntegerByObject(obj[6]));
                declare.setHardCode(ValueUtil.getStringByObject(obj[7]));
                declares.add(declare);
            }
        }
        return declares;
    }

    @Override
    public Optional<Declare> findDeclareByHardCodeAndVisible(String hardCode, Integer visible) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select decl.id_declare, decl.name, decl.code, " +
                "       decl.visible, decl.time_created, decl.time_modified, " +
                "       decl.id_category, decl.hard_code " +
                "from `declare` decl  " +
                "where decl.hard_code = :hardCode " +
                "and decl.visible = :visible ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("hardCode", hardCode);
        query.setParameter("visible", visible);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                Declare declare = new Declare();
                declare.setIdDeclare(ValueUtil.getIntegerByObject(obj[0]));
                declare.setName(ValueUtil.getStringByObject(obj[1]));
                declare.setCode(ValueUtil.getStringByObject(obj[2]));
                declare.setVisible(ValueUtil.getIntegerByObject(obj[3]));
                declare.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                declare.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                declare.setIdCategory(ValueUtil.getIntegerByObject(obj[6]));
                declare.setHardCode(ValueUtil.getStringByObject(obj[7]));
                return Optional.of(declare);
            }
        }
        return Optional.empty();
    }
    @Override
    public Optional<Declare> findDeclareByIdAssetCategoryAndVisible(Integer idAssetCategory, Integer visible) {
        StringBuilder sb = new StringBuilder();
        sb.append("select de.id_declare, de.name, de.code, " +
                "       de.visible, de.time_created, " +
                "       de.time_modified, de.id_category, " +
                "       de.hard_code " +
                "from `declare` de " +
                "    inner join asset_categories assetCategory on de.id_category = assetCategory.id_asset_category " +
                "where de.visible = :visible " +
                "and assetCategory.id_asset_category = :idAssetCategory ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("visible", visible);
        query.setParameter("idAssetCategory", idAssetCategory);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                Declare declare = new Declare();
                declare.setIdDeclare(ValueUtil.getIntegerByObject(obj[0]));
                declare.setName(ValueUtil.getStringByObject(obj[1]));
                declare.setCode(ValueUtil.getStringByObject(obj[2]));
                declare.setVisible(ValueUtil.getIntegerByObject(obj[3]));
                declare.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                declare.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                declare.setIdCategory(ValueUtil.getIntegerByObject(obj[6]));
                declare.setHardCode(ValueUtil.getStringByObject(obj[7]));
                return Optional.of(declare);
            }
        }
        return Optional.empty();
    }
}
