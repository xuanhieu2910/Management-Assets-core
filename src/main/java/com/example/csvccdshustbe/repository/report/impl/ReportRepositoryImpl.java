package com.example.csvccdshustbe.repository.report.impl;

import com.example.csvccdshustbe.dto.assetCategories.FindAllAssetCategoriesPickedDto;
import com.example.csvccdshustbe.dto.report.CurrentUsageReport08aDto;
import com.example.csvccdshustbe.dto.report.inventory.BlueprintInventoryReportDto;
import com.example.csvccdshustbe.dto.report.inventory.CouncilInventoryReportDto;
import com.example.csvccdshustbe.dto.report.inventory.FindAllAssetForInventoryReportDto;
import com.example.csvccdshustbe.dto.report.FindAllReportDto;
import com.example.csvccdshustbe.dto.report.IncreaseDecreaseReport08bDto;
import com.example.csvccdshustbe.entity.Report;
import com.example.csvccdshustbe.repository.report.ReportRepositoryCustom;
import com.example.csvccdshustbe.request.report.CreateReportInCreaseAndDecreaseAllRequest;
import com.example.csvccdshustbe.request.report.FindAllReportRequest;
import com.example.csvccdshustbe.request.report.FindAllReportVisibleRequest;
import com.example.csvccdshustbe.utility.Constants;
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

public class ReportRepositoryImpl implements ReportRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<FindAllReportDto> findAllReportDtoVisible(FindAllReportVisibleRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select rp.id_report, rp.code, rp.title, rp.path,  " +
                "       rp.time_created, rp.time_modified, rp.id_user_created,  " +
                "       rp.id_user_modified, rp.status, rp.type_mime, gc.id_government_circular,  " +
                "       gc.title, rp.path_image  " +
                "from report rp  " +
                "    inner join government_circular gc on rp.id_government_circular = gc.id_government_circular  " +
                "where 1 = 1  " +
                "and rp.status = :statusRp and gc.status = :statusGc ");
        setFindAllReportVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        PageUtils.buildQuery(pageable, query);
        setParameterFindAllReportVisible(request, query);
        List<Object[]> result = query.getResultList();
        List<FindAllReportDto> findAllReportDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                FindAllReportDto allReportDto = new FindAllReportDto();
                allReportDto.setIdReport(ValueUtil.getIntegerByObject(obj[0]));
                allReportDto.setCodeReport(ValueUtil.getStringByObject(obj[1]));
                allReportDto.setTitleReport(ValueUtil.getStringByObject(obj[2]));
                allReportDto.setPath(ValueUtil.getStringByObject(obj[3]));
                allReportDto.setTimeCreated(ValueUtil.getLongByObject(obj[4]));
                allReportDto.setTimeModified(ValueUtil.getLongByObject(obj[5]));
                allReportDto.setIdUserCreated(ValueUtil.getIntegerByObject(obj[6]));
                allReportDto.setIdUserModified(ValueUtil.getIntegerByObject(obj[7]));
                allReportDto.setStatus(ValueUtil.getIntegerByObject(obj[8]));
                allReportDto.setTypeMime(ValueUtil.getStringByObject(obj[9]));
                allReportDto.setIdGovernmentCircular(ValueUtil.getIntegerByObject(obj[10]));
                allReportDto.setPathImage(ValueUtil.getStringByObject(obj[12]));
                findAllReportDtos.add(allReportDto);
            }
        }
        return new PageImpl<>(findAllReportDtos, pageable, countFindAllReportVisible(request));
    }

    @Override
    public Page<FindAllReportDto> findAllReportDto(FindAllReportRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select rp.id_report, rp.code, rp.title, rp.path,  " +
                "       rp.time_created, rp.time_modified, rp.id_user_created,  " +
                "       rp.id_user_modified, rp.status, rp.type_mime, gc.id_government_circular,  " +
                "       gc.title, rp.path_image  " +
                "from report rp  " +
                "    inner join government_circular gc on rp.id_government_circular = gc.id_government_circular  " +
                "where 1 = 1  ");
        setFindAllReport(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        PageUtils.buildQuery(pageable, query);
        setParameterFindAllReport(request, query);
        List<Object[]> result = query.getResultList();
        List<FindAllReportDto> findAllReportDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                FindAllReportDto allReportDto = new FindAllReportDto();
                allReportDto.setIdReport(ValueUtil.getIntegerByObject(obj[0]));
                allReportDto.setCodeReport(ValueUtil.getStringByObject(obj[1]));
                allReportDto.setTitleReport(ValueUtil.getStringByObject(obj[2]));
                allReportDto.setPath(ValueUtil.getStringByObject(obj[3]));
                allReportDto.setTimeCreated(ValueUtil.getLongByObject(obj[4]));
                allReportDto.setTimeModified(ValueUtil.getLongByObject(obj[5]));
                allReportDto.setIdUserCreated(ValueUtil.getIntegerByObject(obj[6]));
                allReportDto.setIdUserModified(ValueUtil.getIntegerByObject(obj[7]));
                allReportDto.setStatus(ValueUtil.getIntegerByObject(obj[8]));
                allReportDto.setTypeMime(ValueUtil.getStringByObject(obj[9]));
                allReportDto.setIdGovernmentCircular(ValueUtil.getIntegerByObject(obj[10]));
                allReportDto.setPathImage(ValueUtil.getStringByObject(obj[11]));
                findAllReportDtos.add(allReportDto);
            }
        }
        return new PageImpl<>(findAllReportDtos, pageable, countFindAllReport(request));
    }

    @Override
    public Optional<Report> findReportByCodeAndStatus(String codeReport, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_report, code, title, path,  " +
                "       time_created, time_modified, id_user_created,  " +
                "       id_user_modified, status, type_mime,  " +
                "       id_government_circular, path_image  " +
                "from report  " +
                "where code = :codeReport  " +
                "and status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("codeReport", codeReport);
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Report report = new Report();
                report.setIdReport(ValueUtil.getIntegerByObject(obj[0]));
                report.setCode(ValueUtil.getStringByObject(obj[1]));
                report.setTitle(ValueUtil.getStringByObject(obj[2]));
                report.setPath(ValueUtil.getStringByObject(obj[3]));
                report.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                report.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                report.setIdUserCreated(ValueUtil.getIntegerByObject(obj[6]));
                report.setIdUserModified(ValueUtil.getIntegerByObject(obj[7]));
                report.setStatus(ValueUtil.getIntegerByObject(obj[8]));
                report.setTypeMime(ValueUtil.getStringByObject(obj[9]));
                report.setIdGovernmentCircular(ValueUtil.getIntegerByObject(obj[10]));
                report.setPathImage(ValueUtil.getStringByObject(obj[11]));
                return Optional.of(report);
            }
        }
        return Optional.empty();
    }

    public List<FindAllAssetForInventoryReportDto> findInfoAssetForInventoryReportByCodeDocument(String codeDocument) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select do.code, pr.id_process, ap.id_asset, " +
                "       ap.value, ap.id_asset_process " +
                "from document do " +
                "    inner join process pr on do.id_process = pr.id_process " +
                "    inner join asset_process ap on pr.id_process = ap.id_process " +
                "where do.code = :codeDocument ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("codeDocument",codeDocument);
        List<FindAllAssetForInventoryReportDto> responses = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                FindAllAssetForInventoryReportDto response = new FindAllAssetForInventoryReportDto();
                response.setCodeDocument(ValueUtil.getStringByObject(obj[0]));
                response.setIdProcess(ValueUtil.getIntegerByObject(obj[1]));
                response.setIdAsset(ValueUtil.getIntegerByObject(obj[2]));
                response.setValue(ValueUtil.getStringByObject(obj[3]));
                response.setIdAssetProcess(ValueUtil.getIntegerByObject(obj[4]));
                responses.add(response);
            }
        }
        return responses;
    }


    public Optional<List<Object[]>> findInfoAssetForRevaluationReport(Integer idAssetProcess, Integer status){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT" +
                "    ap.value AS value" +
                "FROM" +
                "    asset_process ap" +
                "WHERE" +
                "        ap.id_asset_process = :idAssetProcess AND ap.status = :status"
        );

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAssetProcess", idAssetProcess);
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();

        if (!CollectionUtils.isEmpty(result)) {
            return Optional.of(result);
        }

        return Optional.empty();
    }

    @Override
    public BlueprintInventoryReportDto findBlueprintInventoryReportDtoByCodeDocument(String codeDocument){
        StringBuilder sb = new StringBuilder();
        sb.append(" select do.code codeDocument, do.time_increase timeInventory, " +
                "       do.time_document timeDocument, de.id_department, " +
                "       de.code codeDepartment, de.name nameDepartment, " +
                "       cu.user_name, cu.full_name, rsh.position,  " +
                "       rsh.position_instance, rsh.level " +
                "from document do " +
                "    inner join department de on do.id_department = de.id_department " +
                "    inner join process pr on do.id_process = pr.id_process " +
                "    inner join request re on pr.id_process = re.id_process " +
                "    inner join request_stake_holder rsh on re.id_request = rsh.id_request " +
                "    inner join csvc_user cu on rsh.id_user = cu.id_user " +
                "where do.code = :codeDocument ORDER BY rsh.level ASC ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("codeDocument", codeDocument);
        List<Object[]> result = query.getResultList();
        BlueprintInventoryReportDto reportDto = new BlueprintInventoryReportDto();
        if (!CollectionUtils.isEmpty(result)){
            setBlueprintInventoryReportDto(reportDto, result.get(0));
            reportDto.setCouncilInventoryReportDtos(setCouncilInventoryReportDto(result));
        }
        return reportDto;
    }

    private List<CouncilInventoryReportDto> setCouncilInventoryReportDto(List<Object[]> result) {
        List<CouncilInventoryReportDto> reportDtos = new ArrayList<>();
        for (Object[] obj : result){
            CouncilInventoryReportDto reportDto = new CouncilInventoryReportDto();
            reportDto.setUserName(ValueUtil.getStringByObject(obj[6]));
            reportDto.setFullName(ValueUtil.getStringByObject(obj[7]));
            reportDto.setPosition(ValueUtil.getStringByObject(obj[8]));
            reportDto.setInstancePosition(ValueUtil.getStringByObject(obj[9]));
            reportDto.setLevel(ValueUtil.getIntegerByObject(obj[10]));
            reportDtos.add(reportDto);
        }
        return reportDtos;
    }

    private void setBlueprintInventoryReportDto(BlueprintInventoryReportDto reportDto, Object[] obj) {
        reportDto.setCodeDocument(ValueUtil.getStringByObject(obj[0]));
        reportDto.setTimeInventory(ValueUtil.getStringByObject(obj[1]));
        reportDto.setTimeDocument(ValueUtil.getStringByObject(obj[2]));
        reportDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[3]));
        reportDto.setCodeDepartment(ValueUtil.getStringByObject(obj[4]));
        reportDto.setNameDepartment(ValueUtil.getStringByObject(obj[5]));
    }

    public Optional<List<Object[]>> findInfoStakeHolderForRevaluationReport(Integer idAssetProcess){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT" +
                "    u.full_name AS user_name,            -- Tên người dùng" +
                "    rsh.position AS position,            -- Chức vụ của stakeholder" +
                "    rsh.position_instance AS position_instance -- Đại diện" +
                "FROM" +
                "    asset_process ap" +
                "        JOIN" +
                "    request r ON r.id_process = ap.id_process" +
                "        JOIN" +
                "    request_stake_holder rsh ON r.id_request = rsh.id_request" +
                "        JOIN" +
                "    csvc_user u ON rsh.id_user = u.id_user" +
                "WHERE" +
                "        ap.id_asset_process = :idAssetProcess"
        );

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAssetProcess", idAssetProcess);

        List<Object[]> result = query.getResultList();

        if (!CollectionUtils.isEmpty(result)) {
            return Optional.of(result);
        }

        return Optional.empty();
    }


    private void setParameterFindAllReport(FindAllReportRequest request, Query query) {
        if (StringUtils.isNotBlank(request.getCodeReport())) {
            query.setParameter("codeReport", request.getCodeReport());
        }
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
        if (ObjectUtils.isNotEmpty(request.getIdGovernmentCircular())) {
            query.setParameter("idGovernmentCircular", request.getIdGovernmentCircular());
        }
        if (ObjectUtils.isNotEmpty(request.getStatusReport())) {
            query.setParameter("statusRp", request.getIdGovernmentCircular());
        }
        if (ObjectUtils.isNotEmpty(request.getStatusGovernmentCircular())) {
            query.setParameter("statusGc", request.getIdGovernmentCircular());
        }
    }

    private void setFindAllReport(FindAllReportRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getCodeReport())) {
            sb.append(" and  (rp.code REGEXP :codeReport ) ");
        }
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (rp.title REGEXP :keyword) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdGovernmentCircular())) {
            sb.append(" and gc.id_government_circular = :idGovernmentCircular ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusReport())) {
            sb.append(" and rp.status = :statusRp ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusGovernmentCircular())) {
            sb.append(" and gc.status = :statusGc ");
        }
    }

    private long countFindAllReportVisible(FindAllReportVisibleRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) count " +
                "from report rp " +
                "    inner join government_circular gc on rp.id_government_circular = gc.id_government_circular " +
                "where 1 = 1 " +
                "and rp.status = :statusRp and gc.status = :statusGc ");
        setFindAllReportVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllReportVisible(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private long countFindAllReport(FindAllReportRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) count " +
                "from report rp " +
                "    inner join government_circular gc on rp.id_government_circular = gc.id_government_circular " +
                "where 1 = 1 ");
        setFindAllReport(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllReport(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllReportVisible(FindAllReportVisibleRequest request, Query query) {
        query.setParameter("statusRp", Constants.STATUS_REPORT_ACTIVE);
        query.setParameter("statusGc", Constants.STATUS_GOVERNMENT_ACTIVE);
        if (StringUtils.isNotBlank(request.getCodeReport())) {
            query.setParameter("codeReport", request.getCodeReport());
        }
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
        if (ObjectUtils.isNotEmpty(request.getIdGovernmentCircular())) {
            query.setParameter("idGovernmentCircular", request.getIdGovernmentCircular());
        }
    }

    private void setFindAllReportVisible(FindAllReportVisibleRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getCodeReport())) {
            sb.append(" and  (rp.code REGEXP :codeReport ) ");
        }
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (rp.title REGEXP :keyword) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdGovernmentCircular())) {
            sb.append(" and gc.id_government_circular = :idGovernmentCircular ");
        }
    }

    @Override
    public Optional<CurrentUsageReport08aDto> findAllCurrentUsageAssetGroundInReport(List<Integer> idsDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT    " +
                "    COUNT(gd.id_ground_declare) AS total_ground_declare,    " +
                "    SUM(gd.acreage) AS total_acreage,    " +
                "    SUM(gd.hdsn_no_bussiness) AS total_no_business,    " +
                "    SUM(gd.hdsn_bussiness) AS total_business,    " +
                "    SUM(gd.hdsn_rent) AS total_rent,    " +
                "    SUM(gd.hdsn_bonds) AS total_bonds,    " +
                "    SUM(gd.synthetic_use) AS total_synthetic,    " +
                "    SUM(gd.other_use) AS total_other,    " +
                "    asset_categories.name AS category_name    " +
                "FROM ground_declare gd    " +
                "    INNER JOIN asset ON gd.id_asset = asset.id_asset    " +
                "    INNER JOIN asset_declare on asset.id_asset = asset_declare.id_asset    " +
                "    INNER JOIN `declare` on asset_declare.id_declare = `declare`.id_declare    " +
                "    INNER JOIN asset_categories ON declare.id_category = asset_categories.id_asset_category    " +
                "WHERE asset.quantity = 1 AND asset.id_department_origin IN (:idsDepartmentOriginal)    " +
                "GROUP BY asset_declare.id_declare ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsDepartmentOriginal", idsDepartment);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){

                CurrentUsageReport08aDto res = new CurrentUsageReport08aDto();
                res.setCountAsset(ValueUtil.getIntegerByObject(obj[0]));
                res.setAcreage(ValueUtil.getStringByObject(obj[1]));
                res.setTotalNoBusiness(ValueUtil.getStringByObject(obj[2]));
                res.setTotalBusiness(ValueUtil.getStringByObject(obj[3]));
                res.setTotalRent(ValueUtil.getStringByObject(obj[4]));
                res.setTotalBonds(ValueUtil.getStringByObject(obj[5]));
                res.setTotalSynthetic(ValueUtil.getStringByObject(obj[6]));
                res.setTotalOther(ValueUtil.getStringByObject(obj[7]));
                res.setNameCategory(ValueUtil.getStringByObject(obj[8]));
                return Optional.of(res);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<CurrentUsageReport08aDto> findAllCurrentUsageAssetHouseInReport(List<Integer> idsDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT      " +
                "      COUNT(hd.id_house_declare) AS total_ground_declare,      " +
                "      SUM(hd.acreage) as total_acreage,      " +
                "      SUM(hd.hdsn_no_bussiness) AS total_no_business,      " +
                "      SUM(hd.hdsn_bussiness) AS total_business,      " +
                "      SUM(hd.hdsn_rent) AS total_rent,      " +
                "      SUM(hd.hdsn_bonds) AS total_bond,      " +
                "      SUM(hd.synthetic_use) AS total_synthetic,      " +
                "      SUM(hd.other_use) AS total_other,  " +
                "      asset_categories.name AS category_name  " +
                "  FROM      " +
                "      house_declare hd      " +
                "          INNER JOIN asset ON hd.id_asset = asset.id_asset  " +
                "          INNER JOIN asset_declare on asset.id_asset = asset_declare.id_asset  " +
                "          INNER JOIN `declare` on asset_declare.id_declare = `declare`.id_declare  " +
                "          INNER JOIN asset_categories ON declare.id_category = asset_categories.id_asset_category  " +
                "  where asset.quantity = 1 and asset.id_department_origin in (:idsDepartmentOriginal)  " +
                "    GROUP BY asset_declare.id_declare ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsDepartmentOriginal", idsDepartment);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){

                CurrentUsageReport08aDto res = new CurrentUsageReport08aDto();
                res.setCountAsset(ValueUtil.getIntegerByObject(obj[0]));
                res.setAcreage(ValueUtil.getStringByObject(obj[1]));
                res.setTotalNoBusiness(ValueUtil.getStringByObject(obj[2]));
                res.setTotalBusiness(ValueUtil.getStringByObject(obj[3]));
                res.setTotalRent(ValueUtil.getStringByObject(obj[4]));
                res.setTotalBonds(ValueUtil.getStringByObject(obj[5]));
                res.setTotalSynthetic(ValueUtil.getStringByObject(obj[6]));
                res.setTotalOther(ValueUtil.getStringByObject(obj[7]));
                res.setNameCategory(ValueUtil.getStringByObject(obj[8]));
                return Optional.of(res);
            }
        }
        return Optional.empty();

    }

    @Override
    public List<CurrentUsageReport08aDto> findAllCurrentUsageAssetShapeInReport(List<Integer> idsDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_asset_categories AS (     " +
                "    SELECT     " +
                "        id_asset_category,     " +
                "        parent,     " +
                "        code_name,     " +
                "        name AS category_name,     " +
                "        id_asset_category AS root_id    " +
                "    FROM asset_categories     " +
                "    WHERE visible = :visible    " +
                "      AND asset_categories.is_pick = :isPick     " +
                "      AND asset_categories.code_name != :codeNameNoshape     " +
                "      AND asset_categories.code_name != :codeNameGround     " +
                "      AND asset_categories.code_name != :codeNameHouse     " +
                "    UNION ALL     " +
                "    SELECT     " +
                "        ac.id_asset_category,     " +
                "        ac.parent,     " +
                "        ac.code_name,     " +
                "        ac.name,     " +
                "        cte.root_id    " +
                "    FROM asset_categories ac     " +
                "    INNER JOIN cte_asset_categories cte ON ac.parent = cte.id_asset_category     " +
                ")     " +
                "SELECT     " +
                "    COUNT(DISTINCT asset.id_asset) AS total_asset,     " +
                "    SUM(CASE WHEN current_usage.code = 'QLNN' THEN 1 ELSE 0 END) AS QLNN,    " +
                "    SUM(CASE WHEN current_usage.code = 'HĐSN-KHD' THEN 1 ELSE 0 END) AS HĐSNKHD,    " +
                "    SUM(CASE WHEN current_usage.code = 'HĐSN-KD' THEN 1 ELSE 0 END) AS HĐSNKD,      " +
                "    SUM(CASE WHEN current_usage.code = 'HĐSN-CT' THEN 1 ELSE 0 END) AS HĐSNCT,      " +
                "    SUM(CASE WHEN current_usage.code = 'HĐSN-LDLK' THEN 1 ELSE 0 END) AS HĐSNLDLK,     " +
                "    SUM(CASE WHEN current_usage.code = 'SDK' THEN 1 ELSE 0 END) AS SDK,   " +
                "    MAX(root_categories.name) AS root_category_name     " +
                "FROM asset     " +
                "         LEFT JOIN asset_current_usage ON asset.id_asset = asset_current_usage.id_asset     " +
                "         LEFT JOIN current_usage ON asset_current_usage.id_current_usage = current_usage.id_current_usage     " +
                "         LEFT JOIN cte_asset_categories cte ON asset.id_asset_category = cte.id_asset_category     " +
                "         LEFT JOIN asset_categories root_categories ON cte.root_id = root_categories.id_asset_category      " +
                "WHERE     " +
                "  asset.quantity = 1     " +
                "  AND asset.id_department_origin IN (:idsDepartmentOriginal)   " +
                "  AND root_categories.name IS NOT NULL  " +
                "GROUP BY cte.root_id     " +
                "ORDER BY root_category_name  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("visible", Constants.IS_VISIBLE);
        query.setParameter("isPick", Constants.IS_PICKED);
        query.setParameter("codeNameNoshape", Constants.CODE_NAME_NO_SHAPE);
        query.setParameter("codeNameHouse", Constants.CODE_NAME_HOUSE);
        query.setParameter("codeNameGround", Constants.CODE_NAME_GROUND);
        query.setParameter("idsDepartmentOriginal", idsDepartment);
        List<Object[]> result = query.getResultList();
        List<CurrentUsageReport08aDto> currentUsageReport08aDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){

                CurrentUsageReport08aDto res = new CurrentUsageReport08aDto();
                res.setCountAsset(ValueUtil.getIntegerByObject(obj[0]));
                res.setTotalStateManagement(ValueUtil.getStringByObject(obj[1]));
                res.setTotalNoBusiness(ValueUtil.getStringByObject(obj[2]));
                res.setTotalBusiness(ValueUtil.getStringByObject(obj[3]));
                res.setTotalRent(ValueUtil.getStringByObject(obj[4]));
                res.setTotalBonds(ValueUtil.getStringByObject(obj[5]));
                res.setTotalOther(ValueUtil.getStringByObject(obj[6]));
                res.setNameCategory(ValueUtil.getStringByObject(obj[7]));
                currentUsageReport08aDtos.add(res);
            }
        }
        return currentUsageReport08aDtos;

    }

    @Override
    public Optional<CurrentUsageReport08aDto> findAllCurrentUsageAssetOtherInReport(List<Integer> idsDepartment) {
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
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){

                CurrentUsageReport08aDto res = new CurrentUsageReport08aDto();
                res.setCountAsset(ValueUtil.getIntegerByObject(obj[0]));
                res.setTotalStateManagement(ValueUtil.getStringByObject(obj[1]));
                res.setTotalNoBusiness(ValueUtil.getStringByObject(obj[2]));
                res.setTotalBusiness(ValueUtil.getStringByObject(obj[3]));
                res.setTotalRent(ValueUtil.getStringByObject(obj[4]));
                res.setTotalBonds(ValueUtil.getStringByObject(obj[5]));
                res.setTotalOther(ValueUtil.getStringByObject(obj[6]));
                return Optional.of(res);
            }
        }
        return Optional.empty();

    }


    @Override
    public Optional<IncreaseDecreaseReport08bDto>  findAllIncreaseDecreaseGroundInReport(CreateReportInCreaseAndDecreaseAllRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT            " +
                "       COUNT(CASE WHEN asset.time_created <'1731492718697' THEN gd.id_ground_declare END) AS total_ground_declare_time_start,            " +
                "       SUM(CASE WHEN asset.time_created <'1731492718697' THEN gd.acreage ELSE 0 END) AS total_acreage_time_start,            " +
                "       SUM(CASE WHEN asset.time_created <'1731492718697' THEN assetOriginalOfFormation.value ELSE 0 END) AS total_Original_time_start,            " +
                "       COUNT(CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1733281923625') and asset.is_increase = :isIncrease THEN gd.id_ground_declare END) AS total_ground_declare_time_increase,     " +
                "       SUM(CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1733281923625') and asset.is_increase = :isIncrease  THEN gd.acreage ELSE 0 END) AS total_acreage_time_increase,     " +
                "       SUM(CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1733281923625') and asset.is_increase = :isIncrease  THEN assetOriginalOfFormation.value ELSE 0 END) AS total_Original_time_increase,     " +
                "       COUNT(CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1733281923625') and asset.is_decrease = :isDecrease THEN gd.id_ground_declare END) AS total_ground_declare_time_decrease,     " +
                "       SUM(CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1733281923625') and asset.is_decrease = :isDecrease  THEN gd.acreage ELSE 0 END) AS total_acreage_time_decrease,     " +
                "       SUM(CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1733281923625') and asset.is_decrease = :isDecrease  THEN assetOriginalOfFormation.value ELSE 0 END) AS total_Original_time_decrease,     " +
                "       COUNT(CASE WHEN asset.time_created <='1733364968278' THEN gd.id_ground_declare END) AS total_ground_declare_time_end,     " +
                "       SUM(CASE WHEN asset.time_created <='1733364968278' THEN gd.acreage ELSE 0 END) AS total_acreage_time_end,     " +
                "       SUM(CASE WHEN asset.time_created <='1733364968278' THEN assetOriginalOfFormation.value ELSE 0 END) AS total_Original_time_end,     " +
                "       asset_categories.name AS category_name     " +
                "FROM ground_declare gd  INNER JOIN     " +
                "       asset ON gd.id_asset = asset.id_asset            " +
                "       left join asset_original_of_formation assetOriginalOfFormation            " +
                "       on asset.id_asset = assetOriginalOfFormation.id_asset     " +
                "             INNER JOIN asset_declare on asset.id_asset = asset_declare.id_asset     " +
                "        INNER JOIN `declare` on asset_declare.id_declare = `declare`.id_declare     " +
                "        INNER JOIN asset_categories ON declare.id_category = asset_categories.id_asset_category     " +
                "       where asset.quantity = 1 and asset.id_department_origin in (:idsDepartmentOriginal)  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllIncreaseDecreaseAssetReport(request,query);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){

                IncreaseDecreaseReport08bDto res = new IncreaseDecreaseReport08bDto();
                res.setCountAssetStart(ValueUtil.getIntegerByObject(obj[0]));
                res.setAcreageStart(ValueUtil.getStringByObject(obj[1]));
                res.setTotalOriginalStart(ValueUtil.getStringByObject(obj[2]));
                res.setCountAssetIncrease(ValueUtil.getIntegerByObject(obj[3]));
                res.setAcreageIncrease(ValueUtil.getStringByObject(obj[4]));
                res.setTotalOriginalIncrease(ValueUtil.getStringByObject(obj[5]));
                res.setCountDecrease(ValueUtil.getIntegerByObject(obj[6]));
                res.setAcreageDecrease(ValueUtil.getStringByObject(obj[7]));
                res.setTotalOriginalDecrease(ValueUtil.getStringByObject(obj[8]));
                res.setCountAssetEnd(ValueUtil.getIntegerByObject(obj[9]));
                res.setAcreageEnd(ValueUtil.getStringByObject(obj[10]));
                res.setTotalOriginalEnd(ValueUtil.getStringByObject(obj[11]));
                res.setNameCategory(ValueUtil.getStringByObject(obj[12]));
                return Optional.of(res);
            }
        }
        return Optional.empty();
    }


    @Override
    public Optional<IncreaseDecreaseReport08bDto>  findAllIncreaseDecreaseHouseInReport(CreateReportInCreaseAndDecreaseAllRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT              " +
                "      COUNT(CASE WHEN asset.time_created <'1731492718697' THEN hd.id_house_declare END) AS total_ground_declare_time_start,              " +
                "      SUM(CASE WHEN asset.time_created <'1731492718697' THEN hd.acreage ELSE 0 END) AS total_acreage_time_start,              " +
                "      SUM(CASE WHEN asset.time_created <'1731492718697' THEN assetOriginalOfFormation.value ELSE 0 END) AS total_Original_time_start,              " +
                "      COUNT(CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1732616120087') and asset.is_increase = :isIncrease THEN hd.id_house_declare END) AS total_ground_declare_time_increase,              " +
                "      SUM(CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1732616120087') and asset.is_increase = :isIncrease  THEN hd.acreage ELSE 0 END) AS total_acreage_time_increase,              " +
                "      SUM(CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1732616120087') and asset.is_increase = :isIncrease  THEN assetOriginalOfFormation.value ELSE 0 END) AS total_Original_time_increase,              " +
                "      COUNT(CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1732616120087') and asset.is_decrease = :isDecrease THEN hd.id_house_declare END) AS total_ground_declare_time_decrease,              " +
                "      SUM(CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1732616120087') and asset.is_decrease = :isDecrease  THEN hd.acreage ELSE 0 END) AS total_acreage_time_decrease,              " +
                "      SUM(CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1732616120087') and asset.is_decrease = :isDecrease  THEN assetOriginalOfFormation.value ELSE 0 END) AS total_Original_time_decrease,              " +
                "      COUNT(CASE WHEN asset.time_created <='1733364968278' THEN hd.id_house_declare END) AS total_ground_declare_time_end,     " +
                "      SUM(CASE WHEN asset.time_created <='1733364968278' THEN hd.acreage ELSE 0 END) AS total_acreage_time_end,     " +
                "      SUM(CASE WHEN asset.time_created <='1733364968278' THEN assetOriginalOfFormation.value ELSE 0 END) AS total_Original_time_end,     " +
                "      asset_categories.name AS category_name     " +
                "      FROM house_declare hd INNER JOIN                " +
                "      asset ON hd.id_asset = asset.id_asset              " +
                "      left join asset_original_of_formation assetOriginalOfFormation              " +
                "      on asset.id_asset = assetOriginalOfFormation.id_asset     " +
                "      INNER JOIN asset_declare on asset.id_asset = asset_declare.id_asset     " +
                "      INNER JOIN `declare` on asset_declare.id_declare = `declare`.id_declare     " +
                "      INNER JOIN asset_categories ON declare.id_category = asset_categories.id_asset_category     " +
                "                     where asset.quantity = 1 and asset.id_department_origin in (:idsDepartmentOriginal)");
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllIncreaseDecreaseAssetReport(request,query);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){

                IncreaseDecreaseReport08bDto res = new IncreaseDecreaseReport08bDto();
                res.setCountAssetStart(ValueUtil.getIntegerByObject(obj[0]));
                res.setAcreageStart(ValueUtil.getStringByObject(obj[1]));
                res.setTotalOriginalStart(ValueUtil.getStringByObject(obj[2]));
                res.setCountAssetIncrease(ValueUtil.getIntegerByObject(obj[3]));
                res.setAcreageIncrease(ValueUtil.getStringByObject(obj[4]));
                res.setTotalOriginalIncrease(ValueUtil.getStringByObject(obj[5]));
                res.setCountDecrease(ValueUtil.getIntegerByObject(obj[6]));
                res.setAcreageDecrease(ValueUtil.getStringByObject(obj[7]));
                res.setTotalOriginalDecrease(ValueUtil.getStringByObject(obj[8]));
                res.setCountAssetEnd(ValueUtil.getIntegerByObject(obj[9]));
                res.setAcreageEnd(ValueUtil.getStringByObject(obj[10]));
                res.setTotalOriginalEnd(ValueUtil.getStringByObject(obj[11]));
                res.setNameCategory(ValueUtil.getStringByObject(obj[12]));
                return Optional.of(res);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<IncreaseDecreaseReport08bDto>  findAllIncreaseDecreaseAssetShapeInReport(CreateReportInCreaseAndDecreaseAllRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_asset_categories AS (                " +
                "        SELECT                " +
                "            id_asset_category,                " +
                "            parent,      " +
                "            code_name,      " +
                "            name AS category_name,                " +
                "            id_asset_category AS root_id      " +
                "        FROM asset_categories      " +
                "        WHERE asset_categories.visible = :visible      " +
                "          AND asset_categories.is_pick = :isPick      " +
                "          AND asset_categories.code_name != :codeNameNoshape      " +
                "          AND asset_categories.code_name != :codeNameGround      " +
                "          AND asset_categories.code_name != :codeNameHouse      " +
                "        UNION ALL      " +
                "        SELECT      " +
                "            ac.id_asset_category,      " +
                "            ac.parent,      " +
                "            ac.code_name,      " +
                "            ac.name,      " +
                "            cte.root_id      " +
                "        FROM asset_categories ac      " +
                "        INNER JOIN cte_asset_categories cte ON ac.parent = cte.id_asset_category )      " +
                "                     SELECT      " +
                "        COUNT(distinct( CASE WHEN asset.time_created <'1731492718697' THEN  asset.id_asset END) ) AS count_asset_start,      " +
                "        SUM(CASE WHEN asset.time_created <'1731492718697' THEN assetOriginalOfFormation.value ELSE 0 END) as total_Original_time_start,      " +
                "        COUNT(distinct (CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1733364968278') and asset.is_increase = :isIncrease THEN asset.id_asset END)) AS total_ground_declare_time_increase,      " +
                "        SUM(CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1733364968278') and asset.is_increase = :isIncrease  THEN assetOriginalOfFormation.value ELSE 0 END) AS total_Original_time_increase,      " +
                "        COUNT(distinct (CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1733364968278') and asset.is_decrease = :isDecrease THEN asset.id_asset END)) AS total_ground_declare_time_decrease,      " +
                "        SUM(CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1733364968278') and asset.is_decrease = :isDecrease  THEN assetOriginalOfFormation.value ELSE 0 END) AS total_Original_time_decrease,      " +
                "        COUNT(distinct( CASE WHEN asset.time_created <'1733364968278' THEN  asset.id_asset END) ) AS count_asset_end,      " +
                "        SUM(CASE WHEN asset.time_created <'1733364968278' THEN assetOriginalOfFormation.value ELSE 0 END) as total_Original_time_end,      " +
                "        MAX(root_categories.name) AS root_category_name      " +
                "         FROM asset      " +
                "                  LEFT JOIN cte_asset_categories cte ON asset.id_asset_category = cte.id_asset_category      " +
                "                  left join asset_original_of_formation assetOriginalOfFormation      " +
                "                    on asset.id_asset = assetOriginalOfFormation.id_asset      " +
                "    LEFT JOIN asset_categories root_categories ON cte.root_id = root_categories.id_asset_category      " +
                "         WHERE      " +
                "             asset.quantity = 1      " +
                "           AND asset.id_department_origin IN (:idsDepartmentOriginal)      " +
                "           AND root_categories.name IS NOT NULL      " +
                "         GROUP BY cte.root_id      " +
                "         ORDER BY root_category_name ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("visible", Constants.IS_VISIBLE);
        query.setParameter("isPick", Constants.IS_PICKED);
        query.setParameter("codeNameNoshape", Constants.CODE_NAME_NO_SHAPE);
        query.setParameter("codeNameHouse", Constants.CODE_NAME_HOUSE);
        query.setParameter("codeNameGround", Constants.CODE_NAME_GROUND);
        setParameterFindAllIncreaseDecreaseAssetReport(request,query);
        List<Object[]> result = query.getResultList();
        List<IncreaseDecreaseReport08bDto> increaseDecreaseReport08bDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){

                IncreaseDecreaseReport08bDto res = new IncreaseDecreaseReport08bDto();
                res.setCountAssetStart(ValueUtil.getIntegerByObject(obj[0]));
                res.setTotalOriginalStart(ValueUtil.getStringByObject(obj[1]));
                res.setCountAssetIncrease(ValueUtil.getIntegerByObject(obj[2]));
                res.setTotalOriginalIncrease(ValueUtil.getStringByObject(obj[3]));
                res.setCountDecrease(ValueUtil.getIntegerByObject(obj[4]));
                res.setTotalOriginalDecrease(ValueUtil.getStringByObject(obj[5]));
                res.setCountAssetEnd(ValueUtil.getIntegerByObject(obj[6]));
                res.setTotalOriginalEnd(ValueUtil.getStringByObject(obj[7]));
                res.setNameCategory(ValueUtil.getStringByObject(obj[8]));
                increaseDecreaseReport08bDtos.add(res);
            }
        }
        return increaseDecreaseReport08bDtos;
    }

    @Override
    public Optional<IncreaseDecreaseReport08bDto>  findAllIncreaseDecreaseOtherAssetInReport(CreateReportInCreaseAndDecreaseAllRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT    " +
                "      COUNT(distinct( CASE WHEN asset.time_created <'1731492718697' THEN  asset.id_asset END) ) AS count_asset_start,    " +
                "      SUM(CASE WHEN asset.time_created <'1731492718697' THEN assetOriginalOfFormation.value ELSE 0 END) as total_Original_time_start,    " +
                "      COUNT(distinct (CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1732616120087') and asset.is_increase = :isIncrease THEN asset.id_asset END)) AS total_ground_declare_time_increase,    " +
                "     SUM(CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1732616120087') and asset.is_increase = :isIncrease  THEN assetOriginalOfFormation.value ELSE 0 END) AS total_Original_time_increase,    " +
                "      COUNT(distinct (CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1732616120087') and asset.is_decrease = :isDecrease THEN asset.id_asset END)) AS total_ground_declare_time_decrease,    " +
                "      SUM(CASE WHEN (asset.time_created BETWEEN '1731492718696' AND '1732616120087') and asset.is_decrease = :isDecrease  THEN assetOriginalOfFormation.value ELSE 0 END) AS total_Original_time_decrease,    " +
                "      COUNT(distinct( CASE WHEN asset.time_created <'1732616120087' THEN  asset.id_asset END) ) AS count_asset_end,    " +
                "      SUM(CASE WHEN asset.time_created <'1732616120087' THEN assetOriginalOfFormation.value ELSE 0 END) as total_Original_time_end    " +
                "from asset  left join asset_original_of_formation assetOriginalOfFormation    " +
                "            on asset.id_asset = assetOriginalOfFormation.id_asset    " +
                "                  where asset.quantity = 1  and asset.id_department_origin in (:idsDepartmentOriginal)  " +
                "    and asset.id_asset_category in (WITH RECURSIVE cte_asset_categories as (        " +
                "      select assetCategires.id_asset_category,assetCategires.name,        " +
                "             assetCategires.code_name, assetCategires.short_name,        " +
                "             assetCategires.description, assetCategires.parent,        " +
                "             assetCategires.sort_order, assetCategires.asset_count,        " +
                "             assetCategires.visible, assetCategires.time_created,        " +
                "             assetCategires.time_modified, assetCategires.is_pick,        " +
                "             1 as depth,        " +
                "             CAST(assetCategires.id_asset_category as NCHAR ) as path,        " +
                "             assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,        " +
                "             assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,        " +
                "             assetCategires.id_department_original        " +
                "      from asset_categories assetCategires    " +
                "    where (assetCategires.code_name != :codeNameCar  or assetCategires.code_name != :codeNameNoShape ) " +
                "      and assetCategires.visible = :visible  " +
                "      union all        " +
                "      select assetCategires.id_asset_category,assetCategires.name,        " +
                "             assetCategires.code_name, assetCategires.short_name,        " +
                "             assetCategires.description, assetCategires.parent,        " +
                "             assetCategires.sort_order, assetCategires.asset_count,        " +
                "             assetCategires.visible, assetCategires.time_created,        " +
                "             assetCategires.time_modified, assetCategires.is_pick,        " +
                "             cte.depth + 1 as depth,        " +
                "             concat_ws('/',cte.path,CAST(assetCategires.id_asset_category as NCHAR)) as path,        " +
                "             assetCategires.value_wear_tear, assetCategires.year_used_wear_tear,        " +
                "             assetCategires.minimum_time_depreciation, assetCategires.maximum_time_depreciation,        " +
                "             assetCategires.id_department_original        " +
                "      from asset_categories assetCategires        " +
                "               INNER JOIN cte_asset_categories cte ON assetCategires.parent = cte.id_asset_category)        " +
                "           select cte_asset_categories.id_asset_category        " +
                "           from cte_asset_categories ) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("codeNameCar", Constants.CODE_NAME_CAR);
        query.setParameter("codeNameNoShape", Constants.CODE_NAME_NO_SHAPE);
        query.setParameter("visible", Constants.IS_VISIBLE);
        setParameterFindAllIncreaseDecreaseAssetReport(request,query);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){

                IncreaseDecreaseReport08bDto res = new IncreaseDecreaseReport08bDto();
                res.setCountAssetStart(ValueUtil.getIntegerByObject(obj[0]));
                res.setTotalOriginalStart(ValueUtil.getStringByObject(obj[1]));
                res.setCountAssetIncrease(ValueUtil.getIntegerByObject(obj[2]));
                res.setTotalOriginalIncrease(ValueUtil.getStringByObject(obj[3]));
                res.setCountDecrease(ValueUtil.getIntegerByObject(obj[4]));
                res.setTotalOriginalDecrease(ValueUtil.getStringByObject(obj[5]));
                res.setCountAssetEnd(ValueUtil.getIntegerByObject(obj[6]));
                res.setTotalOriginalEnd(ValueUtil.getStringByObject(obj[7]));
                return Optional.of(res);
            }
        }
        return Optional.empty();
    }
    private void setParameterFindAllIncreaseDecreaseAssetReport(CreateReportInCreaseAndDecreaseAllRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("isIncrease", Constants.CODE_TYPE_PROCESS_INCREASE);
        query.setParameter("isDecrease", Constants.CODE_TYPE_PROCESS_DECREASE);
//        query.setParameter("timeStart", request.getTimeStart());
//        query.setParameter("timeEnd", request.getTimeEnd());
    }



}
