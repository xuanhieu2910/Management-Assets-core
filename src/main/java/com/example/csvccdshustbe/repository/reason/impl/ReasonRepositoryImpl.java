package com.example.csvccdshustbe.repository.reason.impl;

import com.example.csvccdshustbe.entity.Reason;
import com.example.csvccdshustbe.repository.reason.ReasonRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class ReasonRepositoryImpl implements ReasonRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Reason> findReasonByIdReasonAndStatus(Integer idReason, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select re.id_reason, re.name, re.type_reason,   " +
                "       re.time_created, re.time_modified, re.status   " +
                "from reason re   " +
                "where re.id_reason = :idReason and re.status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idReason", idReason);
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Reason reason = new Reason();
                reason.setIdReason(ValueUtil.getIntegerByObject(obj[0]));
                reason.setName(ValueUtil.getStringByObject(obj[1]));
                reason.setTypeReason(ValueUtil.getIntegerByObject(obj[2]));
                reason.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                reason.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                reason.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                return Optional.of(reason);
            }
        }
        return Optional.empty();
    }
}
