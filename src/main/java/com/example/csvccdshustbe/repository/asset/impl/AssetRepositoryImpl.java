package com.example.csvccdshustbe.repository.asset.impl;

import com.example.csvccdshustbe.dto.asset.AssetBluePrintDto;
import com.example.csvccdshustbe.dto.asset.FindAllAssetDto;
import com.example.csvccdshustbe.dto.asset.FindAllGroundAssetDto;
import com.example.csvccdshustbe.dto.asset.GroundAssetDto;
import com.example.csvccdshustbe.dto.assetCategories.BluePrintAssetCategoryDto;
import com.example.csvccdshustbe.dto.assetDepreciation.AssetDepreciationDto;
import com.example.csvccdshustbe.dto.declare.AssetDeclareDto;
import com.example.csvccdshustbe.dto.declare.BluePrintDeclareDto;
import com.example.csvccdshustbe.dto.department.BluePrintDepartmentDefaultDto;
import com.example.csvccdshustbe.dto.department.BluePrintDepartmentDto;
import com.example.csvccdshustbe.dto.documentAttack.BluePrintDocumentAttackDto;
import com.example.csvccdshustbe.dto.levelTypeAsset.BluePrintLevelTypeAssetDto;
import com.example.csvccdshustbe.dto.location.BluePrintLocationDto;
import com.example.csvccdshustbe.dto.modules.AssetModulesDto;
import com.example.csvccdshustbe.dto.modules.BluePrintAssetModulesDto;
import com.example.csvccdshustbe.dto.original.AssetOriginalDto;
import com.example.csvccdshustbe.dto.original.BluePrintOriginalDto;
import com.example.csvccdshustbe.dto.process.FindAllAssetChildrenToInventoryDto;
import com.example.csvccdshustbe.dto.process.FindAllAssetParentToInventoryDto;
import com.example.csvccdshustbe.dto.projects.BluePrintProjectsDto;
import com.example.csvccdshustbe.dto.unit.BluePrintUnitDto;
import com.example.csvccdshustbe.entity.Asset;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.repository.asset.AssetRepositoryCustom;
import com.example.csvccdshustbe.request.asset.*;
import com.example.csvccdshustbe.response.asset.FindAllGroundAssetResponse;
import com.example.csvccdshustbe.response.asset.StatisticsAssetFindAllResponse;
import com.example.csvccdshustbe.response.dashboard.StatisticsAssetAndUserFindAllResponse;
import com.example.csvccdshustbe.response.dashboard.StatisticsAssetCategoryStatusUse;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class AssetRepositoryImpl implements AssetRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Page<FindAllAssetDto> findAllAssetDtoByIdsDepartment(FindAllAssetRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select asset.id_asset idAsset, asset.code_asset codeAsset,     " +
                "           asset.name nameAsset, assetCategories.id_asset_category idAssetCategory,     " +
                "              assetCategories.name nameAssetCategory, assetCategories.code_name codeAssetCategory,     " +
                "              de.id_department idDepartment, de.code codeDepartment, de.name nameDepartment,     " +
                "              lo.id_location idLocation, lo.name nameLocation,     " +
                "              asset.time_created, asset.time_modified,     " +
                "              asset.parent, asset.salt, asset.quantity,     " +
                "              asset.is_increase,asset.is_decrease,     " +
                "              asset.status_process_current, asset.status_use,     " +
                "              asset.year_use,asset.acreage, asset.sum_original_of_formation,     " +
                "              ad.cumulative, ad.rest_value     " +
                "    from asset asset     " +
                "               left join asset_categories assetCategories          " +
                "                    on asset.id_asset_category = assetCategories.id_asset_category     " +
                "               left join department de on asset.id_department = de.id_department     " +
                "               left join location lo on asset.id_location = lo.id_location     " +
                "               left join asset_depreciation ad on asset.id_asset = ad.id_asset     " +
                "where 1 = 1     " +
                "and asset.id_department_origin in (:idsDepartmentOriginal) ");
        setConditionFindAllAsset(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAsset(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllAssetDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllAssetDto findAllAssetDto = new FindAllAssetDto();
                findAllAssetDto.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
                findAllAssetDto.setCodeAsset(ValueUtil.getStringByObject(obj[1]));
                findAllAssetDto.setNameAsset(ValueUtil.getStringByObject(obj[2]));
                findAllAssetDto.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[3]));
                findAllAssetDto.setNameAssetCategory(ValueUtil.getStringByObject(obj[4]));
                findAllAssetDto.setCodeAssetCategory(ValueUtil.getStringByObject(obj[5]));
                findAllAssetDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[6]));
                findAllAssetDto.setCodeDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllAssetDto.setNameDepartment(ValueUtil.getStringByObject(obj[8]));
                findAllAssetDto.setIdLocation(ValueUtil.getIntegerByObject(obj[9]));
                findAllAssetDto.setNameLocation(ValueUtil.getStringByObject(obj[10]));
                findAllAssetDto.setTimeCreated(ValueUtil.getLongByObject(obj[11]));
                findAllAssetDto.setTimeModified(ValueUtil.getLongByObject(obj[12]));
                findAllAssetDto.setParent(ValueUtil.getIntegerByObject(obj[13]));
                findAllAssetDto.setSalt(ValueUtil.getStringByObject(obj[14]));
                findAllAssetDto.setQuantity(ValueUtil.getIntegerByObject(obj[15]));
                findAllAssetDto.setIsIncrease(ValueUtil.getIntegerByObject(obj[16]));
                findAllAssetDto.setIsDecrease(ValueUtil.getIntegerByObject(obj[17]));
                findAllAssetDto.setStatusProcessCurrent(ValueUtil.getIntegerByObject(obj[18]));
                findAllAssetDto.setStatusUse(ValueUtil.getIntegerByObject(obj[19]));
                findAllAssetDto.setYearUse(ValueUtil.getStringByObject(obj[20]));
                findAllAssetDto.setAcreage(ValueUtil.getDoubleByObject(obj[21]));
                findAllAssetDto.setOriginalOfFormation(ValueUtil.getStringByObject(obj[22]));
                findAllAssetDto.setCumulative(ValueUtil.getStringByObject(obj[23]));
                findAllAssetDto.setRestValue(ValueUtil.getStringByObject(obj[24]));
                responses.add(findAllAssetDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllAsset(request));
    }

    @Override
    public Page<FindAllAssetDto> findAllAssetLotChildrenDtoByIdsDepartment(FindAllAssetLotChildrenRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select asset.id_asset idAsset, asset.code_asset codeAsset,        " +
                "        asset.name nameAsset, assetCategories.id_asset_category idAssetCategory,        " +
                "        assetCategories.name nameAssetCategory, assetCategories.code_name codeAssetCategory,        " +
                "        de.id_department idDepartment, de.code codeDepartment, de.name nameDepartment,        " +
                "        lo.id_location idLocation, lo.name nameLocation,        " +
                "        asset.time_created, asset.time_modified,     " +
                "        asset.parent, asset.salt , asset.is_increase, asset.is_decrease," +
                "        asset.status_use, asset.year_use,asset.acreage   " +
                " from asset asset        " +
                "     inner join asset_categories assetCategories        " +
                "             on asset.id_asset_category = assetCategories.id_asset_category        " +
                "     inner join department de on asset.id_department = de.id_department        " +
                "     left join location lo on asset.id_location = lo.id_location       " +
                "     inner join (select * from asset where asset.salt = :saltAssetParent ) assetParent   " +
                "         on asset.parent = assetParent.id_asset  " +
                " where 1 = 1  and asset.id_department_origin in (:idsDepartmentOriginal)   ");
        setConditionFindAllAssetLotChildren(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetLotChildren(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllAssetDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllAssetDto findAllAssetDto = new FindAllAssetDto();
                findAllAssetDto.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
                findAllAssetDto.setCodeAsset(ValueUtil.getStringByObject(obj[1]));
                findAllAssetDto.setNameAsset(ValueUtil.getStringByObject(obj[2]));
                findAllAssetDto.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[3]));
                findAllAssetDto.setNameAssetCategory(ValueUtil.getStringByObject(obj[4]));
                findAllAssetDto.setCodeAssetCategory(ValueUtil.getStringByObject(obj[5]));
                findAllAssetDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[6]));
                findAllAssetDto.setCodeDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllAssetDto.setNameDepartment(ValueUtil.getStringByObject(obj[8]));
                findAllAssetDto.setIdLocation(ValueUtil.getIntegerByObject(obj[9]));
                findAllAssetDto.setNameLocation(ValueUtil.getStringByObject(obj[10]));
                findAllAssetDto.setTimeCreated(ValueUtil.getLongByObject(obj[11]));
                findAllAssetDto.setTimeModified(ValueUtil.getLongByObject(obj[12]));
                findAllAssetDto.setParent(ValueUtil.getIntegerByObject(obj[13]));
                findAllAssetDto.setSalt(ValueUtil.getStringByObject(obj[14]));
                findAllAssetDto.setIsIncrease(ValueUtil.getIntegerByObject(obj[15]));
                findAllAssetDto.setIsDecrease(ValueUtil.getIntegerByObject(obj[16]));
                findAllAssetDto.setStatusUse(ValueUtil.getIntegerByObject(obj[17]));
                findAllAssetDto.setYearUse(ValueUtil.getStringByObject(obj[18]));
                findAllAssetDto.setAcreage(ValueUtil.getDoubleByObject(obj[19]));
                responses.add(findAllAssetDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllAssetLotChildren(request));
    }

    private void setParameterFindAllAssetLotChildren(FindAllAssetLotChildrenRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("saltAssetParent", request.getSaltAssetParent());

        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            query.setParameter("idDepartment", request.getIdDepartment());
        }
        if (ObjectUtils.isNotEmpty(request.getIdLocation())){
            query.setParameter("idLocation", request.getIdLocation());
        }
        if (ObjectUtils.isNotEmpty(request.getIsIncrease())){
            query.setParameter("isIncrease", request.getIsIncrease());
        }
        if (ObjectUtils.isNotEmpty(request.getIsDecrease())){
            query.setParameter("isDecrease", request.getIsDecrease());
        }
        if (ObjectUtils.isNotEmpty(request.getIsSingle())){
            query.setParameter("isSingle", Constants.QUANTITY_DEFAULT);
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())){
            query.setParameter("statusUse", request.getStatusUse());
        }
    }

    private void setConditionFindAllAssetLotChildren(FindAllAssetLotChildrenRequest request, StringBuilder sb) {
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdLocation())){
            sb.append(" and lo.id_location = :idLocation ");
        }
        if (ObjectUtils.isNotEmpty(request.getIsIncrease())){
            sb.append("   and (asset.is_increase = :isIncrease) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIsDecrease())){
            sb.append("   and (asset.is_decrease = :isDecrease) ");
        }
        if (Boolean.FALSE.equals(request.getIsSingle())){
            sb.append("   and (asset.quantity != :isSingle) ");
        }
        if (Boolean.TRUE.equals(request.getIsSingle())){
            sb.append("   and (asset.quantity = :isSingle) ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())){
            sb.append(" and asset.status_use = :statusUse ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())){
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY asset.id_asset ");
        }
    }

    @Override
    public Optional<AssetBluePrintDto> findDetailAssetBySaltAsset(String saltAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append("select asset.id_asset, asset.name, asset.code_asset, assetCategory.id_asset_category idAssetCategory,      " +
                "         assetCategory.name nameAssetCategory, de.code codeDepartment, de.id_department idDepartment, de.name nameDepartment,      " +
                "         documentAttack.id_document_attack idDocumentAttack, documentAttack.name nameDocumentAttack,      " +
                "         location.id_location idLocation, location.name nameLocation, unit.id_unit idUnit, unit.name nameUnit,      " +
                "         project.id_project idProject, project.name nameProject, asset.purpose, asset.notes, asset.description, asset.file_attack,      " +
                "         departmentDefault.id_department idDepartmentDefault, departmentDefault.name nameDepartmentDefault,      " +
                "         levelTypeAsset.id_level_type_asset idLevelTypeAsset, levelTypeAsset.name nameLevelTypeAsset,      " +
                "         modules.hard_code typeModules, modules.id_module, modules.name nameModules,      " +
                "         assetModules.id_instance idInstanceModule,      " +
                "         original.hard_code_dev typeOriginal, original.id_original, original.name nameOriginal,      " +
                "         assetOriginal.id_instance idInstanceOriginal,      " +
                "         decl.hard_code typeDeclare, decl.id_declare, decl.name nameDeclare,      " +
                "         assetDeclare.id_instance inInstanceDeclare,       " +
                "         asset.id_instance assetIdInstance, assetDepreciation.id_asset_depreciation,      " +
                "         assetDepreciation.time_started_depreciation, assetDepreciation.amount_months_depreciation,       " +
                "         assetDepreciation.value_depreciation, assetDepreciation.type_depreciation,       " +
                "         assetDepreciation.value_type_depreciation, assetDepreciation.amount_rest_months_depreciation,       " +
                "         assetDepreciation.cumulative, assetDepreciation.rest_value, assetDepreciation.time_started_wear_tear,       " +
                "         assetDepreciation.time_end_wear_tear, assetDepreciation.type_calculate, assetDepreciation.time_buy,       " +
                "         assetDepreciation.time_started_used, assetDepreciation.time_started_increase,       " +
                "         assetDepreciation.time_year_tracking, assetDepreciation.time_created, assetDepreciation.time_modified ,      " +
                "         assetCategory.value_wear_tear, assetCategory.year_used_wear_tear, assetCategory.minimum_time_depreciation,      " +
                "         assetCategory.maximum_time_depreciation, asset.parent, asset.salt, asset.quantity, " +
                "         asset.id_department_origin, asset.id_process_current, asset.status_process_current, " +
                "         asset.id_type_process_current, asset.is_increase, asset.is_decrease, " +
                "         asset.id_user_created, asset.id_user_modified, asset.status_use, asset.year_use,asset.acreage " +
                " from asset asset       " +
                "      left join asset_categories assetCategory on asset.id_asset_category = assetCategory.id_asset_category      " +
                "      left join department de on asset.id_department = de.id_department      " +
                "      left join document_attack documentAttack on asset.id_document_attack = documentAttack.id_document_attack      " +
                "      left join location location on asset.id_location = location.id_location      " +
                "      left join units unit on asset.id_unit = unit.id_unit      " +
                "      left join projects project on asset.id_projects = project.id_project      " +
                "      left join department departmentDefault on asset.id_department_default = departmentDefault.id_department      " +
                "      left join level_type_asset levelTypeAsset on asset.id_level_type_asset = levelTypeAsset.id_level_type_asset      " +
                "      left join asset_modules assetModules on asset.id_asset = assetModules.id_asset      " +
                "      left join modules modules on assetModules.id_module = modules.id_module      " +
                "      left join asset_original assetOriginal on asset.id_asset = assetOriginal.id_asset      " +
                "      left join original original on assetOriginal.id_original = original.id_original      " +
                "      left join asset_declare assetDeclare on asset.id_asset = assetDeclare.id_asset      " +
                "      left join `declare` decl on assetDeclare.id_declare = decl.id_declare       " +
                "      left join asset_depreciation assetDepreciation on asset.id_asset = assetDepreciation.id_asset    " +
                " where asset.salt = :saltAsset ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("saltAsset", saltAsset);
        List<Object[]> result = query.getResultList();
        AssetBluePrintDto dto = new AssetBluePrintDto();
        if (!CollectionUtils.isEmpty(result)) {
            Object[] obj = result.get(0);
            setCommonBluePrintAsset(dto, obj);
            setAssetDepreciation(dto, obj);
            setModulesBluePrintAsset(dto, result);
            setOriginalBluePrintAsset(dto, obj);
            setDeclareBluePrintAsset(dto, obj);
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    private void setAssetDepreciation(AssetBluePrintDto dto, Object[] obj) {
        AssetDepreciationDto assetDepreciationDto = new AssetDepreciationDto();
        assetDepreciationDto.setIdAssetDepreciation(ValueUtil.getIntegerByObject(obj[37]));
        assetDepreciationDto.setTimeStartedDepreciation(ValueUtil.getStringByObject(obj[38]));
        assetDepreciationDto.setAmountMonthsDepreciation(ValueUtil.getIntegerByObject(obj[39]));
        assetDepreciationDto.setValueDepreciation(ValueUtil.getStringByObject(obj[40]));
        assetDepreciationDto.setTypeDepreciation(ValueUtil.getIntegerByObject(obj[41]));
        assetDepreciationDto.setValueTypeDepreciation(ValueUtil.getStringByObject(obj[42]));
        assetDepreciationDto.setAmountRestMonthsDepreciation(ValueUtil.getIntegerByObject(obj[43]));
        assetDepreciationDto.setCumulative(ValueUtil.getStringByObject(obj[44]));
        assetDepreciationDto.setRestValue(ValueUtil.getStringByObject(obj[45]));
        assetDepreciationDto.setTimeStartedWearTear(ValueUtil.getStringByObject(obj[46]));
        assetDepreciationDto.setTimeEndWearTear(ValueUtil.getStringByObject(obj[47]));
        assetDepreciationDto.setTypeCalculate(ValueUtil.getIntegerByObject(obj[48]));
        assetDepreciationDto.setTimeBuy(ValueUtil.getStringByObject(obj[49]));
        assetDepreciationDto.setTimeStartedUsed(ValueUtil.getStringByObject(obj[50]));
        assetDepreciationDto.setTimeStartedIncrease(ValueUtil.getStringByObject(obj[51]));
        assetDepreciationDto.setTimeYearTracking(ValueUtil.getStringByObject(obj[52]));
        assetDepreciationDto.setTimeCreated(ValueUtil.getStringByObject(obj[53]));
        assetDepreciationDto.setTimeModified(ValueUtil.getStringByObject(obj[54]));
        assetDepreciationDto.setValueWearTear(ValueUtil.getStringByObject(obj[55]));
        assetDepreciationDto.setYearUsedWearTear(ValueUtil.getStringByObject(obj[56]));
        assetDepreciationDto.setMinimumTimeDepreciation(ValueUtil.getStringByObject(obj[57]));
        assetDepreciationDto.setMaximumTimeDepreciation(ValueUtil.getStringByObject(obj[58]));
        dto.setAssetDepreciationDto(assetDepreciationDto);
    }

    @Override
    public Optional<Asset> findAssetBySalt(String salt) {
        StringBuilder sb = new StringBuilder();
        sb.append("select asset.id_asset, asset.name, asset.code_asset,  " +
                "        asset.id_asset_category, asset.id_document_attack,  " +
                "        asset.id_department, asset.id_location,  " +
                "        asset.id_unit, asset.id_projects, asset.description,  " +
                "        asset.purpose, asset.notes, asset.file_attack,  " +
                "        asset.time_created, asset.time_modified, asset.id_department_default,  " +
                "        asset.id_level_type_asset, asset.id_user_created,  " +
                "        asset.id_user_modified, asset.quantity, asset.id_instance,  " +
                "        asset.id_department_origin, asset.parent, asset.salt,  " +
                "        asset.id_process_current,asset.status_process_current, asset.id_type_process_current,  " +
                "        asset.is_increase, asset.is_decrease, asset.status_use,asset.year_use, asset.acreage,  " +
                "        asset.sum_original_of_formation " +
                "from asset       " +
                "where asset.salt = :salt ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("salt", salt);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                Asset asset = new Asset();
                asset.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
                asset.setName(ValueUtil.getStringByObject(obj[1]));
                asset.setCodeAsset(ValueUtil.getStringByObject(obj[2]));
                asset.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[3]));
                asset.setIdDocumentAttack(ValueUtil.getIntegerByObject(obj[4]));
                asset.setIdDepartment(ValueUtil.getIntegerByObject(obj[5]));
                asset.setIdLocation(ValueUtil.getIntegerByObject(obj[6]));
                asset.setIdUnit(ValueUtil.getIntegerByObject(obj[7]));
                asset.setIdProjects(ValueUtil.getIntegerByObject(obj[8]));
                asset.setDescription(ValueUtil.getStringByObject(obj[9]));
                asset.setPurpose(ValueUtil.getStringByObject(obj[10]));
                asset.setNotes(ValueUtil.getStringByObject(obj[11]));
                asset.setFileAttack(ValueUtil.getStringByObject(obj[12]));
                asset.setTimeCreated(ValueUtil.getStringByObject(obj[13]));
                asset.setTimeModified(ValueUtil.getStringByObject(obj[14]));
                asset.setIdDepartmentDefault(ValueUtil.getIntegerByObject(obj[15]));
                asset.setIdLevelTypeAsset(ValueUtil.getIntegerByObject(obj[16]));
                asset.setIdUserCreated(ValueUtil.getIntegerByObject(obj[17]));
                asset.setIdUserModified(ValueUtil.getIntegerByObject(obj[18]));
                asset.setQuantity(ValueUtil.getIntegerByObject(obj[19]));
                asset.setIdInstance(ValueUtil.getIntegerByObject(obj[20]));
                asset.setIdDepartmentOrigin(ValueUtil.getIntegerByObject(obj[21]));
                asset.setParent(ValueUtil.getIntegerByObject(obj[22]));
                asset.setSalt(ValueUtil.getStringByObject(obj[23]));
                asset.setIdProcessCurrent(ValueUtil.getIntegerByObject(obj[24]));
                asset.setStatusProcessCurrent(ValueUtil.getIntegerByObject(obj[25]));
                asset.setIdTypeProcessCurrent(ValueUtil.getIntegerByObject(obj[26]));
                asset.setIsIncrease(ValueUtil.getIntegerByObject(obj[27]));
                asset.setIsDecrease(ValueUtil.getIntegerByObject(obj[28]));
                asset.setStatusUse(ValueUtil.getIntegerByObject(obj[29]));
                asset.setYearUse(ValueUtil.getStringByObject(obj[30]));
                asset.setAcreage(ValueUtil.getDoubleByObject(obj[31]));
                asset.setSumOriginalOfFormation(ValueUtil.getStringByObject(obj[32]));
                return Optional.of(asset);
            }
        }
        return Optional.empty();
    }


    @Modifying
    @Transactional
    @Override
    public void deleteByIdAsset(Integer idAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from asset " +
                "where asset.id_asset = :idAsset ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAsset", idAsset);
        query.executeUpdate();
    }

    @Override
    public Page<GroundAssetDto> findAllGroundAsset(Pageable pageable, FindAllGroundAssetRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("select asset.id_asset, asset.code_asset, asset.name,  " +
                " asset.salt,department.name " +
                " from asset asset     " +
                " inner join ground_module groundModule   " +
                " on asset.id_asset = groundModule.id_asset  " +
                " inner join department on asset.id_department_origin = department.id_department   " +
                " where 1 = 1  ");
        setConditionFindAllGroundAsset(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllGroundAsset(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<GroundAssetDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                GroundAssetDto response = new GroundAssetDto();
                response.setIdGroundAsset(ValueUtil.getIntegerByObject(obj[0]));
                response.setCodeGroundAsset(ValueUtil.getStringByObject(obj[1]));
                response.setNameGroundAsset(ValueUtil.getStringByObject(obj[2]));
                response.setSalt(ValueUtil.getStringByObject(obj[3]));
                response.setNameDepartment(ValueUtil.getStringByObject(obj[4]));
                responses.add(response);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllGroundAsset(request));
    }

    @Override
    public List<FindAllGroundAssetDto> findAllGroundAssetToDownload() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select asset.id_asset, asset.code_asset, asset.name    " +
                "from asset asset  " +
                "      inner join ground_module groundModule on  " +
                "          asset.id_asset = groundModule.id_asset  " +
                "where 1 = 1 ");
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> result = query.getResultList();
        List<FindAllGroundAssetDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                FindAllGroundAssetDto groundAssetDto = new FindAllGroundAssetDto();
                groundAssetDto.setIdGroundAsset(ValueUtil.getIntegerByObject(obj[0]));
                groundAssetDto.setCodeGroundAsset(ValueUtil.getStringByObject(obj[1]));
                groundAssetDto.setNameGroundAsset(ValueUtil.getStringByObject(obj[2]));
                responses.add(groundAssetDto);
            }
        }
        return responses;
    }

    @Override
    public Page<FindAllAssetDto> findAllAssetDtoToIncrease(FinaAllAssetToIncreaseRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select asset.id_asset idAsset, asset.code_asset codeAsset,     " +
                "       asset.name nameAsset, assetCategories.id_asset_category idAssetCategory,     " +
                "       assetCategories.name nameAssetCategory, assetCategories.code_name codeAssetCategory,     " +
                "       de.id_department idDepartment, de.code codeDepartment, de.name nameDepartment,     " +
                "       lo.id_location idLocation, lo.name nameLocation,     " +
                "       asset.time_created, asset.time_modified, asset.parent, asset.salt,asset.id_type_process_current,     " +
                "       asset.status_process_current,asset.quantity,     " +
                "       asset.sum_original_of_formation,     " +
                "      ad.cumulative,ad.rest_value, asset.status_use, asset.year_use , " +
                "       (select count(child.id_asset)    " +
                "        from asset child    " +
                "        where child.parent = idAsset    " +
                "          and child.is_increase = :increaseChild and child.status_process_current != :statusProcessCurrent ) as sum_child_increase,    " +
                "       (select count(child.id_asset)    " +
                "        from asset child    " +
                "        where child.parent = idAsset    " +
                "          and child.is_decrease = :decreaseChild and child.status_process_current != :statusProcessCurrent ) as sum_child_decrease   " +
                "from asset asset     " +
                "        inner join asset_categories assetCategories     " +
                "            on asset.id_asset_category = assetCategories.id_asset_category     " +
                "        left join department de on asset.id_department = de.id_department     " +
                "        left join location lo on asset.id_location = lo.id_location     " +
                "        left join asset_depreciation ad on asset.id_asset = ad.id_asset     " +
                "where 1 = 1     " +
                "and asset.id_department_origin in (:idsDepartmentOriginal)     " +
                "and (asset.status_process_current != :statusProcessCurrent or asset.status_process_current is null) ");
        setConditionFindAllAssetDtoToIncrease(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("increaseChild",Constants.IS_INCREASED);
        query.setParameter("decreaseChild", Constants.IS_DECREASED);
        setParameterFindAllAssetDtoToIncrease(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllAssetDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllAssetDto findAllAssetDto = new FindAllAssetDto();
                findAllAssetDto.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
                findAllAssetDto.setCodeAsset(ValueUtil.getStringByObject(obj[1]));
                findAllAssetDto.setNameAsset(ValueUtil.getStringByObject(obj[2]));
                findAllAssetDto.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[3]));
                findAllAssetDto.setNameAssetCategory(ValueUtil.getStringByObject(obj[4]));
                findAllAssetDto.setCodeAssetCategory(ValueUtil.getStringByObject(obj[5]));
                findAllAssetDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[6]));
                findAllAssetDto.setCodeDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllAssetDto.setNameDepartment(ValueUtil.getStringByObject(obj[8]));
                findAllAssetDto.setIdLocation(ValueUtil.getIntegerByObject(obj[9]));
                findAllAssetDto.setNameLocation(ValueUtil.getStringByObject(obj[10]));
                findAllAssetDto.setTimeCreated(ValueUtil.getLongByObject(obj[11]));
                findAllAssetDto.setTimeModified(ValueUtil.getLongByObject(obj[12]));
                findAllAssetDto.setParent(ValueUtil.getIntegerByObject(obj[13]));
                findAllAssetDto.setSalt(ValueUtil.getStringByObject(obj[14]));
                findAllAssetDto.setQuantity(ValueUtil.getIntegerByObject(obj[17]));
                findAllAssetDto.setOriginalOfFormation(ValueUtil.getStringByObject(obj[18]));
                findAllAssetDto.setCumulative(ValueUtil.getStringByObject(obj[19]));
                findAllAssetDto.setRestValue(ValueUtil.getStringByObject(obj[20]));
                findAllAssetDto.setStatusUse(ValueUtil.getIntegerByObject(obj[21]));
                findAllAssetDto.setYearUse(ValueUtil.getStringByObject(obj[22]));
                findAllAssetDto.setCountChildIncrease(ValueUtil.getIntegerByObject(obj[23]));
                findAllAssetDto.setCountChildDecrease(ValueUtil.getIntegerByObject(obj[24]));
                responses.add(findAllAssetDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllAssetToIncrease(request));
    }

    @Override
    public Page<FindAllAssetDto> findAllAssetChildrenDtoToIncrease(FinaAllAssetToIncreaseRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select asset.id_asset idAsset, asset.code_asset codeAsset,     " +
                "        asset.name nameAsset, assetCategories.id_asset_category idAssetCategory,     " +
                "        assetCategories.name nameAssetCategory, assetCategories.code_name codeAssetCategory,     " +
                "        de.id_department idDepartment, de.code codeDepartment, de.name nameDepartment,     " +
                "        lo.id_location idLocation, lo.name nameLocation,     " +
                "        asset.time_created, asset.time_modified, asset.parent, asset.salt,asset.id_type_process_current,     " +
                "        asset.status_process_current,asset.quantity,     " +
                "       asset.sum_original_of_formation,     " +
                "       ad.cumulative,ad.rest_value, asset.status_use, asset.year_use      " +
                "from asset asset      " +
                "         inner join asset_categories assetCategories      " +
                "   on asset.id_asset_category = assetCategories.id_asset_category      " +
                "         inner join department de on asset.id_department = de.id_department      " +
                "         left join location lo on asset.id_location = lo.id_location      " +
                "         left join asset_depreciation ad on asset.id_asset = ad.id_asset      " +
                "         inner join (select id_asset from asset where asset.salt = :salt) assetParent      " +
                "   on asset.parent = assetParent.id_asset      " +
                "                 where 1 = 1      " +
                "and asset.id_department_origin in (:idsDepartmentOriginal)      " +
                "and asset.is_increase = :isIncrease      " +
                "and (asset.status_process_current != :statusProcessCurrent or asset.status_process_current is null) ");
        setConditionFindAllAssetChildrenDtoToIncrease(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetChildrenDtoToIncrease(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllAssetDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllAssetDto findAllAssetDto = new FindAllAssetDto();
                findAllAssetDto.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
                findAllAssetDto.setCodeAsset(ValueUtil.getStringByObject(obj[1]));
                findAllAssetDto.setNameAsset(ValueUtil.getStringByObject(obj[2]));
                findAllAssetDto.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[3]));
                findAllAssetDto.setNameAssetCategory(ValueUtil.getStringByObject(obj[4]));
                findAllAssetDto.setCodeAssetCategory(ValueUtil.getStringByObject(obj[5]));
                findAllAssetDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[6]));
                findAllAssetDto.setCodeDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllAssetDto.setNameDepartment(ValueUtil.getStringByObject(obj[8]));
                findAllAssetDto.setIdLocation(ValueUtil.getIntegerByObject(obj[9]));
                findAllAssetDto.setNameLocation(ValueUtil.getStringByObject(obj[10]));
                findAllAssetDto.setTimeCreated(ValueUtil.getLongByObject(obj[11]));
                findAllAssetDto.setTimeModified(ValueUtil.getLongByObject(obj[12]));
                findAllAssetDto.setParent(ValueUtil.getIntegerByObject(obj[13]));
                findAllAssetDto.setSalt(ValueUtil.getStringByObject(obj[14]));
                findAllAssetDto.setQuantity(ValueUtil.getIntegerByObject(obj[17]));
                findAllAssetDto.setOriginalOfFormation(ValueUtil.getStringByObject(obj[18]));
                findAllAssetDto.setCumulative(ValueUtil.getStringByObject(obj[19]));
                findAllAssetDto.setRestValue(ValueUtil.getStringByObject(obj[20]));
                findAllAssetDto.setStatusUse(ValueUtil.getIntegerByObject(obj[21]));
                findAllAssetDto.setYearUse(ValueUtil.getStringByObject(obj[22]));
                responses.add(findAllAssetDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllAssetChildrenToIncrease(request));
    }

    @Override
    public Optional<Asset> findAssetByIdDepartmentOrigin(Integer idDepartmentOrigin) {
        StringBuilder sb = new StringBuilder();
        sb.append("select id_asset, name, code_asset, id_asset_category,  " +
                "       id_document_attack, id_department, id_location,  " +
                "       id_unit, id_projects, purpose, notes, file_attack,  " +
                "       time_created, time_modified, id_department_default,  " +
                "       id_level_type_asset, id_user_created, id_user_modified,       " +
                "       description, quantity, id_instance, id_department_origin,  " +
                "       parent, salt, id_process_current, status_process_current,  " +
                "       id_type_process_current, is_increase, is_decrease, status_use, " +
                "       year_use, acreage " +
                "from asset where id_department_origin = :idDepartmentOrigin  " +
                "order by id_asset desc limit 1 ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idDepartmentOrigin", idDepartmentOrigin);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Asset asset = new Asset();
                asset.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
                asset.setName(ValueUtil.getStringByObject(obj[1]));
                asset.setCodeAsset(ValueUtil.getStringByObject(obj[2]));
                asset.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[3]));
                asset.setIdDocumentAttack(ValueUtil.getIntegerByObject(obj[4]));
                asset.setIdDepartment(ValueUtil.getIntegerByObject(obj[5]));
                asset.setIdLocation(ValueUtil.getIntegerByObject(obj[6]));
                asset.setIdUnit(ValueUtil.getIntegerByObject(obj[7]));
                asset.setIdProjects(ValueUtil.getIntegerByObject(obj[8]));
                asset.setPurpose(ValueUtil.getStringByObject(obj[9]));
                asset.setNotes(ValueUtil.getStringByObject(obj[10]));
                asset.setFileAttack(ValueUtil.getStringByObject(obj[11]));
                asset.setTimeCreated(ValueUtil.getStringByObject(obj[12]));
                asset.setTimeModified(ValueUtil.getStringByObject(obj[13]));
                asset.setIdDepartmentDefault(ValueUtil.getIntegerByObject(obj[14]));
                asset.setIdLevelTypeAsset(ValueUtil.getIntegerByObject(obj[15]));
                asset.setIdUserCreated(ValueUtil.getIntegerByObject(obj[16]));
                asset.setIdUserModified(ValueUtil.getIntegerByObject(obj[17]));
                asset.setDescription(ValueUtil.getStringByObject(obj[18]));
                asset.setQuantity(ValueUtil.getIntegerByObject(obj[19]));
                asset.setIdInstance(ValueUtil.getIntegerByObject(obj[20]));
                asset.setIdDepartmentOrigin(ValueUtil.getIntegerByObject(obj[21]));
                asset.setParent(ValueUtil.getIntegerByObject(obj[22]));
                asset.setSalt(ValueUtil.getStringByObject(obj[23]));
                asset.setIdProcessCurrent(ValueUtil.getIntegerByObject(obj[24]));
                asset.setStatusProcessCurrent(ValueUtil.getIntegerByObject(obj[25]));
                asset.setIdTypeProcessCurrent(ValueUtil.getIntegerByObject(obj[26]));
                asset.setIsIncrease(ValueUtil.getIntegerByObject(obj[27]));
                asset.setIsDecrease(ValueUtil.getIntegerByObject(obj[28]));
                asset.setStatusUse(ValueUtil.getIntegerByObject(obj[29]));
                asset.setYearUse(ValueUtil.getStringByObject(obj[30]));
                asset.setAcreage(ValueUtil.getDoubleByObject(obj[31]));
                return Optional.of(asset);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Asset> findAssetLotByIdDepartmentOrigin(Integer idDepartmentOrigin) {
        StringBuilder sb = new StringBuilder();
        sb.append("select id_asset, name, code_asset, id_asset_category,  " +
                "        id_document_attack, id_department, id_location,  " +
                "        id_unit, id_projects, purpose, notes, file_attack,  " +
                "        time_created, time_modified, id_department_default,  " +
                "        id_level_type_asset, id_user_created, id_user_modified,  " +
                "        description, quantity, id_instance, id_department_origin,  " +
                "        parent, salt, id_process_current, status_process_current,  " +
                "        id_type_process_current, is_increase, is_decrease, status_use," +
                "        year_use, acreage  " +
                " from asset             " +
                " where id_department_origin = :idDepartmentOrigin             " +
                " and parent is not null             " +
                " order by id_asset desc limit 1  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idDepartmentOrigin", idDepartmentOrigin);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Asset asset = new Asset();
                asset.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
                asset.setName(ValueUtil.getStringByObject(obj[1]));
                asset.setCodeAsset(ValueUtil.getStringByObject(obj[2]));
                asset.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[3]));
                asset.setIdDocumentAttack(ValueUtil.getIntegerByObject(obj[4]));
                asset.setIdDepartment(ValueUtil.getIntegerByObject(obj[5]));
                asset.setIdLocation(ValueUtil.getIntegerByObject(obj[6]));
                asset.setIdUnit(ValueUtil.getIntegerByObject(obj[7]));
                asset.setIdProjects(ValueUtil.getIntegerByObject(obj[8]));
                asset.setPurpose(ValueUtil.getStringByObject(obj[9]));
                asset.setNotes(ValueUtil.getStringByObject(obj[10]));
                asset.setFileAttack(ValueUtil.getStringByObject(obj[11]));
                asset.setTimeCreated(ValueUtil.getStringByObject(obj[12]));
                asset.setTimeModified(ValueUtil.getStringByObject(obj[13]));
                asset.setIdDepartmentDefault(ValueUtil.getIntegerByObject(obj[14]));
                asset.setIdLevelTypeAsset(ValueUtil.getIntegerByObject(obj[15]));
                asset.setIdUserCreated(ValueUtil.getIntegerByObject(obj[16]));
                asset.setIdUserModified(ValueUtil.getIntegerByObject(obj[17]));
                asset.setDescription(ValueUtil.getStringByObject(obj[18]));
                asset.setQuantity(ValueUtil.getIntegerByObject(obj[19]));
                asset.setIdInstance(ValueUtil.getIntegerByObject(obj[20]));
                asset.setIdDepartmentOrigin(ValueUtil.getIntegerByObject(obj[21]));
                asset.setParent(ValueUtil.getIntegerByObject(obj[22]));
                asset.setSalt(ValueUtil.getStringByObject(obj[23]));
                asset.setIdProcessCurrent(ValueUtil.getIntegerByObject(obj[24]));
                asset.setStatusProcessCurrent(ValueUtil.getIntegerByObject(obj[25]));
                asset.setIdTypeProcessCurrent(ValueUtil.getIntegerByObject(obj[26]));
                asset.setIsIncrease(ValueUtil.getIntegerByObject(obj[27]));
                asset.setIsDecrease(ValueUtil.getIntegerByObject(obj[28]));
                asset.setStatusUse(ValueUtil.getIntegerByObject(obj[29]));
                asset.setYearUse(ValueUtil.getStringByObject(obj[30]));
                asset.setAcreage(ValueUtil.getDoubleByObject(obj[31]));
                return Optional.of(asset);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Asset> findAllAssetChildrenByParentId(Integer idAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append("select id_asset, name, code_asset, id_asset_category,  " +
                "        id_document_attack, id_department, id_location,  " +
                "        id_unit, id_projects, purpose, notes, file_attack,  " +
                "        time_created, time_modified, id_department_default,  " +
                "        id_level_type_asset, id_user_created, id_user_modified,  " +
                "        description, quantity, id_instance, id_department_origin,  " +
                "        parent, salt, id_process_current, status_process_current,  " +
                "        id_type_process_current, is_increase, is_decrease, status_use,  " +
                "        year_use, acreage  " +
                " from asset  " +
                " where asset.parent = :idAssetParent  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAssetParent", idAsset);
        List<Object[]> result = query.getResultList();
        List<Asset> assetChildren = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Asset asset = new Asset();
                asset.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
                asset.setName(ValueUtil.getStringByObject(obj[1]));
                asset.setCodeAsset(ValueUtil.getStringByObject(obj[2]));
                asset.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[3]));
                asset.setIdDocumentAttack(ValueUtil.getIntegerByObject(obj[4]));
                asset.setIdDepartment(ValueUtil.getIntegerByObject(obj[5]));
                asset.setIdLocation(ValueUtil.getIntegerByObject(obj[6]));
                asset.setIdUnit(ValueUtil.getIntegerByObject(obj[7]));
                asset.setIdProjects(ValueUtil.getIntegerByObject(obj[8]));
                asset.setPurpose(ValueUtil.getStringByObject(obj[9]));
                asset.setNotes(ValueUtil.getStringByObject(obj[10]));
                asset.setFileAttack(ValueUtil.getStringByObject(obj[11]));
                asset.setTimeCreated(ValueUtil.getStringByObject(obj[12]));
                asset.setTimeModified(ValueUtil.getStringByObject(obj[13]));
                asset.setIdDepartmentDefault(ValueUtil.getIntegerByObject(obj[14]));
                asset.setIdLevelTypeAsset(ValueUtil.getIntegerByObject(obj[15]));
                asset.setIdUserCreated(ValueUtil.getIntegerByObject(obj[16]));
                asset.setIdUserModified(ValueUtil.getIntegerByObject(obj[17]));
                asset.setDescription(ValueUtil.getStringByObject(obj[18]));
                asset.setQuantity(ValueUtil.getIntegerByObject(obj[19]));
                asset.setIdInstance(ValueUtil.getIntegerByObject(obj[20]));
                asset.setIdDepartmentDefault(ValueUtil.getIntegerByObject(obj[21]));
                asset.setParent(ValueUtil.getIntegerByObject(obj[22]));
                asset.setSalt(ValueUtil.getStringByObject(obj[23]));
                asset.setIdProcessCurrent(ValueUtil.getIntegerByObject(obj[24]));
                asset.setStatusProcessCurrent(ValueUtil.getIntegerByObject(obj[25]));
                asset.setIdTypeProcessCurrent(ValueUtil.getIntegerByObject(obj[26]));
                asset.setIsIncrease(ValueUtil.getIntegerByObject(obj[27]));
                asset.setIsDecrease(ValueUtil.getIntegerByObject(obj[28]));
                asset.setStatusUse(ValueUtil.getIntegerByObject(obj[29]));
                asset.setYearUse(ValueUtil.getStringByObject(obj[30]));
                asset.setAcreage(ValueUtil.getDoubleByObject(obj[31]));
                assetChildren.add(asset);
            }
        }
        return assetChildren;
    }
    @Override
    public List<Asset> findAllAssetChildrenToChangeByParentId(Integer idAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append("select id_asset, name, code_asset, id_asset_category,  " +
                "        id_document_attack, id_department, id_location,  " +
                "        id_unit, id_projects, purpose, notes, file_attack,  " +
                "        time_created, time_modified, id_department_default,  " +
                "        id_level_type_asset, id_user_created, id_user_modified,  " +
                "        description, quantity, id_instance, id_department_origin,  " +
                "        parent, salt, id_process_current, status_process_current,  " +
                "        id_type_process_current, is_increase, is_decrease, status_use, " +
                "        year_use, acreage " +
                " from asset  " +
                " where asset.parent = :idAssetParent and asset.is_decrease != :isDecrease ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("isDecrease", Constants.IS_DECREASED);
        query.setParameter("idAssetParent", idAsset);
        List<Object[]> result = query.getResultList();
        List<Asset> assetChildren = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Asset asset = new Asset();
                asset.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
                asset.setName(ValueUtil.getStringByObject(obj[1]));
                asset.setCodeAsset(ValueUtil.getStringByObject(obj[2]));
                asset.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[3]));
                asset.setIdDocumentAttack(ValueUtil.getIntegerByObject(obj[4]));
                asset.setIdDepartment(ValueUtil.getIntegerByObject(obj[5]));
                asset.setIdLocation(ValueUtil.getIntegerByObject(obj[6]));
                asset.setIdUnit(ValueUtil.getIntegerByObject(obj[7]));
                asset.setIdProjects(ValueUtil.getIntegerByObject(obj[8]));
                asset.setPurpose(ValueUtil.getStringByObject(obj[9]));
                asset.setNotes(ValueUtil.getStringByObject(obj[10]));
                asset.setFileAttack(ValueUtil.getStringByObject(obj[11]));
                asset.setTimeCreated(ValueUtil.getStringByObject(obj[12]));
                asset.setTimeModified(ValueUtil.getStringByObject(obj[13]));
                asset.setIdDepartmentDefault(ValueUtil.getIntegerByObject(obj[14]));
                asset.setIdLevelTypeAsset(ValueUtil.getIntegerByObject(obj[15]));
                asset.setIdUserCreated(ValueUtil.getIntegerByObject(obj[16]));
                asset.setIdUserModified(ValueUtil.getIntegerByObject(obj[17]));
                asset.setDescription(ValueUtil.getStringByObject(obj[18]));
                asset.setQuantity(ValueUtil.getIntegerByObject(obj[19]));
                asset.setIdInstance(ValueUtil.getIntegerByObject(obj[20]));
                asset.setIdDepartmentDefault(ValueUtil.getIntegerByObject(obj[21]));
                asset.setParent(ValueUtil.getIntegerByObject(obj[22]));
                asset.setSalt(ValueUtil.getStringByObject(obj[23]));
                asset.setIdProcessCurrent(ValueUtil.getIntegerByObject(obj[24]));
                asset.setStatusProcessCurrent(ValueUtil.getIntegerByObject(obj[25]));
                asset.setIdTypeProcessCurrent(ValueUtil.getIntegerByObject(obj[26]));
                asset.setIsIncrease(ValueUtil.getIntegerByObject(obj[27]));
                asset.setIsDecrease(ValueUtil.getIntegerByObject(obj[28]));
                asset.setStatusUse(ValueUtil.getIntegerByObject(obj[29]));
                asset.setYearUse(ValueUtil.getStringByObject(obj[30]));
                asset.setAcreage(ValueUtil.getDoubleByObject(obj[31]));
                assetChildren.add(asset);
            }
        }
        return assetChildren;
    }

    @Override
    public Page<FindAllAssetParentToInventoryDto> findAllAssetDtoToInventory(FindAllAssetToInventoryRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH ROOT_ASSET_CATEGORIES as    " +
                "        (WITH RECURSIVE cte_asset_categories as    " +
                "          (select assetCategires.id_asset_category,    " +
                "                  assetCategires.name,    " +
                "                  assetCategires.code_name,    " +
                "                  1           as depth,    " +
                "                  CAST(assetCategires.id_asset_category as NCHAR) as path,    " +
                "                  assetCategires.number_code_pattern,    " +
                "                  assetCategires.id_department_original,    " +
                "                  assetCategires.type_target,    " +
                "                  assetCategires.parent    " +
                "           from asset_categories assetCategires    " +
                "           where assetCategires.parent is null    " +
                "             and assetCategires.visible = :visible    " +
                "           union all    " +
                "           select assetCategires.id_asset_category,    " +
                "                  assetCategires.name,    " +
                "                  assetCategires.code_name,    " +
                "                  cte.depth + 1          as depth,    " +
                "                  concat_ws('/', cte.path,    " +
                "                            CAST(assetCategires.id_asset_category as NCHAR)) as path,    " +
                "                  assetCategires.number_code_pattern,    " +
                "                  assetCategires.id_department_original,    " +
                "                  assetCategires.type_target,    " +
                "                  assetCategires.parent    " +
                "           from asset_categories assetCategires    " +
                "                    INNER JOIN cte_asset_categories cte    " +
                "                               ON assetCategires.parent = cte.id_asset_category)    " +
                "         select cte.id_asset_category,    " +
                "                cte.name,    " +
                "                cte.code_name,    " +
                "                cte.depth,    " +
                "                cte.path,    " +
                "                cte.number_code_pattern,    " +
                "                group_concat(un.name SEPARATOR '/') as unitMeasure,    " +
                "                cte.parent,    " +
                "                CASE    " +
                "                    WHEN EXISTS (SELECT 1    " +
                "               FROM asset_categories ac    " +
                "               WHERE ac.parent = cte.id_asset_category) THEN 0    " +
                "                    ELSE 1 END                      AS is_leaf,   " +
                "               cte.type_target    " +
                "         from cte_asset_categories cte    " +
                "                  left join (select un.id_asset_category, un.id_unit, un.name    " +
                "           from units un    " +
                "           where un.is_display = :isDisplay) un    " +
                "          on cte.id_asset_category = un.id_asset_category    " +
                "         where 1 = 1    " +
                "           and cte.id_department_original in (:idsDepartmentOriginal)    " +
                "         group by cte.id_asset_category, cte.name, cte.code_name,    " +
                "                  cte.depth, cte.path, cte.number_code_pattern, is_leaf, cte.type_target    " +
                "         order by cte.path),    " +
                "                      ROOT_ASSET as (select asset.id_asset                           idAsset,    " +
                "                          asset.code_asset                         codeAsset,    " +
                "                          asset.name             nameAsset,    " +
                "                          assetCategories.id_asset_category        idAssetCategory,    " +
                "                          assetCategories.name                     nameAssetCategory,    " +
                "                          assetCategories.code_name                codeAssetCategory,    " +
                "                          de.id_department                         idDepartment,    " +
                "                          de.code                codeDepartment,    " +
                "                          de.name                nameDepartment,    " +
                "                          lo.id_location                           idLocation,    " +
                "                          lo.name                nameLocation,    " +
                "                          asset.time_created,    " +
                "                          asset.time_modified,    " +
                "                          asset.parent,    " +
                "                          asset.salt,    " +
                "                          assetDepreciation.rest_value,    " +
                "                          asset.quantity,    " +
                "                          asset.sum_original_of_formation  assetOriginalOfFormationValue,    " +
                "                          asset.status_use,    " +
                "                          asset.year_use,  " +
                "                          asset.acreage,    " +
                "                          units.name as nameUnit,  " +
                "                          asset.is_increase," +
                "     (select count(child.id_asset) " +
                "     from asset child    " +
                "     where child.parent = idAsset    " +
                "       and child.is_increase = :increaseChild and child.status_process_current != :statusProcess ) as sum_child_increase,    " +
                "                      (select count(child.id_asset)    " +
                "     from asset child    " +
                "     where child.parent = idAsset    " +
                "       and child.is_decrease = :decreaseChild and child.status_process_current != :statusProcess ) as sum_child_decrease   " +
                "                   from asset asset    " +
                "          inner join asset_categories assetCategories    " +
                "                     on asset.id_asset_category = assetCategories.id_asset_category " +
                "          left join department de on asset.id_department = de.id_department " +
                "          left join location lo on asset.id_location = lo.id_location " +
                "          left join asset_depreciation assetDepreciation " +
                "                    on asset.id_asset = assetDepreciation.id_asset " +
                "          left join units on asset.id_unit = units.id_unit " +
                "        left join (select * " +
                "                   from fluctuating_situation_asset fsa " +
                "                   where fsa.status = :statusNotFinished " +
                "                  and fsa.type = :type) fsa on asset.id_asset = fsa.id_asset " +
                "                   where 1 = 1 " +
                "                       and asset.id_department_origin in (:idsDepartmentOriginal) " +
                "                       and (asset.status_process_current != :statusProcess or asset.status_process_current is null ) " +
                "                       and fsa.id_asset is null ") ;

        setConditionFindAllAssetDtoToInventory(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("increaseChild",Constants.IS_INCREASED);
        query.setParameter("decreaseChild", Constants.IS_DECREASED);
        query.setParameter("statusNotFinished", Constants.STATUS_FLUCTUATING_SITUATION_ASSET_NOT_FINISH);
        query.setParameter("type", Constants.TYPE_FLUCTUATING_SITUATION_INCREASE);
        setParameterFindAllAssetDtoToInventory(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        ListOrderedMap<Integer, FindAllAssetParentToInventoryDto> assetMap = new ListOrderedMap<>();
        Integer idAssetCategory, idAsset, isLeaf;
        int index = 0;
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                idAssetCategory = ValueUtil.getIntegerByObject(obj[0]);
                idAsset = obj[7] == null ? null : ValueUtil.getIntegerByObject(obj[7]);
                isLeaf = ValueUtil.getIntegerByObject(obj[24]);
                if (idAsset != null) {
                    if (!assetMap.containsKey(idAssetCategory)) {
      FindAllAssetParentToInventoryDto parentToInventoryDto = new FindAllAssetParentToInventoryDto(obj);
      assetMap.put(index,idAssetCategory,parentToInventoryDto);
      ++index;
                    }
                    if (assetMap.containsKey(idAssetCategory)){
      assetMap.computeIfPresent(idAssetCategory, (k, v) -> {
          v.getAssetLeaves().add(new FindAllAssetChildrenToInventoryDto(obj));
          return v;
      });
                    }
                } else {
                    FindAllAssetParentToInventoryDto parentToInventoryDto = new FindAllAssetParentToInventoryDto(obj);
                    assetMap.put(index,idAssetCategory,parentToInventoryDto);
                    ++index;
                }
            }
        }
        return new PageImpl<>(assetMap.valueList(), pageable, countFindAllAssetToInventory(request));
    }

    @Override
    public List<Asset> findAllAssetByIdsAsset(List<Integer> idsAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append("select id_asset, name, code_asset, id_asset_category,  " +
                "         id_document_attack, id_department, id_location,  " +
                "         id_unit, id_projects, purpose, notes, file_attack,  " +
                "         time_created, time_modified, id_department_default,  " +
                "         id_level_type_asset, id_user_created, id_user_modified,  " +
                "         description, quantity, id_instance, id_department_origin,  " +
                "         parent, salt, id_process_current, status_process_current,  " +
                "         id_type_process_current, is_increase, is_decrease, status_use,  " +
                "         year_use, acreage, sum_original_of_formation  " +
                "from asset where id_asset in (:idsAsset)  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsAsset", idsAsset);
        List<Object[]> result = query.getResultList();
        List<Asset> assets = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Asset asset = new Asset();
                asset.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
                asset.setName(ValueUtil.getStringByObject(obj[1]));
                asset.setCodeAsset(ValueUtil.getStringByObject(obj[2]));
                asset.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[3]));
                asset.setIdDocumentAttack(ValueUtil.getIntegerByObject(obj[4]));
                asset.setIdDepartment(ValueUtil.getIntegerByObject(obj[5]));
                asset.setIdLocation(ValueUtil.getIntegerByObject(obj[6]));
                asset.setIdUnit(ValueUtil.getIntegerByObject(obj[7]));
                asset.setIdProjects(ValueUtil.getIntegerByObject(obj[8]));
                asset.setPurpose(ValueUtil.getStringByObject(obj[9]));
                asset.setNotes(ValueUtil.getStringByObject(obj[10]));
                asset.setFileAttack(ValueUtil.getStringByObject(obj[11]));
                asset.setTimeCreated(ValueUtil.getStringByObject(obj[12]));
                asset.setTimeModified(ValueUtil.getStringByObject(obj[13]));
                asset.setIdDepartmentDefault(ValueUtil.getIntegerByObject(obj[14]));
                asset.setIdLevelTypeAsset(ValueUtil.getIntegerByObject(obj[15]));
                asset.setIdUserCreated(ValueUtil.getIntegerByObject(obj[16]));
                asset.setIdUserModified(ValueUtil.getIntegerByObject(obj[17]));
                asset.setDescription(ValueUtil.getStringByObject(obj[18]));
                asset.setQuantity(ValueUtil.getIntegerByObject(obj[19]));
                asset.setIdInstance(ValueUtil.getIntegerByObject(obj[20]));
                asset.setIdDepartmentOrigin(ValueUtil.getIntegerByObject(obj[21]));
                asset.setParent(ValueUtil.getIntegerByObject(obj[22]));
                asset.setSalt(ValueUtil.getStringByObject(obj[23]));
                asset.setIdProcessCurrent(ValueUtil.getIntegerByObject(obj[24]));
                asset.setStatusProcessCurrent(ValueUtil.getIntegerByObject(obj[25]));
                asset.setIdTypeProcessCurrent(ValueUtil.getIntegerByObject(obj[26]));
                asset.setIsIncrease(ValueUtil.getIntegerByObject(obj[27]));
                asset.setIsDecrease(ValueUtil.getIntegerByObject(obj[28]));
                asset.setStatusUse(ValueUtil.getIntegerByObject(obj[29]));
                asset.setYearUse(ValueUtil.getStringByObject(obj[30]));
                asset.setAcreage(ValueUtil.getDoubleByObject(obj[31]));
                asset.setSumOriginalOfFormation(ValueUtil.getStringByObject(obj[32]));
                assets.add(asset);
            }
        }
        return assets;
    }

    @Override
    public Optional<Asset> findAssetByIdAsset(Integer idAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append("select id_asset, name, code_asset, id_asset_category,  " +
                "        id_document_attack, id_department, id_location,  " +
                "        id_unit, id_projects, purpose, notes, file_attack,  " +
                "        time_created, time_modified, id_department_default,  " +
                "        id_level_type_asset, id_user_created, id_user_modified,      " +
                "        description, quantity, id_instance, id_department_origin,  " +
                "        parent, salt, id_process_current, status_process_current,  " +
                "        id_type_process_current, is_increase, is_decrease, status_use, " +
                "        year_use, acreage  " +
                "from asset where id_asset = :idAsset ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAsset", idAsset);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Asset asset = new Asset();
                asset.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
                asset.setName(ValueUtil.getStringByObject(obj[1]));
                asset.setCodeAsset(ValueUtil.getStringByObject(obj[2]));
                asset.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[3]));
                asset.setIdDocumentAttack(ValueUtil.getIntegerByObject(obj[4]));
                asset.setIdDepartment(ValueUtil.getIntegerByObject(obj[5]));
                asset.setIdLocation(ValueUtil.getIntegerByObject(obj[6]));
                asset.setIdUnit(ValueUtil.getIntegerByObject(obj[7]));
                asset.setIdProjects(ValueUtil.getIntegerByObject(obj[8]));
                asset.setPurpose(ValueUtil.getStringByObject(obj[9]));
                asset.setNotes(ValueUtil.getStringByObject(obj[10]));
                asset.setFileAttack(ValueUtil.getStringByObject(obj[11]));
                asset.setTimeCreated(ValueUtil.getStringByObject(obj[12]));
                asset.setTimeModified(ValueUtil.getStringByObject(obj[13]));
                asset.setIdDepartmentDefault(ValueUtil.getIntegerByObject(obj[14]));
                asset.setIdLevelTypeAsset(ValueUtil.getIntegerByObject(obj[15]));
                asset.setIdUserCreated(ValueUtil.getIntegerByObject(obj[16]));
                asset.setIdUserModified(ValueUtil.getIntegerByObject(obj[17]));
                asset.setDescription(ValueUtil.getStringByObject(obj[18]));
                asset.setQuantity(ValueUtil.getIntegerByObject(obj[19]));
                asset.setIdInstance(ValueUtil.getIntegerByObject(obj[20]));
                asset.setIdDepartmentOrigin(ValueUtil.getIntegerByObject(obj[21]));
                asset.setParent(ValueUtil.getIntegerByObject(obj[22]));
                asset.setSalt(ValueUtil.getStringByObject(obj[23]));
                asset.setIdProcessCurrent(ValueUtil.getIntegerByObject(obj[24]));
                asset.setStatusProcessCurrent(ValueUtil.getIntegerByObject(obj[25]));
                asset.setIdTypeProcessCurrent(ValueUtil.getIntegerByObject(obj[26]));
                asset.setIsIncrease(ValueUtil.getIntegerByObject(obj[27]));
                asset.setIsDecrease(ValueUtil.getIntegerByObject(obj[28]));
                asset.setStatusUse(ValueUtil.getIntegerByObject(obj[29]));
                asset.setYearUse(ValueUtil.getStringByObject(obj[30]));
                asset.setAcreage(ValueUtil.getDoubleByObject(obj[31]));
                return Optional.of(asset);
            }
        }
        return Optional.empty();
    }

    @Override
    public Integer countAssetIncreasedNotDecreasedByIdsAssetOrPending(List<Integer> idsAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(asset.id_asset) count  " +
                "from asset  " +
                "where id_asset in (:idsAsset)  " +
                "and ((is_increase = :isIncreased and is_decrease = :isNotDecreased)  " +
                "    or status_process_current = :isStatusPending) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsAsset", idsAsset);
        query.setParameter("isIncreased", Constants.IS_INCREASED);
        query.setParameter("isNotDecreased", Constants.IS_NOT_DECREASED);
        query.setParameter("isStatusPending", Constants.STATUS_PENDING_PROCESS);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    @Override
    public Integer countAssetByIdsAssetAndNotIncreaseOrDecreasedOrPending(List<Integer> idsAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(asset.id_asset) count   " +
                "from  asset   " +
                "where asset.id_asset in (:idsAsset)   " +
                "and (is_increase = :isNotIncrease or is_decrease = :isDecrease   " +
                "    or status_process_current = :isPending) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsAsset", idsAsset);
        query.setParameter("isNotIncrease", Constants.IS_NOT_INCREASED);
        query.setParameter("isDecrease", Constants.IS_DECREASED);
        query.setParameter("isPending", Constants.STATUS_PENDING_PROCESS);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }


    @Transactional
    @Modifying
    @Override
    public void updateAssetStatusProcessCurrentByIdProcessCurrent(Integer idProcessCurrent, Integer statusProcessCurrent) {
        StringBuilder sb = new StringBuilder();
        sb.append(" update asset set status_process_current = :statusProcessCurrent " +
                "where asset.id_process_current = :idProcessCurrent ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("statusProcessCurrent", statusProcessCurrent);
        query.setParameter("idProcessCurrent", idProcessCurrent);
        query.executeUpdate();
    }


        @Transactional
        @Modifying
    @Override
    public void updateAssetStatusProcessCurrentAndIsIncrease(Integer idProcess, Integer status, Integer isIncrease) {
        StringBuilder sb = new StringBuilder();
        sb.append("update asset  " +
                "set status_process_current = :statusProcessCurrent, " +
                "    is_increase = :isIncrease " +
                "where asset.id_process_current = :idProcessCurrent ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("statusProcessCurrent", status);
        query.setParameter("isIncrease", isIncrease);
        query.setParameter("idProcessCurrent", idProcess);
        query.executeUpdate();
    }


    @Transactional
    @Modifying
    @Override
    public void updateAssetStatusProcessCurrentAndIsDecrease(Integer idProcess, Integer status, Integer isDecrease) {
        StringBuilder sb = new StringBuilder();
        sb.append("update asset  " +
                "set status_process_current = :statusProcessCurrent, " +
                "    is_decrease = :isDecrease " +
                "where asset.id_process_current = :idProcessCurrent ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("statusProcessCurrent", status);
        query.setParameter("isDecrease", isDecrease);
        query.setParameter("idProcessCurrent", idProcess);
        query.executeUpdate();
    }

    @Override
    public Page<FindAllAssetDto> findAllAssetDtoToChange(FindAllAssetToChangeRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select asset.id_asset idAsset, asset.code_asset codeAsset,  " +
                "       asset.name nameAsset, assetCategories.id_asset_category idAssetCategory,  " +
                "       assetCategories.name nameAssetCategory, assetCategories.code_name codeAssetCategory,  " +
                "       de.id_department idDepartment, de.code codeDepartment, de.name nameDepartment,  " +
                "       lo.id_location idLocation, lo.name nameLocation,  " +
                "       asset.time_created, asset.time_modified, asset.parent, asset.salt,  " +
                "       asset.id_type_process_current,asset.status_process_current , asset.quantity, " +
                "       asset.status_use, asset.year_use  " +
                " from asset asset     " +
                "          inner join asset_categories assetCategories     " +
                "     on asset.id_asset_category = assetCategories.id_asset_category     " +
                "          left join department de on asset.id_department = de.id_department     " +
                "          left join location lo on asset.id_location = lo.id_location     " +
                "          left join asset_depreciation assetDepreciation     " +
                "     on asset.id_asset = assetDepreciation.id_asset     " +
                " where 1 = 1     " +
                "   and asset.id_department_origin in (:idsDepartmentOriginal)     " +
                "   and (asset.status_process_current != :statusProcessCurrent or asset.status_process_current is null ) ");
        setConditionFindAllAssetDtoToChange(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetDtoToChange(query, request);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllAssetDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllAssetDto findAllAssetDto = new FindAllAssetDto();
                findAllAssetDto.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
                findAllAssetDto.setCodeAsset(ValueUtil.getStringByObject(obj[1]));
                findAllAssetDto.setNameAsset(ValueUtil.getStringByObject(obj[2]));
                findAllAssetDto.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[3]));
                findAllAssetDto.setNameAssetCategory(ValueUtil.getStringByObject(obj[4]));
                findAllAssetDto.setCodeAssetCategory(ValueUtil.getStringByObject(obj[5]));
                findAllAssetDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[6]));
                findAllAssetDto.setCodeDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllAssetDto.setNameDepartment(ValueUtil.getStringByObject(obj[8]));
                findAllAssetDto.setIdLocation(ValueUtil.getIntegerByObject(obj[9]));
                findAllAssetDto.setNameLocation(ValueUtil.getStringByObject(obj[10]));
                findAllAssetDto.setTimeCreated(ValueUtil.getLongByObject(obj[11]));
                findAllAssetDto.setTimeModified(ValueUtil.getLongByObject(obj[12]));
                findAllAssetDto.setParent(ValueUtil.getIntegerByObject(obj[13]));
                findAllAssetDto.setSalt(ValueUtil.getStringByObject(obj[14]));
                findAllAssetDto.setQuantity(ValueUtil.getIntegerByObject(obj[17]));
                findAllAssetDto.setStatusUse(ValueUtil.getIntegerByObject(obj[18]));
                findAllAssetDto.setYearUse(ValueUtil.getStringByObject(obj[19]));
                responses.add(findAllAssetDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllAssetToChange(request));
    }

    @Override
    public Page<FindAllAssetDto> findAllAssetDtoToRevaluation(FindAllAssetToRevaluationRequest request,
                          Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select asset.id_asset idAsset, asset.code_asset codeAsset,    " +
                "          asset.name nameAsset, assetCategories.id_asset_category idAssetCategory,    " +
                "          assetCategories.name nameAssetCategory, assetCategories.code_name codeAssetCategory,   " +
                "          de.id_department idDepartment, de.code codeDepartment, de.name nameDepartment,    " +
                "          lo.id_location idLocation, lo.name nameLocation,    " +
                "          asset.time_created, asset.time_modified, asset.parent, asset.salt,   " +
                "          assetDepreciation.rest_value,asset.quantity,   " +
                "          asset.sum_original_of_formation assetOriginalOfFormationValue,   " +
                "          asset.status_use, asset.year_use    " +
                "from asset asset    " +
                "          inner join asset_categories assetCategories    " +
                "                     on asset.id_asset_category = assetCategories.id_asset_category    " +
                "          left join department de on asset.id_department = de.id_department    " +
                "          left join location lo on asset.id_location = lo.id_location    " +
                "          left join asset_depreciation assetDepreciation    " +
                "                    on asset.id_asset = assetDepreciation.id_asset   " +
                "                   where 1 = 1     " +
                "   and asset.id_department_origin in (:idsDepartmentOriginal)    " +
                "   and (asset.status_process_current != :statusProcessCurrent or asset.status_process_current is null )  ");
        setConditionFindAllAssetDtoToRevaluation(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetDtoToRevaluation(query, request);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllAssetDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllAssetDto findAllAssetDto = new FindAllAssetDto();
                findAllAssetDto.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
                findAllAssetDto.setCodeAsset(ValueUtil.getStringByObject(obj[1]));
                findAllAssetDto.setNameAsset(ValueUtil.getStringByObject(obj[2]));
                findAllAssetDto.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[3]));
                findAllAssetDto.setNameAssetCategory(ValueUtil.getStringByObject(obj[4]));
                findAllAssetDto.setCodeAssetCategory(ValueUtil.getStringByObject(obj[5]));
                findAllAssetDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[6]));
                findAllAssetDto.setCodeDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllAssetDto.setNameDepartment(ValueUtil.getStringByObject(obj[8]));
                findAllAssetDto.setIdLocation(ValueUtil.getIntegerByObject(obj[9]));
                findAllAssetDto.setNameLocation(ValueUtil.getStringByObject(obj[10]));
                findAllAssetDto.setTimeCreated(ValueUtil.getLongByObject(obj[11]));
                findAllAssetDto.setTimeModified(ValueUtil.getLongByObject(obj[12]));
                findAllAssetDto.setParent(ValueUtil.getIntegerByObject(obj[13]));
                findAllAssetDto.setSalt(ValueUtil.getStringByObject(obj[14]));
                findAllAssetDto.setRestValue(ValueUtil.getStringByObject(obj[15]));
                findAllAssetDto.setQuantity(ValueUtil.getIntegerByObject(obj[16]));
                findAllAssetDto.setOriginalOfFormation(ValueUtil.getStringByObject(obj[17]));
                findAllAssetDto.setStatusUse(ValueUtil.getIntegerByObject(obj[18]));
                findAllAssetDto.setYearUse(ValueUtil.getStringByObject(obj[19]));
                responses.add(findAllAssetDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllAssetToRevaluation(request));
    }

    @Override
    public Page<FindAllAssetDto> findAllAssetDtoToDecrease(FindAllAssetToDecreaseRequest decreaseRequest, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select asset.id_asset idAsset, asset.code_asset codeAsset,  " +
                "        asset.name nameAsset, assetCategories.id_asset_category idAssetCategory,  " +
                "        assetCategories.name nameAssetCategory, assetCategories.code_name codeAssetCategory,  " +
                "        de.id_department idDepartment, de.code codeDepartment, de.name nameDepartment,  " +
                "        lo.id_location idLocation, lo.name nameLocation,  " +
                "        asset.time_created, asset.time_modified, asset.parent, asset.salt,  " +
                "        assetDepreciation.rest_value,asset.quantity,  " +
                "        asset.sum_original_of_formation assetOriginalOfFormationValue,  " +
                "        assetDepreciation.cumulative, asset.status_use, asset.year_use ," +
                "       (select count(child.id_asset)    " +
                "        from asset child    " +
                "        where child.parent = idAsset    " +
                "          and child.is_increase = :increaseChild and child.status_process_current != :statusProcessCurrent ) as sum_child_increase,    " +
                "       (select count(child.id_asset)    " +
                "        from asset child    " +
                "        where child.parent = idAsset    " +
                "          and child.is_decrease = :decreaseChild and child.status_process_current != :statusProcessCurrent ) as sum_child_decrease   " +
                "from asset asset  " +
                "         inner join asset_categories assetCategories  " +
                "   on asset.id_asset_category = assetCategories.id_asset_category  " +
                "         left join department de on asset.id_department = de.id_department  " +
                "         left join location lo on asset.id_location = lo.id_location  " +
                "         left join asset_depreciation assetDepreciation  " +
                "  on asset.id_asset = assetDepreciation.id_asset  " +
                "where 1 = 1     " +
                "  and asset.id_department_origin in (:idsDepartmentOriginal)     " +
                "  and (asset.status_process_current != :statusProcessCurrent or asset.status_process_current is null)   ");
        setConditionFindAllAssetDtoToDecrease(decreaseRequest, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("increaseChild",Constants.IS_INCREASED);
        query.setParameter("decreaseChild", Constants.IS_DECREASED);
        setParameterFindAllAssetDtoToDecrease(decreaseRequest, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllAssetDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllAssetDto findAllAssetDto = new FindAllAssetDto();
                findAllAssetDto.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
                findAllAssetDto.setCodeAsset(ValueUtil.getStringByObject(obj[1]));
                findAllAssetDto.setNameAsset(ValueUtil.getStringByObject(obj[2]));
                findAllAssetDto.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[3]));
                findAllAssetDto.setNameAssetCategory(ValueUtil.getStringByObject(obj[4]));
                findAllAssetDto.setCodeAssetCategory(ValueUtil.getStringByObject(obj[5]));
                findAllAssetDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[6]));
                findAllAssetDto.setCodeDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllAssetDto.setNameDepartment(ValueUtil.getStringByObject(obj[8]));
                findAllAssetDto.setIdLocation(ValueUtil.getIntegerByObject(obj[9]));
                findAllAssetDto.setNameLocation(ValueUtil.getStringByObject(obj[10]));
                findAllAssetDto.setTimeCreated(ValueUtil.getLongByObject(obj[11]));
                findAllAssetDto.setTimeModified(ValueUtil.getLongByObject(obj[12]));
                findAllAssetDto.setParent(ValueUtil.getIntegerByObject(obj[13]));
                findAllAssetDto.setSalt(ValueUtil.getStringByObject(obj[14]));
                findAllAssetDto.setRestValue(ValueUtil.getStringByObject(obj[15]));
                findAllAssetDto.setQuantity(ValueUtil.getIntegerByObject(obj[16]));
                findAllAssetDto.setOriginalOfFormation(ValueUtil.getStringByObject(obj[17]));
                findAllAssetDto.setCumulative(ValueUtil.getStringByObject(obj[18]));
                findAllAssetDto.setStatusUse(ValueUtil.getIntegerByObject(obj[19]));
                findAllAssetDto.setYearUse(ValueUtil.getStringByObject(obj[20]));
                findAllAssetDto.setCountChildIncrease(ValueUtil.getIntegerByObject(obj[21]));
                findAllAssetDto.setCountChildDecrease(ValueUtil.getIntegerByObject(obj[22]));
                responses.add(findAllAssetDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllAssetToDecrease(decreaseRequest));
    }

    @Override
    public Page<FindAllAssetDto> findAllAssetChildrenDtoToDecrease(FindAllAssetToDecreaseRequest decreaseRequest,
                               Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select asset.id_asset idAsset, asset.code_asset codeAsset,     " +
                "        asset.name nameAsset, assetCategories.id_asset_category idAssetCategory,     " +
                "        assetCategories.name nameAssetCategory, assetCategories.code_name codeAssetCategory,     " +
                "        de.id_department idDepartment, de.code codeDepartment, de.name nameDepartment,     " +
                "        lo.id_location idLocation, lo.name nameLocation,     " +
                "        asset.time_created, asset.time_modified, asset.parent, asset.salt,     " +
                "        assetDepreciation.rest_value,asset.quantity,     " +
                "        asset.sum_original_of_formation assetOriginalOfFormationValue,     " +
                "        assetDepreciation.cumulative, asset.status_use, asset.year_use     " +
                "from asset asset     " +
                "         inner join asset_categories assetCategories     " +
                "   on asset.id_asset_category = assetCategories.id_asset_category     " +
                "         left join department de on asset.id_department = de.id_department     " +
                "         left join location lo on asset.id_location = lo.id_location     " +
                "         left join asset_depreciation assetDepreciation     " +
                "            on asset.id_asset = assetDepreciation.id_asset     " +
                "         inner join (select id_asset from asset where salt = :salt)    " +
                "             assetParent on asset.parent = assetParent.id_asset    " +
                "where 1 = 1     " +
                "  and asset.id_department_origin in (:idsDepartmentOriginal)     " +
                "  and asset.status_process_current != :statusProcess     " +
                "  and asset.is_increase = :isIncrease    " +
                "  and asset.is_decrease != :isDecrease  ");
        setConditionFindAllAssetChildrenDtoToDecrease(decreaseRequest, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetChildrenDtoToDecrease(decreaseRequest, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllAssetDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllAssetDto findAllAssetDto = new FindAllAssetDto();
                findAllAssetDto.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
                findAllAssetDto.setCodeAsset(ValueUtil.getStringByObject(obj[1]));
                findAllAssetDto.setNameAsset(ValueUtil.getStringByObject(obj[2]));
                findAllAssetDto.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[3]));
                findAllAssetDto.setNameAssetCategory(ValueUtil.getStringByObject(obj[4]));
                findAllAssetDto.setCodeAssetCategory(ValueUtil.getStringByObject(obj[5]));
                findAllAssetDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[6]));
                findAllAssetDto.setCodeDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllAssetDto.setNameDepartment(ValueUtil.getStringByObject(obj[8]));
                findAllAssetDto.setIdLocation(ValueUtil.getIntegerByObject(obj[9]));
                findAllAssetDto.setNameLocation(ValueUtil.getStringByObject(obj[10]));
                findAllAssetDto.setTimeCreated(ValueUtil.getLongByObject(obj[11]));
                findAllAssetDto.setTimeModified(ValueUtil.getLongByObject(obj[12]));
                findAllAssetDto.setParent(ValueUtil.getIntegerByObject(obj[13]));
                findAllAssetDto.setSalt(ValueUtil.getStringByObject(obj[14]));
                findAllAssetDto.setRestValue(ValueUtil.getStringByObject(obj[15]));
                findAllAssetDto.setQuantity(ValueUtil.getIntegerByObject(obj[16]));
                findAllAssetDto.setOriginalOfFormation(ValueUtil.getStringByObject(obj[17]));
                findAllAssetDto.setCumulative(ValueUtil.getStringByObject(obj[18]));
                findAllAssetDto.setStatusUse(ValueUtil.getIntegerByObject(obj[19]));
                findAllAssetDto.setYearUse(ValueUtil.getStringByObject(obj[20]));
                responses.add(findAllAssetDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllAssetChildrenToDecrease(decreaseRequest));
    }

    @Override
    public Page<FindAllAssetDto> findAllAssetChildrenDtoToInventory(FindAllAssetToInventoryRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select asset.id_asset idAsset, asset.code_asset codeAsset,   " +
                "       asset.name nameAsset, assetCategories.id_asset_category idAssetCategory,   " +
                "       assetCategories.name nameAssetCategory, assetCategories.code_name codeAssetCategory,   " +
                "       de.id_department idDepartment, de.code codeDepartment, de.name nameDepartment,   " +
                "       lo.id_location idLocation, lo.name nameLocation,   " +
                "       asset.time_created, asset.time_modified, asset.parent, asset.salt,   " +
                "       assetDepreciation.rest_value,asset.quantity,   " +
                "       asset.sum_original_of_formation assetOriginalOfFormationValue,   " +
                "       asset.status_use, asset.year_use,assetCategories.type_target,asset.acreage,units.name,asset.is_increase   " +
                "from asset asset   " +
                "        inner join asset_categories assetCategories   " +
                "                   on asset.id_asset_category = assetCategories.id_asset_category   " +
                "        inner join department de on asset.id_department = de.id_department   " +
                "        inner join  units on asset.id_unit = units.id_unit   " +
                "        left join location lo on asset.id_location = lo.id_location   " +
                "        left join asset_depreciation assetDepreciation   " +
                "                  on asset.id_asset = assetDepreciation.id_asset   " +
                "        inner join (select id_asset from asset where salt = :salt)   " +
                "         assetParent on asset.parent = assetParent.id_asset   " +
                "where 1 = 1    " +
                " and asset.id_department_origin in (:idsDepartmentOriginal)     " +
                " and asset.status_process_current != :statusProcess    " +
                " and asset.is_increase = :isIncrease     " +
                " and asset.is_decrease != :isDecrease  ");
        setConditionFindAllAssetChildrenDtoToInventory(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetChildrenDtoToInventory(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllAssetDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllAssetDto findAllAssetDto = new FindAllAssetDto();
                findAllAssetDto.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
                findAllAssetDto.setCodeAsset(ValueUtil.getStringByObject(obj[1]));
                findAllAssetDto.setNameAsset(ValueUtil.getStringByObject(obj[2]));
                findAllAssetDto.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[3]));
                findAllAssetDto.setNameAssetCategory(ValueUtil.getStringByObject(obj[4]));
                findAllAssetDto.setCodeAssetCategory(ValueUtil.getStringByObject(obj[5]));
                findAllAssetDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[6]));
                findAllAssetDto.setCodeDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllAssetDto.setNameDepartment(ValueUtil.getStringByObject(obj[8]));
                findAllAssetDto.setIdLocation(ValueUtil.getIntegerByObject(obj[9]));
                findAllAssetDto.setNameLocation(ValueUtil.getStringByObject(obj[10]));
                findAllAssetDto.setTimeCreated(ValueUtil.getLongByObject(obj[11]));
                findAllAssetDto.setTimeModified(ValueUtil.getLongByObject(obj[12]));
                findAllAssetDto.setParent(ValueUtil.getIntegerByObject(obj[13]));
                findAllAssetDto.setSalt(ValueUtil.getStringByObject(obj[14]));
                findAllAssetDto.setRestValue(ValueUtil.getStringByObject(obj[15]));
                findAllAssetDto.setQuantity(ValueUtil.getIntegerByObject(obj[16]));
                findAllAssetDto.setOriginalOfFormation(ValueUtil.getStringByObject(obj[17]));
                findAllAssetDto.setStatusUse(ValueUtil.getIntegerByObject(obj[18]));
                findAllAssetDto.setYearUse(ValueUtil.getStringByObject(obj[19]));
                findAllAssetDto.setTypeTarget(ValueUtil.getIntegerByObject(obj[20]));
                findAllAssetDto.setAcreage(ValueUtil.getDoubleByObject(obj[21]));
                findAllAssetDto.setUnit(ValueUtil.getStringByObject(obj[22]));
                findAllAssetDto.setIsIncrease(ValueUtil.getIntegerByObject(obj[23]));
                responses.add(findAllAssetDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllAssetChildrenToInventory(request));
    }

    @Override
    public Page<FindAllAssetDto> findAllAssetChildrenDtoToRevaluation(FindAllAssetToRevaluationRequest request,
                Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select asset.id_asset idAsset, asset.code_asset codeAsset,  " +
                "        asset.name nameAsset, assetCategories.id_asset_category idAssetCategory,  " +
                "        assetCategories.name nameAssetCategory, assetCategories.code_name codeAssetCategory,  " +
                "        de.id_department idDepartment, de.code codeDepartment, de.name nameDepartment,  " +
                "        lo.id_location idLocation, lo.name nameLocation,   " +
                "        asset.time_created, asset.time_modified, asset.parent, asset.salt,   " +
                "        assetDepreciation.rest_value,asset.quantity,   " +
                "        asset.sum_original_of_formation assetOriginalOfFormationValue,  " +
                "        asset.status_use, asset.year_use   " +
                "from asset asset   " +
                "         inner join asset_categories assetCategories   " +
                "    on asset.id_asset_category = assetCategories.id_asset_category   " +
                "         inner join department de on asset.id_department = de.id_department   " +
                "         left join location lo on asset.id_location = lo.id_location   " +
                "         left join asset_depreciation assetDepreciation   " +
                "   on asset.id_asset = assetDepreciation.id_asset   " +
                "         inner join (select id_asset from asset where salt = :salt)   " +
                "          assetParent on asset.parent = assetParent.id_asset   " +
                "  where 1 = 1   " +
                "  and asset.id_department_origin in (:idsDepartmentOriginal)   " +
                "  and asset.status_process_current != :statusProcess  " +
                " and asset.is_increase = :isIncrease   " +
                " and asset.is_decrease != :isDecrease  ");
        setConditionFindAllAssetChildrenDtoToRevaluation(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetChildrenDtoToRevaluation(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllAssetDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllAssetDto findAllAssetDto = new FindAllAssetDto();
                findAllAssetDto.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
                findAllAssetDto.setCodeAsset(ValueUtil.getStringByObject(obj[1]));
                findAllAssetDto.setNameAsset(ValueUtil.getStringByObject(obj[2]));
                findAllAssetDto.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[3]));
                findAllAssetDto.setNameAssetCategory(ValueUtil.getStringByObject(obj[4]));
                findAllAssetDto.setCodeAssetCategory(ValueUtil.getStringByObject(obj[5]));
                findAllAssetDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[6]));
                findAllAssetDto.setCodeDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllAssetDto.setNameDepartment(ValueUtil.getStringByObject(obj[8]));
                findAllAssetDto.setIdLocation(ValueUtil.getIntegerByObject(obj[9]));
                findAllAssetDto.setNameLocation(ValueUtil.getStringByObject(obj[10]));
                findAllAssetDto.setTimeCreated(ValueUtil.getLongByObject(obj[11]));
                findAllAssetDto.setTimeModified(ValueUtil.getLongByObject(obj[12]));
                findAllAssetDto.setParent(ValueUtil.getIntegerByObject(obj[13]));
                findAllAssetDto.setSalt(ValueUtil.getStringByObject(obj[14]));
                findAllAssetDto.setRestValue(ValueUtil.getStringByObject(obj[15]));
                findAllAssetDto.setQuantity(ValueUtil.getIntegerByObject(obj[16]));
                findAllAssetDto.setOriginalOfFormation(ValueUtil.getStringByObject(obj[17]));
                responses.add(findAllAssetDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllAssetChildrenToRevaluation(request));
    }

    @Override
    public StatisticsAssetFindAllResponse getStatisticFindAllAsset() {
        StringBuilder sb = new StringBuilder();
        sb.append("select sum(totalSingle) as totalSingle,  " +
                "       sum(totalDistribution) as totalDistribution,  " +
                "       sum(totalLot) as totalLot  " +
                " from(  " +
                " select count(0) as totalSingle, 0 as totalDistribution, 0 as totalLot from asset  " +
                "    where asset.parent is null and asset.quantity = :isSingle  " +
                "    and asset.id_department_origin in (:idsDepartmentOriginal)  " +
                " union all  " +
                " select 0 as totalSingle,count(0) as totalDistribution, 0 as totalLot  from asset  " +
                " where asset.parent is not null and asset.quantity = :isSingle  " +
                "  and asset.id_department_origin in (:idsDepartmentOriginal)  " +
                " union all  " +
                " select 0 as totalSingle,0 as totalDistribution, count(0) as totalLot from asset  " +
                " where asset.parent is null and  asset.quantity != :isSingle  " +
                "  and asset.id_department_origin in (:idsDepartmentOriginal)) result ");
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("isSingle", Constants.QUANTITY_DEFAULT);
        query.setParameter("idsDepartmentOriginal", csvcUser.getIdsDepartmentCurrent());
        List<Object[]> result = query.getResultList();
        StatisticsAssetFindAllResponse response = new StatisticsAssetFindAllResponse();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                response.setTotalSingle(ValueUtil.getIntegerByObject(obj[0]));
                response.setTotalDistribution(ValueUtil.getIntegerByObject(obj[1]));
                response.setTotalLot(ValueUtil.getIntegerByObject(obj[2]));
            }
        }
        return response;
    }

    @Override
    public StatisticsAssetAndUserFindAllResponse getStatisticFindAllAssetInCategoryAndUser() {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE category_ground AS (    " +
                "   SELECT asset_categories.id_asset_category    " +
                "    FROM asset_categories    " +
                "    WHERE asset_categories.code_name = :codeGround    " +
                "    UNION ALL    " +
                "    SELECT ac.id_asset_category    " +
                "    FROM asset_categories ac    " +
                "    INNER JOIN category_ground ct ON ac.parent = ct.id_asset_category),    " +
                "category_house AS (    " +
                "    SELECT asset_categories.id_asset_category    " +
                "    FROM asset_categories    " +
                "    WHERE asset_categories.code_name = :codeHouse    " +
                "    UNION ALL    " +
                "    SELECT ac.id_asset_category    " +
                "    FROM asset_categories ac    " +
                "    INNER JOIN category_house ct ON ac.parent = ct.id_asset_category),    " +
                "category_architecture AS (    " +
                "    SELECT asset_categories.id_asset_category    " +
                "    FROM asset_categories    " +
                "    WHERE asset_categories.code_name = :codeArchitecture    " +
                "    UNION ALL    " +
                "    SELECT ac.id_asset_category    " +
                "    FROM asset_categories ac    " +
                "    INNER JOIN category_architecture ct ON ac.parent = ct.id_asset_category),    " +
                "category_car AS (    " +
                "    SELECT asset_categories.id_asset_category    " +
                "    FROM asset_categories    " +
                "    WHERE asset_categories.code_name = :codeCar    " +
                "    UNION ALL    " +
                "    SELECT ac.id_asset_category    " +
                "    FROM asset_categories ac    " +
                "    INNER JOIN category_car ct ON ac.parent = ct.id_asset_category),    " +
                "category_other_vehicle_transport AS (    " +
                "    SELECT asset_categories.id_asset_category    " +
                "    FROM asset_categories    " +
                "    WHERE asset_categories.code_name = :codeOtherVehicleTransport    " +
                "    UNION ALL    " +
                "    SELECT ac.id_asset_category    " +
                "    FROM asset_categories ac    " +
                "    INNER JOIN category_other_vehicle_transport ct ON ac.parent = ct.id_asset_category),    " +
                "category_machine AS (    " +
                "    SELECT asset_categories.id_asset_category    " +
                "    FROM asset_categories    " +
                "    WHERE asset_categories.code_name = :codeMachine    " +
                "    UNION ALL    " +
                "    SELECT ac.id_asset_category    " +
                "    FROM asset_categories ac    " +
                "    INNER JOIN category_machine ct ON ac.parent = ct.id_asset_category),    " +
                "category_tree_and_animal AS (    " +
                "    SELECT asset_categories.id_asset_category    " +
                "    FROM asset_categories    " +
                "    WHERE asset_categories.code_name = :codeTreeAndAnimal    " +
                "    UNION ALL    " +
                "    SELECT ac.id_asset_category    " +
                "    FROM asset_categories ac    " +
                "    INNER JOIN category_tree_and_animal ct ON ac.parent = ct.id_asset_category),    " +
                "category_other_asset AS (    " +
                "    SELECT asset_categories.id_asset_category    " +
                "    FROM asset_categories    " +
                "    WHERE asset_categories.code_name = :codeOtherAsset    " +
                "    UNION ALL    " +
                "    SELECT ac.id_asset_category    " +
                "    FROM asset_categories ac    " +
                "    INNER JOIN category_other_asset ct ON ac.parent = ct.id_asset_category),    " +
                "category_invisible_asset AS (    " +
                "    SELECT asset_categories.id_asset_category    " +
                "    FROM asset_categories    " +
                "    WHERE asset_categories.code_name = :codeTSCDVH    " +
                "    UNION ALL    " +
                "    SELECT ac.id_asset_category    " +
                "    FROM asset_categories ac    " +
                "    INNER JOIN category_invisible_asset ct ON ac.parent = ct.id_asset_category),    " +
                "category_invisible_asset_special AS (    " +
                "    SELECT asset_categories.id_asset_category    " +
                "    FROM asset_categories    " +
                "    WHERE asset_categories.code_name = :codeSpecialAsset    " +
                "    UNION ALL    " +
                "    SELECT ac.id_asset_category    " +
                "    FROM asset_categories ac    " +
                "    INNER JOIN category_invisible_asset_special ct ON ac.parent = ct.id_asset_category),    " +
                "category_construction AS (    " +
                "    SELECT asset_categories.id_asset_category    " +
                "    FROM asset_categories    " +
                "    WHERE asset_categories.code_name = :codeConstruction    " +
                "    UNION ALL    " +
                "    SELECT ac.id_asset_category    " +
                "    FROM asset_categories ac    " +
                "    INNER JOIN category_construction ct ON ac.parent = ct.id_asset_category),    " +
                "category_electric_construction AS (    " +
                "    SELECT asset_categories.id_asset_category    " +
                "    FROM asset_categories    " +
                "    WHERE asset_categories.code_name = :codeElectricConstruction    " +
                "    UNION ALL    " +
                "    SELECT ac.id_asset_category    " +
                "    FROM asset_categories ac    " +
                "    INNER JOIN category_electric_construction ct ON ac.parent = ct.id_asset_category)    " +
                "select sum(TotalAsset) as TotalAsset,sum(totalUser) as totalUser,sum(totalGround) as totalGround,sum(totalHouse) as totalHouse,sum(totalArchitecture) as totalArchitecture,    " +
                "       sum(totalCar) as totalCar, sum(totalOtherVehicleTransport) as totalOtherVehicleTransport,sum(totalMachine) as totalMachine,sum(totalTreeAndAnimal) as totalTreeAndAnimal,    " +
                "       sum(totalOtherAsset) as totalOtherAsset,sum(totalInvisibleAsset) as totalInvisibleAsset,sum(totalInvisibleAssetSpecial) as totalInvisibleAssetSpecial,sum(totalConstruction) as totalConstruction, sum(totalElectricConstruction) as totalElectricConstruction    " +
                "from (    " +
                "select count(0) as TotalAsset,0 as totalUser,0 as totalGround,0 as totalHouse,0 as totalArchitecture,    " +
                "       0 as totalCar, 0 as totalOtherVehicleTransport,0 as totalMachine,0 as totalTreeAndAnimal,    " +
                "       0 as totalOtherAsset,0 as totalInvisibleAsset,0 as totalInvisibleAssetSpecial,0 as totalConstruction, 0 as totalElectricConstruction    " +
                "from asset    " +
                "where asset.quantity = :quantityDefault    " +
                " and asset.id_department_origin in (:idsDepartmentOriginal)    " +
                "union all    " +
                "select 0 as TotalAsset,count(0) as totalUser,0 as totalGround,0 as totalHouse,0 as totalArchitecture,    " +
                "       0 as totalCar, 0 as totalOtherVehicleTransport,0 as totalMachine,0 as totalTreeAndAnimal,    " +
                "       0 as totalOtherAsset,0 as totalInvisibleAsset,0 as totalInvisibleAssetSpecial,0 as totalConstruction, 0 as totalElectricConstruction    " +
                "from csvc_user inner join user_role ur on  csvc_user.id_user=ur.id_user   " +
                "where ur.id_department in (:idsDepartmentOriginal)    " +
                "union all    " +
                "select 0 as TotalAsset,0 as totalUser,count(0) as totalGround,0 as totalHouse,0 as totalArchitecture,    " +
                "       0 as totalCar, 0 as totalOtherVehicleTransport,0 as totalMachine,0 as totalTreeAndAnimal,    " +
                "       0 as totalOtherAsset,0 as totalInvisibleAsset,0 as totalInvisibleAssetSpecial,0 as totalConstruction, 0 as totalElectricConstruction    " +
                "FROM asset    " +
                "WHERE asset.id_asset_category IN (SELECT id_asset_category FROM category_ground) and asset.quantity = :quantityDefault    " +
                "and asset.id_department_origin in (:idsDepartmentOriginal)    " +
                "union all    " +
                "select 0 as TotalAsset,0 as totalUser,0 as totalGround,count(0) as totalHouse,0 as totalArchitecture,    " +
                "       0 as totalCar, 0 as totalOtherVehicleTransport,0 as totalMachine,0 as totalTreeAndAnimal,    " +
                "       0 as totalOtherAsset,0 as totalInvisibleAsset,0 as totalInvisibleAssetSpecial,0 as totalConstruction, 0 as totalElectricConstruction    " +
                "FROM asset    " +
                "WHERE asset.id_asset_category IN (SELECT id_asset_category FROM category_house) and asset.quantity = :quantityDefault    " +
                "and asset.id_department_origin in (:idsDepartmentOriginal)    " +
                "union all    " +
                "select 0 as TotalAsset,0 as totalUser,0 as totalGround,0 as totalHouse,count(0) as totalArchitecture,    " +
                "       0 as totalCar, 0 as totalOtherVehicleTransport,0 as totalMachine,0 as totalTreeAndAnimal,    " +
                "       0 as totalOtherAsset,0 as totalInvisibleAsset,0 as totalInvisibleAssetSpecial,0 as totalConstruction, 0 as totalElectricConstruction    " +
                "FROM asset    " +
                "WHERE asset.id_asset_category IN (SELECT id_asset_category FROM category_architecture) and asset.quantity = :quantityDefault    " +
                "and asset.id_department_origin in (:idsDepartmentOriginal)    " +
                "union all    " +
                "select 0 as TotalAsset,0 as totalUser,0 as totalGround,0 as totalHouse,0 as totalArchitecture,    " +
                "       count(0) as totalCar, 0 as totalOtherVehicleTransport,0 as totalMachine,0 as totalTreeAndAnimal,    " +
                "       0 as totalOtherAsset,0 as totalInvisibleAsset,0 as totalInvisibleAssetSpecial,0 as totalConstruction, 0 as totalElectricConstruction    " +
                "FROM asset    " +
                "WHERE asset.id_asset_category IN (SELECT id_asset_category FROM category_car) and asset.quantity = :quantityDefault    " +
                "and asset.id_department_origin in (:idsDepartmentOriginal)    " +
                "union all    " +
                "select 0 as TotalAsset,0 as totalUser,0 as totalGround,0 as totalHouse,0 as totalArchitecture,    " +
                "       0 as totalCar, count(0) as totalOtherVehicleTransport,0 as totalMachine,0 as totalTreeAndAnimal,    " +
                "       0 as totalOtherAsset,0 as totalInvisibleAsset,0 as totalInvisibleAssetSpecial,0 as totalConstruction, 0 as totalElectricConstruction    " +
                "FROM asset    " +
                "WHERE asset.id_asset_category IN (SELECT id_asset_category FROM category_other_vehicle_transport) and asset.quantity = :quantityDefault    " +
                "and asset.id_department_origin in (:idsDepartmentOriginal)    " +
                "union all    " +
                "select 0 as TotalAsset,0 as totalUser,0 as totalGround,0 as totalHouse,0 as totalArchitecture,    " +
                "       0 as totalCar, 0 as totalOtherVehicleTransport, count(0) as totalMachine,0 as totalTreeAndAnimal,    " +
                "       0 as totalOtherAsset,0 as totalInvisibleAsset,0 as totalInvisibleAssetSpecial,0 as totalConstruction, 0 as totalElectricConstruction    " +
                "FROM asset    " +
                "WHERE asset.id_asset_category IN (SELECT id_asset_category FROM category_machine) and asset.quantity = :quantityDefault    " +
                "and asset.id_department_origin in (:idsDepartmentOriginal)    " +
                "union all    " +
                "select 0 as TotalAsset,0 as totalUser,0 as totalGround,0 as totalHouse,0 as totalArchitecture,    " +
                "       0 as totalCar, 0 as totalOtherVehicleTransport,0 as totalMachine,count(0) as totalTreeAndAnimal,    " +
                "       0 as totalOtherAsset,0 as totalInvisibleAsset,0 as totalInvisibleAssetSpecial,0 as totalConstruction, 0 as totalElectricConstruction    " +
                "FROM asset    " +
                "WHERE asset.id_asset_category IN (SELECT id_asset_category FROM category_tree_and_animal) and asset.quantity = :quantityDefault    " +
                "and asset.id_department_origin in (:idsDepartmentOriginal)    " +
                "union all    " +
                "select 0 as TotalAsset,0 as totalUser,0 as totalGround,0 as totalHouse,0 as totalArchitecture,    " +
                "       0 as totalCar, 0 as totalOtherVehicleTransport,0 as totalMachine,0 as totalTreeAndAnimal,    " +
                "       count(0) as totalOtherAsset,0 as totalInvisibleAsset,0 as totalInvisibleAssetSpecial,0 as totalConstruction, 0 as totalElectricConstruction    " +
                "FROM asset    " +
                "WHERE asset.id_asset_category IN (SELECT id_asset_category FROM category_other_asset) and asset.quantity = :quantityDefault    " +
                "and asset.id_department_origin in (:idsDepartmentOriginal)    " +
                "union all    " +
                "select 0 as TotalAsset,0 as totalUser,0 as totalGround,0 as totalHouse,0 as totalArchitecture,    " +
                "       0 as totalCar, 0 as totalOtherVehicleTransport,0 as totalMachine,0 as totalTreeAndAnimal,    " +
                "       0 as totalOtherAsset, count(0) as totalInvisibleAsset,0 as totalInvisibleAssetSpecial,0 as totalConstruction, 0 as totalElectricConstruction    " +
                "FROM asset    " +
                "WHERE asset.id_asset_category IN (SELECT id_asset_category FROM category_invisible_asset) and asset.quantity = :quantityDefault    " +
                "and asset.id_department_origin in (:idsDepartmentOriginal)    " +
                "union all    " +
                "select 0 as TotalAsset,0 as totalUser,0 as totalGround,0 as totalHouse,0 as totalArchitecture,    " +
                "       0 as totalCar, 0 as totalOtherVehicleTransport,0 as totalMachine,0 as totalTreeAndAnimal,    " +
                "       0 as totalOtherAsset,0 as totalInvisibleAsset,count(0) as totalInvisibleAssetSpecial,0 as totalConstruction, 0 as totalElectricConstruction    " +
                "FROM asset    " +
                "WHERE asset.id_asset_category IN (SELECT id_asset_category FROM category_invisible_asset_special) and asset.quantity = :quantityDefault    " +
                "and asset.id_department_origin in (:idsDepartmentOriginal)    " +
                "union all    " +
                "select 0 as TotalAsset,0 as totalUser,0 as totalGround,0 as totalHouse,0 as totalArchitecture,    " +
                "       0 as totalCar, 0 as totalOtherVehicleTransport,0 as totalMachine,0 as totalTreeAndAnimal,    " +
                "       0 as totalOtherAsset,0 as totalInvisibleAsset,0 as totalInvisibleAssetSpecial,count(0) as totalConstruction, 0 as totalElectricConstruction    " +
                "FROM asset    " +
                "WHERE asset.id_asset_category IN (SELECT id_asset_category FROM category_construction) and asset.quantity = :quantityDefault    " +
                "and asset.id_department_origin in (:idsDepartmentOriginal)    " +
                "union all    " +
                "select 0 as TotalAsset,0 as totalUser,0 as totalGround,0 as totalHouse,0 as totalArchitecture,    " +
                "       0 as totalCar, 0 as totalOtherVehicleTransport,0 as totalMachine,0 as totalTreeAndAnimal,    " +
                "       0 as totalOtherAsset,0 as totalInvisibleAsset,0 as totalInvisibleAssetSpecial,0 as totalConstruction,count(0) as totalElectricConstruction    " +
                "FROM asset    " +
                "WHERE asset.id_asset_category IN (SELECT id_asset_category FROM category_electric_construction) and asset.quantity = :quantityDefault    " +
                "and asset.id_department_origin in (:idsDepartmentOriginal)    " +
                ")  resultDashboard     ");
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("quantityDefault", Constants.QUANTITY_DEFAULT);
        query.setParameter("idsDepartmentOriginal", csvcUser.getIdsDepartmentCurrent());
        query.setParameter("codeGround", Constants.CODE_NAME_GROUND);
        query.setParameter("codeHouse", Constants.CODE_NAME_HOUSE);
        query.setParameter("codeArchitecture", Constants.CODE_NAME_ARCHITECTURE);
        query.setParameter("codeCar", Constants.CODE_NAME_CAR);
        query.setParameter("codeOtherVehicleTransport", Constants.CODE_NAME_OTHER_VEHICLE_TRANSPORT);
        query.setParameter("codeMachine", Constants.CODE_NAME_MACHINE);
        query.setParameter("codeTreeAndAnimal", Constants.CODE_NAME_TREE_AND_ANIMAL);
        query.setParameter("codeOtherAsset", Constants.CODE_NAME_OTHER_ASSET);
        query.setParameter("codeTSCDVH", Constants.CODE_NAME_NO_SHAPE);
        query.setParameter("codeSpecialAsset", Constants.CODE_NAME_SPECIAL_ASSET);
        query.setParameter("codeConstruction", Constants.CODE_NAME_CONSTRUCTION);
        query.setParameter("codeElectricConstruction", Constants.CODE_NAME_ELECTRIC_CONSTRUCTION);
        List<Object[]> result = query.getResultList();
        StatisticsAssetAndUserFindAllResponse response = new StatisticsAssetAndUserFindAllResponse();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                response.setTotalAsset(ValueUtil.getIntegerByObject(obj[0]));
                response.setTotalUser(ValueUtil.getIntegerByObject(obj[1]));
                response.setTotalGround(ValueUtil.getIntegerByObject(obj[2]));
                response.setTotalHouse(ValueUtil.getIntegerByObject(obj[3]));
                response.setTotalArchitecture(ValueUtil.getIntegerByObject(obj[4]));
                response.setTotalCar(ValueUtil.getIntegerByObject(obj[5]));
                response.setTotalOtherVehicleTransport(ValueUtil.getIntegerByObject(obj[6]));
                response.setTotalMachine(ValueUtil.getIntegerByObject(obj[7]));
                response.setTotalTreeAndAnimal(ValueUtil.getIntegerByObject(obj[8]));
                response.setTotalOtherAsset(ValueUtil.getIntegerByObject(obj[9]));
                response.setTotalInvisibleAsset(ValueUtil.getIntegerByObject(obj[10]));
                response.setTotalInvisibleAssetSpecial(ValueUtil.getIntegerByObject(obj[11]));
                response.setTotalConstruction(ValueUtil.getIntegerByObject(obj[12]));
                response.setTotalElectricConstruction(ValueUtil.getIntegerByObject(obj[13]));
            }
        }
        return response;
    }

    @Override
    public StatisticsAssetCategoryStatusUse getStatisticFindAllAssetCategoryStatusUse(String codeName) {
        StringBuilder sb = new StringBuilder();
        sb.append("     " +
                "WITH RECURSIVE category_tree AS (     " +
                "     SELECT asset_categories.id_asset_category             " +
                "      FROM asset_categories             " +
                "      WHERE asset_categories.code_name = :codeCategory     " +
                "      UNION ALL             " +
                "      SELECT ac.id_asset_category             " +
                "      FROM asset_categories ac             " +
                "      INNER JOIN category_tree ct ON ac.parent = ct.id_asset_category)     " +
                "select sum(totalNotUsing),sum(totalUsing), sum(totalDecrease) from (     " +
                "select count(0) as totalNotUsing,0 as totalUsing, 0 as totalDecrease from asset     " +
                "where   asset.quantity = :quantityDefault and asset.is_increase != :inCrease and asset.is_decrease != :inDecrease     " +
                "and asset.id_asset_category IN (SELECT id_asset_category FROM category_tree) and asset.id_department_origin in (:idsDepartmentOriginal)     " +
                "union all     " +
                "select 0 as totalNotUsing,count(0) as totalUsing, 0 as totalDecrease from asset     " +
                "where  asset.quantity = :quantityDefault and asset.is_increase = :inCrease and asset.is_decrease != :inDecrease     " +
                "  and asset.id_asset_category IN (SELECT id_asset_category FROM category_tree) and asset.id_department_origin in (:idsDepartmentOriginal)     " +
                "union all     " +
                "select 0 as totalNotUsing,0 as totalUsing, count(0) as totalDecrease from asset     " +
                "where   asset.quantity = :quantityDefault and asset.is_increase = :inCrease and asset.is_decrease = :inDecrease     " +
                "  and asset.id_asset_category IN (SELECT id_asset_category FROM category_tree) and asset.id_department_origin in (:idsDepartmentOriginal) ) results ");
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("codeCategory", codeName);
        query.setParameter("quantityDefault", Constants.QUANTITY_DEFAULT);
        query.setParameter("idsDepartmentOriginal", csvcUser.getIdsDepartmentCurrent());
        query.setParameter("inCrease",Constants.IS_INCREASED);
        query.setParameter("inDecrease",Constants.IS_DECREASED);
        List<Object[]> result = query.getResultList();
        StatisticsAssetCategoryStatusUse response = new StatisticsAssetCategoryStatusUse();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                response.setTotalNotUsing(ValueUtil.getIntegerByObject(obj[0]));
                response.setTotalUsing(ValueUtil.getIntegerByObject(obj[1]));
                response.setTotalDecrease(ValueUtil.getIntegerByObject(obj[2]));
            }
        }
        return response;
    }

    private long countFindAllAssetToDecrease(FindAllAssetToDecreaseRequest decreaseRequest) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0)  " +
                "from asset asset  " +
                "         inner join asset_categories assetCategories  " +
                "   on asset.id_asset_category = assetCategories.id_asset_category  " +
                "         left join department de on asset.id_department = de.id_department  " +
                "         left join location lo on asset.id_location = lo.id_location  " +
                "         left join asset_depreciation assetDepreciation  " +
                "  on asset.id_asset = assetDepreciation.id_asset  " +
                "where 1 = 1  " +
                "  and asset.id_department_origin in (:idsDepartmentOriginal)     " +
                "  and (asset.status_process_current != :statusProcessCurrent or asset.status_process_current is null)   ");
        setConditionCountFindAllAssetDtoToDecrease(decreaseRequest, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterCountFindAllAssetDtoToDecrease(decreaseRequest, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private long countFindAllAssetChildrenToDecrease(FindAllAssetToDecreaseRequest decreaseRequest) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0)   " +
                "from asset asset   " +
                "         inner join asset_categories assetCategories      " +
                "   on asset.id_asset_category = assetCategories.id_asset_category      " +
                "         left join department de on asset.id_department = de.id_department      " +
                "         left join location lo on asset.id_location = lo.id_location   " +
                "         left join asset_depreciation assetDepreciation      " +
                "            on asset.id_asset = assetDepreciation.id_asset   " +
                "         inner join (select id_asset from asset where salt = :salt)   " +
                "             assetParent on asset.parent = assetParent.id_asset     " +
                "where 1 = 1   " +
                "  and asset.id_department_origin in (:idsDepartmentOriginal)      " +
                "  and asset.status_process_current != :statusProcess " +
                "  and asset.is_increase = :isIncrease " +
                "  and asset.is_decrease != :isDecrease ");
        setConditionCountFindAllAssetChildrenDtoToDecrease(decreaseRequest, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetChildrenDtoToDecrease(decreaseRequest, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }


    private void setParameterCountFindAllAssetDtoToDecrease(FindAllAssetToDecreaseRequest request,
                        Query query) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("statusProcessCurrent", Constants.STATUS_PENDING_PROCESS);
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE)
                || request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)) {
            query.setParameter("isIncrease", Constants.IS_INCREASED);
            query.setParameter("isDecrease", Constants.IS_DECREASED);
            query.setParameter("quantityDefault", Constants.QUANTITY_DEFAULT);
        }
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)) {
            query.setParameter("isIncrease", Constants.IS_INCREASED_WHOLE_LOT);
            query.setParameter("isIncreasePart", Constants.IS_INCREASED_PART_LOT);
            query.setParameter("isDecrease", Constants.IS_DECREASED_WHOLE_LOT);
            query.setParameter("quantityDefault", Constants.QUANTITY_DEFAULT);
        }
        if (StringUtils.isNotBlank(request.getNameAsset())){
            query.setParameter("nameAsset", request.getNameAsset());
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            query.setParameter("idAssetCategory", request.getIdAssetCategory());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            query.setParameter("idDepartment", request.getIdDepartment());
        }
    }

    private void setConditionCountFindAllAssetDtoToDecrease(FindAllAssetToDecreaseRequest request,
                        StringBuilder sb) {
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE)){
            sb.append(" and asset.is_increase = :isIncrease  " +
                    "  and asset.is_decrease != :isDecrease  " +
                    "  and asset.quantity = :quantityDefault and asset.parent is null ");
        } else if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)){
            sb.append("   and (((asset.is_increase = :isIncrease or  " +
                    "      asset.is_increase = :isIncreasePart) and  " +
                    "     asset.is_decrease != :isDecrease))  " +
                    "  and asset.quantity > :quantityDefault and asset.parent is null ");
        } else if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_ALLOCATE)) {
            sb.append(" and asset.is_increase = :isIncrease  " +
                    "  and asset.is_decrease != :isDecrease  " +
                    "  and asset.quantity = :quantityDefault and asset.parent is not null ");
        }
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())) {
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())) {
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY asset.id_asset desc  ");
        }
    }

    private void setConditionCountFindAllAssetChildrenDtoToDecrease(FindAllAssetToDecreaseRequest request,
                        StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())) {
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())) {
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY asset.id_asset desc ");
        }
    }


    private void setParameterFindAllAssetDtoToDecrease(FindAllAssetToDecreaseRequest request, Query query) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("statusProcessCurrent", Constants.STATUS_PENDING_PROCESS);
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE)
            || request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_ALLOCATE)) {
            query.setParameter("isIncrease", Constants.IS_INCREASED);
            query.setParameter("isDecrease", Constants.IS_DECREASED);
            query.setParameter("quantityDefault", Constants.QUANTITY_DEFAULT);
        }
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)) {
                query.setParameter("isIncrease", Constants.IS_INCREASED_WHOLE_LOT);
                query.setParameter("isIncreasePart", Constants.IS_INCREASED_PART_LOT);
                query.setParameter("isDecrease", Constants.IS_DECREASED_WHOLE_LOT);
                query.setParameter("quantityDefault", Constants.QUANTITY_DEFAULT);
        }

        if (StringUtils.isNotBlank(request.getNameAsset())){
            query.setParameter("nameAsset", request.getNameAsset());
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            query.setParameter("idAssetCategory", request.getIdAssetCategory());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            query.setParameter("idDepartment", request.getIdDepartment());
        }
    }

    private void setParameterFindAllAssetChildrenDtoToDecrease(FindAllAssetToDecreaseRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("statusProcess", Constants.STATUS_PENDING_PROCESS);
        query.setParameter("isIncrease", Constants.IS_INCREASED);
        query.setParameter("isDecrease", Constants.IS_DECREASED);
        query.setParameter("salt", request.getSalt());
        if (StringUtils.isNotBlank(request.getNameAsset())){
            query.setParameter("nameAsset", request.getNameAsset());
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            query.setParameter("idAssetCategory", request.getIdAssetCategory());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            query.setParameter("idDepartment", request.getIdDepartment());
        }
    }

    private void setConditionFindAllAssetDtoToDecrease(FindAllAssetToDecreaseRequest request, StringBuilder sb) {
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE)){
            sb.append(" and asset.is_increase = :isIncrease  " +
                    "  and asset.is_decrease != :isDecrease  " +
                    "  and asset.quantity = :quantityDefault and asset.parent is null ");
        } else if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)){
            sb.append("   and (((asset.is_increase = :isIncrease or  " +
                    "      asset.is_increase = :isIncreasePart) and  " +
                    "     asset.is_decrease != :isDecrease))  " +
                    "  and asset.quantity > :quantityDefault and asset.parent is null ");
        } else if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_ALLOCATE)) {
            sb.append(" and asset.is_increase = :isIncrease  " +
                    "  and asset.is_decrease != :isDecrease  " +
                    "  and asset.quantity = :quantityDefault and asset.parent is not null ");
        }
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())) {
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())) {
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY asset.id_asset desc ");
        }
    }


    private void setConditionFindAllAssetChildrenDtoToDecrease(FindAllAssetToDecreaseRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())) {
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())) {
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY asset.id_asset desc ");
        }
    }

    private long countFindAllAssetToChange(FindAllAssetToChangeRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0)  " +
                " from asset asset     " +
                "          inner join asset_categories assetCategories     " +
                "     on asset.id_asset_category = assetCategories.id_asset_category     " +
                "          left join department de on asset.id_department = de.id_department     " +
                "          left join location lo on asset.id_location = lo.id_location     " +
                "          left join asset_depreciation assetDepreciation     " +
                "     on asset.id_asset = assetDepreciation.id_asset     " +
                " where 1 = 1     " +
                "   and asset.parent is null       " +
                "   and asset.id_department_origin in (:idsDepartmentOriginal)     " +
                "   and asset.is_increase = :isIncrease  " +
                "   and asset.is_decrease != :isDecrease  " +
                "   and (asset.status_process_current != :statusProcessCurrent or asset.status_process_current is null )   ");
        setConditionFindAllAssetDtoToChange(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetDtoToChange(query, request);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private long countFindAllAssetToRevaluation(FindAllAssetToRevaluationRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0)   " +
                "from asset asset   " +
                "          inner join asset_categories assetCategories    " +
                "                     on asset.id_asset_category = assetCategories.id_asset_category    " +
                "          left join department de on asset.id_department = de.id_department    " +
                "          left join location lo on asset.id_location = lo.id_location   " +
                "          left join asset_depreciation assetDepreciation    " +
                "                    on asset.id_asset = assetDepreciation.id_asset   " +
                "                   where 1 = 1     " +
                "   and asset.id_department_origin in (:idsDepartmentOriginal)    " +
                "   and (asset.status_process_current != :statusProcessCurrent or asset.status_process_current is null )  ");
        setCountConditionFindAllAssetDtoToRevaluation(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetDtoToRevaluation(query, request);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setCountConditionFindAllAssetDtoToRevaluation(StringBuilder sb, FindAllAssetToRevaluationRequest request) {
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE)) {
            sb.append("   and asset.is_increase = :isIncrease  " +
                    "  and asset.is_decrease != :isDecrease  " +
                    "  and asset.quantity = :quantityDefault  " +
                    "  and asset.parent is null ");
        } else  if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)){
            sb.append("   and (((asset.is_increase = :isIncrease or " +
                    "         asset.is_increase = :isIncreasePart) and " +
                    "        asset.is_decrease != :isDecrease)) " +
                    "  and asset.quantity > :quantityDefault ");
        } else if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_ALLOCATE)) {
            sb.append("   and asset.is_increase = :isIncrease  " +
                    "  and asset.is_decrease != :isDecrease  " +
                    "  and asset.quantity = :quantityDefault  " +
                    "  and asset.parent is not null ");
        }
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())) {
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())) {
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY asset.id_asset desc ");
        }

    }

    private void setParameterFindAllAssetDtoToChange(Query query, FindAllAssetToChangeRequest request) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("statusProcessCurrent", Constants.STATUS_PENDING_PROCESS);
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE) || request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_ALLOCATE)){
            query.setParameter("isIncrease", Constants.IS_INCREASED);
            query.setParameter("isDecrease", Constants.IS_DECREASED);
            query.setParameter("quantityDefault", Constants.QUANTITY_DEFAULT);
        } else if(request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)){
            query.setParameter("isIncrease", Constants.IS_INCREASED_WHOLE_LOT);
            query.setParameter("isDecrease", Constants.IS_DECREASED_WHOLE_LOT);
            query.setParameter("isDecreasePart", Constants.IS_DECREASED_PART_LOT);
            query.setParameter("quantityDefault", Constants.QUANTITY_DEFAULT);
        }
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            query.setParameter("nameAsset", request.getNameAsset());
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())) {
            query.setParameter("idAssetCategory", request.getIdAssetCategory());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            query.setParameter("idDepartment", request.getIdDepartment());
        }
    }

    private void setParameterFindAllAssetDtoToRevaluation(Query query, FindAllAssetToRevaluationRequest request) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("statusProcessCurrent", Constants.STATUS_PENDING_PROCESS);
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE) || request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_ALLOCATE)){
            query.setParameter("isIncrease", Constants.IS_INCREASED);
            query.setParameter("isDecrease", Constants.IS_DECREASED);
            query.setParameter("quantityDefault", Constants.QUANTITY_DEFAULT);
        } else if(request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)){
            query.setParameter("isIncrease", Constants.IS_INCREASED_WHOLE_LOT);
            query.setParameter("isIncreasePart", Constants.IS_INCREASED_PART_LOT);
            query.setParameter("isDecrease", Constants.IS_DECREASED_WHOLE_LOT);
            query.setParameter("quantityDefault", Constants.QUANTITY_DEFAULT);
        }
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            query.setParameter("nameAsset", request.getNameAsset());
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())) {
            query.setParameter("idAssetCategory", request.getIdAssetCategory());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            query.setParameter("idDepartment", request.getIdDepartment());
        }
    }

    private void setConditionFindAllAssetDtoToChange(StringBuilder sb, FindAllAssetToChangeRequest request) {
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE)) {
            sb.append("   and asset.is_increase = :isIncrease  " +
                    "  and asset.is_decrease != :isDecrease  " +
                    "  and asset.quantity = :quantityDefault  " +
                    "  and asset.parent is null ");
        } else  if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)){
            sb.append("   and (asset.is_increase = :isIncrease and " +
                    "        (asset.is_decrease != :isDecrease and asset.is_decrease != :isDecreasePart)) " +
                    "  and asset.quantity > :quantityDefault ");
        } else if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_ALLOCATE)) {
            sb.append("   and asset.is_increase = :isIncrease  " +
                    "  and asset.is_decrease != :isDecrease  " +
                    "  and asset.quantity = :quantityDefault  " +
                    "  and asset.parent is not null ");
        }
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())) {
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }

        if (StringUtils.isNotBlank(request.getSortBy())) {
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY asset.id_asset desc ");
        }
    }

    private void setConditionFindAllAssetDtoToRevaluation(StringBuilder sb, FindAllAssetToRevaluationRequest request) {
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE)) {
            sb.append("   and asset.is_increase = :isIncrease  " +
                    "  and asset.is_decrease != :isDecrease  " +
                    "  and asset.quantity = :quantityDefault  " +
                    "  and asset.parent is null ");
        } else  if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)){
            sb.append("   and (((asset.is_increase = :isIncrease or " +
                    "         asset.is_increase = :isIncreasePart) and " +
                    "        asset.is_decrease != :isDecrease)) " +
                    "  and asset.quantity > :quantityDefault ");
        } else if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_ALLOCATE)) {
            sb.append("   and asset.is_increase = :isIncrease  " +
                    "  and asset.is_decrease != :isDecrease  " +
                    "  and asset.quantity = :quantityDefault  " +
                    "  and asset.parent is not null ");
        }
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())) {
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())) {
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY asset.id_asset desc ");
        }
    }


    private long countFindAllAssetToIncrease(FinaAllAssetToIncreaseRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0)     " +
                "from asset asset     " +
                "        inner join asset_categories assetCategories     " +
                "            on asset.id_asset_category = assetCategories.id_asset_category     " +
                "        left join department de on asset.id_department = de.id_department     " +
                "        left join location lo on asset.id_location = lo.id_location     " +
                "        left join asset_depreciation ad on asset.id_asset = ad.id_asset     " +
                "where 1 = 1     " +
                "and asset.id_department_origin in (:idsDepartmentOriginal)     " +
                "and (asset.status_process_current != :statusProcessCurrent or asset.status_process_current is null) ");
        setConditionCountFindAllAssetDtoToIncrease(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetDtoToIncrease(request,query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private long countFindAllAssetChildrenToIncrease(FinaAllAssetToIncreaseRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(0)     " +
                "from asset asset     " +
                "         inner join asset_categories assetCategories     " +
                "                    on asset.id_asset_category = assetCategories.id_asset_category     " +
                "         inner join department de on asset.id_department = de.id_department     " +
                "         left join location lo on asset.id_location = lo.id_location     " +
                "         left join asset_depreciation ad on asset.id_asset = ad.id_asset     " +
                "         inner join (select id_asset from asset where asset.salt = :salt) assetParent     " +
                "                    on asset.parent = assetParent.id_asset     " +
                "where 1 = 1     " +
                "  and asset.id_department_origin in (:idsDepartmentOriginal)     " +
                "  and asset.is_increase = :isIncrease     " +
                "  and (asset.status_process_current != :statusProcessCurrent or asset.status_process_current is null) ");
        setConditionCountFindAllAssetChildrenDtoToIncrease(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetChildrenDtoToIncrease(request,query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private long countFindAllAssetToInventory(FindAllAssetToInventoryRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH ROOT_ASSET_CATEGORIES as     " +
                "         (WITH RECURSIVE cte_asset_categories as     " +
                "           (select assetCategires.id_asset_category,     " +
                "                   assetCategires.name,     " +
                "                   assetCategires.code_name,     " +
                "                   1           as depth,     " +
                "                   CAST(assetCategires.id_asset_category as NCHAR) as path,     " +
                "                   assetCategires.number_code_pattern,     " +
                "                   assetCategires.id_department_original,     " +
                "                   assetCategires.type_target,     " +
                "                   assetCategires.parent     " +
                "            from asset_categories assetCategires     " +
                "            where assetCategires.parent is null     " +
                "              and assetCategires.visible = :visible     " +
                "            union all     " +
                "            select assetCategires.id_asset_category,     " +
                "                   assetCategires.name,     " +
                "                   assetCategires.code_name,     " +
                "                   cte.depth + 1          as depth,     " +
                "                   concat_ws('/', cte.path,     " +
                "                             CAST(assetCategires.id_asset_category as NCHAR)) as path,     " +
                "                   assetCategires.number_code_pattern,     " +
                "                   assetCategires.id_department_original,     " +
                "                   assetCategires.type_target,     " +
                "                   assetCategires.parent     " +
                "            from asset_categories assetCategires     " +
                "                     INNER JOIN cte_asset_categories cte     " +
                "                                ON assetCategires.parent = cte.id_asset_category)     " +
                "          select cte.id_asset_category,     " +
                "                 cte.name,     " +
                "                 cte.code_name,     " +
                "                 cte.depth,     " +
                "                 cte.path,     " +
                "                 cte.number_code_pattern,     " +
                "                 group_concat(un.name SEPARATOR '/') as unitMeasure,     " +
                "                 cte.parent,     " +
                "                 CASE     " +
                "                     WHEN EXISTS (SELECT 1     " +
                "                FROM asset_categories ac     " +
                "                WHERE ac.parent = cte.id_asset_category) THEN 0     " +
                "                     ELSE 1 END                      AS is_leaf     " +
                "          from cte_asset_categories cte     " +
                "                   left join (select un.id_asset_category, un.id_unit, un.name     " +
                "            from units un     " +
                "            where un.is_display = :isDisplay) un     " +
                "           on cte.id_asset_category = un.id_asset_category     " +
                "          where 1 = 1     " +
                "            and cte.id_department_original in (:idsDepartmentOriginal)     " +
                "          group by cte.id_asset_category, cte.name, cte.code_name,     " +
                "                   cte.depth, cte.path, cte.number_code_pattern, is_leaf     " +
                "          order by cte.path),     " +
                "     ROOT_ASSET as (select asset.id_asset                           idAsset,     " +
                "                           asset.code_asset                         codeAsset,     " +
                "                           asset.name             nameAsset,     " +
                "                           assetCategories.id_asset_category        idAssetCategory, " +
                "                           assetCategories.name                     nameAssetCategory, " +
                "                           assetCategories.code_name                codeAssetCategory, " +
                "                           de.id_department                         idDepartment, " +
                "                           de.code                codeDepartment, " +
                "                           de.name                nameDepartment, " +
                "                           lo.id_location                           idLocation, " +
                "                           lo.name                nameLocation, " +
                "                           asset.time_created, " +
                "                           asset.time_modified, " +
                "                           asset.parent,     " +
                "                           asset.salt, " +
                "                           assetDepreciation.rest_value, " +
                "                           asset.quantity, " +
                "                           asset.sum_original_of_formation, " +
                "                           asset.status_use, " +
                "                           asset.year_use , asset.acreage,asset.is_increase " +
                "                    from asset asset " +
                "           inner join asset_categories assetCategories " +
                "                      on asset.id_asset_category = assetCategories.id_asset_category " +
                "           left join department de on asset.id_department = de.id_department " +
                "           left join location lo on asset.id_location = lo.id_location " +
                "           left join asset_original_of_formation assetOriginalOfFormation " +
                "                     on asset.id_asset = assetOriginalOfFormation.id_asset " +
                "           left join asset_depreciation assetDepreciation " +
                "                     on asset.id_asset = assetDepreciation.id_asset " +
                "                    where 1 = 1 " +
                "                   and asset.id_department_origin in (:idsDepartmentOriginal)      " +
                "                   and (asset.status_process_current != :statusProcess or asset.status_process_current is null) ");
        setCountConditionFindAllAssetDtoToInventory(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetDtoToInventory(request,query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private long countFindAllAssetChildrenToInventory(FindAllAssetToInventoryRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(0)  " +
                "from asset asset  " +
                "        inner join asset_categories assetCategories  " +
                "                   on asset.id_asset_category = assetCategories.id_asset_category  " +
                "        inner join department de on asset.id_department = de.id_department  " +
                "        inner join  units on asset.id_unit = units.id_unit  " +
                "        left join location lo on asset.id_location = lo.id_location  " +
                "        left join asset_depreciation assetDepreciation  " +
                "                  on asset.id_asset = assetDepreciation.id_asset  " +
                "        inner join (select id_asset from asset where salt = :salt)  " +
                "         assetParent on asset.parent = assetParent.id_asset  " +
                "where 1 = 1  " +
                " and asset.id_department_origin in (:idsDepartmentOriginal)     " +
                " and asset.status_process_current != :statusProcess    " +
                " and asset.is_increase = :isIncrease  " +
                " and asset.is_decrease != :isDecrease ");
        setCountConditionFindAllAssetChildrenDtoToInventory(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetChildrenDtoToInventory(request,query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }


    private long countFindAllAssetChildrenToRevaluation(FindAllAssetToRevaluationRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0)  " +
                "from asset asset  " +
                "         inner join asset_categories assetCategories  " +
                "    on asset.id_asset_category = assetCategories.id_asset_category  " +
                "         inner join department de on asset.id_department = de.id_department  " +
                "         left join location lo on asset.id_location = lo.id_location  " +
                "         left join asset_depreciation assetDepreciation  " +
                "   on asset.id_asset = assetDepreciation.id_asset  " +
                "         inner join (select id_asset from asset where salt = :salt)  " +
                "          assetParent on asset.parent = assetParent.id_asset  " +
                "  where 1 = 1  " +
                "  and asset.id_department_origin in (:idsDepartmentOriginal)  " +
                "  and asset.status_process_current != :statusProcess  " +
                " and asset.is_increase = :isIncrease  " +
                " and asset.is_decrease != :isDecrease  ");
        setCountConditionFindAllAssetChildrenDtoToRevaluation(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetChildrenDtoToRevaluation(request,query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }


    private void setParameterFindAllAssetDtoToIncrease(FinaAllAssetToIncreaseRequest request, Query query) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE)
            || request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)
            || request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_ALLOCATE)) {
            query.setParameter("isIncrease", Constants.IS_NOT_INCREASED);
            query.setParameter("quantityDefault", Constants.QUANTITY_DEFAULT);
            if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)) {
                query.setParameter("isIncreasePart", Constants.IS_INCREASED_PART_LOT);
            }
        }
        query.setParameter("statusProcessCurrent", Constants.STATUS_PENDING_PROCESS);
        if (StringUtils.isNotBlank(request.getNameAsset())){
            query.setParameter("nameAsset", request.getNameAsset());
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            query.setParameter("idAssetCategory", request.getIdAssetCategory());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            query.setParameter("idDepartment", request.getIdDepartment());
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())){
            query.setParameter("statusUse", request.getStatusUse());
        }
    }

    private void setParameterFindAllAssetChildrenDtoToIncrease(FinaAllAssetToIncreaseRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("isIncrease",Constants.IS_NOT_INCREASED);
        query.setParameter("statusProcessCurrent", Constants.STATUS_PENDING_PROCESS);
        query.setParameter("salt", request.getSalt());
        if (StringUtils.isNotBlank(request.getNameAsset())){
            query.setParameter("nameAsset", request.getNameAsset());
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            query.setParameter("idAssetCategory", request.getIdAssetCategory());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            query.setParameter("idDepartment", request.getIdDepartment());
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())) {
            query.setParameter("statusUse", request.getStatusUse());
        }
    }

    private void setParameterFindAllAssetDtoToInventory(FindAllAssetToInventoryRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());

        query.setParameter("statusProcess", Constants.STATUS_PENDING_PROCESS);
        query.setParameter("visible", Constants.ASSET_CATEGORY_IS_VISIBLE);
        query.setParameter("isDisplay", Constants.UNITES_IS_DISPLAY);
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE)
                || request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)
                || request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_ALLOCATE)){
            query.setParameter("quantityDefault", Constants.QUANTITY_DEFAULT);
            if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE) || request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_ALLOCATE) ) {
                query.setParameter("isIncrease", Constants.IS_INCREASED);
                query.setParameter("isDecrease", Constants.IS_DECREASED);
            }
            if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)) {
                query.setParameter("isIncrease", Constants.IS_INCREASED_WHOLE_LOT);
                query.setParameter("isDecrease", Constants.IS_DECREASED_WHOLE_LOT);
                query.setParameter("isIncreasePart", Constants.IS_INCREASED_PART_LOT);
            }

        }
        if (StringUtils.isNotBlank(request.getNameAsset())){
            query.setParameter("nameAsset", request.getNameAsset());
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            query.setParameter("idAssetCategory", request.getIdAssetCategory());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            query.setParameter("idDepartment", request.getIdDepartment());
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())) {
            query.setParameter("statusUse", request.getStatusUse());
        }
    }


    private void setParameterFindAllAssetChildrenDtoToInventory(FindAllAssetToInventoryRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("statusProcess", Constants.STATUS_PENDING_PROCESS);
        query.setParameter("salt", request.getSalt());
        query.setParameter("isIncrease", Constants.IS_INCREASED);
        query.setParameter("isDecrease", Constants.IS_DECREASED);
        if (StringUtils.isNotBlank(request.getNameAsset())){
            query.setParameter("nameAsset", request.getNameAsset());
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            query.setParameter("idAssetCategory", request.getIdAssetCategory());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            query.setParameter("idDepartment", request.getIdDepartment());
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())) {
            query.setParameter("statusUse", request.getStatusUse());
        }
    }

    private void setParameterFindAllAssetChildrenDtoToRevaluation(FindAllAssetToRevaluationRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("statusProcess", Constants.STATUS_PENDING_PROCESS);
        query.setParameter("salt", request.getSalt());
        query.setParameter("isIncrease", Constants.IS_INCREASED);
        query.setParameter("isDecrease", Constants.IS_DECREASED);
        if (StringUtils.isNotBlank(request.getNameAsset())){
            query.setParameter("nameAsset", request.getNameAsset());
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            query.setParameter("idAssetCategory", request.getIdAssetCategory());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            query.setParameter("idDepartment", request.getIdDepartment());
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())) {
            query.setParameter("statusUse", request.getStatusUse());
        }
    }

    private void setConditionFindAllAssetDtoToIncrease(FinaAllAssetToIncreaseRequest request,StringBuilder sb) {
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE)) {
            sb.append("  and asset.is_increase = :isIncrease ");
            sb.append("  and asset.quantity = :quantityDefault and asset.parent is null ");
        } else if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)) {
            sb.append(" and (asset.is_increase = :isIncrease or asset.is_increase = :isIncreasePart) ");
            sb.append("  and asset.quantity > :quantityDefault and asset.parent is null ");
        } else if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_ALLOCATE)){
            sb.append("  and asset.is_increase = :isIncrease ");
            sb.append("  and asset.quantity = :quantityDefault and asset.parent is not null ");
        }
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())) {
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())){
            sb.append(" and asset.status_use = : statusUse ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())) {
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY asset.id_asset desc ");
        }
    }

    private void setConditionFindAllAssetChildrenDtoToIncrease(FinaAllAssetToIncreaseRequest request,StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())) {
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())){
            sb.append(" and asset.status_use = :status_use ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())) {
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY asset.id_asset desc ");
        }
    }

    private void setConditionCountFindAllAssetDtoToIncrease(FinaAllAssetToIncreaseRequest request,StringBuilder sb) {
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE)) {
            sb.append("  and asset.is_increase = :isIncrease ");
            sb.append("  and asset.quantity = :quantityDefault and asset.parent is null ");
        } else if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)) {
            sb.append(" and (asset.is_increase = :isIncrease or asset.is_increase = :isIncreasePart) ");
            sb.append("  and asset.quantity > :quantityDefault and asset.parent is null ");
        } else if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_ALLOCATE)){
            sb.append("  and asset.is_increase = :isIncrease ");
            sb.append("  and asset.quantity = :quantityDefault and asset.parent is not null ");
        }
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())) {
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())){
            sb.append(" and asset.status_use = :statusUse ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())) {
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        }
    }

    private void setConditionCountFindAllAssetChildrenDtoToIncrease(FinaAllAssetToIncreaseRequest request,StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())) {
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())){
            sb.append(" and asset.status_use = :statusUse ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())) {
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        }
    }

    private void setConditionFindAllAssetDtoToInventory(FindAllAssetToInventoryRequest request,StringBuilder sb) {
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE)){
            sb.append("   and asset.is_increase = :isIncrease  " +
                    "  and asset.is_decrease != :isDecrease  " +
                    "  and asset.quantity = :quantityDefault  " +
                    "  and asset.parent is null ");
        } else if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)){
            sb.append("   and (((asset.is_increase = :isIncrease or  " +
                    "     asset.is_increase = :isIncreasePart) and   " +
                    "     asset.is_decrease != :isDecrease))   " +
                    "     and asset.quantity > :quantityDefault and asset.parent is null ");
        } else if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_ALLOCATE)) {
            sb.append("   and asset.is_increase = :isIncrease  " +
                    "  and asset.is_decrease != :isDecrease  " +
                    "  and asset.quantity = :quantityDefault  " +
                    "  and asset.parent is not null  ");
        }
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())) {
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())) {
            sb.append(" and asset.status_use = :statusUse ");
        }
        sb.append(" ) " +
                "select rootAssetCategories.id_asset_category   as idAssetCategory,  " +
                "       rootAssetCategories.name                as nameAssetCategory,  " +
                "       rootAssetCategories.parent              as idParentAssetCategory,  " +
                "       rootAssetCategories.code_name           as codeAssetCategory,  " +
                "       rootAssetCategories.depth               as depth,  " +
                "       rootAssetCategories.path                as path,  " +
                "       rootAssetCategories.number_code_pattern as numberCodePattern,  " +
                "       rootAsset.idAsset     as idAsset,  " +
                "       rootAsset.nameAsset                     as nameAsset,  " +
                "       rootAsset.codeAsset                     as codeAsset,  " +
                "       rootAsset.idDepartment                  as idDepartment,  " +
                "       rootAsset.codeDepartment                as codeDepartment,  " +
                "       rootAsset.nameDepartment                as nameDepartment,  " +
                "       rootAsset.idLocation                    as idLocation,  " +
                "       rootAsset.nameLocation                  as nameLocation,  " +
                "       rootAsset.time_created                  as timeCreated,  " +
                "       rootAsset.time_modified                 as timeModified,  " +
                "       rootAsset.parent      as parent,  " +
                "       rootAsset.salt        as salt,  " +
                "       rootAsset.rest_value                    as restValue,  " +
                "       rootAsset.quantity                      as quantity,  " +
                "       rootAsset.assetOriginalOfFormationValue as originalOfFormation,  " +
                "       rootAsset.status_use                    as statusUse,  " +
                "       rootAsset.year_use                      as yearUse,  " +
                "rootAssetCategories.is_leaf, " +
                "       rootAssetCategories.type_target,  " +
                "       rootAsset.nameUnit,  " +
                "       rootAsset.acreage," +
                "       rootAsset.is_increase," +
                "       rootAsset.sum_child_increase            as sumChildIncrease,  " +
                "       rootAsset.sum_child_decrease            as sumChildDecrease   " +

                "from ROOT_ASSET_CATEGORIES rootAssetCategories  " +
                "         left join ROOT_ASSET rootAsset on rootAssetCategories.id_asset_category = rootAsset.idAssetCategory  " +
                "order by rootAssetCategories.path ");
    }

    private void setConditionFindAllAssetChildrenDtoToInventory(FindAllAssetToInventoryRequest request,StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())) {
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())) {
            sb.append(" and asset.status_use = :statusUse ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())) {
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY asset.id_asset desc ");
        }
    }


    private void setConditionFindAllAssetChildrenDtoToRevaluation(FindAllAssetToRevaluationRequest request,StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())) {
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())) {
            sb.append(" and asset.status_use = :statusUse ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())) {
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY asset.id_asset desc ");
        }
    }


    private void setCountConditionFindAllAssetDtoToInventory(FindAllAssetToInventoryRequest request,StringBuilder sb) {
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE)){
            sb.append("   and asset.is_increase = :isIncrease  " +
                    "  and asset.is_decrease != :isDecrease  " +
                    "  and asset.quantity = :quantityDefault  " +
                    "  and asset.parent is null ");
        } else if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)){
            sb.append("   and (((asset.is_increase = :isIncrease or  " +
                    "     asset.is_increase = :isIncreasePart) and   " +
                    "     asset.is_decrease != :isDecrease))   " +
                    "     and asset.quantity > :quantityDefault and asset.parent is null ");
        } else if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_ALLOCATE)) {
            sb.append("   and asset.is_increase = :isIncrease  " +
                    "  and asset.is_decrease != :isDecrease  " +
                    "  and asset.quantity = :quantityDefault  " +
                    "  and asset.parent is not null  ");
        }
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())) {
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())) {
            sb.append(" and asset.status_use = :statusUse ");
        }
        sb.append(" ) " +
                "select count(0)  " +
                "from ROOT_ASSET_CATEGORIES rootAssetCategories  " +
                "         left join ROOT_ASSET rootAsset on rootAssetCategories.id_asset_category = rootAsset.idAssetCategory  " +
                "order by rootAssetCategories.path ");

    }

    private void setCountConditionFindAllAssetChildrenDtoToInventory(FindAllAssetToInventoryRequest request,StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())) {
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())) {
            sb.append(" and asset.status_use = : statusUse ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())) {
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY asset.id_asset desc ");
        }

    }
    private void setCountConditionFindAllAssetChildrenDtoToRevaluation(FindAllAssetToRevaluationRequest request,StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())) {
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())) {
            sb.append(" and asset.status_use = :statusUse ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())) {
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY asset.id_asset desc  ");
        }

    }


    private long countFindAllGroundAsset(FindAllGroundAssetRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "from asset asset " +
                "    inner join ground_module groundModule on asset.id_asset = groundModule.id_asset " +
                "where 1 = 1 ");
        setConditionFindAllGroundAsset(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllGroundAsset(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllGroundAsset(FindAllGroundAssetRequest request, Query query) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllGroundAsset(FindAllGroundAssetRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append("   and (asset.name REGEXP  :keyword ) ");
        }
    }


    private void setDeclareBluePrintAsset(AssetBluePrintDto dto, Object[] obj) {
        AssetDeclareDto assetDeclareDto = new AssetDeclareDto();
        BluePrintDeclareDto bluePrintDeclareDto = new BluePrintDeclareDto();
        bluePrintDeclareDto.setTypeDeclare(ValueUtil.getStringByObject(obj[32]));
        bluePrintDeclareDto.setIdDeclare(ValueUtil.getIntegerByObject(obj[33]));
        bluePrintDeclareDto.setNameDeclare(ValueUtil.getStringByObject(obj[34]));
        bluePrintDeclareDto.setIdInstance(ValueUtil.getIntegerByObject(obj[35]));
        assetDeclareDto.setBluePrintDeclare(bluePrintDeclareDto);
        dto.setDeclare(assetDeclareDto);
    }

    private void setOriginalBluePrintAsset(AssetBluePrintDto dto, Object[] obj) {
        AssetOriginalDto assetOriginalDto = new AssetOriginalDto();
        BluePrintOriginalDto bluePrintOriginalDto = new BluePrintOriginalDto();
        bluePrintOriginalDto.setTypeOriginal(ValueUtil.getStringByObject(obj[28]));
        bluePrintOriginalDto.setIdOriginal(ValueUtil.getIntegerByObject(obj[29]));
        bluePrintOriginalDto.setNameOriginal(ValueUtil.getStringByObject(obj[30]));
        bluePrintOriginalDto.setIdInstance(ValueUtil.getIntegerByObject(obj[31]));
        assetOriginalDto.setBluePrintAssetOriginalDto(bluePrintOriginalDto);
        dto.setOriginal(assetOriginalDto);
    }

    private void setModulesBluePrintAsset(AssetBluePrintDto dto, List<Object[]> result) {
        List<AssetModulesDto> assetModulesDto = new ArrayList<>();
        for (Object[] obj: result){
            AssetModulesDto modulesDto = new AssetModulesDto();
            setBluePrintModules(obj, modulesDto);
            assetModulesDto.add(modulesDto);
        }
        dto.setModules(assetModulesDto);
    }

    private void setBluePrintModules(Object[] obj, AssetModulesDto modulesDto) {
        BluePrintAssetModulesDto bluePrintAssetModulesDto = new BluePrintAssetModulesDto();
        bluePrintAssetModulesDto.setTypeModules(ValueUtil.getStringByObject(obj[24]));
        bluePrintAssetModulesDto.setIdModules(ValueUtil.getIntegerByObject(obj[25]));
        bluePrintAssetModulesDto.setNameModules(ValueUtil.getStringByObject(obj[26]));
        bluePrintAssetModulesDto.setIdInstance(ValueUtil.getIntegerByObject(obj[27]));
        modulesDto.setBluePrintAssetModules(bluePrintAssetModulesDto);
    }

    private void setCommonBluePrintAsset(AssetBluePrintDto dto, Object[] obj) {
        dto.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
        dto.setName(ValueUtil.getStringByObject(obj[1]));
        dto.setCodeAsset(ValueUtil.getStringByObject(obj[2]));
        setBluePrintAssetCategory(dto, obj);
        setBluePrintDepartment(dto, obj);
        setBluePrintDocumentAttack(dto, obj);
        setBluePrintLocation(dto, obj);
        setBluePrintUnit(dto, obj);
        setBluePrintProjects(dto, obj);
        setBluePrintDepartmentDefault(dto, obj);
        setBluePrintDepartmentLevelTypeAsset(dto, obj);
        dto.setPurpose(ValueUtil.getStringByObject(obj[16]));
        dto.setNotes(ValueUtil.getStringByObject(obj[17]));
        dto.setDescription(ValueUtil.getStringByObject(obj[18]));
        dto.setFileAttack(ValueUtil.getStringByObject(obj[19]));
        dto.setIdInstance(ValueUtil.getIntegerByObject(obj[36]));
        dto.setParent(ValueUtil.getIntegerByObject(obj[59]));
        dto.setSalt(ValueUtil.getStringByObject(obj[60]));
        dto.setQuantity(ValueUtil.getIntegerByObject(obj[61]));
        dto.setIdDepartmentOrigin(ValueUtil.getIntegerByObject(obj[62]));
        dto.setIdProcessCurrent(ValueUtil.getIntegerByObject(obj[63]));
        dto.setStatusProcessCurrent(ValueUtil.getIntegerByObject(obj[64]));
        dto.setIdTypeProcessCurrent(ValueUtil.getIntegerByObject(obj[65]));
        dto.setIsIncrease(ValueUtil.getIntegerByObject(obj[66]));
        dto.setIsDecrease(ValueUtil.getIntegerByObject(obj[67]));
        dto.setIdUserCreated(ValueUtil.getIntegerByObject(obj[68]));
        dto.setIdUserModified(ValueUtil.getIntegerByObject(obj[69]));
        dto.setStatusUse(ValueUtil.getIntegerByObject(obj[70]));
        dto.setYearUse(ValueUtil.getStringByObject(obj[71]));
        dto.setAcreage(ValueUtil.getDoubleByObject(obj[72]));
    }

    private void setBluePrintDepartmentLevelTypeAsset(AssetBluePrintDto dto, Object[] obj) {
        BluePrintLevelTypeAssetDto bluePrintLevelTypeAssetDto = new BluePrintLevelTypeAssetDto();
        bluePrintLevelTypeAssetDto.setIdLevelTypeAsset(ValueUtil.getIntegerByObject(obj[22]));
        bluePrintLevelTypeAssetDto.setNameLevelTypeAsset(ValueUtil.getStringByObject(obj[23]));
        dto.setLevelTypeAsset(bluePrintLevelTypeAssetDto);
    }

    private void setBluePrintDepartmentDefault(AssetBluePrintDto dto, Object[] obj) {
        BluePrintDepartmentDefaultDto bluePrintDepartmentDefaultDto = new BluePrintDepartmentDefaultDto();
        bluePrintDepartmentDefaultDto.setIdDefaultDepartment(ValueUtil.getIntegerByObject(obj[20]));
        bluePrintDepartmentDefaultDto.setNameDefaultDepartment(ValueUtil.getStringByObject(obj[21]));
        dto.setDepartmentDefault(bluePrintDepartmentDefaultDto);
    }

    private void setBluePrintProjects(AssetBluePrintDto dto, Object[] obj) {
        BluePrintProjectsDto bluePrintProjectsDto = new BluePrintProjectsDto();
        bluePrintProjectsDto.setIdProjects(ValueUtil.getIntegerByObject(obj[14]));
        bluePrintProjectsDto.setNameProjects(ValueUtil.getStringByObject(obj[15]));
        dto.setProjects(bluePrintProjectsDto);
    }

    private void setBluePrintUnit(AssetBluePrintDto dto, Object[] obj) {
        BluePrintUnitDto bluePrintUnitDto = new BluePrintUnitDto();
        bluePrintUnitDto.setIdUnit(ValueUtil.getIntegerByObject(obj[12]));
        bluePrintUnitDto.setNameUnit(ValueUtil.getStringByObject(obj[13]));
        dto.setUnits(bluePrintUnitDto);
    }

    private void setBluePrintLocation(AssetBluePrintDto dto, Object[] obj) {
        BluePrintLocationDto bluePrintLocationDto = new BluePrintLocationDto();
        bluePrintLocationDto.setIdLocation(ValueUtil.getIntegerByObject(obj[10]));
        bluePrintLocationDto.setNameLocation(ValueUtil.getStringByObject(obj[11]));
        dto.setLocation(bluePrintLocationDto);
    }

    private void setBluePrintDocumentAttack(AssetBluePrintDto dto, Object[] obj) {
        BluePrintDocumentAttackDto bluePrintDocumentAttackDto = new BluePrintDocumentAttackDto();
        bluePrintDocumentAttackDto.setIdDocumentAttack(ValueUtil.getIntegerByObject(obj[8]));
        bluePrintDocumentAttackDto.setNameDocumentAttack(ValueUtil.getStringByObject(obj[9]));
        dto.setDocumentAttack(bluePrintDocumentAttackDto);
    }

    private void setBluePrintDepartment(AssetBluePrintDto dto, Object[] obj) {
        BluePrintDepartmentDto bluePrintDepartmentDto = new BluePrintDepartmentDto();
        bluePrintDepartmentDto.setCodeDepartment(ValueUtil.getStringByObject(obj[5]));
        bluePrintDepartmentDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[6]));
        bluePrintDepartmentDto.setNameDepartment(ValueUtil.getStringByObject(obj[7]));
        dto.setDepartment(bluePrintDepartmentDto);
    }

    private void setBluePrintAssetCategory(AssetBluePrintDto dto, Object[] obj) {
        BluePrintAssetCategoryDto bluePrintAssetCategoryDto = new BluePrintAssetCategoryDto();
        bluePrintAssetCategoryDto.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[3]));
        bluePrintAssetCategoryDto.setNameAssetCategory(ValueUtil.getStringByObject(obj[4]));
        dto.setAssetCategory(bluePrintAssetCategoryDto);
    }

    private long countFindAllAsset(FindAllAssetRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(0)     " +
                "    from asset asset     " +
                "               left join asset_categories assetCategories          " +
                "                    on asset.id_asset_category = assetCategories.id_asset_category     " +
                "               left join department de on asset.id_department = de.id_department     " +
                "               left join location lo on asset.id_location = lo.id_location     " +
                "               left join asset_depreciation ad on asset.id_asset = ad.id_asset     " +
                "where 1 = 1     " +
                "and asset.parent is null     " +
                "and asset.id_department_origin in (:idsDepartmentOriginal) ");
        setConditionCountFindAllAsset(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAsset(request, query);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    private long countFindAllAssetLotChildren(FindAllAssetLotChildrenRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) count  " +
                " from asset asset        " +
                "     inner join asset_categories assetCategories        " +
                "             on asset.id_asset_category = assetCategories.id_asset_category        " +
                "     inner join department de on asset.id_department = de.id_department  " +
                "     left join location lo on asset.id_location = lo.id_location  " +
                "     inner join (select * from asset where asset.salt = :saltAssetParent ) assetParent  " +
                "         on asset.parent = assetParent.id_asset  " +
                " where 1 = 1  and asset.id_department_origin in (:idsDepartmentOriginal) ");
        setConditionFindAllAssetLotChildren(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetLotChildren(request, query);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }


    private void setParameterFindAllAsset(FindAllAssetRequest request, Query query) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        if (StringUtils.isNotBlank(request.getCodeAsset())){
            query.setParameter("codeAsset", request.getCodeAsset());
        }
        if (StringUtils.isNotBlank(request.getNameAsset())){
            query.setParameter("nameAsset", request.getNameAsset());
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            query.setParameter("idAssetCategory", request.getIdAssetCategory());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            query.setParameter("idDepartment", request.getIdDepartment());
        }
        if (ObjectUtils.isNotEmpty(request.getIsIncrease())){
            query.setParameter("isIncrease", request.getIsIncrease());
        }
        if (ObjectUtils.isNotEmpty(request.getIsDecrease())){
            query.setParameter("isDecrease", request.getIsDecrease());
        }
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE)
                || request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)
                || request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_ALLOCATE)
        ) {
            query.setParameter("isSingle", Constants.QUANTITY_DEFAULT);
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())){
            query.setParameter("statusUse", request.getStatusUse());
        }
    }

    private void setConditionFindAllAsset(FindAllAssetRequest request, StringBuilder sb) {

        if (StringUtils.isNotBlank(request.getCodeAsset())){
            sb.append(" and (asset.code_asset REGEXP :codeAsset ) ");
        }
        if (StringUtils.isNotBlank(request.getNameAsset())){
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getIsIncrease())){
            sb.append("   and (asset.is_increase = :isIncrease) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIsDecrease())){
            sb.append("   and (asset.is_decrease = :isDecrease) ");
        }
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE)) {
            sb.append("  and asset.quantity = :isSingle and asset.parent is null  ");
        } else if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)) {
            sb.append(" and asset.quantity > :isSingle and asset.parent is null ");
        } else if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_ALLOCATE)) {
            sb.append(" and asset.quantity = :isSingle and asset.parent is not null ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())) {
            sb.append(" and asset.status_use = :statusUse ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())){
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY asset.id_asset desc ");
        }
    }

    private void setConditionCountFindAllAsset(FindAllAssetRequest request, StringBuilder sb) {

        if (StringUtils.isNotBlank(request.getCodeAsset())){
            sb.append(" and (asset.code_asset REGEXP :codeAsset ) ");
        }
        if (StringUtils.isNotBlank(request.getNameAsset())){
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getIsIncrease())){
            sb.append("   and (asset.is_increase = :isIncrease) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIsDecrease())){
            sb.append("   and (asset.is_decrease = :isDecrease) ");
        }
        if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE)) {
            sb.append("  and asset.quantity = :isSingle and asset.parent is null  ");
        } else if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_LOT)) {
            sb.append(" and asset.quantity > :isSingle and asset.parent is null ");
        } else if (request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_ALLOCATE)) {
            sb.append(" and asset.quantity = :isSingle and asset.parent is not null ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())){
            sb.append("  and asset.status_use = :statusUse ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())){
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY asset.id_asset desc ");
        }
    }
}
