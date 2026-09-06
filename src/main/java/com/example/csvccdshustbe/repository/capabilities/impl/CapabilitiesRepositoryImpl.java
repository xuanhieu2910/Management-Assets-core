package com.example.csvccdshustbe.repository.capabilities.impl;

import com.example.csvccdshustbe.entity.Capabilities;
import com.example.csvccdshustbe.repository.capabilities.CapabilitiesRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CapabilitiesRepositoryImpl implements CapabilitiesRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Capabilities> findAllCapabilities() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ca.id_capability, ca.name, ca.cap_type, " +
                "       ca.status, ca.component, " +
                "       ca.time_created, ca.time_modified " +
                "from capabilities ca ");
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> result = query.getResultList();
        List<Capabilities> capabilities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                Capabilities capability = new Capabilities();
                capability.setIdCapability(ValueUtil.getIntegerByObject(obj[0]));
                capability.setName(ValueUtil.getStringByObject(obj[1]));
                capability.setCapType(ValueUtil.getStringByObject(obj[2]));
                capability.setStatus(ValueUtil.getIntegerByObject(obj[3]));
                capability.setComponent(ValueUtil.getStringByObject(obj[4]));
                capability.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                capability.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                capabilities.add(capability);
            }
        }
        return capabilities;
    }
}
