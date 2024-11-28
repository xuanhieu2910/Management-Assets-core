package com.example.csvccdshustbe.repository.assetCurrentUsage.impl;

import com.example.csvccdshustbe.dto.assetCurrentUsage.AssetCurrentUsageDetailsDto;
import com.example.csvccdshustbe.dto.report.CurrentUsageReport08aDto;
import com.example.csvccdshustbe.entity.AssetCurrentUsage;
import com.example.csvccdshustbe.repository.assetCurrentUsage.AssetCurrentUsageRepositoryCustom;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class AssetCurrentUsageRepositoryImpl implements AssetCurrentUsageRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Modifying
    @Transactional
    @Override
    public void deleteAssetCurrentUsageByIdAsset(Integer idAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete " +
                "from asset_current_usage " +
                "where asset_current_usage.id_asset = :idAsset  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAsset", idAsset);
        query.executeUpdate();
    }

    @Override
    public List<AssetCurrentUsage> findByIdAsset(Integer idAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select acu.id_asset_current_usage, acu.id_asset, " +
                "       acu.id_current_usage, acu.time_created " +
                "from asset_current_usage acu  " +
                "where acu.id_asset = :idAsset ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAsset", idAsset);
        List<Object[]> result = query.getResultList();
        List<AssetCurrentUsage> usages = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                AssetCurrentUsage usage = new AssetCurrentUsage();
                usage.setIdAssetCurrentUsage(ValueUtil.getIntegerByObject(obj[0]));
                usage.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                usage.setIdCurrentUsage(ValueUtil.getIntegerByObject(obj[2]));
                usage.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                usages.add(usage);
            }
        }
        return usages;
    }

    @Override
    public List<AssetCurrentUsageDetailsDto> findAssetCurrentUsageDetailsDtoByIdAsset(Integer idAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select cu.id_current_usage, cu.code, cu.name " +
                "from asset_current_usage acu " +
                "    inner join asset ast on acu.id_asset = ast.id_asset " +
                "    inner join current_usage cu on acu.id_current_usage = cu.id_current_usage " +
                "where acu.id_asset = :idAsset ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAsset", idAsset);
        List<Object[]> result = query.getResultList();
        List<AssetCurrentUsageDetailsDto> usageDetailsDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object [] obj : result) {
                AssetCurrentUsageDetailsDto res = new AssetCurrentUsageDetailsDto();
                res.setIdCurrentUsage(ValueUtil.getIntegerByObject(obj[0]));
                res.setCode(ValueUtil.getStringByObject(obj[1]));
                res.setName(ValueUtil.getStringByObject(obj[2]));
                usageDetailsDtos.add(res);
            }
        }
        return usageDetailsDtos;
    }

    @Override
    public CurrentUsageReport08aDto findAllCurrentUsageAssetGroundInReport(List<Integer> idsDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT " +
                "    COUNT(gd.id_ground_declare) AS total_ground_declare, " +
                "    SUM(gd.acreage) as total_acreage, " +
                "    SUM(gd.hdsn_no_bussiness) AS total_no_business, " +
                "    SUM(gd.hdsn_bussiness) AS total_business, " +
                "    SUM(gd.hdsn_rent) AS total_rent, " +
                "    SUM(gd.hdsn_bonds) AS total_bonds, " +
                "    SUM(gd.synthetic_use) AS total_synthetic, " +
                "    SUM(gd.other_use) AS total_other " +
                "FROM " +
                "    ground_declare gd " +
                "        INNER JOIN " +
                "    asset ON gd.id_asset = asset.id_asset " +
                "where asset.quantity = 1 and asset.id_department_origin in (:idsDepartmentOriginal) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsDepartmentOriginal", idsDepartment);
        Object[] obj = (Object[]) query.getSingleResult();
        if (obj == null) {
            return null;
        }

        CurrentUsageReport08aDto res = new CurrentUsageReport08aDto();
        res.setCountAsset(ValueUtil.getIntegerByObject(obj[0]));
        res.setAcreage(ValueUtil.getStringByObject(obj[1]));
        res.setTotalNoBusiness(ValueUtil.getStringByObject(obj[2]));
        res.setTotalBusiness(ValueUtil.getStringByObject(obj[3]));
        res.setTotalRent(ValueUtil.getStringByObject(obj[4]));
        res.setTotalBonds(ValueUtil.getStringByObject(obj[5]));
        res.setTotalSynthetic(ValueUtil.getStringByObject(obj[6]));
        res.setTotalOther(ValueUtil.getStringByObject(obj[7]));
        return res;
    }

    @Override
    public CurrentUsageReport08aDto findAllCurrentUsageAssetHouseInReport(List<Integer> idsDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT  " +
                "    COUNT(hd.id_house_declare) AS total_ground_declare,  " +
                "    SUM(hd.acreage) as total_acreage,  " +
                "    SUM(hd.hdsn_no_bussiness) AS total_no_business,  " +
                "    SUM(hd.hdsn_bussiness) AS total_business,  " +
                "    SUM(hd.hdsn_rent) AS total_rent,  " +
                "    SUM(hd.hdsn_bonds) AS total_bond,  " +
                "    SUM(hd.synthetic_use) AS total_synthetic,  " +
                "    SUM(hd.other_use) AS total_other  " +
                "FROM  " +
                "    house_declare hd  " +
                "        INNER JOIN  " +
                "    asset ON hd.id_asset = asset.id_asset  " +
                "where asset.quantity = 1 and asset.id_department_origin in (:idsDepartmentOriginal) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsDepartmentOriginal", idsDepartment);
        Object[] obj = (Object[]) query.getSingleResult();
        if (obj == null) {
            return null;
        }

        CurrentUsageReport08aDto res = new CurrentUsageReport08aDto();
        res.setCountAsset(ValueUtil.getIntegerByObject(obj[0]));
        res.setAcreage(ValueUtil.getStringByObject(obj[1]));
        res.setTotalNoBusiness(ValueUtil.getStringByObject(obj[2]));
        res.setTotalBusiness(ValueUtil.getStringByObject(obj[3]));
        res.setTotalRent(ValueUtil.getStringByObject(obj[4]));
        res.setTotalBonds(ValueUtil.getStringByObject(obj[5]));
        res.setTotalSynthetic(ValueUtil.getStringByObject(obj[6]));
        res.setTotalOther(ValueUtil.getStringByObject(obj[7]));
        return res;

    }

    @Override
    public CurrentUsageReport08aDto findAllCurrentUsageAssetCarInReport(List<Integer> idsDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT  " +
                "    COUNT( distinct asset.id_asset) AS total_asset,  " +
                "    SUM(CASE WHEN code = 'QLNN' THEN 1 ELSE 0 END) AS QLNN,  " +
                "    SUM(CASE WHEN code = 'HĐSN-KHD' THEN 1 ELSE 0 END) AS HĐSNKHD,  " +
                "    SUM(CASE WHEN code = 'HĐSN-KD' THEN 1 ELSE 0 END) AS HĐSNKD,  " +
                "    SUM(CASE WHEN code = 'HĐSN-CT' THEN 1 ELSE 0 END) AS HĐSNCT,  " +
                "    SUM(CASE WHEN code = 'HĐSN-LDLK' THEN 1 ELSE 0 END) AS HĐSNLDLK,  " +
                "    SUM(CASE WHEN code = 'SDK' THEN 1 ELSE 0 END) AS SDK  " +
                "    from asset  left join asset_current_usage on asset.id_asset = asset_current_usage.id_asset  " +
                "                left join current_usage on asset_current_usage.id_current_usage = current_usage.id_current_usage  " +
                "  " +
                "where asset.quantity = 1 and asset.id_department_origin in (:idsDepartmentOriginal)  " +
                "  and asset.id_asset_category in (WITH RECURSIVE cte_asset_categories as (  " +
                "    select assetCategires.id_asset_category,assetCategires.name,  " +
                "           assetCategires.code_name, assetCategires.short_name,  " +
                "           assetCategires.description, assetCategires.parent,  " +
                "           assetCategires.sort_order, assetCategires.asset_count,  " +
                "           assetCategires.visible, assetCategires.time_created,  " +
                "           assetCategires.time_modified, assetCategires.is_pick,  " +
                "           1 as depth,  " +
                "           CAST(assetCategires.id_asset_category as NCHAR ) as path,  " +
                "           assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,  " +
                "           assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,  " +
                "           assetCategires.id_department_original  " +
                "    from asset_categories assetCategires  " +
                "    where assetCategires.code_name = :codeName  " +
                "      and assetCategires.visible = :visible  " +
                "    union all  " +
                "    select assetCategires.id_asset_category,assetCategires.name,  " +
                "           assetCategires.code_name, assetCategires.short_name,  " +
                "           assetCategires.description, assetCategires.parent,  " +
                "           assetCategires.sort_order, assetCategires.asset_count,  " +
                "           assetCategires.visible, assetCategires.time_created,  " +
                "           assetCategires.time_modified, assetCategires.is_pick,  " +
                "           cte.depth + 1 as depth,  " +
                "           concat_ws('/',cte.path,CAST(assetCategires.id_asset_category as NCHAR)) as path,  " +
                "           assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,  " +
                "           assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,  " +
                "           assetCategires.id_department_original  " +
                "    from asset_categories assetCategires  " +
                "             INNER JOIN cte_asset_categories cte ON assetCategires.parent = cte.id_asset_category  " +
                ")  " +
                "                                                         select cte_asset_categories.id_asset_category  " +
                "                                                         from cte_asset_categories )  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsDepartmentOriginal", idsDepartment);
        query.setParameter("codeName", Constants.CODE_NAME_CAR);
        query.setParameter("visible", Constants.IS_VISIBLE);
        Object[] obj = (Object[]) query.getSingleResult();
        if (obj == null) {
            return null;
        }

        CurrentUsageReport08aDto res = new CurrentUsageReport08aDto();
        res.setCountAsset(ValueUtil.getIntegerByObject(obj[0]));
        res.setTotalStateManagement(ValueUtil.getStringByObject(obj[1]));
        res.setTotalNoBusiness(ValueUtil.getStringByObject(obj[2]));
        res.setTotalBusiness(ValueUtil.getStringByObject(obj[3]));
        res.setTotalRent(ValueUtil.getStringByObject(obj[4]));
        res.setTotalBonds(ValueUtil.getStringByObject(obj[5]));
        res.setTotalOther(ValueUtil.getStringByObject(obj[6]));
        return res;

    }

    @Override
    public CurrentUsageReport08aDto findAllCurrentUsageAssetOtherInReport(List<Integer> idsDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT  " +
                "    COUNT( distinct asset.id_asset) AS total_asset,  " +
                "    SUM(CASE WHEN code = 'QLNN' THEN 1 ELSE 0 END) AS QLNN,  " +
                "    SUM(CASE WHEN code = 'HĐSN-KHD' THEN 1 ELSE 0 END) AS HĐSNKHD,  " +
                "    SUM(CASE WHEN code = 'HĐSN-KD' THEN 1 ELSE 0 END) AS HĐSNKD,  " +
                "    SUM(CASE WHEN code = 'HĐSN-CT' THEN 1 ELSE 0 END) AS HĐSNCT,  " +
                "    SUM(CASE WHEN code = 'HĐSN-LDLK' THEN 1 ELSE 0 END) AS HĐSNLDLK,  " +
                "    SUM(CASE WHEN code = 'SDK' THEN 1 ELSE 0 END) AS SDK  " +
                "    from asset  left join asset_current_usage on asset.id_asset = asset_current_usage.id_asset  " +
                "                left join current_usage on asset_current_usage.id_current_usage = current_usage.id_current_usage  " +
                "  " +
                "where asset.quantity = 1 and asset.id_department_origin in (:idsDepartmentOriginal)  " +
                "  and asset.id_asset_category in (WITH RECURSIVE cte_asset_categories as (  " +
                "    select assetCategires.id_asset_category,assetCategires.name,  " +
                "           assetCategires.code_name, assetCategires.short_name,  " +
                "           assetCategires.description, assetCategires.parent,  " +
                "           assetCategires.sort_order, assetCategires.asset_count,  " +
                "           assetCategires.visible, assetCategires.time_created,  " +
                "           assetCategires.time_modified, assetCategires.is_pick,  " +
                "           1 as depth,  " +
                "           CAST(assetCategires.id_asset_category as NCHAR ) as path,  " +
                "           assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,  " +
                "           assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,  " +
                "           assetCategires.id_department_original  " +
                "    from asset_categories assetCategires  " +
                "    where (assetCategires.code_name != :codeNameCar  or assetCategires.code_name != :codeNameNoShape ) " +
                "      and assetCategires.visible = :visible  " +
                "    union all  " +
                "    select assetCategires.id_asset_category,assetCategires.name,  " +
                "           assetCategires.code_name, assetCategires.short_name,  " +
                "           assetCategires.description, assetCategires.parent,  " +
                "           assetCategires.sort_order, assetCategires.asset_count,  " +
                "           assetCategires.visible, assetCategires.time_created,  " +
                "           assetCategires.time_modified, assetCategires.is_pick,  " +
                "           cte.depth + 1 as depth,  " +
                "           concat_ws('/',cte.path,CAST(assetCategires.id_asset_category as NCHAR)) as path,  " +
                "           assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,  " +
                "           assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,  " +
                "           assetCategires.id_department_original  " +
                "    from asset_categories assetCategires  " +
                "             INNER JOIN cte_asset_categories cte ON assetCategires.parent = cte.id_asset_category  " +
                ")  " +
                "                                                         select cte_asset_categories.id_asset_category  " +
                "                                                         from cte_asset_categories )  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsDepartmentOriginal", idsDepartment);
        query.setParameter("codeNameCar", Constants.CODE_NAME_CAR);
        query.setParameter("codeNameNoShape", Constants.CODE_NAME_NO_SHAPE);
        query.setParameter("visible", Constants.IS_VISIBLE);
        Object[] obj = (Object[]) query.getSingleResult();
        if (obj == null) {
            return null;
        }

        CurrentUsageReport08aDto res = new CurrentUsageReport08aDto();
        res.setCountAsset(ValueUtil.getIntegerByObject(obj[0]));
        res.setTotalStateManagement(ValueUtil.getStringByObject(obj[1]));
        res.setTotalNoBusiness(ValueUtil.getStringByObject(obj[2]));
        res.setTotalBusiness(ValueUtil.getStringByObject(obj[3]));
        res.setTotalRent(ValueUtil.getStringByObject(obj[4]));
        res.setTotalBonds(ValueUtil.getStringByObject(obj[5]));
        res.setTotalOther(ValueUtil.getStringByObject(obj[6]));
        return res;

    }
}
