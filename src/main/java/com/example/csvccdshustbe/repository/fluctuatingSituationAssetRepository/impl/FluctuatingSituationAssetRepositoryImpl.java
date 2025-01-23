package com.example.csvccdshustbe.repository.fluctuatingSituationAssetRepository.impl;

import com.example.csvccdshustbe.entity.FluctuatingSituationAsset;
import com.example.csvccdshustbe.repository.fluctuatingSituationAssetRepository.FluctuatingSituationAssetRepositoryCustom;
import com.example.csvccdshustbe.request.fluctuatingSituationAsset.FindAllFluctuatingSituationAssetRequest;
import com.example.csvccdshustbe.response.fluctuatingSituationAsset.FindAllFluctuatingSituationAssetResponses;
import com.example.csvccdshustbe.response.fluctuatingSituationAsset.StatisticFluctuatingSituationAsset;
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

public class FluctuatingSituationAssetRepositoryImpl implements FluctuatingSituationAssetRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<FindAllFluctuatingSituationAssetResponses> findAllFluctuatingSituationAssetByIdFluctuatingSituation(Pageable pageable,
                                                                                            FindAllFluctuatingSituationAssetRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select fsa.id_fluctuating_situation_asset,  " +
                "       fsa.id_asset,  " +
                "       ap.value,   " +
                "       ast.name,  " +
                "       fsa.status,   " +
                "       fsa.type,  " +
                "       ast.salt  " +
                "from fluctuating_situation_asset fsa  " +
                "         inner join fluctuating_situation fs on fsa.id_fluctuating_situation = fs.id_fluctuating_situation  " +
                "         inner join process pr on fsa.id_process = pr.id_process  " +
                "         inner join asset ast on fsa.id_asset = ast.id_asset  " +
                "         inner join asset_process ap on ast.id_asset = ap.id_asset and fs.id_process = ap.id_process " +
                "where fs.id_fluctuating_situation = :idFluctuatingSituation ");
        setConditionFindFluctuatingSituationAsset(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindFluctuatingSituationAsset(query, request);
        PageUtils.buildQuery(pageable, query);
        List<FindAllFluctuatingSituationAssetResponses> responses = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                FindAllFluctuatingSituationAssetResponses response = new FindAllFluctuatingSituationAssetResponses();
                response.setIdFluctuatingSituationAsset(ValueUtil.getIntegerByObject(obj[0]));
                response.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                response.setValue(ValueUtil.getStringByObject(obj[2]));
                response.setNameAsset(ValueUtil.getStringByObject(obj[3]));
                response.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                response.setTypeFluctuatingSituation(ValueUtil.getIntegerByObject(obj[5]));
                response.setSalt(ValueUtil.getStringByObject(obj[6]));
                responses.add(response);
            }
        }
        return new PageImpl<>(responses, pageable, countFindFluctuatingSituationAsset(request));
    }

    @Override
    public List<FluctuatingSituationAsset> findFluctuatingSituationAssetByIds(List<Integer> idsFluctuatingSituationAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_fluctuating_situation_asset, " +
                "       id_asset, " +
                "       id_process, " +
                "       status, " +
                "       type, " +
                "       time_created, " +
                "       time_modified, " +
                "       id_user_modified, " +
                "       id_fluctuating_situation " +
                "from fluctuating_situation_asset " +
                "where id_fluctuating_situation_asset in (:ids) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("ids", idsFluctuatingSituationAsset);
        List<FluctuatingSituationAsset> response = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                FluctuatingSituationAsset fluctuatingSituationAsset = new FluctuatingSituationAsset();
                fluctuatingSituationAsset.setIdFluctuatingSituationAsset(ValueUtil.getIntegerByObject(obj[0]));
                fluctuatingSituationAsset.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                fluctuatingSituationAsset.setIdProcess(ValueUtil.getIntegerByObject(obj[2]));
                fluctuatingSituationAsset.setStatus(ValueUtil.getIntegerByObject(obj[3]));
                fluctuatingSituationAsset.setType(ValueUtil.getIntegerByObject(obj[4]));
                fluctuatingSituationAsset.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                fluctuatingSituationAsset.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                fluctuatingSituationAsset.setIdUserModified(ValueUtil.getIntegerByObject(obj[7]));
                fluctuatingSituationAsset.setIdFluctuatingSituation(ValueUtil.getIntegerByObject(obj[8]));
                response.add(fluctuatingSituationAsset);
            }
        }
        return response;
    }

    @Override
    public StatisticFluctuatingSituationAsset getStatisticFluctuatingSituationAsset(Integer idFluctuatingSituation) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select sum(totalDeclare) as totalDeclare,  " +
                "       sum(totalIncrease) as totalIncrease,  " +
                "       sum(totalDecrease) as totalDecrease  " +
                "from (select count(0) totalDeclare, 0 totalIncrease, 0 totalDecrease  " +
                "from fluctuating_situation_asset  " +
                "where fluctuating_situation_asset.id_fluctuating_situation = :idFluctuatingSituation  " +
                "  and fluctuating_situation_asset.type = :typeDeclare  " +
                "  and fluctuating_situation_asset.status = :statusNotYetFinish  " +
                "union all  " +
                "select 0 totalDeclare, count(0) totalIncrease, 0 totalDecrease  " +
                "from fluctuating_situation_asset  " +
                "where fluctuating_situation_asset.id_fluctuating_situation = :idFluctuatingSituation  " +
                "    and fluctuating_situation_asset.type = :typeIncrease  " +
                "    and fluctuating_situation_asset.status = :statusNotYetFinish  " +
                "union all  " +
                "select 0 totalDeclare, 0 totalIncrease, count(0) totalDecrease  " +
                "from fluctuating_situation_asset  " +
                "where fluctuating_situation_asset.id_fluctuating_situation = :idFluctuatingSituation  " +
                "  and fluctuating_situation_asset.type = :typeDecrease  " +
                "  and fluctuating_situation_asset.status = :statusNotYetFinish) result ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("statusNotYetFinish", Constants.STATUS_FLUCTUATING_SITUATION_NOT_FINISH);
        query.setParameter("idFluctuatingSituation", idFluctuatingSituation);
        query.setParameter("typeDeclare", Constants.TYPE_FLUCTUATING_SITUATION_DECLARE);
        query.setParameter("typeIncrease", Constants.TYPE_FLUCTUATING_SITUATION_INCREASE);
        query.setParameter("typeDecrease", Constants.TYPE_FLUCTUATING_SITUATION_DECREASE);
        StatisticFluctuatingSituationAsset situation = new StatisticFluctuatingSituationAsset();
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

    private long countFindFluctuatingSituationAsset(FindAllFluctuatingSituationAssetRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "from fluctuating_situation_asset fsa  " +
                "         inner join fluctuating_situation fs on fsa.id_fluctuating_situation = fs.id_fluctuating_situation  " +
                "         inner join process pr on fsa.id_process = pr.id_process  " +
                "         inner join asset ast on fsa.id_asset = ast.id_asset  " +
                "         inner join asset_process ap on ast.id_asset = ap.id_asset  " +
                "where fs.id_fluctuating_situation = :idFluctuatingSituation ");
        setConditionFindFluctuatingSituationAsset(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindFluctuatingSituationAsset(query, request);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    private void setParameterFindFluctuatingSituationAsset(Query query, FindAllFluctuatingSituationAssetRequest request) {
        query.setParameter("idFluctuatingSituation", request.getIdFluctuatingSituation());
        if (StringUtils.isNotBlank(request.getNameAsset())){
            query.setParameter("nameAsset", request.getNameAsset());
        }
        if (ObjectUtils.isNotEmpty(request.getTypeFluctuatingSituation())){
            query.setParameter("type", request.getTypeFluctuatingSituation());
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())) {
            query.setParameter("status", request.getStatus());
        }
    }

    private void setConditionFindFluctuatingSituationAsset(StringBuilder sb,
                                                           FindAllFluctuatingSituationAssetRequest request) {
        if (StringUtils.isNotBlank(request.getNameAsset())){
            sb.append(" and  (ast.name REGEXP :nameAsset) ");
        }
        if (ObjectUtils.isNotEmpty(request.getTypeFluctuatingSituation())){
            sb.append(" and fsa.type = :type ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())) {
            sb.append(" and fsa.status = :status ");
        }
        sb.append(" order by fsa.id_fluctuating_situation_asset DESC  ");
    }
}
