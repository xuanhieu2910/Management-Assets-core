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
}
