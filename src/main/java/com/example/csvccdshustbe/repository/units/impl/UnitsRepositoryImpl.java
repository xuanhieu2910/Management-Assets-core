package com.example.csvccdshustbe.repository.units.impl;

import com.example.csvccdshustbe.entity.Units;
import com.example.csvccdshustbe.repository.units.UnitsRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class UnitsRepositoryImpl implements UnitsRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Units> findAllUnits() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select units.id_unit, units.name, units.time_created, " +
                "units.time_modified, units.id_asset_category, units.status " +
                "from units ");
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> result = query.getResultList();
        List<Units> units = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Units unit = new Units();
                unit.setIdUnit(ValueUtil.getIntegerByObject(obj[0]));
                unit.setName(ValueUtil.getStringByObject(obj[1]));
                unit.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                unit.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                unit.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[4]));
                unit.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                units.add(unit);
            }
        }
        return units;
    }
}
