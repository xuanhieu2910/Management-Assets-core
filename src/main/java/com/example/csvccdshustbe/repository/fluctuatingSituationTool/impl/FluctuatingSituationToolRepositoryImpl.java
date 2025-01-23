package com.example.csvccdshustbe.repository.fluctuatingSituationTool.impl;

import com.example.csvccdshustbe.repository.fluctuatingSituationTool.FluctuatingSituationToolRepositoryCustom;
import com.example.csvccdshustbe.response.fluctuatingSituationAsset.StatisticFluctuatingSituationAsset;
import com.example.csvccdshustbe.response.fluctuatingSituationTool.StatisticFluctuatingSituationTool;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

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
}
