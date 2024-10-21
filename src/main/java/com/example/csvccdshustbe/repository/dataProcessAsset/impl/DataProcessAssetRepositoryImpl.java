package com.example.csvccdshustbe.repository.dataProcessAsset.impl;

import com.example.csvccdshustbe.entity.DataProcessAsset;
import com.example.csvccdshustbe.repository.dataProcessAsset.DataProcessAssetRepositoryCustom;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class DataProcessAssetRepositoryImpl implements DataProcessAssetRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<DataProcessAsset> findDataProcessAssetByIdsAsset(List<Integer> idsAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select dp.id_data_process_asset, dp.id_document, dp.id_asset, " +
                "       dp.id_process, dp.time_created, dp.time_modified, dp.status " +
                "from data_process_asset dp " +
                "inner join asset asset on dp.id_asset = asset.id_asset " +
                "inner join process pro on dp.id_process = pro.id_process " +
                "inner join type_process tp on pro.id_type_process = tp.code " +
                "where asset.id_asset in (:idAsset) " +
                "and tp.code = :codeTypeProcess ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAsset", idsAsset);
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_INCREASE);
        List<Object[]> result = query.getResultList();
        List<DataProcessAsset> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                DataProcessAsset processAsset = new DataProcessAsset();
                processAsset.setIdDataProcessAsset(ValueUtil.getIntegerByObject(obj[0]));
                processAsset.setIdDocument(ValueUtil.getIntegerByObject(obj[1]));
                processAsset.setIdAsset(ValueUtil.getIntegerByObject(obj[2]));
                processAsset.setIdProcess(ValueUtil.getIntegerByObject(obj[3]));
                processAsset.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                processAsset.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                processAsset.setStatus(ValueUtil.getIntegerByObject(obj[6]));
                responses.add(processAsset);
            }
        }
        return responses;
    }
}
