package com.example.csvccdshustbe.repository.noShapeOriginalAssetTransferLand.impl;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetTransferLandDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetTransferLand;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetTransferLand.NoShapeOriginalAssetTransferLandRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class NoShapeOriginalAssetTransferLandRepositoryImpl implements NoShapeOriginalAssetTransferLandRepositoryCustom {
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Override
    public Optional<NoShapeOriginalAssetTransferLandDetailsDto> findNoShapeOriginalAssetTransferLandDetailsDtoById(Integer id) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select shape.id_ns_original_asset_transfer_land, shape.id_asset,   " +
                "       shape.value_use, shape.value_tax, shape.value_other,   " +
                "       shape.time_created, shape.time_modified   " +
                "from ns_original_asset_transfer_land shape   " +
                "where shape.id_ns_original_asset_transfer_land = :idShape ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idShape", id);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                NoShapeOriginalAssetTransferLandDetailsDto detailsDto = new NoShapeOriginalAssetTransferLandDetailsDto();
                detailsDto.setIdNoShapeOriginalAssetTransferLand(ValueUtil.getIntegerByObject(obj[0]));
                detailsDto.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                detailsDto.setValueUse(ValueUtil.getDoubleByObject(obj[2]));
                detailsDto.setValueTax(ValueUtil.getDoubleByObject(obj[3]));
                detailsDto.setValueOther(ValueUtil.getDoubleByObject(obj[4]));
                detailsDto.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                detailsDto.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                return Optional.of(detailsDto);
            }
        }

        return Optional.empty();
    }

    @Override
    public void deleteNoShapeOriginalAssetTransferLandById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from ns_original_asset_transfer_land nsTransferLand " +
                "where nsTransferLand.id_ns_original_asset_transfer_land = :idNsTransferLand ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idNsTransferLand", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<NoShapeOriginalAssetTransferLand> findNoShapeOriginalAssetTransferLandById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select transferLand.id_ns_original_asset_transfer_land, transferLand.id_asset, " +
                "       transferLand.value_use, transferLand.value_tax, " +
                "       transferLand.value_other, transferLand.time_created, " +
                "       transferLand.time_modified " +
                "from ns_original_asset_transfer_land transferLand " +
                "where transferLand.id_ns_original_asset_transfer_land = :idTransferLand ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idTransferLand", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                NoShapeOriginalAssetTransferLand transferLand = new NoShapeOriginalAssetTransferLand();
                transferLand.setIdNoShapeOriginalAssetTransferLand(ValueUtil.getIntegerByObject(obj[0]));
                transferLand.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                transferLand.setValueUse(ValueUtil.getDoubleByObject(obj[2]));
                transferLand.setValueTax(ValueUtil.getDoubleByObject(obj[3]));
                transferLand.setValueOther(ValueUtil.getDoubleByObject(obj[4]));
                transferLand.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                transferLand.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                return Optional.of(transferLand);
            }
        }
        return Optional.empty();
    }
}
