package com.example.csvccdshustbe.repository.requestData.impl;

import com.example.csvccdshustbe.dto.requestData.RequestDataDetailsDto;
import com.example.csvccdshustbe.repository.requestData.RequestDataRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class RequestDataRepositoryImpl implements RequestDataRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<RequestDataDetailsDto> findRequestDataDetailsByIdRequest(Integer idRequest) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select rd.id_request_data, rd.id_request,  " +
                "       rd.name, rd.value, rd.status,  " +
                "       rd.time_created, rd.time_modified  " +
                "from request_data rd  " +
                "    inner join request re on rd.id_request = re.id_request  " +
                "where re.id_request = :idRequest ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idRequest", idRequest);
        List<RequestDataDetailsDto> response = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                RequestDataDetailsDto dataDetailsDto = new RequestDataDetailsDto();
                dataDetailsDto.setIdRequestData(ValueUtil.getIntegerByObject(obj[0]));
                dataDetailsDto.setIdRequest(ValueUtil.getIntegerByObject(obj[1]));
                dataDetailsDto.setName(ValueUtil.getStringByObject(obj[2]));
                dataDetailsDto.setValue(ValueUtil.getStringByObject(obj[3]));
                dataDetailsDto.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                dataDetailsDto.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                dataDetailsDto.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                response.add(dataDetailsDto);
            }
        }
        return response;
    }
}
