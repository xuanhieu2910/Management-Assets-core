package com.example.csvccdshustbe.repository.wards.impl;

import com.example.csvccdshustbe.repository.wards.WardsRepositoryCustom;
import com.example.csvccdshustbe.request.wards.FindAllWardsRequest;
import com.example.csvccdshustbe.response.wards.FindAllWardsResponse;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class WardsRepositoryImpl implements WardsRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<FindAllWardsResponse> findAllWardsResponse(FindAllWardsRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select wa.code, wa.name,  " +
                "       dis.code codeDistricts " +
                "from wards wa " +
                "    inner join districts dis on dis.code = wa.district_code " +
                "where 1 = 1 " +
                "and dis.code = :districtsCode ");
        setConditionFindAllWards(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllWards(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllWardsResponse> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                FindAllWardsResponse response = new FindAllWardsResponse();
                response.setCodeWards(ValueUtil.getStringByObject(obj[0]));
                response.setNameWards(ValueUtil.getStringByObject(obj[1]));
                response.setCodeDistricts(ValueUtil.getStringByObject(obj[2]));
                responses.add(response);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllWardsResponse(request));
    }


    private long countFindAllWardsResponse(FindAllWardsRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) count " +
                "from wards wa " +
                "    inner join districts dis on dis.code = wa.district_code " +
                "where 1 = 1 " +
                "and dis.code = :districtsCode ");
        setConditionFindAllWards(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllWards(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllWards(FindAllWardsRequest request, Query query) {
        query.setParameter("districtsCode", request.getCodeDistricts());
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllWards(FindAllWardsRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (wa.name REGEXP  :keyword ) ");
        }
    }
}
