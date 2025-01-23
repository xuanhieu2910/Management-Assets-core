package com.example.csvccdshustbe.repository.fluctuatingSituationRepository.impl;

import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.repository.fluctuatingSituationRepository.FluctuatingSituationRepositoryCustom;
import com.example.csvccdshustbe.request.fluctuatingSituation.FindAllFluctuatingSituationRequest;
import com.example.csvccdshustbe.response.fluctuatingSituation.FindAllFluctuationSituationResponse;
import com.example.csvccdshustbe.response.fluctuatingSituation.StatisticFluctuatingSituation;
import com.example.csvccdshustbe.response.fluctuatingSituationAsset.StatisticFluctuatingSituationAsset;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.DateUtil;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FluctuatingSituationRepositoryImpl implements FluctuatingSituationRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Page<FindAllFluctuationSituationResponse>
    findAllFluctuationSituationResponse(Pageable pageable, FindAllFluctuatingSituationRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select fs.id_fluctuating_situation, pr.id_process,  " +
                "         fs.status, pr.time_created, pr.time_modified,  " +
                "         do.code, do.id_document, cu.user_name, cu.full_name,  " +
                "         do.time_increase, de.id_department, de.name  " +
                "  from fluctuating_situation fs  " +
                "      inner join process pr on fs.id_process = pr.id_process  " +
                "      inner join document do on pr.id_process = do.id_process  " +
                "      inner join csvc_user cu on do.id_user_created = cu.id_user  " +
                "      inner join department de on do.id_department = de.id_department  " +
                "  where do.id_department_original in (:idsDepartmentOriginal)  " +
                "  and fs.type = :type ");
        setConditionFindAllFluctuationSituation(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        PageUtils.buildQuery(pageable, query);
        setParameterFindAllFluctuationSituation(query, request);
        List<FindAllFluctuationSituationResponse> responses = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                FindAllFluctuationSituationResponse response = new FindAllFluctuationSituationResponse();
                response.setIdFluctuatingSituation(ValueUtil.getIntegerByObject(obj[0]));
                response.setIdProcess(ValueUtil.getIntegerByObject(obj[1]));
                response.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                response.setTimeCreated(DateUtil.formatToPattern(new Date(Long.parseLong(ValueUtil.getStringByObject(obj[3]))),DateUtil.DATE_FORMAT));
                response.setTimeModified(DateUtil.formatToPattern(new Date(Long.parseLong(ValueUtil.getStringByObject(obj[4]))),DateUtil.DATE_FORMAT));
                response.setCodeDocument(ValueUtil.getStringByObject(obj[5]));
                response.setIdDocument(ValueUtil.getIntegerByObject(obj[6]));
                response.setUserName(ValueUtil.getStringByObject(obj[7]));
                response.setFullName(ValueUtil.getStringByObject(obj[8]));
                response.setTimeInventory(ValueUtil.getStringByObject(obj[9]));
                response.setIdDepartment(ValueUtil.getIntegerByObject(obj[10]));
                response.setNameDepartment(ValueUtil.getStringByObject(obj[11]));
                responses.add(response);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllFluctuationSituation(request));
    }


    @Modifying
    @Transactional
    @Override
    public void calculatorStatusFluctuatingSituationById(Integer idFluctuatingSituation) {
        StringBuilder sb = new StringBuilder();
        sb.append(" update fluctuating_situation fs  " +
                "    inner join (select fsa.id_fluctuating_situation,  " +
                "                    case when fsa.status = :notYetFinish then -1 else 1 end totalyStatus  " +
                "                from fluctuating_situation_asset fsa  " +
                "                where fsa.id_fluctuating_situation = :idFsa  " +
                "                group by fsa.id_fluctuating_situation) fsa  " +
                "    on fs.id_fluctuating_situation = fsa.id_fluctuating_situation  " +
                "set fs.status = fsa.totalyStatus  " +
                "where 1 = 1   ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idFsa", idFluctuatingSituation);
        query.setParameter("notYetFinish", Constants.STATUS_FLUCTUATING_SITUATION_NOT_FINISH);
        query.executeUpdate();
    }

    @Override
    public StatisticFluctuatingSituation getStatisticFluctuatingSituation(Integer typeFluctuatingSituation) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) countNoyYetFinished  " +
                "from fluctuating_situation fs  " +
                "    inner join process pr on fs.id_process = pr.id_process  " +
                "    inner join document dc on pr.id_process = dc.id_process  " +
                "where dc.id_department_original in (:idsDepartmentOriginal)  " +
                "and fs.status = :statusNotYetFinished " +
                "and fs.type = :type ");
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsDepartmentOriginal", csvcUser.getIdsDepartmentCurrent());
        query.setParameter("statusNotYetFinished", Constants.STATUS_FLUCTUATING_SITUATION_NOT_FINISH);
        query.setParameter("type", typeFluctuatingSituation);
        Object result = query.getSingleResult();
        StatisticFluctuatingSituation situation = new StatisticFluctuatingSituation();
        if (result != null) {
            situation.setTotalNotYetFinish(ValueUtil.getIntegerByObject(result));
        }
        return situation;
    }

    private long countFindAllFluctuationSituation(FindAllFluctuatingSituationRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "from fluctuating_situation fs " +
                "    inner join process pr on fs.id_process = pr.id_process " +
                "    inner join document do on pr.id_process = do.id_process " +
                "    inner join csvc_user cu on do.id_user_created = cu.id_user " +
                "    inner join department de on do.id_department = de.id_department " +
                " where do.id_department_original in (:idsDepartmentOriginal) " +
                " and fs.type = :type  ");
        setConditionFindAllFluctuationSituation(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllFluctuationSituation(query, request);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    private void setParameterFindAllFluctuationSituation(Query query, FindAllFluctuatingSituationRequest request) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("type", request.getType());
        if (StringUtils.isNotBlank(request.getCodeDocument())){
            query.setParameter("keyword", request.getCodeDocument());
        }
        if (ObjectUtils.isNotEmpty(request.getStatusFluctuatingSituation())){
            query.setParameter("status", request.getStatusFluctuatingSituation());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            query.setParameter("idDepartment", request.getIdDepartment());
        }
    }

    private void setConditionFindAllFluctuationSituation(StringBuilder sb, FindAllFluctuatingSituationRequest request) {
        if (StringUtils.isNotBlank(request.getCodeDocument())){
            sb.append("  and (do.code REGEXP :keyword ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusFluctuatingSituation())){
            sb.append(" and fs.status = :status ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            sb.append(" and de.id_department = :idDepartment ");
        }
        sb.append(" order by fs.id_fluctuating_situation DESC ");
    }
}
