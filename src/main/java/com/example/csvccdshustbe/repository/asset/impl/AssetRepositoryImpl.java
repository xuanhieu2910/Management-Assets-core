package com.example.csvccdshustbe.repository.asset.impl;

import com.example.csvccdshustbe.dto.asset.AssetBluePrintDto;
import com.example.csvccdshustbe.dto.asset.FindAllAssetDto;
import com.example.csvccdshustbe.dto.asset.FindAllGroundAssetDto;
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
import com.example.csvccdshustbe.dto.projects.BluePrintProjectsDto;
import com.example.csvccdshustbe.dto.unit.BluePrintUnitDto;
import com.example.csvccdshustbe.entity.Asset;
import com.example.csvccdshustbe.repository.asset.AssetRepositoryCustom;
import com.example.csvccdshustbe.request.asset.FindAllAssetRequest;
import com.example.csvccdshustbe.request.asset.FindAllGroundAssetRequest;
import com.example.csvccdshustbe.response.asset.FindAllGroundAssetResponse;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AssetRepositoryImpl implements AssetRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Page<FindAllAssetDto> findAllAssetDtoByIdsDepartment(FindAllAssetRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select asset.id_asset idAsset, asset.code_asset codeAsset, " +
                "       asset.name nameAsset, assetCategories.id_asset_category idAssetCategory, " +
                "       assetCategories.name nameAssetCategory, assetCategories.code_name codeAssetCategory, " +
                "       de.id_department idDepartment, de.code codeDepartment, de.name nameDepartment, " +
                "       lo.id_location idLocation, lo.name nameLocation, " +
                "       asset.time_created, asset.time_modified " +
                "from asset asset " +
                "    inner join asset_categories assetCategories " +
                "            on asset.id_asset_category = assetCategories.id_asset_category " +
                "    inner join department de on asset.id_department = de.id_department " +
                "    inner join location lo on asset.id_location = lo.id_location " +
                "where 1 = 1 and asset.id_department_origin in (:idsDepartmentOriginal) ");
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
                findAllAssetDto.setTimeCreated(ValueUtil.getStringByObject(obj[11]));
                findAllAssetDto.setTimeModified(ValueUtil.getStringByObject(obj[12]));
                responses.add(findAllAssetDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllAsset(request));
    }

    @Override
    public Optional<AssetBluePrintDto> findDetailAssetByCodeAsset(String codeAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append("select asset.id_asset, asset.name, asset.code_asset, assetCategory.id_asset_category idAssetCategory,             " +
                "        assetCategory.name nameAssetCategory, de.code codeDepartment, de.id_department idDepartment, de.name nameDepartment,             " +
                "        documentAttack.id_document_attack idDocumentAttack, documentAttack.name nameDocumentAttack,             " +
                "        location.id_location idLocation, location.name nameLocation, unit.id_unit idUnit, unit.name nameUnit,             " +
                "        project.id_project idProject, project.name nameProject, asset.purpose, asset.notes, asset.description, asset.file_attack,             " +
                "        departmentDefault.id_department idDepartmentDefault, departmentDefault.name nameDepartmentDefault,             " +
                "        levelTypeAsset.id_level_type_asset idLevelTypeAsset, levelTypeAsset.name nameLevelTypeAsset,             " +
                "        modules.hard_code typeModules, modules.id_module, modules.name nameModules,             " +
                "        assetModules.id_instance idInstanceModule,             " +
                "        original.hard_code_dev typeOriginal, original.id_original, original.name nameOriginal,             " +
                "        assetOriginal.id_instance idInstanceOriginal,             " +
                "        decl.hard_code typeDeclare, decl.id_declare, decl.name nameDeclare,             " +
                "        assetDeclare.id_instance inInstanceDeclare,       " +
                "        asset.id_instance assetIdInstance, assetDepreciation.id_asset_depreciation,   " +
                "        assetDepreciation.time_started_depreciation, assetDepreciation.amount_months_depreciation,    " +
                "        assetDepreciation.value_depreciation, assetDepreciation.type_depreciation,    " +
                "        assetDepreciation.value_type_depreciation, assetDepreciation.amount_rest_months_depreciation,    " +
                "        assetDepreciation.cumulative, assetDepreciation.rest_value, assetDepreciation.time_started_wear_tear,    " +
                "        assetDepreciation.time_end_wear_tear, assetDepreciation.type_calculate, assetDepreciation.time_buy,    " +
                "        assetDepreciation.time_started_used, assetDepreciation.time_started_increase,    " +
                "        assetDepreciation.time_year_tracking, assetDepreciation.time_created, assetDepreciation.time_modified , " +
                "        assetCategory.value_wear_tear, assetCategory.year_used_wear_tear, assetCategory.minimum_time_depreciation, " +
                "        assetCategory.maximum_time_depreciation " +
                "from asset asset       " +
                "     inner join asset_categories assetCategory on asset.id_asset_category = assetCategory.id_asset_category             " +
                "     inner join department de on asset.id_department = de.id_department             " +
                "     inner join document_attack documentAttack on asset.id_document_attack = documentAttack.id_document_attack             " +
                "     inner join location location on asset.id_location = location.id_location             " +
                "     inner join units unit on asset.id_unit = unit.id_unit             " +
                "     inner join projects project on asset.id_projects = project.id_project             " +
                "     left join department departmentDefault on asset.id_department_default = departmentDefault.id_department             " +
                "     left join level_type_asset levelTypeAsset on asset.id_level_type_asset = levelTypeAsset.id_level_type_asset             " +
                "     inner join asset_modules assetModules on asset.id_asset = assetModules.id_asset             " +
                "     inner join modules modules on assetModules.id_module = modules.id_module             " +
                "     inner join asset_original assetOriginal on asset.id_asset = assetOriginal.id_asset             " +
                "     inner join original original on assetOriginal.id_original = original.id_original             " +
                "     inner join asset_declare assetDeclare on asset.id_asset = assetDeclare.id_asset             " +
                "     inner join `declare` decl on assetDeclare.id_declare = decl.id_declare    " +
                "     inner join asset_depreciation assetDepreciation on asset.id_asset = assetDepreciation.id_asset   " +
                "where asset.code_asset = :codeAsset ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("codeAsset", codeAsset);
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
    public Optional<Asset> findAssetByCodeAsset(String codeAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append("select asset.id_asset, asset.name, asset.code_asset,    " +
                "        asset.id_asset_category, asset.id_document_attack,    " +
                "        asset.id_department, asset.id_location,    " +
                "        asset.id_unit, asset.id_projects, asset.description,    " +
                "        asset.purpose, asset.notes, asset.file_attack,    " +
                "        asset.time_created, asset.time_modified, asset.id_department_default,    " +
                "        asset.id_level_type_asset, asset.id_user_created,    " +
                "        asset.id_user_modified, asset.quantity, asset.id_instance,  " +
                "        asset.id_department_origin  " +
                " from asset     " +
                " where asset.code_asset = :codeAsset ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("codeAsset", codeAsset);
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
    public Page<FindAllGroundAssetResponse> findAllGroundAsset(Pageable pageable, FindAllGroundAssetRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select asset.id_asset, asset.code_asset, asset.name " +
                "from asset asset " +
                "    inner join ground_module groundModule on asset.id_asset = groundModule.asset_id " +
                "where 1 = 1 ");
        setConditionFindAllGroundAsset(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllGroundAsset(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllGroundAssetResponse> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                FindAllGroundAssetResponse response = new FindAllGroundAssetResponse();
                response.setIdGroundAsset(ValueUtil.getIntegerByObject(obj[0]));
                response.setCodeGroundAsset(ValueUtil.getStringByObject(obj[1]));
                response.setNameGroundAsset(ValueUtil.getStringByObject(obj[2]));
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
                "          asset.id_asset = groundModule.asset_id  " +
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

    private long countFindAllGroundAsset(FindAllGroundAssetRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "from asset asset " +
                "    inner join ground_module groundModule on asset.id_asset = groundModule.asset_id " +
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
        sb.append(" select count(0) " +
                "from asset asset " +
                "    inner join asset_categories assetCategories " +
                "            on asset.id_asset_category = assetCategories.id_asset_category " +
                "    inner join department de on asset.id_department = de.id_department " +
                "    inner join location lo on asset.id_location = lo.id_location " +
                "where 1 = 1 and asset.id_department_origin in (:idsDepartmentOriginal) ");
        setConditionFindAllAsset(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAsset(request, query);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    private void setParameterFindAllAsset(FindAllAssetRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        if (StringUtils.isNotBlank(request.getNameAsset())){
            query.setParameter("nameAsset", request.getNameAsset());
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            query.setParameter("idAssetCategory", request.getIdAssetCategory());
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            query.setParameter("idDepartment", request.getIdDepartment());
        }
    }

    private void setConditionFindAllAsset(FindAllAssetRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getNameAsset())){
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            sb.append(" and de.id_department = :idDepartment ");
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
