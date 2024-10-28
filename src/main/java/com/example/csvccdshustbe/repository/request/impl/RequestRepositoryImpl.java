package com.example.csvccdshustbe.repository.request.impl;

import com.example.csvccdshustbe.dto.request.RequestDetailsDto;
import com.example.csvccdshustbe.entity.Request;
import com.example.csvccdshustbe.repository.request.RequestRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RequestRepositoryImpl implements RequestRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Request> findRequestByIdRequest(Integer idRequest) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select re.id_request, re.id_process, re.id_state, " +
                "       re.name, re.description, re.status, re.time_created, " +
                "       re.time_modified " +
                "from request re " +
                "where re.id_request = :idRequest ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idRequest", idRequest);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Request request = new Request();
                request.setIdRequest(ValueUtil.getIntegerByObject(obj[0]));
                request.setIdProcess(ValueUtil.getIntegerByObject(obj[1]));
                request.setIdState(ValueUtil.getIntegerByObject(obj[2]));
                request.setName(ValueUtil.getStringByObject(obj[3]));
                request.setDescription(ValueUtil.getStringByObject(obj[4]));
                request.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                request.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                request.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                return Optional.of(request);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Request> findAllRequestByIdState(Integer idState) {
        StringBuilder sb = new StringBuilder();
        sb.append("select re.id_request, re.id_process, re.id_state,  " +
                "         re.name, re.description, re.status,  " +
                "         re.time_created, re.time_modified  " +
                "  from request re  " +
                "        inner join state st on re.id_state = st.id_state " +
                "  where st.id_state = :idState ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idState", idState);
        List<Object[]> result = query.getResultList();
        List<Request> requests = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Request request = new Request();
                request.setIdRequest(ValueUtil.getIntegerByObject(obj[0]));
                request.setIdProcess(ValueUtil.getIntegerByObject(obj[1]));
                request.setIdState(ValueUtil.getIntegerByObject(obj[2]));
                request.setName(ValueUtil.getStringByObject(obj[3]));
                request.setDescription(ValueUtil.getStringByObject(obj[4]));
                request.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                request.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                request.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                requests.add(request);
            }
        }
        return requests;
    }

    @Override
    public List<RequestDetailsDto> findRequestDetailsByIdState(Integer idState) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select re.id_request, re.id_process, re.id_state,  " +
                "       re.name, re.description, re.status,  " +
                "       re.time_created, re.time_modified  " +
                "from request re  " +
                "    inner join state st on re.id_state = st.id_state  " +
                "where st.id_state = :idState ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idState", idState);
        List<Object[]> result = query.getResultList();
        List<RequestDetailsDto> requests = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                RequestDetailsDto request = new RequestDetailsDto();
                request.setIdRequest(ValueUtil.getIntegerByObject(obj[0]));
                request.setIdProcess(ValueUtil.getIntegerByObject(obj[1]));
                request.setIdState(ValueUtil.getIntegerByObject(obj[2]));
                request.setName(ValueUtil.getStringByObject(obj[3]));
                request.setDescription(ValueUtil.getStringByObject(obj[4]));
                request.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                request.setTimeCreated(ValueUtil.getLongByObject(obj[6]));
                request.setTimeModified(ValueUtil.getLongByObject(obj[7]));
                requests.add(request);
            }
        }
        return requests;
    }
}
