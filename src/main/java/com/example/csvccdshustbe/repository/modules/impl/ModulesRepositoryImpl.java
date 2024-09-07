package com.example.csvccdshustbe.repository.modules.impl;

import com.example.csvccdshustbe.entity.Modules;
import com.example.csvccdshustbe.repository.modules.ModulesRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ModulesRepositoryImpl implements ModulesRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Modules> findAllModulesByIdAssetCategoryAndStatus(Integer idAssetCategory, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select mo.id_module, mo.name, mo.code,  " +
                "       mo.time_created, mo.time_modified,  " +
                "       mo.status, mo.id_user_created,  " +
                "       mo.id_user_modified, mo.id_asset_category,  " +
                "       mo.hard_code  " +
                "from modules mo  " +
                "    inner join asset_categories assetCategory on mo.id_asset_category = assetCategory.id_asset_category  " +
                "where assetCategory.id_asset_category = :idAssetCategory  " +
                "and mo.status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAssetCategory", idAssetCategory);
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();
        List<Modules> modules = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                Modules module = new Modules();
                module.setIdModule(ValueUtil.getIntegerByObject(obj[0]));
                module.setName(ValueUtil.getStringByObject(obj[1]));
                module.setCode(ValueUtil.getStringByObject(obj[2]));
                module.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                module.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                module.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                module.setIdUserCreated(ValueUtil.getIntegerByObject(obj[6]));
                module.setIdUserModified(ValueUtil.getIntegerByObject(obj[7]));
                module.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[8]));
                module.setHardCode(ValueUtil.getStringByObject(obj[9]));
                modules.add(module);
            }
        }
        return modules;
    }
}
