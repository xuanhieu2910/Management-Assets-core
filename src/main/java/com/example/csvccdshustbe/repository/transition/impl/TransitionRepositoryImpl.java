package com.example.csvccdshustbe.repository.transition.impl;

import com.example.csvccdshustbe.entity.Transition;
import com.example.csvccdshustbe.repository.transition.TransitionRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class TransitionRepositoryImpl implements TransitionRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Transition> findTransitionByIdProcess(Integer idProcess) {
        StringBuilder sb = new StringBuilder();
        sb.append("select tr.id_transition, tr.id_process, " +
                "           tr.id_state_current, tr.id_state_next " +
                "from transition tr " +
                "    inner join process pr on tr.id_process = pr.id_process " +
                "where pr.id_process = :idProcess ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idProcess", idProcess);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Transition transition = new Transition();
                transition.setIdTransition(ValueUtil.getIntegerByObject(obj[0]));
                transition.setIdProcess(ValueUtil.getIntegerByObject(obj[1]));
                transition.setIdStateCurrent(ValueUtil.getIntegerByObject(obj[2]));
                transition.setIdStateNext(ValueUtil.getIntegerByObject(obj[3]));
                return Optional.of(transition);
            }
        }
        return Optional.empty();
    }
}
