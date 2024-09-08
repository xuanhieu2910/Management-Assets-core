package com.example.csvccdshustbe.repository.units.impl;

import com.example.csvccdshustbe.entity.Units;
import com.example.csvccdshustbe.repository.units.UnitsRepositoryCustom;
import com.example.csvccdshustbe.request.units.FindAllUnitsByAssetCategoryRequest;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UnitsRepositoryImpl implements UnitsRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Units> findAllUnits() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select units.id_unit, units.name, units.time_created, " +
                "       units.time_modified, units.id_asset_category,  " +
                " units.status " +
                "from units ");
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> result = query.getResultList();
        List<Units> units = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
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

    @Override
    public Page<Units> findAllUnitsActiveByCodeAssetCategory(FindAllUnitsByAssetCategoryRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select units.id_unit, units.name, units.time_created, " +
                "       units.time_modified, units.id_asset_category, " +
                "       units.status " +
                "from units units " +
                "    inner join asset_categories assetCategory on units.id_asset_category = assetCategory.id_asset_category " +
                "where assetCategory.code_name = :codeName " +
                "and units.status = :status ");
        setConditionFindAllUnitsActiveByCodeAssetCategory(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllUnitsActiveByCodeAssetCategory(request, query);
        PageUtils.buildQuery(pageable, query);
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
        return new PageImpl<>(units, pageable, countFindAllUnitsActiveByCodeAssetCategory(request));
    }


    private long countFindAllUnitsActiveByCodeAssetCategory(FindAllUnitsByAssetCategoryRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "  from units units  " +
                "      inner join asset_categories assetCategory " +
                "          on units.id_asset_category = assetCategory.id_asset_category  " +
                "  where assetCategory.code_name = :codeName  " +
                "  and units.status = :status ");
        setConditionFindAllUnitsActiveByCodeAssetCategory(request,sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllUnitsActiveByCodeAssetCategory(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllUnitsActiveByCodeAssetCategory(FindAllUnitsByAssetCategoryRequest request, Query query) {
        query.setParameter("codeName", request.getCodeName());
        query.setParameter("status", Constants.UNITS_IS_ACTIVE);
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllUnitsActiveByCodeAssetCategory(FindAllUnitsByAssetCategoryRequest request,
                                                                   StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (units.name REGEXP :keyword ) ");
        }
    }

    @Override
    public Optional<Units> findUnitByName(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ut.id_unit, ut.name, " +
                "ut.time_created, ut.time_modified, ut.id_asset_category ,ut.status " +
                "from units ut " +
                "where ut.name = :name ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                Units units = new Units();
                units.setIdUnit(ValueUtil.getIntegerByObject(obj[0]));
                units.setName(ValueUtil.getStringByObject(obj[1]));
                units.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                units.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                units.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                units.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                return Optional.of(units);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Units> findUnitById(Integer idUnit) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ut.id_unit, ut.name, " +
                "ut.time_created, ut.time_modified, ut.id_asset_category ,ut.status " +
                "from units ut " +
                "where ut.id_unit = :idUnit ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idUnit", idUnit);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                Units units = new Units();
                units.setIdUnit(ValueUtil.getIntegerByObject(obj[0]));
                units.setName(ValueUtil.getStringByObject(obj[1]));
                units.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                units.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                units.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                units.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                return Optional.of(units);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Units> findUnitByIdUnitAndIdAssetCategoryAndStatus(Integer idUnit, Integer idAssetCategory, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select un.id_unit, un.name, un.time_created,  " +
                "       un.time_modified, un.id_asset_category,  " +
                "       un.status  " +
                "from units un  " +
                "    inner join asset_categories assetCategory   " +
                "        on un.id_asset_category = assetCategory.id_asset_category  " +
                "where un.id_unit = :idUnit  " +
                "and assetCategory.id_asset_category = :idAssetCategory  " +
                "and un.status = :status   ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idUnit", idUnit);
        query.setParameter("idAssetCategory", idAssetCategory);
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Units units = new Units();
                units.setIdUnit(ValueUtil.getIntegerByObject(obj[0]));
                units.setName(ValueUtil.getStringByObject(obj[1]));
                units.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                units.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                units.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[4]));
                units.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                return Optional.of(units);
            }
        }
        return Optional.empty();
    }

}
