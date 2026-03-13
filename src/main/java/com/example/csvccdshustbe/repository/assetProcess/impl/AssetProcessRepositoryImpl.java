package com.example.csvccdshustbe.repository.assetProcess.impl;

import com.example.csvccdshustbe.dto.asset.FindAllAssetDto;
import com.example.csvccdshustbe.dto.assetProcess.AssetProcessDto;
import com.example.csvccdshustbe.dto.fluctuatingSituationAsset.AssetsFluctuatingSituationAssetDto;
import com.example.csvccdshustbe.dto.process.*;
import com.example.csvccdshustbe.entity.AssetProcess;
import com.example.csvccdshustbe.repository.assetProcess.AssetProcessRepositoryCustom;
import com.example.csvccdshustbe.request.assetProcess.FindAllAssetProcessRequest;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AssetProcessRepositoryImpl implements AssetProcessRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Page<FindAllAssetDto> findAllAssetProcess(FindAllAssetProcessRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select asset.id_asset idAsset, asset.code_asset codeAsset,   " +
                "          asset.name nameAsset, assetCategories.id_asset_category idAssetCategory,   " +
                "          assetCategories.name nameAssetCategory, assetCategories.code_name codeAssetCategory,   " +
                "          de.id_department idDepartment, de.code codeDepartment, de.name nameDepartment,   " +
                "          lo.id_location idLocation, lo.name nameLocation,   " +
                "          asset.time_created, asset.time_modified, asset.parent, asset.salt,   " +
                "          asset.quantity, assetProcess.value, assetProcess.id_asset_process   " +
                "from asset asset   " +
                "       left join asset_process assetProcess on asset.id_asset = assetProcess.id_asset   " +
                "       left join process process on assetProcess.id_process = process.id_process   " +
                "       left join asset_categories assetCategories   " +
                "               on asset.id_asset_category = assetCategories.id_asset_category   " +
                "       left join department de on asset.id_department = de.id_department          " +
                "       left join location lo on asset.id_location = lo.id_location          " +
                "       left join document do on process.id_process = do.id_process           " +
                "where 1 = 1        " +
                "and asset.id_department_origin in (:idsDepartmentOriginal)        " +
                "and do.code = :codeDocument  ");
        setConditionFindAllAssetProcess(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetProcess(request, query);
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
                findAllAssetDto.setValue(ValueUtil.getStringByObject(obj[16]));
                findAllAssetDto.setIdAssetProcess(ValueUtil.getIntegerByObject(obj[17]));
                responses.add(findAllAssetDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllAssetProcess(request));
    }

    @Override
    public Optional<AssetProcess> findAssetProcessByIdProcess(Integer idProcess) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ap.id_asset_process, ap.id_asset, ap.id_process,  " +
                "         ap.id_type_process, ap.status, value, ap.time_created,  " +
                "         ap.time_modified, ap.id_user_created, ap.id_user_modified  " +
                "  from asset_process ap  " +
                "      inner join asset at on ap.id_asset = at.id_asset  " +
                "  where ap.id_process = :idProcess  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idProcess", idProcess);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                AssetProcess assetProcess = new AssetProcess();
                assetProcess.setIdAssetProcess(ValueUtil.getIntegerByObject(obj[0]));
                assetProcess.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                assetProcess.setIdProcess(ValueUtil.getIntegerByObject(obj[2]));
                assetProcess.setIdTypeProcess(ValueUtil.getIntegerByObject(obj[3]));
                assetProcess.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                assetProcess.setValue(ValueUtil.getStringByObject(obj[5]));
                assetProcess.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                assetProcess.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                return Optional.of(assetProcess);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<AssetProcessDto> findAssetProcessDtoByIdProcess(Integer idProcess) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_asset_process, id_asset,  " +
                "       id_process, id_type_process,  " +
                "       status, value,  " +
                "       time_created, time_modified " +
                "from asset_process  " +
                "where id_process = :idProcess ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idProcess", idProcess);
        List<Object[]> result = query.getResultList();
        List<AssetProcessDto> assetProcessDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                AssetProcessDto assetProcessDto = new AssetProcessDto();
                assetProcessDto.setIdAssetProcess(ValueUtil.getIntegerByObject(obj[0]));
                assetProcessDto.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                assetProcessDto.setIdProcess(ValueUtil.getIntegerByObject(obj[2]));
                assetProcessDto.setIdTypeProcess(ValueUtil.getIntegerByObject(obj[3]));
                assetProcessDto.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                assetProcessDto.setValue(ValueUtil.getStringByObject(obj[5]));
                assetProcessDto.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                assetProcessDto.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                assetProcessDtos.add(assetProcessDto);
            }
        }
        return assetProcessDtos;
    }

    @Override
    public List<AssetProcess> findAssetProcessListByIdsAssetAndIdProcess(List<Integer> idsAsset, Integer idProcess) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ap.id_asset_process, ap.id_asset, ap.id_process, " +
                "       ap.id_type_process, ap.status, ap.value, " +
                "       ap.time_created, ap.time_modified, ap.id_user_created, " +
                "       ap.id_user_modified " +
                "from asset_process ap  " +
                "where ap.id_asset in (:idsAsset) " +
                "and ap.id_process = :idProcess ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsAsset", idsAsset);
        query.setParameter("idProcess", idProcess);
        List<Object[]> result = query.getResultList();
        List<AssetProcess> assetProcessList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                AssetProcess assetProcess = new AssetProcess();
                assetProcess.setIdAssetProcess(ValueUtil.getIntegerByObject(obj[0]));
                assetProcess.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                assetProcess.setIdProcess(ValueUtil.getIntegerByObject(obj[2]));
                assetProcess.setIdTypeProcess(ValueUtil.getIntegerByObject(obj[3]));
                assetProcess.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                assetProcess.setValue(ValueUtil.getStringByObject(obj[5]));
                assetProcess.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                assetProcess.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                assetProcess.setIdUserCreated(ValueUtil.getIntegerByObject(obj[8]));
                assetProcess.setIdUserModified(ValueUtil.getIntegerByObject(obj[9]));
                assetProcessList.add(assetProcess);
            }
        }
        return assetProcessList;
    }

    @Override
    public List<AssetProcess> findAllAssetProcessListByIdProcess(Integer idProcess) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ap.id_asset_process, ap.id_asset, ap.id_process, " +
                "       ap.id_type_process, ap.status, ap.value, " +
                "       ap.time_created, ap.time_modified, ap.id_user_created, " +
                "       ap.id_user_modified " +
                "from asset_process ap  " +
                "where 1 = 1 " +
                "and ap.id_process = :idProcess ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idProcess", idProcess);
        List<Object[]> result = query.getResultList();
        List<AssetProcess> assetProcessList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                AssetProcess assetProcess = new AssetProcess();
                assetProcess.setIdAssetProcess(ValueUtil.getIntegerByObject(obj[0]));
                assetProcess.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                assetProcess.setIdProcess(ValueUtil.getIntegerByObject(obj[2]));
                assetProcess.setIdTypeProcess(ValueUtil.getIntegerByObject(obj[3]));
                assetProcess.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                assetProcess.setValue(ValueUtil.getStringByObject(obj[5]));
                assetProcess.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                assetProcess.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                assetProcess.setIdUserCreated(ValueUtil.getIntegerByObject(obj[8]));
                assetProcess.setIdUserModified(ValueUtil.getIntegerByObject(obj[9]));
                assetProcessList.add(assetProcess);
            }
        }
        return assetProcessList;
    }

    @Override
    public List<AssetProcessDto> findResultAssetLotByIdProcessAndCalculatorIsIncreaseAndIsDecrease(Integer idProcess) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select assetParent.id_asset,  " +
                "       case when  " +
                "               (sum(case when assetChild.is_increase = 1 then 1 else 0 end) =  " +
                "                count(assetChild.id_asset))  " +
                "               then 2 else 1 end as resultIsCrease,  " +
                "       case when  " +
                "               (sum(case when assetChild.is_decrease = 1 then 1 else 0 end) =  " +
                "                count(assetChild.id_asset))  " +
                "               then 2 else 1 end as resultIsDecrease  " +
                "from asset assetParent  " +
                "         inner join asset assetChild on assetParent.id_asset = assetChild.parent  " +
                "where exists (  " +
                "    select distinct at.parent  " +
                "                from asset at  " +
                "                    inner join asset_process ap on at.id_asset = ap.id_asset  " +
                "                    inner join process pr on ap.id_process = pr.id_process  " +
                "                where pr.id_process = :idProcess  " +
                "                and at.parent is not null and assetParent.id_asset = at.parent)  " +
                "group by assetParent.id_asset  " +
                "order by assetParent.id_asset ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idProcess", idProcess);
        List<AssetProcessDto> responses = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                AssetProcessDto assetProcessDto = new AssetProcessDto();
                assetProcessDto.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
                assetProcessDto.setIsIncrease(ValueUtil.getIntegerByObject(obj[1]));
                assetProcessDto.setIsDecrease(ValueUtil.getIntegerByObject(obj[2]));
                responses.add(assetProcessDto);
            }
        }
        return responses;
    }

    @Override
    public Page<FindAllAssetDto> findAllAssetLotProcess(FindAllAssetProcessRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select assetParent.id_asset idAsset, assetParent.code_asset codeAsset, " +
                "       assetParent.name nameAsset, assetCategories.id_asset_category idAssetCategory, " +
                "        assetCategories.name nameAssetCategory, assetCategories.code_name codeAssetCategory, " +
                "       assetParent.time_created, assetParent.time_modified, assetParent.salt, " +
                "       assetProcess.value " +
                " from asset asset " +
                "     left join asset_process assetProcess on asset.id_asset = assetProcess.id_asset " +
                "     left join process process on assetProcess.id_process = process.id_process " +
                "     left join asset_categories assetCategories " +
                "             on asset.id_asset_category = assetCategories.id_asset_category " +
                "     left join document do on process.id_process = do.id_process " +
                "     inner join asset assetParent on asset.parent = assetParent.id_asset " +
                " where 1 = 1          " +
                " and asset.id_department_origin in (:idsDepartmentOriginal)          " +
                " and do.code = :codeDocument ");
        setConditionFindAllAssetLotProcess(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetLotProcess(query, request);
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
                findAllAssetDto.setTimeCreated(ValueUtil.getLongByObject(obj[6]));
                findAllAssetDto.setTimeModified(ValueUtil.getLongByObject(obj[7]));
                findAllAssetDto.setSalt(ValueUtil.getStringByObject(obj[8]));
                findAllAssetDto.setValue(ValueUtil.getStringByObject(obj[9]));
                responses.add(findAllAssetDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllAssetLotProcess(request));
    }

    @Override
    public Page<FindAllAssetDto> findAllAssetChildrenProcess(FindAllAssetProcessRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select asset.id_asset idAsset, asset.code_asset codeAsset,      " +
                "        asset.name nameAsset, assetCategories.id_asset_category idAssetCategory,      " +
                "        assetCategories.name nameAssetCategory, assetCategories.code_name codeAssetCategory,      " +
                "        de.id_department idDepartment, de.code codeDepartment, de.name nameDepartment,      " +
                "        lo.id_location idLocation, lo.name nameLocation,      " +
                "        asset.time_created, asset.time_modified, asset.parent, asset.salt,      " +
                "        asset.quantity, assetProcess.value, assetProcess.id_asset_process, assetProcess.status,   " +
                "        asset.is_increase " +
                "from asset asset  " +
                "         left join asset_process assetProcess on asset.id_asset = assetProcess.id_asset  " +
                "         left join process process on assetProcess.id_process = process.id_process  " +
                "         left join asset_categories assetCategories  " +
                "                   on asset.id_asset_category = assetCategories.id_asset_category  " +
                "         left join department de on asset.id_department = de.id_department  " +
                "         left join location lo on asset.id_location = lo.id_location  " +
                "         left join document do on process.id_process = do.id_process  " +
                "         inner join asset assetParent on asset.parent = assetParent.id_asset  " +
                "where 1 = 1  " +
                "  and asset.id_department_origin in (:idsDepartmentOriginal)  " +
                "  and do.code = :codeDocument    " +
                "  and assetParent.salt = :salt ");
        setConditionFindAllAssetChildrenProcess(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetChildrenProcess(request, query);
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
                findAllAssetDto.setValue(ValueUtil.getStringByObject(obj[16]));
                findAllAssetDto.setIdAssetProcess(ValueUtil.getIntegerByObject(obj[17]));
                findAllAssetDto.setStatusAssetProcess(ValueUtil.getIntegerByObject(obj[18]));
                findAllAssetDto.setIsIncrease(ValueUtil.getIntegerByObject(obj[19]));
                responses.add(findAllAssetDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllAssetChildrenProcess(request));
    }

    @Override
    public Page<FindAllAssetParentToUpdateInventoryDto>
    findALlAssetProcessToUpdateInventory(FindAllAssetProcessRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH ROOT_ASSET_CATEGORIES as  " +
                "         (WITH RECURSIVE cte_asset_categories as  " +
                "           (select assetCategires.id_asset_category,  " +
                "                   assetCategires.name,  " +
                "                   assetCategires.code_name,  " +
                "                   1           as depth,  " +
                "                   CAST(assetCategires.id_asset_category as NCHAR) as path,  " +
                "                   assetCategires.number_code_pattern,  " +
                "                   assetCategires.id_department_original,  " +
                "                   assetCategires.type_target,  " +
                "                   assetCategires.parent  " +
                "            from asset_categories assetCategires  " +
                "            where assetCategires.parent is null  " +
                "              and assetCategires.visible = :visible  " +
                "            union all  " +
                "            select assetCategires.id_asset_category,  " +
                "                   assetCategires.name,  " +
                "                   assetCategires.code_name,  " +
                "                   cte.depth + 1          as depth,  " +
                "                   concat_ws('/', cte.path,  " +
                "           CAST(assetCategires.id_asset_category as NCHAR)) as path,  " +
                "                   assetCategires.number_code_pattern,  " +
                "                   assetCategires.id_department_original,  " +
                "                   assetCategires.type_target,  " +
                "                   assetCategires.parent  " +
                "            from asset_categories assetCategires  " +
                "                     INNER JOIN cte_asset_categories cte  " +
                "              ON assetCategires.parent = cte.id_asset_category)  " +
                "          select cte.id_asset_category,  " +
                "                 cte.name,  " +
                "                 cte.code_name,  " +
                "                 cte.depth,  " +
                "                 cte.path,  " +
                "                 cte.number_code_pattern,  " +
                "                 group_concat(un.name SEPARATOR '/') as unitMeasure,  " +
                "                 cte.parent,  " +
                "                 CASE  " +
                "   WHEN EXISTS (SELECT 1  " +
                "                FROM asset_categories ac  " +
                "                WHERE ac.parent = cte.id_asset_category) THEN 0  " +
                "   ELSE 1 END    AS is_leaf,  " +
                "                 cte.type_target  " +
                "          from cte_asset_categories cte  " +
                "                   left join (select un.id_asset_category, un.id_unit, un.name  " +
                "            from units un  " +
                "            where un.is_display = :isDisplay) un  " +
                "           on cte.id_asset_category = un.id_asset_category  " +
                "          where 1 = 1  " +
//                "            and cte.id_department_original in (:idsDepartmentOriginal)  " +
                "          group by cte.id_asset_category, cte.name, cte.code_name,  " +
                "                   cte.depth, cte.path, cte.number_code_pattern, is_leaf, cte.type_target  " +
                "          order by cte.path),  " +
                "     ROOT_ASSET_PROCESS as (  " +
                "         select asset.id_asset idAsset, assetCategories.id_asset_category idAssetCategory,  " +
                "                assetProcess.value, asset.salt, assetProcess.id_asset_process, assetProcess.status,  " +
                "                asset.is_increase " +
                "         from asset asset  " +
                "                  left join asset_process assetProcess on asset.id_asset = assetProcess.id_asset  " +
                "                  left join process process on assetProcess.id_process = process.id_process  " +
                "                  left join asset_categories assetCategories  " +
                "                       on asset.id_asset_category = assetCategories.id_asset_category  " +
                "                  left join department de on asset.id_department = de.id_department  " +
                "                  left join location lo on asset.id_location = lo.id_location  " +
                "                  left join document do on process.id_process = do.id_process  " +
                "         where 1 = 1  " +
//                "           and asset.id_department_origin in (:idsDepartmentOriginal)  " +
                "           and do.code = :codeDocument  and asset.parent is null ");
        setConditionFindAllAssetProcessToUpdateInventory(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetProcessToUpdateInventory(query, request);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        ListOrderedMap<Integer, FindAllAssetParentToUpdateInventoryDto> assetMap = new ListOrderedMap<>();
        Integer idAssetCategory, idAsset;
        int index = 0;
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                idAssetCategory = ValueUtil.getIntegerByObject(obj[0]);
                idAsset = obj[9] == null ? null : ValueUtil.getIntegerByObject(obj[9]);
                if (idAsset != null) {
                    if (!assetMap.containsKey(idAssetCategory)) {
                        FindAllAssetParentToUpdateInventoryDto parentToInventoryDto = new FindAllAssetParentToUpdateInventoryDto(obj);
                        assetMap.put(index,idAssetCategory,parentToInventoryDto);
                        ++index;
                    }
                    if (assetMap.containsKey(idAssetCategory)){
                        assetMap.computeIfPresent(idAssetCategory, (k, v) -> {
                            v.getAssetLeaves().add(new FindAllAssetChildrenToUpdateInventoryDto(obj));
                            return v;
                        });
                    }
                } else {
                    FindAllAssetParentToUpdateInventoryDto parentToInventoryDto = new FindAllAssetParentToUpdateInventoryDto(obj);
                    assetMap.put(index,idAssetCategory,parentToInventoryDto);
                    ++index;
                }
            }
        }
        return new PageImpl<>(assetMap.valueList(), pageable, countFindAllAssetProcessToUpdateInventory(request));
    }

    @Override
    public Page<FindAllAssetLotParentToUpdateInventoryDto>
    findALlAssetProcessLotToUpdateInventory(FindAllAssetProcessRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH ROOT_ASSET_CATEGORIES as  " +
                "         (WITH RECURSIVE cte_asset_categories as  " +
                "           (select assetCategires.id_asset_category,  " +
                "   assetCategires.name,  " +
                "   assetCategires.code_name,  " +
                "   1           as depth,  " +
                "   CAST(assetCategires.id_asset_category as NCHAR) as path,  " +
                "   assetCategires.number_code_pattern,  " +
                "   assetCategires.id_department_original,  " +
                "   assetCategires.type_target,  " +
                "   assetCategires.parent  " +
                "            from asset_categories assetCategires  " +
                "            where assetCategires.parent is null  " +
                "              and assetCategires.visible = :visible  " +
                "            union all  " +
                "            select assetCategires.id_asset_category,  " +
                "   assetCategires.name,  " +
                "   assetCategires.code_name,  " +
                "   cte.depth + 1          as depth,  " +
                "   concat_ws('/', cte.path,  " +
                "           CAST(assetCategires.id_asset_category as NCHAR)) as path,  " +
                "   assetCategires.number_code_pattern,  " +
                "   assetCategires.id_department_original,  " +
                "   assetCategires.type_target,  " +
                "   assetCategires.parent  " +
                "            from asset_categories assetCategires  " +
                "     INNER JOIN cte_asset_categories cte  " +
                "              ON assetCategires.parent = cte.id_asset_category)  " +
                "          select cte.id_asset_category,  " +
                "                 cte.name,  " +
                "                 cte.code_name,  " +
                "                 cte.depth,  " +
                "                 cte.path,  " +
                "                 cte.number_code_pattern,  " +
                "                 group_concat(un.name SEPARATOR '/') as unitMeasure,  " +
                "                 cte.parent,  " +
                "                 CASE  " +
                "   WHEN EXISTS (SELECT 1  " +
                "                FROM asset_categories ac  " +
                "                WHERE ac.parent = cte.id_asset_category) THEN 0  " +
                "   ELSE 1 END    AS is_leaf,  " +
                "                 cte.type_target  " +
                "          from cte_asset_categories cte  " +
                "   left join (select un.id_asset_category, un.id_unit, un.name  " +
                "            from units un  " +
                "            where un.is_display = :isDisplay) un  " +
                "           on cte.id_asset_category = un.id_asset_category  " +
                "          where 1 = 1  " +
//                "            and cte.id_department_original in (:idsDepartmentOriginal)  " +
                "          group by cte.id_asset_category, cte.name, cte.code_name,  " +
                "   cte.depth, cte.path, cte.number_code_pattern, is_leaf, cte.type_target  " +
                "          order by cte.path),  " +
                "     ROOT_ASSET_PROCESS as (  " +
                "         select assetParent.id_asset idAsset,assetCategories.id_asset_category idAssetCategory,  " +
                "                assetParent.salt, assetParent.name  " +
                "         from asset asset  " +
                "                  left join asset_process assetProcess on asset.id_asset = assetProcess.id_asset  " +
                "                  left join process process on assetProcess.id_process = process.id_process  " +
                "                  left join asset_categories assetCategories  " +
                "                            on asset.id_asset_category = assetCategories.id_asset_category  " +
                "                  left join department de on asset.id_department = de.id_department  " +
                "                  left join location lo on asset.id_location = lo.id_location  " +
                "                  left join document do on process.id_process = do.id_process  " +
                "                  inner join asset assetParent on asset.parent = assetParent.id_asset  " +
                "         where 1 = 1  " +
//                "           and asset.id_department_origin in (:idsDepartmentOriginal)  " +
                "           and do.code = :codeDocument ");
        setConditionFindAllAssetProcessLotToUpdateInventory(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetProcessLotToUpdateInventory(query, request);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        ListOrderedMap<Integer, FindAllAssetLotParentToUpdateInventoryDto> assetMap = new ListOrderedMap<>();
        Integer idAssetCategory, idAsset;
        int index = 0;
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                idAssetCategory = ValueUtil.getIntegerByObject(obj[0]);
                idAsset = obj[9] == null ? null : ValueUtil.getIntegerByObject(obj[9]);
                if (idAsset != null) {
                    if (!assetMap.containsKey(idAssetCategory)) {
                        FindAllAssetLotParentToUpdateInventoryDto parentToInventoryDto = new FindAllAssetLotParentToUpdateInventoryDto(obj);
                        assetMap.put(index,idAssetCategory,parentToInventoryDto);
                        ++index;
                    }
                    if (assetMap.containsKey(idAssetCategory)){
                        assetMap.computeIfPresent(idAssetCategory, (k, v) -> {
                            v.getAssetLeaves().add(new FindAllAssetLotChildrenToUpdateInventoryDto(obj));
                            return v;
                        });
                    }
                } else {
                    FindAllAssetLotParentToUpdateInventoryDto parentToInventoryDto = new FindAllAssetLotParentToUpdateInventoryDto(obj);
                    assetMap.put(index,idAssetCategory,parentToInventoryDto);
                    ++index;
                }
            }
        }
        return new PageImpl<>(assetMap.valueList(), pageable, countFindAllAssetProcessLotToUpdateInventory(request));
    }

    @Override
    public Optional<AssetProcess> findAssetProcessByIdAssetProcess(Integer idAssetProcess) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_asset_process,   " +
                "       id_asset,   " +
                "       id_process,   " +
                "       id_type_process,   " +
                "       status,   " +
                "       value,   " +
                "       time_created,   " +
                "       time_modified,   " +
                "       id_user_created,   " +
                "       id_user_modified   " +
                "from asset_process   " +
                "where id_asset_process = :idAssetProcess ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAssetProcess", idAssetProcess);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                AssetProcess assetProcess = new AssetProcess();
                assetProcess.setIdAssetProcess(ValueUtil.getIntegerByObject(obj[0]));
                assetProcess.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                assetProcess.setIdProcess(ValueUtil.getIntegerByObject(obj[2]));
                assetProcess.setIdTypeProcess(ValueUtil.getIntegerByObject(obj[3]));
                assetProcess.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                assetProcess.setValue(ValueUtil.getStringByObject(obj[5]));
                assetProcess.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                assetProcess.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                assetProcess.setIdUserCreated(ValueUtil.getIntegerByObject(obj[8]));
                assetProcess.setIdUserModified(ValueUtil.getIntegerByObject(obj[9]));
                return Optional.of(assetProcess);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<AssetsFluctuatingSituationAssetDto> findAssetsToFluctuatingSituationByIdProcess(Integer idProcess) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_asset, value, status " +
                "from asset_process where id_process = :idProcess " +
                "and asset_process.status in (:statusFluctuationSituation) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idProcess", idProcess);
        query.setParameter("statusFluctuationSituation", Arrays.asList(
                Constants.TYPE_FLUCTUATING_SITUATION_DECLARE,
                Constants.TYPE_FLUCTUATING_SITUATION_INCREASE,
                Constants.TYPE_FLUCTUATING_SITUATION_DECREASE));
        List<AssetsFluctuatingSituationAssetDto> fluctuatingSituationAssetDtos = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result) {
                AssetsFluctuatingSituationAssetDto situationAssetDto = new AssetsFluctuatingSituationAssetDto();
                situationAssetDto.setIdAsset(ValueUtil.getIntegerByObject(obj[0]));
                situationAssetDto.setValue(ValueUtil.getStringByObject(obj[1]));
                situationAssetDto.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                if (ValueUtil.getIntegerByObject(obj[2]).equals(Constants.TYPE_FLUCTUATING_SITUATION_DECLARE)) {
                    situationAssetDto.setTypeFluctuatingSituationAsset(Constants.TYPE_FLUCTUATING_SITUATION_DECLARE);
                } else if (ValueUtil.getIntegerByObject(obj[2]).equals(Constants.TYPE_FLUCTUATING_SITUATION_INCREASE)) {
                    situationAssetDto.setTypeFluctuatingSituationAsset(Constants.TYPE_FLUCTUATING_SITUATION_INCREASE);
                } else if (ValueUtil.getIntegerByObject(obj[2]).equals(Constants.TYPE_FLUCTUATING_SITUATION_DECREASE)) {
                    situationAssetDto.setTypeFluctuatingSituationAsset(Constants.TYPE_FLUCTUATING_SITUATION_DECREASE);
                }
                fluctuatingSituationAssetDtos.add(situationAssetDto);
            }
        }
        return fluctuatingSituationAssetDtos;
    }

    @Override
    public List<AssetProcess> findListAssetProcessDtoByIdProcess(Integer idProcess) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ap.id_asset_process, ap.id_asset,  " +
                "       ap.id_process,ap.id_type_process,  " +
                "       ap.status, ap.value,  " +
                "       ap.time_created, ap.time_modified,   " +
                "       ap.id_user_created,ap.id_user_modified  " +
                "       from asset_process  ap  " +
                "       where id_process = :idProcess");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idProcess", idProcess);
        List<Object[]> result = query.getResultList();
        List<AssetProcess> assetProcessDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                AssetProcess assetProcessDto = new AssetProcess();
                assetProcessDto.setIdAssetProcess(ValueUtil.getIntegerByObject(obj[0]));
                assetProcessDto.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                assetProcessDto.setIdProcess(ValueUtil.getIntegerByObject(obj[2]));
                assetProcessDto.setIdTypeProcess(ValueUtil.getIntegerByObject(obj[3]));
                assetProcessDto.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                assetProcessDto.setValue(ValueUtil.getStringByObject(obj[5]));
                assetProcessDto.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                assetProcessDto.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                assetProcessDto.setIdUserCreated(ValueUtil.getIntegerByObject(obj[8]));
                assetProcessDto.setIdUserModified(ValueUtil.getIntegerByObject(obj[9]));
                assetProcessDtos.add(assetProcessDto);
            }
        }
        return assetProcessDtos;
    }

    private long countFindAllAssetProcessLotToUpdateInventory(FindAllAssetProcessRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH ROOT_ASSET_CATEGORIES as   " +
                "         (WITH RECURSIVE cte_asset_categories as   " +
                "           (select assetCategires.id_asset_category,   " +
                "   assetCategires.name,   " +
                "   assetCategires.code_name,   " +
                "   1           as depth,   " +
                "   CAST(assetCategires.id_asset_category as NCHAR) as path,   " +
                "   assetCategires.number_code_pattern,   " +
                "   assetCategires.id_department_original,   " +
                "   assetCategires.type_target,   " +
                "   assetCategires.parent   " +
                "            from asset_categories assetCategires   " +
                "            where assetCategires.parent is null   " +
                "              and assetCategires.visible = :visible   " +
                "            union all   " +
                "            select assetCategires.id_asset_category,   " +
                "   assetCategires.name,   " +
                "   assetCategires.code_name,   " +
                "   cte.depth + 1          as depth,   " +
                "   concat_ws('/', cte.path,   " +
                "           CAST(assetCategires.id_asset_category as NCHAR)) as path,   " +
                "   assetCategires.number_code_pattern,   " +
                "   assetCategires.id_department_original,   " +
                "   assetCategires.type_target,   " +
                "   assetCategires.parent   " +
                "            from asset_categories assetCategires   " +
                "     INNER JOIN cte_asset_categories cte   " +
                "              ON assetCategires.parent = cte.id_asset_category)   " +
                "          select cte.id_asset_category,   " +
                "                 cte.name,   " +
                "                 cte.code_name,   " +
                "                 cte.depth,   " +
                "                 cte.path,   " +
                "                 cte.number_code_pattern,   " +
                "                 group_concat(un.name SEPARATOR '/') as unitMeasure,   " +
                "                 cte.parent,   " +
                "                 CASE   " +
                "   WHEN EXISTS (SELECT 1   " +
                "                FROM asset_categories ac   " +
                "                WHERE ac.parent = cte.id_asset_category) THEN 0   " +
                "   ELSE 1 END    AS is_leaf,   " +
                "                 cte.type_target   " +
                "          from cte_asset_categories cte   " +
                "   left join (select un.id_asset_category, un.id_unit, un.name   " +
                "            from units un   " +
                "            where un.is_display = :isDisplay) un   " +
                "           on cte.id_asset_category = un.id_asset_category   " +
                "          where 1 = 1   " +
//                "            and cte.id_department_original in (:idsDepartmentOriginal)   " +
                "          group by cte.id_asset_category, cte.name, cte.code_name,   " +
                "   cte.depth, cte.path, cte.number_code_pattern, is_leaf, cte.type_target   " +
                "          order by cte.path),   " +
                "     ROOT_ASSET_PROCESS as (   " +
                "         select assetParent.id_asset idAsset,assetCategories.id_asset_category idAssetCategory,   " +
                "                assetParent.salt   " +
                "         from asset asset   " +
                "                  left join asset_process assetProcess on asset.id_asset = assetProcess.id_asset   " +
                "                  left join process process on assetProcess.id_process = process.id_process   " +
                "                  left join asset_categories assetCategories   " +
                "                            on asset.id_asset_category = assetCategories.id_asset_category   " +
                "                  left join department de on asset.id_department = de.id_department   " +
                "                  left join location lo on asset.id_location = lo.id_location   " +
                "                  left join document do on process.id_process = do.id_process   " +
                "                  inner join asset assetParent on asset.parent = assetParent.id_asset   " +
                "         where 1 = 1   " +
//                "           and asset.id_department_origin in (:idsDepartmentOriginal)   " +
                "           and do.code = :codeDocument ");
        setCountConditionFindAllAssetProcessLotToUpdateInventory(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetProcessLotToUpdateInventory(query, request);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    private void setCountConditionFindAllAssetProcessLotToUpdateInventory(StringBuilder sb, FindAllAssetProcessRequest request) {
        if (StringUtils.isNotBlank(request.getNameAsset())){
            sb.append(" and (assetParent.name REGEXP :nameAsset)  ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            sb.append(" and de.id_department = :idDepartment ");
        }
        sb.append(" group by assetParent.id_asset, assetCategories.id_asset_category,   " +
                "                  assetParent.salt   " +
                "     )   " +
                "select count(0)   " +
                "from ROOT_ASSET_CATEGORIES rootAssetCategories   " +
                "         left join ROOT_ASSET_PROCESS rootAssetProcess" +
                "  on rootAssetCategories.id_asset_category = rootAssetProcess.idAssetCategory   " +
                "order by rootAssetCategories.path ");
    }

    private void setParameterFindAllAssetProcessLotToUpdateInventory(Query query, FindAllAssetProcessRequest request) {
        query.setParameter("visible", Constants.ASSET_CATEGORY_IS_VISIBLE);
        query.setParameter("isDisplay", Constants.UNITES_IS_DISPLAY);
//        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("codeDocument", request.getCodeDocument());
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

    private void setConditionFindAllAssetProcessLotToUpdateInventory(StringBuilder sb, FindAllAssetProcessRequest request) {
        if (StringUtils.isNotBlank(request.getNameAsset())){
            sb.append(" and (assetParent.name REGEXP :nameAsset)  ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            sb.append(" and de.id_department = :idDepartment ");
        }
        sb.append(" group by assetParent.id_asset, assetCategories.id_asset_category,   " +
                "                  assetParent.salt, assetParent.name   " +
                "     )   " +
                "select rootAssetCategories.id_asset_category   as idAssetCategory,   " +
                "       rootAssetCategories.name                as nameAssetCategory,   " +
                "       rootAssetCategories.parent              as idParentAssetCategory,   " +
                "       rootAssetCategories.code_name           as codeAssetCategory,   " +
                "       rootAssetCategories.depth               as depth,   " +
                "       rootAssetCategories.path                as path,   " +
                "       rootAssetCategories.number_code_pattern as numberCodePattern,   " +
                "       rootAssetCategories.is_leaf             as isLeaf,   " +
                "       rootAssetCategories.type_target         as targetType,   " +
                "       rootAssetProcess.idAsset                as idAsset,   " +
                "       rootAssetProcess.salt                   as salt,   " +
                "       rootAssetProcess.name                   as nameAssetParent " +
                "from ROOT_ASSET_CATEGORIES rootAssetCategories   " +
                "         left join ROOT_ASSET_PROCESS rootAssetProcess on rootAssetCategories.id_asset_category = rootAssetProcess.idAssetCategory   " +
                "order by rootAssetCategories.path ");
    }

    private long countFindAllAssetProcessToUpdateInventory(FindAllAssetProcessRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH ROOT_ASSET_CATEGORIES as  " +
                "         (WITH RECURSIVE cte_asset_categories as  " +
                "           (select assetCategires.id_asset_category,  " +
                "                   assetCategires.name,  " +
                "                   assetCategires.code_name,  " +
                "                   1           as depth,  " +
                "                   CAST(assetCategires.id_asset_category as NCHAR) as path,  " +
                "                   assetCategires.number_code_pattern,  " +
                "                   assetCategires.id_department_original,  " +
                "                   assetCategires.type_target,  " +
                "                   assetCategires.parent  " +
                "            from asset_categories assetCategires  " +
                "            where assetCategires.parent is null  " +
                "              and assetCategires.visible = :visible  " +
                "            union all  " +
                "            select assetCategires.id_asset_category,  " +
                "                   assetCategires.name,  " +
                "                   assetCategires.code_name,  " +
                "                   cte.depth + 1          as depth,  " +
                "                   concat_ws('/', cte.path,  " +
                "           CAST(assetCategires.id_asset_category as NCHAR)) as path,  " +
                "                   assetCategires.number_code_pattern,  " +
                "                   assetCategires.id_department_original,  " +
                "                   assetCategires.type_target,  " +
                "                   assetCategires.parent  " +
                "            from asset_categories assetCategires  " +
                "                     INNER JOIN cte_asset_categories cte  " +
                "              ON assetCategires.parent = cte.id_asset_category)  " +
                "          select cte.id_asset_category,  " +
                "                 cte.name,  " +
                "                 cte.code_name,  " +
                "                 cte.depth,  " +
                "                 cte.path,  " +
                "                 cte.number_code_pattern,  " +
                "                 group_concat(un.name SEPARATOR '/') as unitMeasure,  " +
                "                 cte.parent,  " +
                "                 CASE  " +
                "   WHEN EXISTS (SELECT 1  " +
                "                FROM asset_categories ac  " +
                "                WHERE ac.parent = cte.id_asset_category) THEN 0  " +
                "   ELSE 1 END    AS is_leaf,  " +
                "                 cte.type_target  " +
                "          from cte_asset_categories cte  " +
                "                   left join (select un.id_asset_category, un.id_unit, un.name  " +
                "            from units un  " +
                "            where un.is_display = :isDisplay) un  " +
                "           on cte.id_asset_category = un.id_asset_category  " +
                "          where 1 = 1  " +
//                "            and cte.id_department_original in (:idsDepartmentOriginal)  " +
                "          group by cte.id_asset_category, cte.name, cte.code_name,  " +
                "                   cte.depth, cte.path, cte.number_code_pattern, is_leaf, cte.type_target  " +
                "          order by cte.path),  " +
                "     ROOT_ASSET_PROCESS as (  " +
                "         select asset.id_asset idAsset, assetCategories.id_asset_category idAssetCategory,  " +
                "                assetProcess.value, asset.salt, assetProcess.id_asset_process, assetProcess.status  " +
                "         from asset asset  " +
                "                  left join asset_process assetProcess on asset.id_asset = assetProcess.id_asset  " +
                "                  left join process process on assetProcess.id_process = process.id_process  " +
                "                  left join asset_categories assetCategories  " +
                "                       on asset.id_asset_category = assetCategories.id_asset_category  " +
                "                  left join department de on asset.id_department = de.id_department  " +
                "                  left join location lo on asset.id_location = lo.id_location  " +
                "                  left join document do on process.id_process = do.id_process  " +
                "         where 1 = 1  " +
//                "           and asset.id_department_origin in (:idsDepartmentOriginal)  " +
                "           and do.code = :codeDocument  and asset.parent is null ");
        setCountConditionFindAllAssetProcessToUpdateInventory(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetProcessToUpdateInventory(query, request);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    private void setParameterFindAllAssetProcessToUpdateInventory(Query query, FindAllAssetProcessRequest request) {
        query.setParameter("visible", Constants.ASSET_CATEGORY_IS_VISIBLE);
        query.setParameter("isDisplay", Constants.UNITES_IS_DISPLAY);
//        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("codeDocument", request.getCodeDocument());
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

    private void setConditionFindAllAssetProcessToUpdateInventory(StringBuilder sb, FindAllAssetProcessRequest request) {
        if (StringUtils.isNotBlank(request.getNameAsset())){
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            sb.append(" and de.id_department = :idDepartment ");
        }
        sb.append("      )  " +
                "select rootAssetCategories.id_asset_category   as idAssetCategory,  " +
                "       rootAssetCategories.name                as nameAssetCategory,  " +
                "       rootAssetCategories.parent              as idParentAssetCategory,  " +
                "       rootAssetCategories.code_name           as codeAssetCategory,  " +
                "       rootAssetCategories.depth               as depth,  " +
                "       rootAssetCategories.path                as path,  " +
                "       rootAssetCategories.number_code_pattern as numberCodePattern,  " +
                "       rootAssetCategories.is_leaf             as isLeaf,  " +
                "       rootAssetCategories.type_target         as targetType,  "+
                "       rootAssetProcess.idAsset                as idAsset,  " +
                "       rootAssetProcess.salt                   as salt,  " +
                "       rootAssetProcess.value                  as valueAssetProcess,  " +
                "       rootAssetProcess.id_asset_process       as idAssetProcess, " +
                "       rootAssetProcess.status                 as status, " +
                "       rootAssetProcess.is_increase            as isIncrease " +
                "from ROOT_ASSET_CATEGORIES rootAssetCategories  " +
                "         left join ROOT_ASSET_PROCESS rootAssetProcess on rootAssetCategories.id_asset_category = rootAssetProcess.idAssetCategory  " +
                "order by rootAssetCategories.path ");
    }

    private void setCountConditionFindAllAssetProcessToUpdateInventory(StringBuilder sb, FindAllAssetProcessRequest request) {
        if (StringUtils.isNotBlank(request.getNameAsset())){
            sb.append(" and (asset.name REGEXP :nameAsset ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            sb.append(" and de.id_department = :idDepartment ");
        }
        sb.append("      )  " +
                "select count(0)  " +
                "from ROOT_ASSET_CATEGORIES rootAssetCategories  " +
                "         left join ROOT_ASSET_PROCESS rootAssetProcess on rootAssetCategories.id_asset_category = rootAssetProcess.idAssetCategory  " +
                "order by rootAssetCategories.path ");
    }

    private long countFindAllAssetChildrenProcess(FindAllAssetProcessRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0)  " +
                "from asset asset  " +
                "         left join asset_process assetProcess on asset.id_asset = assetProcess.id_asset  " +
                "         left join process process on assetProcess.id_process = process.id_process  " +
                "         left join asset_categories assetCategories  " +
                "                   on asset.id_asset_category = assetCategories.id_asset_category  " +
                "         left join department de on asset.id_department = de.id_department  " +
                "         left join location lo on asset.id_location = lo.id_location  " +
                "         left join document do on process.id_process = do.id_process  " +
                "         inner join asset assetParent on asset.parent = assetParent.id_asset  " +
                "where 1 = 1  " +
                "  and asset.id_department_origin in (:idsDepartmentOriginal)  " +
                "  and do.code = :codeDocument  " +
                "  and assetParent.salt = :salt ");
        setConditionFindAllAssetChildrenProcess(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetChildrenProcess(request, query);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    private long countFindAllAssetLotProcess(FindAllAssetProcessRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) from  " +
                " (select count(0)  " +
                " from asset asset   " +
                "    left join asset_process assetProcess on asset.id_asset = assetProcess.id_asset   " +
                "    left join process process on assetProcess.id_process = process.id_process   " +
                "    left join asset_categories assetCategories   " +
                "            on asset.id_asset_category = assetCategories.id_asset_category   " +
                "    left join document do on process.id_process = do.id_process   " +
                "    inner join asset assetParent on asset.parent = assetParent.id_asset   " +
                "where 1 = 1  " +
                "  and asset.id_department_origin in (:idsDepartmentOriginal)  " +
                "  and do.code = :codeDocument ");
        setConditionCountFindAllAssetLotProcess(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetLotProcess(query, request);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    private void setParameterFindAllAssetLotProcess(Query query, FindAllAssetProcessRequest request) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("codeDocument", request.getCodeDocument());
        if (StringUtils.isNotBlank(request.getNameAsset())) {
            query.setParameter("nameAsset", request.getNameAsset());
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            query.setParameter("idAssetCategory", request.getIdAssetCategory());
        }
    }

    private void setConditionFindAllAssetLotProcess(StringBuilder sb, FindAllAssetProcessRequest request) {
        if (StringUtils.isNotBlank(request.getNameAsset())){
            sb.append(" and (assetParent.name REGEXP :nameAsset)  ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }
        sb.append(" group by assetParent.id_asset, assetParent.code_asset, " +
                "         assetParent.name, assetCategories.id_asset_category, " +
                "         assetCategories.name, assetCategories.code_name, " +
                " assetParent.time_created, " +
                "         assetParent.time_modified, assetParent.salt ");
        if (StringUtils.isNotBlank(request.getSortBy())){
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY assetParent.id_asset desc ");
        }
    }
    private void setConditionCountFindAllAssetLotProcess(StringBuilder sb, FindAllAssetProcessRequest request) {
        if (StringUtils.isNotBlank(request.getNameAsset())){
            sb.append(" and (assetParent.name REGEXP :nameAsset)  ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            sb.append(" and assetCategories.id_asset_category = :idAssetCategory ");
        }

        sb.append(" group by assetParent.id_asset, assetParent.code_asset, " +
                "         assetParent.name, assetCategories.id_asset_category, " +
                "         assetCategories.name, assetCategories.code_name, " +
                "         assetParent.time_created, " +
                "         assetParent.time_modified, assetParent.salt ");
        if (StringUtils.isNotBlank(request.getSortBy())){
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameAsset")) {
                sb.append(" asset.name ");
            }

            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY assetParent.id_asset desc ");
        }
        sb.append(" ) as result ");
    }


    private long countFindAllAssetProcess(FindAllAssetProcessRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("  select count(0)   " +
                "from asset asset   " +
                "       left join asset_process assetProcess on asset.id_asset = assetProcess.id_asset   " +
                "       left join process process on assetProcess.id_process = process.id_process   " +
                "       left join asset_categories assetCategories   " +
                "               on asset.id_asset_category = assetCategories.id_asset_category   " +
                "       left join department de on asset.id_department = de.id_department   " +
                "       left join location lo on asset.id_location = lo.id_location   " +
                "       left join document do on process.id_process = do.id_process   " +
                "where 1 = 1   " +
                "and asset.id_department_origin in (:idsDepartmentOriginal)   " +
                "and do.code = :codeDocument   ");
        setConditionFindAllAssetProcess(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetProcess(request, query);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    private void setConditionFindAllAssetProcess(FindAllAssetProcessRequest request, StringBuilder sb) {
        if(request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_SINGLE)){
            sb.append("  and asset.quantity = :isSingle and asset.parent is null  ");
        }
        if(request.getTypeSearch().equals(Constants.FIND_ALL_ASSET_ALLOCATE)){
            sb.append("  and asset.quantity = :isSingle and asset.parent is not null  ");
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

    private void setConditionFindAllAssetChildrenProcess(FindAllAssetProcessRequest request, StringBuilder sb) {
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

    private void setParameterFindAllAssetProcess(FindAllAssetProcessRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("codeDocument", request.getCodeDocument());
        query.setParameter("isSingle", Constants.QUANTITY_DEFAULT);
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

    private void setParameterFindAllAssetChildrenProcess(FindAllAssetProcessRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("codeDocument", request.getCodeDocument());
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
}
