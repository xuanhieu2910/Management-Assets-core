package com.example.csvccdshustbe.repository.assetOriginal.impl;

import com.example.csvccdshustbe.dto.original.BluePrintOriginalDto;
import com.example.csvccdshustbe.repository.assetOriginal.AssetOriginalRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class AssetOriginalRepositoryImpl implements AssetOriginalRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<BluePrintOriginalDto> findBluePrintAssetOriginalByIdAsset(Integer idAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ori.hard_code_dev typeOriginal, ori.id_original, " +
                "       ori.name nameOriginal, am.id_instance " +
                "from asset st " +
                "    inner join asset_original am on st.id_asset = am.id_asset " +
                "    inner join original ori on am.id_original = ori.id_original " +
                "where st.id_asset = :idAsset ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAsset", idAsset);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                BluePrintOriginalDto bluePrintOriginalDto = new BluePrintOriginalDto();
                bluePrintOriginalDto.setTypeOriginal(ValueUtil.getStringByObject(obj[0]));
                bluePrintOriginalDto.setIdOriginal(ValueUtil.getIntegerByObject(obj[1]));
                bluePrintOriginalDto.setNameOriginal(ValueUtil.getStringByObject(obj[2]));
                bluePrintOriginalDto.setIdInstance(ValueUtil.getIntegerByObject(obj[3]));
                return Optional.of(bluePrintOriginalDto);
            }
        }
        return Optional.empty();
    }



    @Modifying
    @Transactional
    @Override
    public void deleteAssetOriginalByIdOriginalAndIdInstance(Integer idOriginal, Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete " +
                "from asset_original  " +
                "where asset_original.id_original = :idOriginal " +
                "  and asset_original.id_instance = :idInstance  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idOriginal", idOriginal);
        query.setParameter("idInstance", idInstance);
        query.executeUpdate();
    }
}
