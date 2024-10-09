package com.example.csvccdshustbe.repository.wards.impl;

import com.example.csvccdshustbe.dto.wards.WardsDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, List<WardsDto>> findAllWardsToDownload() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select districts.code,districts.name , wards.code, wards.name " +
                "from wards " +
                "    inner join districts on wards.district_code = districts.code ");
        Query query = entityManager.createNativeQuery(sb.toString());
        Map<String, List<WardsDto>> responses = new HashMap<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            String keyword;
            String codeDistrict;
            String nameDistrict;
            for (Object[] obj : result){
                codeDistrict = ValueUtil.getStringByObject(obj[0]);
                nameDistrict = ValueUtil.getStringByObject(obj[1]);
                keyword = "STT_" + codeDistrict + "_" + ValueUtil.convertToVietnamese(nameDistrict).
                        replaceAll(ValueUtil.REGEX_letter_digit_period_underscore, "");
                if (responses.containsKey(keyword)){
                    WardsDto wardsDto = new WardsDto();
                    wardsDto.setCodeWard(ValueUtil.getStringByObject(obj[2]));
                    wardsDto.setNameWard(ValueUtil.getStringByObject(obj[3]));
                    responses.get(keyword).add(wardsDto);
                } else {
                    List<WardsDto> dtos = new ArrayList<>();
                    WardsDto wardsDto = new WardsDto();
                    wardsDto.setCodeWard(ValueUtil.getStringByObject(obj[2]));
                    wardsDto.setNameWard(ValueUtil.getStringByObject(obj[3]));
                    dtos.add(wardsDto);
                    responses.put(keyword,dtos);
                }
            }
        }
        return responses;
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
        query.setParameter("districtsCode", request.getCodeDistrict());
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
