package com.example.csvccdshustbe.repository.state.impl;

import com.example.csvccdshustbe.entity.State;
import com.example.csvccdshustbe.repository.state.StateRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class StateRepositoryImpl implements StateRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<State> findStateByIdState(Integer idState) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select st.id_state, st.id_type_state, st.code_type_state,  " +
                "       st.id_process, st.status, st.time_created,  " +
                "       st.time_modified, st.step  " +
                "from state st  " +
                "where st.id_state = :idState ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idState", idState);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                State state = new State();
                state.setIdState(ValueUtil.getIntegerByObject(obj[0]));
                state.setIdTypeState(ValueUtil.getIntegerByObject(obj[1]));
                state.setCodeTypeState(ValueUtil.getStringByObject(obj[2]));
                state.setIdProcess(ValueUtil.getIntegerByObject(obj[3]));
                state.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                state.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                state.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                state.setStep(ValueUtil.getIntegerByObject(obj[7]));
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<State> findStateByIdProcessAndStepNext(Integer idProcess, Integer stepNext) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select st.id_state, st.id_type_state, st.code_type_state,  " +
                "       st.id_process, st.status, st.time_created, st.time_modified,  " +
                "       st.step  " +
                "from state st   " +
                "    inner join process pr on st.id_process = pr.id_process  " +
                "where pr.id_process = :idProcess  " +
                "and st.step = :stepNext   ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idProcess", idProcess);
        query.setParameter("stepNext", stepNext);
        List<Object[]> result = query.getResultList();
        if (CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                State state = new State();
                state.setIdState(ValueUtil.getIntegerByObject(obj[0]));
                state.setIdTypeState(ValueUtil.getIntegerByObject(obj[1]));
                state.setCodeTypeState(ValueUtil.getStringByObject(obj[2]));
                state.setIdProcess(ValueUtil.getIntegerByObject(obj[3]));
                state.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                state.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                state.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                state.setStep(ValueUtil.getIntegerByObject(obj[7]));
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
