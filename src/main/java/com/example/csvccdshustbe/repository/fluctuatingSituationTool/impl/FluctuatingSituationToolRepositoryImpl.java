package com.example.csvccdshustbe.repository.fluctuatingSituationTool.impl;

import com.example.csvccdshustbe.entity.FluctuatingSituationAsset;
import com.example.csvccdshustbe.entity.FluctuatingSituationTool;
import com.example.csvccdshustbe.repository.fluctuatingSituationTool.FluctuatingSituationToolRepositoryCustom;
import com.example.csvccdshustbe.request.fluctuatingSituationTool.FindAllFluctuatingSituationToolRequest;
import com.example.csvccdshustbe.response.fluctuatingSituationAsset.StatisticFluctuatingSituationAsset;
import com.example.csvccdshustbe.response.fluctuatingSituationTool.FindAllFluctuatingSituationToolResponses;
import com.example.csvccdshustbe.response.fluctuatingSituationTool.StatisticFluctuatingSituationTool;
import com.example.csvccdshustbe.utility.Constants;
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

public class FluctuatingSituationToolRepositoryImpl implements FluctuatingSituationToolRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public StatisticFluctuatingSituationTool getStatisticFluctuatingSituationTool(Integer idFluctuatingSituation) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select sum(totalDeclare) as totalDeclare,   " +
                "         sum(totalIncrease) as totalIncrease,   " +
                "         sum(totalDecrease) as totalDecrease   " +
                "  from (select count(0) totalDeclare, 0 totalIncrease, 0 totalDecrease   " +
                "  from fluctuating_situation_tool   " +
                "  where fluctuating_situation_tool.id_fluctuating_situation = :idFluctuatingSituation   " +
                "    and fluctuating_situation_tool.type = :typeDeclare   " +
                "    and fluctuating_situation_tool.status = :statusNotYetFinish   " +
                "  union all    " +
                "  select 0 totalDeclare, count(0) totalIncrease, 0 totalDecrease   " +
                "  from fluctuating_situation_tool   " +
                "  where fluctuating_situation_tool.id_fluctuating_situation = :idFluctuatingSituation   " +
                "      and fluctuating_situation_tool.type = :typeIncrease   " +
                "      and fluctuating_situation_tool.status = :statusNotYetFinish   " +
                "  union all   " +
                "  select 0 totalDeclare, 0 totalIncrease, count(0) totalDecrease   " +
                "  from fluctuating_situation_tool   " +
                "  where fluctuating_situation_tool.id_fluctuating_situation = :idFluctuatingSituation   " +
                "    and fluctuating_situation_tool.type = :typeDecrease   " +
                "    and fluctuating_situation_tool.status = :statusNotYetFinish) result ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("statusNotYetFinish", Constants.STATUS_FLUCTUATING_SITUATION_TOOL_NOT_FINISH);
        query.setParameter("idFluctuatingSituation", idFluctuatingSituation);
        query.setParameter("typeDeclare", Constants.TYPE_FLUCTUATING_SITUATION_DECLARE);
        query.setParameter("typeIncrease", Constants.TYPE_FLUCTUATING_SITUATION_INCREASE);
        query.setParameter("typeDecrease", Constants.TYPE_FLUCTUATING_SITUATION_DECREASE);
        StatisticFluctuatingSituationTool situation = new StatisticFluctuatingSituationTool();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                situation.setTotalDeclare(ValueUtil.getIntegerByObject(obj[0]));
                situation.setTotalIncrease(ValueUtil.getIntegerByObject(obj[1]));
                situation.setTotalDecrease(ValueUtil.getIntegerByObject(obj[2]));
            }
        }
        return situation;
    }

    @Override
    public Page<FindAllFluctuatingSituationToolResponses>
    findAllFluctuatingSituationTool(FindAllFluctuatingSituationToolRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select fst.id_fluctuating_situation_tool, " +
                "       tl.id_tool, " +
                "       tp.value, " +
                "       tl.name, " +
                "       fst.status, " +
                "       fst.type, " +
                "       tl.salt," +
                "       parent_tool.salt AS parent_salt " +
                "from fluctuating_situation_tool fst " +
                "         inner join fluctuating_situation fs on fst.id_fluctuating_situation = fs.id_fluctuating_situation " +
                "         inner join process pr on fst.id_process = pr.id_process " +
                "         inner join tool tl on fst.id_tool = tl.id_tool " +
                "         inner join tool_process tp on tl.id_tool = tp.id_tool and fs.id_process = tp.id_process " +
                "         LEFT JOIN tool parent_tool ON tl.parent = parent_tool.id_tool " +
                "where fs.id_fluctuating_situation = :idFluctuatingSituation ");
        setConditionFindAllFluctuatingSituationTool(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllFluctuatingSituationTool(request, query);
        List<Object[]> result = query.getResultList();
        List<FindAllFluctuatingSituationToolResponses> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                FindAllFluctuatingSituationToolResponses response = new FindAllFluctuatingSituationToolResponses();
                response.setIdFluctuatingSituationTool(ValueUtil.getIntegerByObject(obj[0]));
                response.setIdTool(ValueUtil.getIntegerByObject(obj[1]));
                response.setValue(ValueUtil.getStringByObject(obj[2]));
                response.setNameTool(ValueUtil.getStringByObject(obj[3]));
                response.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                response.setTypeFluctuatingSituation(ValueUtil.getIntegerByObject(obj[5]));
                response.setSalt(ValueUtil.getStringByObject(obj[6]));
                response.setSaltParent(ValueUtil.getStringByObject(obj[7]));
                responses.add(response);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllFluctuatingSituationTool(request));
    }

    private long countFindAllFluctuatingSituationTool(FindAllFluctuatingSituationToolRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) count " +
                "from fluctuating_situation_tool fst " +
                "         inner join fluctuating_situation fs on fst.id_fluctuating_situation = fs.id_fluctuating_situation " +
                "         inner join process pr on fst.id_process = pr.id_process " +
                "         inner join tool tl on fst.id_tool = tl.id_tool " +
                "         inner join tool_process tp on tl.id_tool = tp.id_tool and fs.id_process = tp.id_process " +
                "where fs.id_fluctuating_situation = :idFluctuatingSituation ");
        setConditionFindAllFluctuatingSituationTool(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllFluctuatingSituationTool(request, query);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    @Override
    public List<FluctuatingSituationTool> findFluctuatingSituationToolByIds(List<Integer> idsFluctuatingSituationTool) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_fluctuating_situation_tool, " +
                "       id_tool, " +
                "       id_process, " +
                "       status, " +
                "       type, " +
                "       time_created, " +
                "       time_modified, " +
                "       id_user_modified, " +
                "       id_fluctuating_situation " +
                "from fluctuating_situation_tool " +
                "where id_fluctuating_situation_tool in (:ids) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("ids", idsFluctuatingSituationTool);
        List<FluctuatingSituationTool> response = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                FluctuatingSituationTool fluctuatingSituationTool = new FluctuatingSituationTool();
                fluctuatingSituationTool.setIdFluctuatingSituationTool(ValueUtil.getIntegerByObject(obj[0]));
                fluctuatingSituationTool.setIdTool(ValueUtil.getIntegerByObject(obj[1]));
                fluctuatingSituationTool.setIdProcess(ValueUtil.getIntegerByObject(obj[2]));
                fluctuatingSituationTool.setStatus(ValueUtil.getIntegerByObject(obj[3]));
                fluctuatingSituationTool.setType(ValueUtil.getIntegerByObject(obj[4]));
                fluctuatingSituationTool.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                fluctuatingSituationTool.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                fluctuatingSituationTool.setIdUserModified(ValueUtil.getIntegerByObject(obj[7]));
                fluctuatingSituationTool.setIdFluctuatingSituation(ValueUtil.getIntegerByObject(obj[8]));
                response.add(fluctuatingSituationTool);
            }
        }
        return response;
    }

    @Override
    public List<FluctuatingSituationTool> findFluctuatingSituationToolByIdFlu(Integer idFluctuatingSituation) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_fluctuating_situation_tool, " +
                "       id_tool, " +
                "       id_process, " +
                "       status, " +
                "       type, " +
                "       time_created, " +
                "       time_modified, " +
                "       id_user_modified, " +
                "       id_fluctuating_situation " +
                "from fluctuating_situation_tool " +
                "where id_fluctuating_situation = :idFluctuatingSituation ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idFluctuatingSituation", idFluctuatingSituation);
        List<FluctuatingSituationTool> response = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                FluctuatingSituationTool fluctuatingSituationTool = new FluctuatingSituationTool();
                fluctuatingSituationTool.setIdFluctuatingSituationTool(ValueUtil.getIntegerByObject(obj[0]));
                fluctuatingSituationTool.setIdTool(ValueUtil.getIntegerByObject(obj[1]));
                fluctuatingSituationTool.setIdProcess(ValueUtil.getIntegerByObject(obj[2]));
                fluctuatingSituationTool.setStatus(ValueUtil.getIntegerByObject(obj[3]));
                fluctuatingSituationTool.setType(ValueUtil.getIntegerByObject(obj[4]));
                fluctuatingSituationTool.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                fluctuatingSituationTool.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                fluctuatingSituationTool.setIdUserModified(ValueUtil.getIntegerByObject(obj[7]));
                fluctuatingSituationTool.setIdFluctuatingSituation(ValueUtil.getIntegerByObject(obj[8]));
                response.add(fluctuatingSituationTool);
            }
        }
        return response;
    }

    private void setParameterFindAllFluctuatingSituationTool(FindAllFluctuatingSituationToolRequest request, Query query) {
        query.setParameter("idFluctuatingSituation", request.getIdFluctuatingSituation());
        if (StringUtils.isNotBlank(request.getNameTool())) {
            query.setParameter("nameTool", request.getNameTool());
        }
        if (ObjectUtils.isNotEmpty(request.getTypeFluctuatingSituation())){
            query.setParameter("type", request.getTypeFluctuatingSituation());
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())) {
            query.setParameter("status", request.getStatus());
        }
    }

    private void setConditionFindAllFluctuatingSituationTool(FindAllFluctuatingSituationToolRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getNameTool())) {
            sb.append(" and (tl.name REGEXP :nameTool ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getTypeFluctuatingSituation())){
            sb.append(" and fst.type = :type ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())) {
            sb.append(" and fst.status = :status ");
        }
        sb.append(" order by fst.id_fluctuating_situation_tool DESC ");
    }
}
