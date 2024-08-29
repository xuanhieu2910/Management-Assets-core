package com.example.csvccdshustbe.repository.typeUse.impl;


import com.example.csvccdshustbe.entity.TypeUse;
import com.example.csvccdshustbe.repository.typeUse.TypeUseRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class TypeUseRepositoryImpl implements TypeUseRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<TypeUse> findAllTypeUse(){
        StringBuilder sb = new StringBuilder();
        sb.append(" Select type_use.id_type_use, type_use.name, type_use.status, " +
                "type_use.time_created, type_use.time_modified from type_use");
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> result = query.getResultList();
        List<TypeUse> typeUses=new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                TypeUse typeUse= new TypeUse();
                typeUse.setIdTypeUse(ValueUtil.getIntegerByObject(obj[0]));
                typeUse.setName(ValueUtil.getStringByObject(obj[1]));
                typeUse.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                typeUse.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                typeUse.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                typeUses.add(typeUse);
            }
        }
        return typeUses;
    }
}
