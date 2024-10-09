package com.example.csvccdshustbe.repository.province.impl;

import com.example.csvccdshustbe.dto.provinces.ProvincesDto;
import com.example.csvccdshustbe.repository.province.ProvinceRepositoryCustom;
import com.example.csvccdshustbe.request.province.FindAllProvinceRequest;
import com.example.csvccdshustbe.response.province.FindAllProvinceResponse;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProvinceRepositoryImpl implements ProvinceRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Page<FindAllProvinceResponse> findAllProvinceResponse(FindAllProvinceRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select pro.code, pro.name " +
                "from provinces pro " +
                "where 1 = 1 ");
        setConditionalProvince(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterProvince(request, query);
        PageUtils.buildQuery(pageable, query);
        List<FindAllProvinceResponse> responses = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                FindAllProvinceResponse response = new FindAllProvinceResponse();
                response.setCodeProvince(ValueUtil.getStringByObject(obj[0]));
                response.setNameProvince(ValueUtil.getStringByObject(obj[1]));
                responses.add(response);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllProvinceResponse(request));
    }

    @Override
    public List<ProvincesDto> findAllProvincesDto() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select code, name " +
                "from provinces ");
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> result = query.getResultList();
        List<ProvincesDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                ProvincesDto provincesDto = new ProvincesDto();
                provincesDto.setCodeProvince(ValueUtil.getStringByObject(obj[0]));
                provincesDto.setNameProvince(ValueUtil.getStringByObject(obj[1]));
                responses.add(provincesDto);
            }
        }
        return responses;
    }

    private long countFindAllProvinceResponse(FindAllProvinceRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) count " +
                "from provinces pro " +
                "where 1 = 1 ");
        setConditionalProvince(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterProvince(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterProvince(FindAllProvinceRequest request, Query query) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionalProvince(FindAllProvinceRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (pro.name REGEXP  :keyword ) ");
        }
    }
}
