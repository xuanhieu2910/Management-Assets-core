package com.example.csvccdshustbe.repository.state.impl;

import com.example.csvccdshustbe.dto.state.StateDetailsDto;
import com.example.csvccdshustbe.dto.state.StateLinkListDto;
import com.example.csvccdshustbe.entity.State;
import com.example.csvccdshustbe.repository.state.StateRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
    public Optional<StateLinkListDto> findStateByIdProcessAndStep(Integer idProcess, Integer stepState) {
        StringBuilder sb = new StringBuilder();
        sb.append("select st.id_state, st.id_type_state, st.code_type_state,   " +
                "       st.id_process, st.status, st.time_created, st.time_modified, " +
                "       st.step " +
                " from state st    " +
                "     inner join process pr on st.id_process = pr.id_process   " +
                " where pr.id_process = :idProcess   " +
                " and st.step = :stepCurrent " +
                "union " +
                "select st.id_state, st.id_type_state, st.code_type_state, " +
                "       st.id_process, st.status, st.time_created, st.time_modified, " +
                "       st.step " +
                "from state st " +
                "         inner join process pr on st.id_process = pr.id_process " +
                "where pr.id_process = :idProcess " +
                "  and st.step = :stepNext ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idProcess", idProcess);
        query.setParameter("stepCurrent", stepState + 1);
        query.setParameter("stepNext", stepState + 2);
        List<Object[]> result = query.getResultList();
        StateLinkListDto stateLinkListDto = new StateLinkListDto();
        if (!CollectionUtils.isEmpty(result)){
            int indexStateCurrent = 0;
            for (Object[] obj : result) {
                if (indexStateCurrent > 0){
                    stateLinkListDto.setStateNext(contructStateNext(obj));
                } else {
                    stateLinkListDto.setStateCurrent(contructStateCurrent(obj));
                }
                ++indexStateCurrent;
            }
            return Optional.of(stateLinkListDto);
        }
        return Optional.empty();
    }

    private State contructStateNext(Object[] obj) {
        if (obj != null){
            State state = new State();
            state.setIdState(ValueUtil.getIntegerByObject(obj[0]));
            state.setIdTypeState(ValueUtil.getIntegerByObject(obj[1]));
            state.setCodeTypeState(ValueUtil.getStringByObject(obj[2]));
            state.setIdProcess(ValueUtil.getIntegerByObject(obj[3]));
            state.setStatus(ValueUtil.getIntegerByObject(obj[4]));
            state.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
            state.setTimeModified(ValueUtil.getStringByObject(obj[6]));
            state.setStep(ValueUtil.getIntegerByObject(obj[7]));
            return state;
        }
        return null;
    }

    private State contructStateCurrent(Object[] obj) {
        if (obj != null) {
            State state = new State();
            state.setIdState(ValueUtil.getIntegerByObject(obj[0]));
            state.setIdTypeState(ValueUtil.getIntegerByObject(obj[1]));
            state.setCodeTypeState(ValueUtil.getStringByObject(obj[2]));
            state.setIdProcess(ValueUtil.getIntegerByObject(obj[3]));
            state.setStatus(ValueUtil.getIntegerByObject(obj[4]));
            state.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
            state.setTimeModified(ValueUtil.getStringByObject(obj[6]));
            state.setStep(ValueUtil.getIntegerByObject(obj[7]));
            return state;
        }
        return null;
    }

    @Override
    public Optional<StateDetailsDto> findStateDetailsByIdState(Integer idState, List<Integer> idsDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select st.id_state, st.status statusState, typeState.id_type_state,  " +
                "       typeState.code codeTypeState, typeState.name nameTypeState,  " +
                "       st.time_created, st.time_modified, pr.id_process, st.step  " +
                "from state st  " +
                "    inner join process pr on st.id_process = pr.id_process  " +
                "    inner join type_state typeState on st.id_type_state = typeState.id_type_state  " +
                "where st.id_state = :idState  " +
                "and id_department in (:idsDepartmentOriginal) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idState", idState);
        query.setParameter("idsDepartmentOriginal", idsDepartment);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                StateDetailsDto stateDetailsDto = new StateDetailsDto();
                stateDetailsDto.setIdState(ValueUtil.getIntegerByObject(obj[0]));
                stateDetailsDto.setStatusState(ValueUtil.getIntegerByObject(obj[1]));
                stateDetailsDto.setIdTypeState(ValueUtil.getIntegerByObject(obj[2]));
                stateDetailsDto.setCodeTypeState(ValueUtil.getStringByObject(obj[3]));
                stateDetailsDto.setNameTypeState(ValueUtil.getStringByObject(obj[4]));
                stateDetailsDto.setTimeCreated(ValueUtil.getLongByObject(obj[5]));
                stateDetailsDto.setTimeModified(ValueUtil.getLongByObject(obj[6]));
                stateDetailsDto.setIdProcess(ValueUtil.getIntegerByObject(obj[7]));
                stateDetailsDto.setStep(ValueUtil.getIntegerByObject(obj[8]));
                return Optional.of(stateDetailsDto);
            }
        }
        return Optional.empty();
    }
}
