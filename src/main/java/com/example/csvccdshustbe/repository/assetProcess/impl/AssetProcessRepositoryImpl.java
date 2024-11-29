package com.example.csvccdshustbe.repository.assetProcess.impl;

import com.example.csvccdshustbe.dto.asset.FindAllAssetDto;
import com.example.csvccdshustbe.dto.assetProcess.AssetProcessDto;
import com.example.csvccdshustbe.entity.AssetProcess;
import com.example.csvccdshustbe.repository.assetProcess.AssetProcessRepositoryCustom;
import com.example.csvccdshustbe.request.assetProcess.FindAllAssetProcessRequest;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AssetProcessRepositoryImpl implements AssetProcessRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Page<FindAllAssetDto> findAllAssetProcess(FindAllAssetProcessRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select asset.id_asset idAsset, asset.code_asset codeAsset, " +
                "        asset.name nameAsset, assetCategories.id_asset_category idAssetCategory, " +
                "        assetCategories.name nameAssetCategory, assetCategories.code_name codeAssetCategory, " +
                "        de.id_department idDepartment, de.code codeDepartment, de.name nameDepartment, " +
                "        lo.id_location idLocation, lo.name nameLocation, " +
                "        asset.time_created, asset.time_modified, asset.parent, asset.salt " +
                "from asset asset " +
                "     left join asset_process assetProcess on asset.id_asset = assetProcess.id_asset " +
                "     left join process process on assetProcess.id_process = process.id_process " +
                "     left join asset_categories assetCategories " +
                "             on asset.id_asset_category = assetCategories.id_asset_category " +
                "     left join department de on asset.id_department = de.id_department " +
                "     left join location lo on asset.id_location = lo.id_location " +
                "     left join document do on process.id_process = do.id_process " +
                "where 1 = 1 and asset.id_department_origin in (:idsDepartmentOriginal) " +
                "and do.code = :codeDocument ");
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
                responses.add(findAllAssetDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllAssetProcess(request));
    }

    @Override
    public Optional<AssetProcess> findAssetProcessByIdProcess(Integer idProcess) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ap.id_asset_process, ap.id_asset, ap.id_process,  " +
                "       ap.id_type_process, ap.status, value, ap.time_created, ap.time_modified " +
                "from asset_process ap " +
                "    inner join asset at on ap.id_asset = at.id_asset " +
                "where ap.id_process = :idProcess ");
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
        if (CollectionUtils.isEmpty(result)){
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

    private long countFindAllAssetProcess(FindAllAssetProcessRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "from asset asset " +
                "     left join asset_process assetProcess on asset.id_asset = assetProcess.id_asset " +
                "     left join process process on assetProcess.id_process = process.id_process " +
                "     left join asset_categories assetCategories " +
                "             on asset.id_asset_category = assetCategories.id_asset_category " +
                "     left join department de on asset.id_department = de.id_department " +
                "     left join location lo on asset.id_location = lo.id_location " +
                "     left join document do on process.id_process = do.id_process " +
                "where 1 = 1 and asset.id_department_origin in (:idsDepartmentOriginal) " +
                "and do.code = :codeDocument ");
        setConditionFindAllAssetProcess(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetProcess(request, query);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    private void setConditionFindAllAssetProcess(FindAllAssetProcessRequest request, StringBuilder sb) {
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
