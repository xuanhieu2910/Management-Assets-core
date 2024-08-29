package com.example.csvccdshustbe.repository.currentUsage.Impl;

import com.example.csvccdshustbe.entity.CurrentUsage;

import com.example.csvccdshustbe.repository.currentUsage.CurrentUsageRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CurrentUsageRepositoryImpl implements CurrentUsageRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<CurrentUsage>findAllCurrentUsage(){
        StringBuilder sb = new StringBuilder();
        sb.append("select current_usage.id_current_usage, " +
                "current_usage.name, current_usage.code, " +
                " current_usage.time_created, current_usage.time_modified " +
                "from current_usage ");
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> result = query.getResultList();
        List<CurrentUsage> currentUsages = new ArrayList<>();
        if(!CollectionUtils.isEmpty(result)) {
            for(Object[] obj :result) {
                CurrentUsage currentUsage=new CurrentUsage();
                currentUsage.setIdCurrentUsage(ValueUtil.getIntegerByObject(obj[0]));
                currentUsage.setName(ValueUtil.getStringByObject(obj[1]));
                currentUsage.setCode(ValueUtil.getStringByObject(obj[2]));
                currentUsage.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                currentUsage.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                currentUsages.add(currentUsage);
            }
        }
        return currentUsages;
    }
}
