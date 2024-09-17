package com.example.csvccdshustbe.repository.assetDepreciation.impl;

import com.example.csvccdshustbe.entity.AssetDepreciation;
import com.example.csvccdshustbe.repository.assetDepreciation.AssetDepreciationRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class AssetDepreciationRepositoryImpl implements AssetDepreciationRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Optional<AssetDepreciation> findAssetDepreciationById(Integer idAssetDepreciation) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ad.id_asset_depreciation, ad.id_asset, ad.time_started_depreciation, " +
                "       ad.amount_months_depreciation, ad.value_depreciation, ad.type_depreciation, " +
                "       ad.value_type_depreciation, ad.amount_rest_months_depreciation, ad.cumulative, " +
                "       ad.rest_value, ad.time_started_wear_tear, ad.time_end_wear_tear, " +
                "       ad.type_calculate, ad.time_buy, ad.time_started_used, ad.time_started_increase, " +
                "       ad.time_year_tracking, ad.time_created, ad.time_modified " +
                "from asset_depreciation ad " +
                "where ad.id_asset_depreciation = :idAssetDepreciation ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAssetDepreciation", idAssetDepreciation);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                AssetDepreciation depreciation = new AssetDepreciation();
                depreciation.setIdAssetDepreciation(ValueUtil.getIntegerByObject(obj[0]));
                depreciation.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                depreciation.setTimeStartedDepreciation(ValueUtil.getStringByObject(obj[2]));
                depreciation.setAmountMonthsDepreciation(ValueUtil.getIntegerByObject(obj[3]));
                depreciation.setValueDepreciation(ValueUtil.getStringByObject(obj[4]));
                depreciation.setTypeDepreciation(ValueUtil.getIntegerByObject(obj[5]));
                depreciation.setValueTypeDepreciation(ValueUtil.getStringByObject(obj[6]));
                depreciation.setAmountRestMonthsDepreciation(ValueUtil.getIntegerByObject(obj[7]));
                depreciation.setCumulative(ValueUtil.getStringByObject(obj[8]));
                depreciation.setRestValue(ValueUtil.getStringByObject(obj[9]));
                depreciation.setTimeStartedWearTear(ValueUtil.getStringByObject(obj[10]));
                depreciation.setTimeEndWearTear(ValueUtil.getStringByObject(obj[11]));
                depreciation.setTypeCalculate(ValueUtil.getIntegerByObject(obj[12]));
                depreciation.setTimeBuy(ValueUtil.getStringByObject(obj[13]));
                depreciation.setTimeStartedUsed(ValueUtil.getStringByObject(obj[14]));
                depreciation.setTimeStartedIncrease(ValueUtil.getStringByObject(obj[15]));
                depreciation.setTimeYearTracking(ValueUtil.getStringByObject(obj[16]));
                depreciation.setTimeCreated(ValueUtil.getStringByObject(obj[17]));
                depreciation.setTimeModified(ValueUtil.getStringByObject(obj[18]));
                return Optional.of(depreciation);
            }
        }
        return Optional.empty();
    }

    @Modifying
    @Transactional
    @Override
    public void deleteAssetDepreciationByIdAsset(Integer idAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from asset_depreciation  " +
                "where asset_depreciation.id_asset = :idAsset ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAsset", idAsset);
        query.executeUpdate();
    }
}
